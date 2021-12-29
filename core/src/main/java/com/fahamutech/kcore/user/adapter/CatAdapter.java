package com.fahamutech.kcore.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fahamutech.kcore.R;
import com.fahamutech.kcore.user.activities.CategoryContent;
import com.fahamutech.kcore.user.model.Category;
import com.fahamutech.kcore.user.session.Session;
import com.fahamutech.kcore.user.vholder.CatViewHolder;
import com.google.android.material.snackbar.Snackbar;

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
                .inflate(R.layout.cat_view_user, parent, false);
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
