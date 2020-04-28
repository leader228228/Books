package ua.edu.sumdu;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import picocli.CommandLine;
import ua.edu.sumdu.controller.commands.CommandsStore;
import ua.edu.sumdu.controller.commands.CommandExecutionCode;

import java.util.Arrays;
import java.util.Scanner;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ua.edu.sumdu.model")
@SpringBootApplication
public class App implements CommandLineRunner {
    private static final Logger LOGGER = Logger.getLogger(App.class);
    private final CommandsStore commandsStore;

    public App(CommandsStore commandsStore) {
        this.commandsStore = commandsStore;
    }

    public static void main(String [] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        commandsStore.getCommand("help").execute();
        String splitRegex = " ";
        CommandExecutionCode executionCode = CommandExecutionCode.OK;
        while (executionCode != CommandExecutionCode.EXIT) {
            String input = scanner.nextLine();
            LOGGER.debug("User input command: " + input);
            if (StringUtils.isBlank(input)) {
                LOGGER.error("Blank input: " + input);
                continue;
            }
            try {
                String [] splitInput = input.split(splitRegex);
                String cmd = splitInput[0];
                String [] cmdArgs = Arrays.copyOfRange(splitInput, 1, splitInput.length);
                CommandLine commandLine = commandsStore.getCommand(cmd);
                if (commandLine == null) {
                    LOGGER.error("Unknown command: " + cmd);
                } else {
                    executionCode = CommandExecutionCode.from(commandLine.execute(cmdArgs));
                    LOGGER.info("Command: " + input + System.lineSeparator() + "Execution code: " + executionCode);
                }
            } catch (Throwable t) {
                LOGGER.error(t);
                commandsStore.getCommand("help").execute();
            }
        }
        LOGGER.debug("Shutting down");
    }
}
