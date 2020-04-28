package ua.edu.sumdu.controller.commands;

import picocli.CommandLine;

import java.util.Set;

public interface CommandsStore {
    CommandLine getCommand(String command);
    Set<CommandLine> getAllCommands();
}
