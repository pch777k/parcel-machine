package com.pch777.model;

import com.pch777.exceptions.PackNotFoundException;

import java.util.*;
import java.util.logging.Logger;

import static com.pch777.model.PackSize.*;

public class ParcelMachine {
    private static final int NUMBER_OF_SMALL_SIZE_BOXES = 2;
    private static final int NUMBER_OF_MEDIUM_SIZE_BOXES = 1;
    private static final int NUMBER_OF_LARGE_SIZE_BOXES = 1;
    private static final Logger LOG = Logger.getLogger(ParcelMachine.class.getName());
    private final Map<String, Pack> packStorage = new HashMap<>();
    private final Map<PackSize, Integer> boxSizeManager = new HashMap<>();

    public ParcelMachine() {
        boxSizeManager.put(SMALL, NUMBER_OF_SMALL_SIZE_BOXES);
        boxSizeManager.put(MEDIUM, NUMBER_OF_MEDIUM_SIZE_BOXES);
        boxSizeManager.put(LARGE, NUMBER_OF_LARGE_SIZE_BOXES);
    }

    public Map<PackSize, Integer> getBoxSizeManager() {
        return boxSizeManager;
    }

    public Collection<Pack> getPacks() {
        return packStorage.values();
    }

    public Optional<Pack> getPack(String number) {
        return Optional.ofNullable(packStorage.get(number));
    }

    public void sendPack(Pack pack) {
        int totalBoxes = NUMBER_OF_SMALL_SIZE_BOXES + NUMBER_OF_MEDIUM_SIZE_BOXES + NUMBER_OF_LARGE_SIZE_BOXES;
        if (packStorage.size() < totalBoxes) {
            int availableBoxes = boxSizeManager.get(pack.getSize());

            if (availableBoxes > 0) {
                pack.setCode(generateCode());
                packStorage.put(pack.getNumber(), pack);
                boxSizeManager.put(pack.getSize(), availableBoxes - 1);
                LOG.info("Pack number: " + pack.getNumber() + ", Code: " + pack.getCode());

            } else {
                throw new IllegalArgumentException("No available slots for size: " + pack.getSize());
            }
        } else {
            throw new IllegalArgumentException("The packStorage is full");
        }
    }

    public void receivePack(String packNumber, String code) throws PackNotFoundException {
        if(!packStorage.containsKey(packNumber)) {
            throw new PackNotFoundException("Pack with number " + packNumber + " not found");
        }

        if (code != null && code.equals(packStorage.get(packNumber).getCode())) {
            Pack pack = packStorage.remove(packNumber);
            PackSize packSize = pack.getSize();
            boxSizeManager.put(packSize, boxSizeManager.get(packSize) + 1);
            LOG.info("The pack has been received : " + pack);
        } else {
            throw new IllegalArgumentException("The code is wrong");
        }
    }

    private String generateCode() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

}
