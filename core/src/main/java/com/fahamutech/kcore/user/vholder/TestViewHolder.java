package com.fahamutech.kcore.user.vholder;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.fahamutech.kcore.R;

public class TestViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;

    public TestViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.testimony_image);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
