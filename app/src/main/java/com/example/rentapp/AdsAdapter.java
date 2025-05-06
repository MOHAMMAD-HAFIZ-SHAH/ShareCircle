package com.example.rentapp;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdViewHolder> {

    private final List<AdItem> adList;

    public AdsAdapter(List<AdItem> adList) {
        this.adList = adList;
    }

    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad_card, parent, false);
        return new AdViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {
        AdItem item = adList.get(position);
        if (item != null) {
            Uri imageUri = item.getImageUri();
            if (imageUri != null) {
                Glide.with(holder.itemView.getContext())
                        .load(imageUri)
                        .placeholder(R.drawable.ic_photo) // fallback image
                        .error(R.drawable.ic_photo)       // error fallback
                        .into(holder.imageView);
            }

            holder.name.setText(item.getName() != null ? item.getName() : "Unnamed");
            holder.price.setText("₹" + item.getPrice());
            holder.desc.setText(item.getDescription() != null ? item.getDescription() : "");
        }
    }

    @Override
    public int getItemCount() {
        return adList != null ? adList.size() : 0;
    }

    static class AdViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price, desc;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.adImage);
            name = itemView.findViewById(R.id.adName);
            price = itemView.findViewById(R.id.adPrice);
            desc = itemView.findViewById(R.id.adDesc);
        }
    }
}
