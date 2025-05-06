package com.example.rentapp;

import android.net.Uri;

public class AdItem {
    private String imageUriString;
    private String name;
    private String price;
    private String description;

    public AdItem(Uri imageUri, String name, String price, String description) {
        this.imageUriString = imageUri.toString();
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Required for Gson
    public AdItem() {
    }

    public Uri getImageUri() {
        return Uri.parse(imageUriString);
    }

    public void setImageUri(Uri uri) {
        this.imageUriString = uri.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUriString() {
        return imageUriString;
    }

    public void setImageUriString(String imageUriString) {
        this.imageUriString = imageUriString;
    }
}
