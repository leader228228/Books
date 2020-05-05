package ua.edu.sumdu.controller.commands;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.Callable;

@Component
@CommandLine.Command(name = "help")
public class CommandsStoreImpl implements CommandsStore, Callable<Integer> {
    private Map<String, CommandLine> commands = new HashMap<>();
    private final Logger LOGGER = Logger.getLogger(CommandsStoreImpl.class);

    public CommandsStoreImpl(PrintBooks printBooks, Exit exit) {
        addCommand(this).addCommand(printBooks).addCommand(exit);
    }

    private CommandsStoreImpl addCommand(Object command) {
        if (!command.getClass().isAnnotationPresent(CommandLine.Command.class)) {
            throw new UncheckedIOException(
                    new IOException("Passed object is not a command, see " + CommandLine.Command.class.getName())
            );
        }
        CommandLine.Command commandAnnotation = command.getClass().getAnnotation(CommandLine.Command.class);
        Set<String> names = new HashSet<>(Arrays.asList(commandAnnotation.aliases()));
        names.add(commandAnnotation.name());
        CommandLine commandLine =
            registerConverters(new CommandLine(command))
            .setCaseInsensitiveEnumValuesAllowed(true);
        names.forEach(
            e -> {
                if (commands.put(e, commandLine) != null) {
                    throw new UncheckedIOException(new IOException("There is a command with " + e + " name (alias)"));
                }
            }
        );
        return this;
    }

    public CommandLine getCommand(String command) {
        LOGGER.debug("Searching command " + command);
        CommandLine commandLine = commands.get(command);
        LOGGER.debug("Found command: " + commandLine);
        return commandLine;
    }

    @Override
    public Set<CommandLine> getAllCommands() {
        return new HashSet<>(commands.values());
    }

    @Override
    public Integer call() {
        getAllCommands().forEach(e -> e.usage(System.out));
        return CommandExecutionCode.OK.getCode();
    }

    private CommandLine registerConverters(CommandLine commandLine) {
        commandLine.registerConverter(Date.class, Date::valueOf);
        return commandLine;
    }
}
