package com.example.homepage;

public class CategoryItem {
    private String name;
    private int iconResourceId;

    public CategoryItem(String name, int iconResourceId) {
        this.name = name;
        this.iconResourceId = iconResourceId;
    }

    public String getName() {
        return name;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }
}