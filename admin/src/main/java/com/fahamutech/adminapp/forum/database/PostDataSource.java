package com.fahamutech.adminapp.forum.database;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

public interface PostDataSource {

    void deletePost(String docId);


    Object getAllPosts(RecyclerView recyclerView, Context context);
}
