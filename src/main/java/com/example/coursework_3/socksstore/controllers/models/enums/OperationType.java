package com.example.coursework_3.socksstore.controllers.models.enums;

public enum OperationType {
    ADD("Приёмка"), REMOVE("Выдача");
    private final String operationType;

    OperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getTranslate() {
        return operationType;
    }
}

