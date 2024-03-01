package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;
import ru.itmo.utils.StreamDescriptor;

import java.util.ArrayList;
import java.util.List;

public class PwdCommand implements ICommand {

    private List<String> args_;
    private List<String> flag_;

    public PwdCommand() {
        this.args_ = new ArrayList<>();
        this.flag_ = new ArrayList<>();
    }

    public PwdCommand(List<String> args_, List<String> flag_) {
        this.args_ = args_;
        this.flag_ = flag_;
    }

    @Override
    public void execute(Stream stream1) {

        // Get working dir
        String userDirectory = System.getProperty("user.dir");

        // Put result to stream
        stream1.put(userDirectory, StreamDescriptor.OUTPUT, true);
        // TODO: remove StreamDescriptor.ERROR????
    }
}
