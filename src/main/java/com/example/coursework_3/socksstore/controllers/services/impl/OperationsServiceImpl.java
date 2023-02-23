package com.example.coursework_3.socksstore.controllers.services.impl;

import com.example.coursework_3.socksstore.controllers.models.Operation;
import com.example.coursework_3.socksstore.controllers.models.Socks;
import com.example.coursework_3.socksstore.controllers.models.enums.OperationType;
import com.example.coursework_3.socksstore.controllers.services.FilesService;
import com.example.coursework_3.socksstore.controllers.services.OperationsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OperationsServiceImpl implements OperationsService {

    private List<Operation> operations = new LinkedList<>();

    private final FilesService filesService;
    private final ObjectMapper mapper;


    @Override
    public void addOperation(OperationType type, Socks socks) {
        operations.add(new Operation(type, socks.getQuantity(), socks.getSize(), socks.getCotton(), socks.getColor()));
        saveToFile();
    }

    private void saveToFile() {
        try {
            String json = mapper.writeValueAsString(operations);
            filesService.saveOperationsToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        String json = filesService.readOperationsFromFile();
        try {
            if (!json.isBlank()) {
                operations = mapper.readValue(json, new TypeReference<List<Operation>>() {
                });
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void use() {
        readFromFile();
    }
}
