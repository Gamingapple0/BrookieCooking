package com.example.brookiecooking;

public class Chat {
    String value ;
    boolean isBot;
    String category;

    public void setValue(String value) {
        this.value = value;
    }

    public void setIcon(boolean icon) {
        isBot = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Chat(String value, boolean isBot, String category) {
        this.value = value;
        this.isBot = isBot;
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public boolean isBot() {
        return isBot;
    }
}
