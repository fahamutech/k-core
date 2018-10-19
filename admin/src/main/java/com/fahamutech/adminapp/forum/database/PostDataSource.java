package com.fahamutech.adminapp.forum.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.fahamutech.adminapp.forum.model.ChatTopic;

public interface PostDataSource {

    void deletePost(String docId);


    Object getAllPosts(RecyclerView recyclerView, Context context);
}
