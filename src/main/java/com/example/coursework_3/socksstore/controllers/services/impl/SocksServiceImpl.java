package com.example.coursework_3.socksstore.controllers.services.impl;
import com.example.coursework_3.socksstore.controllers.models.Socks;
import com.example.coursework_3.socksstore.controllers.models.enums.Color;
import com.example.coursework_3.socksstore.controllers.models.enums.OperationType;
import com.example.coursework_3.socksstore.controllers.models.enums.Size;
import com.example.coursework_3.socksstore.controllers.services.FilesService;
import com.example.coursework_3.socksstore.controllers.services.OperationsService;
import com.example.coursework_3.socksstore.controllers.services.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
@RequiredArgsConstructor
@Service
public class SocksServiceImpl implements SocksService {
    private List<Socks> socksList = new ArrayList<>();
    private final FilesService filesService;
    private final OperationsService operationsService;
    private final ObjectMapper mapper;



    @Override
    public List<Socks> getAllSocks(){
        return socksList;
    }

    @Override
    public Socks addSocks(Socks socks) {
        boolean flag = false;
        for (Socks socksTemp : socksList) {
            if (socks.getColor().equals(socksTemp.getColor()) && socks.getSize().equals(socksTemp.getSize()) && socks.getCotton() == socksTemp.getCotton()) {
                int index = socksList.indexOf(socksTemp);
                socks.setQuantity(socks.getQuantity() + socksTemp.getQuantity());
                socksList.set(index, socks);
                flag = true;
            }
        }
        if (!flag) {
            socksList.add(socks);
        }
        operationsService.addOperation(OperationType.ADD, socks);
        saveToFile();
        return socks;
    }

    @Override
    public int getSocks (Color color, Size size, int cottonMin, int cottonMax) {
        int quantity = 0;
        for (Socks socks : socksList) {
            if (socks.getColor().equals(color) && socks.getSize().equals(size) && socks.getCotton() >= cottonMin && socks.getCotton() <= cottonMax) {
                quantity += socks.getQuantity();
            }
        }
        return quantity;
    }

    @Override
    public boolean updateSocks(Socks socks) {
        for (Socks socksTemp : socksList) {
            if (socks.getColor().equals(socksTemp.getColor()) && socks.getSize().equals(socksTemp.getSize())
                    && socks.getCotton() == socksTemp.getCotton() && (socksTemp.getQuantity() - socks.getQuantity() >= 0)){
                int index = socksList.indexOf(socksTemp);
                operationsService.addOperation(OperationType.REMOVE, socks);
                socks.setQuantity(socksTemp.getQuantity() - socks.getQuantity());
                socksList.set(index, socks);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeSocks(Socks socks) {
        boolean flag = false;
        if (socksList.contains(socks)){
            operationsService.addOperation(OperationType.REMOVE, socks);
            flag = socksList.remove(socks);
            saveToFile();
        }
        return flag;
    }

    private void saveToFile() {
        try {
            String json = mapper.writeValueAsString(socksList);
            filesService.saveSocksToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        String json = filesService.readSocksFromFile();
        try {
            if (!json.isBlank()) {
                socksList = mapper.readValue(json, new TypeReference<List<Socks>>() {});
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void use(){
        readFromFile();
    }
}