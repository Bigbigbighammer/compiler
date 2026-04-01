package command.impl;

import command.Command;
import command.Response;
import command.executor.CommandExecutor;
import exception.InvalidArgumentException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BatchCommand implements Command<List<String>> {

    private final CommandExecutor commandExecutor;

    public BatchCommand(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public Response<List<String>> execute(String[] args) {
        if (args.length != 1) {
            throw new InvalidArgumentException("batch requires 1 argument: <filename>");
        }
        String filename = args[0];
        List<String> results = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String result = commandExecutor.executeLine(line);
                results.add(result);
            }
        } catch (IOException e) {
            return new Response<>(false, "Failed to read file: " + filename, null);
        }

        return new Response<>(true, "Batch execution completed", results);
    }
}
