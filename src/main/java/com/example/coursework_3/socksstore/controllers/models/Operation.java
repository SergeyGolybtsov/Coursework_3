package com.example.coursework_3.socksstore.controllers.models;

import com.example.coursework_3.socksstore.controllers.models.enums.Color;
import com.example.coursework_3.socksstore.controllers.models.enums.OperationType;
import com.example.coursework_3.socksstore.controllers.models.enums.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Operation {
    private OperationType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;
    private int quantity;
    private Size size;
    private int cotton;
    private Color color;

    public Operation(OperationType type, int quantity, Size size, int cotton, Color color) {
        this.type = type;
        this.quantity = quantity;
        this.size = size;
        this.cotton = cotton;
        this.color = color;
        this.dateTime = LocalDateTime.now();
    }
}