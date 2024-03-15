package com.pch777.dao;

import com.pch777.exceptions.PackNotFoundException;
import com.pch777.handlers.PackCommandHandler;
import com.pch777.model.Pack;
import com.pch777.model.ParcelMachine;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackDaoImpl implements PackDao {
    private static final Logger LOG = Logger.getLogger(PackDaoImpl.class.getName());
    private final ParcelMachine parcelMachine;

    public PackDaoImpl(ParcelMachine parcelMachine) {
        this.parcelMachine = parcelMachine;
    }
    @Override
    public Optional<Pack> findOne(String number) {
        return parcelMachine.getPack(number);
    }
    @Override
    public List<Pack> getPacks() {
        return parcelMachine.getPacks().stream().toList();
    }
    @Override
    public void sendPack(Pack pack) {
        parcelMachine.sendPack(pack);
    }
    @Override
    public void receivePack(String packNumber, String packCode) throws PackNotFoundException {
        parcelMachine.receivePack(packNumber, packCode);
    }

}
