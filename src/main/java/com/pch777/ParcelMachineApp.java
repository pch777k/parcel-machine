package com.pch777;

import com.pch777.dao.PackDao;
import com.pch777.dao.PackDaoImpl;
import com.pch777.exceptions.PackNotFoundException;
import com.pch777.exceptions.QuitIdeasApplicationException;
import com.pch777.handlers.CommandHandler;
import com.pch777.handlers.HelpCommandHandler;
import com.pch777.handlers.PackCommandHandler;
import com.pch777.handlers.QuitCommandHandler;
import com.pch777.input.UserInputCommand;
import com.pch777.input.UserInputManager;
import com.pch777.model.ParcelMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParcelMachineApp {

    private static final Logger LOG = Logger.getLogger(ParcelMachineApp.class.getName());

    public static void main(String[] args) {
        new ParcelMachineApp().start();
    }
    void start() {
        LOG.info("ParcelMachineApp \n" + howToUseApplication() + "Start app ...");

        boolean applicationLoop = true;

        PackDao packDao = new PackDaoImpl(new ParcelMachine());
        UserInputManager userInoutManager = new UserInputManager();

        List<CommandHandler> handlers = new ArrayList<>();
        handlers.add(new HelpCommandHandler());
        handlers.add(new QuitCommandHandler());
        handlers.add(new PackCommandHandler(packDao));

        while (applicationLoop) {
            try {
                UserInputCommand userInputCommand = userInoutManager.nextCommand();
                LOG.info(userInputCommand.toString());

                Optional<CommandHandler> currentHandler = Optional.empty();

                for (CommandHandler handler : handlers) {
                    if(handler.supports(userInputCommand.getCommand())){
                        currentHandler = Optional.of(handler);
                        break;
                    }
                }

                currentHandler
                        .orElseThrow(() -> new IllegalArgumentException("Unknown handler: " + userInputCommand.getCommand()))
                        .handle(userInputCommand);

            } catch(QuitIdeasApplicationException e) {
                LOG.info("Quit...");
                applicationLoop = false;

            }
            catch(PackNotFoundException e) {
                LOG.log(Level.WARNING, "Pack exception " + e.getMessage());
            }

            catch (IllegalArgumentException e) {
                LOG.log(Level.WARNING, "Validation exception " + e.getMessage());

            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Unknown error", e);

            }

        }
    }

    public static String howToUseApplication() {
        String allowedCommands = "Allowed commands: help, quit, pack \n";
        String commandsWithoutActionAndParams = "Command pattern for help, quit: <command> \n";
        String commandPattern = "Command pattern for pack: <command> <action> <param1> <param2> \n";
        String exampleSend = "Example for send pack: pack send PackName PackSize \n";
        String exampleReceive = "Example for receive pack: pack receive PackNumber ReceiveCode \n";
        return allowedCommands + commandsWithoutActionAndParams + commandPattern + exampleSend + exampleReceive;
    }
}
