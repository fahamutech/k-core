package com.fahamutech.adminapp.forum.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.fahamutech.adminapp.R;
import com.fahamutech.adminapp.forum.database.PostNoSqlDataBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllChartFragment extends Fragment {

    private ListenerRegistration listenerRegistration;
    private PostNoSqlDataBase dataSource;

    public AllChartFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.forum_all_chart_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.all_chart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataSource = new PostNoSqlDataBase(getContext());
        listenerRegistration = dataSource.getAllPosts(recyclerView, getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public String getUser() {
        return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }

    @Override
    public void onDestroy() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        super.onDestroy();
    }
}
