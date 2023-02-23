package com.example.coursework_3.socksstore.controllers.models.enums;

public enum Color {
    BLACK("Чёрный"), WHITE("Белый");
    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
