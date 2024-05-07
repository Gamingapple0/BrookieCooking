package com.example.brookiecooking;

public class Chat {
    String value ;
    boolean isBot;

    public void setValue(String value) {
        this.value = value;
    }

    public void setIcon(boolean icon) {
        isBot = icon;
    }

    public Chat(String value, boolean isBot) {
        this.value = value;
        this.isBot = isBot;
    }

    public String getValue() {
        return value;
    }

    public boolean isBot() {
        return isBot;
    }
}
