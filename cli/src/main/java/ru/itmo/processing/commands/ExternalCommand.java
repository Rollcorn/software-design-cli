package ru.itmo.processing.commands;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import ru.itmo.streams.Stream;
import ru.itmo.utils.StreamDescriptor;

public class ExternalCommand implements ICommand{

    public ExternalCommand() {  }

    @Override
    public void execute(Stream stream) {
        try {

            ProcessBuilder pb = new ProcessBuilder(stream.get(StreamDescriptor.OUTPUT).split(" "));

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
