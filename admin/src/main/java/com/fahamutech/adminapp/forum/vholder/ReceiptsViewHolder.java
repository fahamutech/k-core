package com.fahamutech.adminapp.forum.vholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fahamutech.adminapp.R;


public class ReceiptsViewHolder extends RecyclerView.ViewHolder {
    private TextView date;
    private TextView amount;

    public ReceiptsViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.receipt_date);
        amount = itemView.findViewById(R.id.receipt_amount);
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getAmount() {
        return amount;
    }

    public void setAmount(TextView amount) {
        this.amount = amount;
    }
}
