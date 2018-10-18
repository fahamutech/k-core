package com.fahamutech.adminapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.fahamutech.adminapp.R;
import com.fahamutech.adminapp.activities.CategoryContent;
import com.fahamutech.adminapp.model.Category;
import com.fahamutech.adminapp.session.Session;
import com.fahamutech.adminapp.vholder.CatViewHolder;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatViewHolder> {

    private List<Category> lists;
    private Context context;
    private Session session;

    public CatAdapter(List<Category> lists, Context context) {
        this.lists = lists;
        this.context = context;
        this.session = new Session(context);
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context)
                .inflate(R.layout.cat_view, parent, false);
        return new CatViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        holder.getTitle().setText(lists.get(position).getName());
        holder.getSubtitle().setText(lists.get(position).getDescription());
        Glide.with(context).load(lists.get(position).getImage()).into(holder.getImageView());

        holder.getView().setOnClickListener(v -> {
            Snackbar.make(v, "Itemc clicked is : " + position, Snackbar.LENGTH_SHORT).show();
            Intent intent = new Intent(context, CategoryContent.class);
            intent.putExtra("_category", lists.get(position));
            session.saveLastCategory(lists.get(position).getId());
            session.saveLastTitle(lists.get(position).getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
