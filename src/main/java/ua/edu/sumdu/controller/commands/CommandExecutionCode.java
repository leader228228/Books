package ua.edu.sumdu.controller.commands;

public enum CommandExecutionCode {
    OK(1), EXIT(0);

    private final int code;

    CommandExecutionCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CommandExecutionCode from(int code) {
        switch (code) {
            case 1:
                return OK;
            case 0:
                return EXIT;
            default:
                return null;
        }
    }
}
