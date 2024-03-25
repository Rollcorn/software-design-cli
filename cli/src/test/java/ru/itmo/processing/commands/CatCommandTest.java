package ru.itmo.processing.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.streams.Stream;
import ru.itmo.streams.StreamImpl;
import ru.itmo.utils.StreamDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatCommandTest {

    @Test
    void execute_wrong_flags() {
        List<String> input = Arrays.asList("-aaa", "example.txt");
        ICommand cat = new CatCommand(input);
        Stream stream1 = new StreamImpl();
        cat.execute(stream1);
        assertEquals("cat: неверный ключ — -aaa", stream1.get(StreamDescriptor.ERROR));
    }

    @Test
    void execute_simple_params_without_flags(){
        List<String> input = List.of("/example.txt");
        ICommand cat = new CatCommand(input);
        Stream stream1 = new StreamImpl();
        cat.execute(stream1);
        assertEquals("", stream1.get(StreamDescriptor.ERROR));
        assertEquals("""
                hello, hi
                world, Fedoooorrrr
                1 2 3 4 hi""", stream1.get(StreamDescriptor.OUTPUT));
    }

    @Test
    void execute_simple_params_with_flags(){
        List<String> input = Arrays.asList("-n", "/example.txt");
        ICommand cat = new CatCommand(input);
        Stream stream1 = new StreamImpl();
        cat.execute(stream1);
        assertEquals("", stream1.get(StreamDescriptor.ERROR));
        assertEquals("""
                1\thello, hi
                2\tworld, Fedoooorrrr
                3\t1 2 3 4 hi""", stream1.get(StreamDescriptor.OUTPUT));
    }

    @Test
    void execute_wrong_file(){
        List<String> input = Arrays.asList("-n", "dum.txt");
        ICommand cat = new CatCommand(input);
        Stream stream1 = new StreamImpl();
        cat.execute(stream1);
        assertEquals("cat: dum.txt: Нет такого файла или каталога", stream1.get(StreamDescriptor.ERROR));
    }

    @Test
    void execute_with_nonempty_stream_without_args(){
        List<String> input = List.of("");
        ICommand cat = new CatCommand(input);
        Stream stream = new StreamImpl();
        stream.put("Hello ", StreamDescriptor.OUTPUT, false);
        stream.put("Denis", StreamDescriptor.OUTPUT, false);
        cat.execute(stream);
        assertEquals("", stream.get(StreamDescriptor.ERROR));
        assertEquals("Hello Denis\n", stream.get(StreamDescriptor.OUTPUT));
    }

}