package ru.itmo.processing.commands;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import ru.itmo.streams.Stream;

public class ExternalCommand implements ICommand{

    private String[] args;

    public ExternalCommand(String[] args) {
        this.args = args;
    }

    @Override
    public void execute(Stream stream) {
        try {
            ProcessBuilder pb = new ProcessBuilder(this.args);

            pb.redirectInput(Redirect.INHERIT);
            pb.redirectOutput(Redirect.INHERIT);
            pb.redirectError(Redirect.INHERIT);

            Process p = pb.start();

            p.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
