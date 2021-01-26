package main.java;

import main.java.command.*;
import main.java.exceptions.IllegalInputFormatException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * A Parser class that is used to parse user input as Commands
 */
public class Parser {

    /**
     * Creates a parser instance
     */
    public Parser() {

    }

    /**
     * Parse a particular command string and create a command to be returned
     * @param commandStr user input string command
     * @return parsed command
     * @throws Exception parsing exceptions including wrong input, index out of bounds due to wrong input etc.
     */
    public Command parse(String commandStr) throws Exception {
        Command command = null;
        try {
            if (commandStr == null) {
                command = new ExitCommand();
            } else if (commandStr.toLowerCase().equals("bye")) {
                command = new ExitCommand();
            } else if (commandStr.trim().toLowerCase().equals("list")) {
                command = new ListCommand();
            } else if (commandStr.toLowerCase().split(" ")[0].equals("done")) {
                int doneIndex = Integer.parseInt(commandStr.split(" ")[1]) - 1;
                command = new DoneCommand(doneIndex);
            } else if (commandStr.toLowerCase().split(" ")[0].equals("delete")) {
                int deleteIndex = Integer.parseInt(commandStr.split(" ")[1]) - 1;
                command = new DeleteCommand(deleteIndex);
            } else if (commandStr.toLowerCase().split(" ")[0].equals("todo")) {
                String todoName = commandStr.substring(5);
                if (todoName.trim().length() == 0) {
                    throw new IllegalInputFormatException();
                }
                command = new AddTodoCommand(todoName);
            } else if (commandStr.toLowerCase().split(" ")[0].equals("deadline")) {
                String[] arr = commandStr.split(" /");
                String name = arr[0].substring(9);
                String[] arr2 = arr[1].split(" ", 2);
                try {
                    LocalDate timeDate = LocalDate.parse(arr2[1]);
                    command = new AddDeadlineCommand(name, arr2[0], timeDate);
                } catch (DateTimeParseException e) {
                    command = new AddDeadlineCommand(name, arr2[0], arr2[1]);
                }
            } else if (commandStr.toLowerCase().split(" ")[0].equals("event")) {
                String[] arr = commandStr.split(" /");
                String name = arr[0].substring(6);
                String[] arr2 = arr[1].split(" ", 2);
                try {
                    LocalDate timeDate = LocalDate.parse(arr2[1]);
                    command = new AddEventCommand(name, arr2[0], timeDate);
                } catch (DateTimeParseException e) {
                    command = new AddEventCommand(name, arr2[0], arr2[1]);
                }
            } else if (commandStr.toLowerCase().split(" ")[0].equals("find")) {
                String keyword = commandStr.substring(5);
                if (keyword.trim().length() == 0) {
                    throw new IllegalInputFormatException();
                }
                command = new FindCommand(keyword);
            } else {
                command = new WrongCommand();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }
}
