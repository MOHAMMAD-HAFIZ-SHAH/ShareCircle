package com.example.homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private List<RecommendationItem> items;

    public RecommendationAdapter(List<RecommendationItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendationItem item = items.get(position);
        holder.productName.setText(item.getName());
        holder.productPrice.setText(item.getPrice());
        holder.productImage.setImageResource(item.getImageResourceId());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(),
                    "Selected: " + item.getName(),
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}