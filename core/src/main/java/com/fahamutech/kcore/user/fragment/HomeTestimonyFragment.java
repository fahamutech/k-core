package com.fahamutech.kcore.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fahamutech.kcore.R;
import com.fahamutech.kcore.user.database.connector.HomeDataSource;
import com.fahamutech.kcore.user.database.noSql.HomeNoSqlDatabase;

public class HomeTestimonyFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_testimony_user_user, container, false);
        return initView(inflate, new HomeNoSqlDatabase(inflate.getContext()));
    }


    private View initView(View view, HomeDataSource homeDataSource) {
        recyclerView = view.findViewById(R.id.testimony_cat_recy);
        swipeRefreshLayout = view.findViewById(R.id.testimony_cat_swipe);
        homeDataSource.getTestimony(recyclerView, swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() ->
                homeDataSource.getTestimony(recyclerView, swipeRefreshLayout));
        return view;
    }
}
