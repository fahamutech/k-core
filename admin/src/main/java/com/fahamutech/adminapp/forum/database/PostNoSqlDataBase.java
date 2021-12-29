package com.fahamutech.adminapp.forum.database;

import android.content.Context;
import android.net.ConnectivityManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.fahamutech.adminapp.forum.adapter.AllChatFragmentAdapter;
import com.fahamutech.adminapp.forum.model.ChatTopic;
import com.fahamutech.adminapp.forum.model.Doctor;
import com.fahamutech.adminapp.forum.model.DoctorOnlineStatus;
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


    /**
     * delete the post of a specific user
     *
     * @param docId the id of the document to delete
     */
    @Override
    public void deletePost(String docId) {
        DocumentReference document = firestore.collection(ForumC.FORUMS.name()).document(docId);
        document.delete()
                .addOnSuccessListener(aVoid -> Log.e(TAG, "delete : " + docId))
                .addOnFailureListener(e -> Log.e(TAG, "not deleted, cause : " + e));
    }

    /**
     * get all the topic available in the database
     *
     * @param recyclerView the list view for the topics
     * @param context      the activity to show the view
     */
    @Override
    public ListenerRegistration getAllPosts(RecyclerView recyclerView, Context context) {

        return firestore.collection(ForumC.FORUMS.name())
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    if (queryDocumentSnapshots != null) {
                        List<ChatTopic> chatTopics = queryDocumentSnapshots.toObjects(ChatTopic.class);

                        RecyclerView.Adapter adapter;
                        adapter = new AllChatFragmentAdapter(chatTopics, context);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    if (e != null) {
                        Log.e("POSTNOSQL", "Problem get posts,reason : " + e);
                    }
                });
    }


    public void online(String userId) {
        if (isNetworkConnected()) {
            try {
                firestore.collection(ForumC.FORUM_ONLINE.name())
                        .document(userId)
                        .set(new DoctorOnlineStatus("online", Doctor.DOCTOR), SetOptions.merge());
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
                    .set(new DoctorOnlineStatus("offline",Doctor.DOCTOR), SetOptions.merge());
        }
    }


}
