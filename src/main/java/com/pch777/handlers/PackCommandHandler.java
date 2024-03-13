package com.pch777.handlers;

import com.pch777.dao.PackDao;
import com.pch777.exceptions.PackNotFoundException;
import com.pch777.input.UserInputCommand;
import com.pch777.model.Pack;
import com.pch777.model.PackSize;

import java.util.Optional;
import java.util.logging.Logger;

import static com.pch777.model.PackSize.of;

public class PackCommandHandler extends BaseCommandHandler {


        private static final Logger LOG = Logger.getLogger(PackCommandHandler.class.getName());

        public static final String COMMAND_NAME = "pack";

        private final PackDao packDao;

        public PackCommandHandler(PackDao packDao) {
            this.packDao = packDao;
        }

        @Override
        protected String getCommandName() {
            return COMMAND_NAME;
        }

        @Override
        public void handle(UserInputCommand command) throws PackNotFoundException {

            if (command.getAction() == null) {
                throw new IllegalArgumentException("Action can't be null");
            }

            switch (command.getAction()) {
                case LIST:
                    LOG.info("List of packs...");

                    validateNumberOfParams(command, 0);

                    if(packDao.getPacks().isEmpty()) {
                        LOG.info("There are no packs, create one");
                    } else {
                        packDao.getPacks().forEach(System.out::println);
                    }

                    break;

                case SEND:
                    LOG.info("Send pack");

                    validateNumberOfParams(command, 2);

                    String packName = command.getParams().get(0);
                    PackSize packSize = of(command.getParams().get(1));
                    packDao.sendPack(new Pack(packName, packSize));
                    break;

                case FIND:
                    LOG.info("Find a pack");

                    validateNumberOfParams(command, 1);

                    String packNumber = command.getParams().get(0);

                    Optional<Pack> foundPack = packDao.findOne(packNumber);
                    if (foundPack.isPresent()) {
                        System.out.println(foundPack.get());
                    } else {
                        throw new PackNotFoundException("Pack with number " + packNumber + " not found");
                    }
                    break;

                case RECEIVE:
                    LOG.info("Receive a pack");

                    validateNumberOfParams(command, 2);

                    packNumber = command.getParams().get(0);
                    String packCode = command.getParams().get(1);

                    packDao.receivePack(packNumber, packCode);
                    break;

                default:
                    throw new IllegalArgumentException(
                            String.format("Unknown action: %s from command: %s", command.getAction(), command.getCommand()));
            }
        }

        private static void validateNumberOfParams(UserInputCommand command, int numberOfParams) {
            if (command.getParams().size() != numberOfParams) {
                throw new IllegalArgumentException("Wrong command format. Check help for more information");
            }
        }



}
