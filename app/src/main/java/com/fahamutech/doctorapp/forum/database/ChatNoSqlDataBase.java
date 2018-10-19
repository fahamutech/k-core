package com.fahamutech.doctorapp.forum.database;

import android.content.Context;
import android.util.Log;

import com.fahamutech.doctorapp.forum.model.ChatMessages;
import com.fahamutech.doctorapp.forum.model.OnlineStatus;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatNoSqlDataBase extends NoSqlDatabase implements ChatDataSource {

    public ChatNoSqlDataBase(Context context) {
        super(context);
    }

    @Override
    public void sendMessage(String docId, ChatMessages chatMessages, DataBaseCallback... dataBaseCallbacks) {
        firestore.collection(ForumC.FORUMS.name())
                .document(docId)
                .collection(ForumC.FORUM_MESSAGE.name())
                .document()
                .set(chatMessages, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {

                    JSONObject android = new JSONObject();
                    JSONObject message = new JSONObject();
                    JSONObject data = new JSONObject();
                    JSONObject notification = new JSONObject();

                    try {
                        data.put("message", chatMessages.getMessage().trim());
                        data.put("senderId", chatMessages.getSenderId().trim());
                        data.put("type", chatMessages.getType());
                        notification.put("title", "Kemifra");
                        notification.put("body", chatMessages.getMessage().trim());
                        android.put("priority", "high");
                        android.put("data", data);
                        android.put("notification", notification);
                        message.put("android", android);
                        message.put("topic", docId);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dataBaseCallbacks[0].then(message);

                })
                .addOnFailureListener(e -> {
                    dataBaseCallbacks[1].then("failed");
                    Log.e(TAG, "Failed to send message");
                });

        //update the thread

    }

    @Override
    public void updateDoctorSeen(String docId, boolean flag, DataBaseCallback... dataBaseCallbacks) {
        Map<String, Object> data = new HashMap<>();
        data.put("doctorSeen", flag);
        data.put("userSeen", !flag);
        firestore.collection(ForumC.FORUMS.name())
                .document(docId)
                .update(data)
                .addOnSuccessListener(aVoid ->
                        dataBaseCallbacks[0].then("done update doctor flag"))
                .addOnFailureListener(e ->
                        dataBaseCallbacks[1].then("failed to update doctor flag"));
    }

    @Override
    public void updateUserSeen(String docId, boolean flag, DataBaseCallback... dataBaseCallbacks) {
        Map<String, Object> data = new HashMap<>();
        data.put("doctorSeen", !flag);
        data.put("userSeen", flag);
        firestore.collection(ForumC.FORUMS.name())
                .document(docId)
                .update(data)
                .addOnSuccessListener(aVoid ->
                        dataBaseCallbacks[0].then("done update user flag"))
                .addOnFailureListener(e ->
                        dataBaseCallbacks[1].then("failed to update user flag"));
    }

    @Override
    public void updateThreadTimestamp(String docId, String time, DataBaseCallback... dataBaseCallbacks) {
        firestore.collection(ForumC.FORUMS.name()).document(docId)
                .update("time", time)
                .addOnSuccessListener(aVoid ->
                        dataBaseCallbacks[0].then("Done update : " + docId))
                .addOnFailureListener(e ->
                        dataBaseCallbacks[1]
                                .then("Failed to update : " + e.getLocalizedMessage()));
    }

    @Override
    public void getAllMessage(String docId, DataBaseCallback... dataBaseCallbacks) {
        firestore.collection(ForumC.FORUMS.name()).document(docId)
                .collection(ForumC.FORUM_MESSAGE.name())
                .orderBy("time", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null) {
                        List<ChatMessages> chatMessages = queryDocumentSnapshots.toObjects(ChatMessages.class);
                        dataBaseCallbacks[0].then(chatMessages);
                    }
                })
                .addOnFailureListener(e -> dataBaseCallbacks[1].then(e));
    }

    /**
     * first callback is the changed document and the second callback is for all document
     */
    @Override
    public Object receiveMessage(String docId, DataBaseCallback... dataBaseCallbacks) {
        return firestore.collection(ForumC.FORUMS.name()).document(docId)
                .collection(ForumC.FORUM_MESSAGE.name())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (queryDocumentSnapshots != null) {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();
                        for (DocumentChange doc : documentChanges) {
                            if (doc.getType() == DocumentChange.Type.ADDED)
                                dataBaseCallbacks[0].then(doc);
                        }
                    }
                });
    }

    @Override
    public Object onlineStatus(String userId, DataBaseCallback... dataBaseCallbacks) {
        return firestore.collection(ForumC.FORUM_ONLINE.name())
                .whereEqualTo("flag", "doctor")
                .limit(1)
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (documentSnapshot != null) {
                        List<DocumentChange> documentChanges = documentSnapshot.getDocumentChanges();
                        if (documentChanges.size() == 1) {
                            String online = documentChanges.get(0).getDocument().getString("online");
                            OnlineStatus onlineStatus = new OnlineStatus(online);
                            dataBaseCallbacks[0].then(onlineStatus);
                        }
                    }
                });
    }
}
