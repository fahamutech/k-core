package com.fahamutech.kcoreuser.vholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.fahamutech.kcoreuser.R;

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
