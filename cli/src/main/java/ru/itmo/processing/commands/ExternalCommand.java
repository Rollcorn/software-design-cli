package ru.itmo.processing.commands;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import ru.itmo.streams.Stream;

public class ExternalCommand implements ICommand{

    private List<String> args;

    public ExternalCommand(List<String> args) {
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
