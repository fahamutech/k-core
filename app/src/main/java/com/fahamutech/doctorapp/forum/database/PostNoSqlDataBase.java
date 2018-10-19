package com.fahamutech.doctorapp.forum.database;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fahamutech.doctorapp.forum.adapter.MyChatFragmentAdapter;
import com.fahamutech.doctorapp.forum.model.ChatTopic;
import com.fahamutech.doctorapp.forum.model.OnlineStatus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.List;
import java.util.Objects;

public class PostNoSqlDataBase extends NoSqlDatabase implements PostDataSource {

    public PostNoSqlDataBase(Context context) {
        super(context);
    }

    @Override
    public void createChatTopic(ChatTopic chatTopic, DataBaseCallback... dataBaseCallbacks) {
        DocumentReference forum_posts = firestore.collection(ForumC.FORUMS.name()).document();
        chatTopic.setDocId(forum_posts.getId());
        forum_posts.set(chatTopic, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    dataBaseCallbacks[0].then(chatTopic.getDocId());
                })
                .addOnFailureListener(e -> {
                    dataBaseCallbacks[1].then(chatTopic.getDocId());
                    Log.e(TAG, "failed : " + e);
                });
    }

    @Override
    public ListenerRegistration getMyChatPost(String userId, RecyclerView recyclerView, Context context) {

        //return the listener
        return firestore.collection(ForumC.FORUMS.name())
                .whereEqualTo("userId", userId)
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    if (queryDocumentSnapshots != null) {
                        List<ChatTopic> chatTopics = queryDocumentSnapshots.toObjects(ChatTopic.class);
                        MyChatFragmentAdapter adapter = new MyChatFragmentAdapter(chatTopics, context);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    if (e != null) {
                        Log.e(TAG, "error getting the post, cause : " + e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void deletePost(String docId) {
        DocumentReference document = firestore.collection(ForumC.FORUMS.name()).document(docId);
        document.delete()
                .addOnSuccessListener(aVoid -> Log.e(TAG, "delete : " + docId))
                .addOnFailureListener(e -> Log.e(TAG, "not deleted, cause : " + e));
    }

    public void online(String userId) {
        if (isNetworkConnected()) {
            try {
                firestore.collection(ForumC.FORUM_ONLINE.name())
                        .document(userId)
                        .set(new OnlineStatus("online"), SetOptions.merge());
            } catch (Throwable throwable) {
                Log.e(TAG, throwable.getLocalizedMessage());
            }
        } else {
            offline(userId);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return Objects.requireNonNull(cm).getActiveNetworkInfo() != null;
    }

    public void offline(String userId) {
        if (userId != null) {
            firestore.collection(ForumC.FORUM_ONLINE.name())
                    .document(userId)
                    .set(new OnlineStatus("offline"), SetOptions.merge());
        }
    }

}
