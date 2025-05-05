package com.example.item_description;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.sharecircle.model.ItemModel;

public class ItemDescriptionActivity extends AppCompatActivity {

    private ImageView itemImage;
    private TextView itemTitle, itemDescription, itemLocation, itemContact, itemDeposit, itemDuration;
    private Button rentButton, chatButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        itemImage = findViewById(R.id.desc_image);
        itemTitle = findViewById(R.id.desc_title);
        itemDescription = findViewById(R.id.desc_description);
        itemLocation = findViewById(R.id.desc_location);
        itemContact = findViewById(R.id.desc_contact);
        itemDeposit = findViewById(R.id.desc_deposit);
        itemDuration = findViewById(R.id.desc_duration);
        rentButton = findViewById(R.id.desc_rent_button);
        chatButton = findViewById(R.id.desc_chat_button);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("item")) {
            ItemModel item = (ItemModel) intent.getSerializableExtra("item");

            if (item != null) {
                itemTitle.setText(item.getItemName());
                itemDescription.setText(item.getDescription());
                itemLocation.setText("Location: " + item.getLocation());
                itemContact.setText("Contact: " + item.getContact());
                itemDeposit.setText("Deposit: ₹" + item.getDeposit());
                itemDuration.setText("Duration: " + item.getDuration() + " days");

                Glide.with(this)
                        .load(item.getImageUrl())
                        .placeholder(R.drawable.placeholder) // Make sure this exists in drawable
                        .into(itemImage);

                rentButton.setOnClickListener(v ->
                        Toast.makeText(this, "Rent feature coming soon!", Toast.LENGTH_SHORT).show());

                chatButton.setOnClickListener(v -> {
                    Intent chatIntent = new Intent(Intent.ACTION_DIAL);
                    chatIntent.setData(Uri.parse("tel:" + item.getContact()));
                    startActivity(chatIntent);
                });
            }
        }
    }
}
