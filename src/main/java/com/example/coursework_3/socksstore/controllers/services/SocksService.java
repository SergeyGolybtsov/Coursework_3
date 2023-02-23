package com.example.coursework_3.socksstore.controllers.services;

import com.example.coursework_3.socksstore.controllers.models.Socks;
import com.example.coursework_3.socksstore.controllers.models.enums.Color;
import com.example.coursework_3.socksstore.controllers.models.enums.Size;

import java.util.List;

public interface SocksService {
    List<Socks> getAllSocks();

    Socks addSocks(Socks socks);

    int getSocks(Color color, Size size, int cottonMin, int cottonMax);

    boolean updateSocks(Socks socks);
    boolean removeSocks(Socks socks);
}
