package com.pch777.dao;

import com.pch777.exceptions.PackNotFoundException;
import com.pch777.model.Pack;

import java.util.List;
import java.util.Optional;

public interface PackDao {

    Optional<Pack> findOne(String number);
    List<Pack> getPacks();
    void sendPack(Pack pack);
    void receivePack(String packNumber, String packCode) throws PackNotFoundException;
}
