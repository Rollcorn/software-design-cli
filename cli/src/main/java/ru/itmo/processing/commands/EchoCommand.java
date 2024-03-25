package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;
import ru.itmo.utils.StreamDescriptor;

import java.util.ArrayList;
import java.util.List;

public class EchoCommand implements ICommand {
    private List<String> args_;

    public EchoCommand() {
        this.args_ = new ArrayList<>();
    }

    public EchoCommand(List<String> args_) {
        this.args_ = args_;
    }

    @Override
    public void execute(Stream stream1) {
        // TODO: flags
        String result = String.join(" ", this.args_) + "\n";
        stream1.put(result, StreamDescriptor.OUTPUT, true);
    }
}
