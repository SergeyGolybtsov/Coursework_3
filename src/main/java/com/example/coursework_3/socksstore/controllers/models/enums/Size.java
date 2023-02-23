package com.example.coursework_3.socksstore.controllers.models.enums;


public enum Size {
    L(27), M(25), S(23), XL(29);
    private final int size;

    Size(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
