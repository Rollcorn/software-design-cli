package ru.itmo.processing.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.streams.Stream;
import ru.itmo.streams.StreamImpl;
import ru.itmo.utils.StreamDescriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EchoCommandTest {

    @Test
    void execute_params_simple() {
        List<String> args = Arrays.asList("foo", "bar");
        List<String> flags = new ArrayList<>();
        ICommand echo = new EchoCommand (args, flags);
        Stream stream1 = new StreamImpl();
        echo.execute(stream1);
        assertEquals("foo bar\n", stream1.get(StreamDescriptor.OUTPUT));
    }

    @Test
    void execute_no_params() {
        ICommand echo = new EchoCommand ();
        Stream stream1 = new StreamImpl();
        echo.execute(stream1);
        assertEquals("\n", stream1.get(StreamDescriptor.OUTPUT));
    }
}