package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;

import java.util.ArrayList;
import java.util.List;

public class EchoCommand implements ICommand {

    private List<String> args_;
    private List<String> flag_;

    public EchoCommand() {
        this.args_ = new ArrayList<>();
        this.flag_ = new ArrayList<>();

    }

    public EchoCommand(List<String> args_) {
        this.args_ = args_;
        this.flag_ = new ArrayList<>();
    }

    public EchoCommand(List<String> args_, List<String> flag_) {
        this.args_ = args_;
        this.flag_ = flag_;
    }

    @Override
    public void execute(Stream stream1) {
        // TODO: implement (9((
    }
}
