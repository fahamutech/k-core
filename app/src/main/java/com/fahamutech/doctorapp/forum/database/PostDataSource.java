package com.fahamutech.doctorapp.forum.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.fahamutech.doctorapp.forum.model.ChatTopic;

public interface PostDataSource {
    Object getMyChatPost(String id, RecyclerView recyclerView, Context context);

    void deletePost(String docId);

    void createChatTopic(ChatTopic chatTopic, DataBaseCallback... dataBaseCallbacks);
}
