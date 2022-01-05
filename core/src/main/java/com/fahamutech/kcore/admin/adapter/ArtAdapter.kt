package com.fahamutech.kcore.admin.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fahamutech.kcore.R;
import com.fahamutech.kcore.admin.activities.ReadActivity;
import com.fahamutech.kcore.admin.model.Article;
import com.fahamutech.kcore.admin.vholder.ArtViewHolder;

import java.util.List;

public class ArtAdapter extends RecyclerView.Adapter<ArtViewHolder> {

    private List<Article> articles;
    private Context context;

    public ArtAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ArtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.article_view, parent, false);
        return new ArtViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtViewHolder holder, int position) {
        holder.getTitle().setText(articles.get(position).getTitle());
        holder.getDescription().setText(articles.get(position).getContent());
        Glide.with(context)
                .load(articles.get(position).getImage())
                .apply(new RequestOptions().circleCrop())
                .into(holder.getImage());
        holder.getView().setOnClickListener(v -> {
            Intent intent = new Intent(context, ReadActivity.class);
            intent.putExtra("_article", articles.get(position));
            context.startActivity(intent);
//            Snackbar.make(v, "Item clicked : " + position, Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
