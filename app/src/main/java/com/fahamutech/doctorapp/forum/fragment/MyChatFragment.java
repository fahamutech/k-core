package com.fahamutech.doctorapp.forum.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fahamutech.doctorapp.R;
import com.fahamutech.doctorapp.forum.database.PostNoSqlDataBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyChatFragment extends Fragment {

    private ListenerRegistration listenerRegistration;

    public MyChatFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //user id
        String uid = FirebaseAuth.getInstance().getUid();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.forum_my_chart_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.my_chart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //add listener to my chart fragment
        PostNoSqlDataBase dataSource = new PostNoSqlDataBase(getContext());
        listenerRegistration = dataSource.getMyChatPost(uid, recyclerView, getContext());
        return view;
    }

    @Override
    public void onDestroy() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        super.onDestroy();
    }
}
