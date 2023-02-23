package com.example.coursework_3.socksstore.controllers.services;

import com.example.coursework_3.socksstore.controllers.models.Socks;
import com.example.coursework_3.socksstore.controllers.models.enums.OperationType;

public interface OperationsService {
    void addOperation(OperationType type, Socks socks);
}
