package ua.edu.sumdu.controller.commands;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "exit")
@Component
public class Exit implements Callable<Integer> {

    Exit() {
    }

    @Override
    public Integer call() {
        return CommandExecutionCode.EXIT.getCode();
    }
}
