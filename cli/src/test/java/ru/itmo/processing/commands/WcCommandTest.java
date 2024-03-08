package ru.itmo.processing.commands;

import org.junit.jupiter.api.Test;
import ru.itmo.streams.Stream;
import ru.itmo.streams.StreamImpl;
import ru.itmo.utils.StreamDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WcCommandTest {

    @Test
    void execute_wrong_flags(){
        List<String> input = Arrays.asList("-aaa", "example.txt");
        ICommand wc = new WcCommand(input);
        Stream stream = new StreamImpl();
        wc.execute(stream);
        assertEquals("wc: неверный ключ — -aaa", stream.get(StreamDescriptor.ERROR));

    }

    @Test
    void execute_simple_params_without_flags() {
        List<String> input = List.of("/example.txt");
        ICommand wc = new WcCommand(input);
        Stream stream = new StreamImpl();
        wc.execute(stream);
        assertEquals("", stream.get(StreamDescriptor.ERROR));
        assertEquals("3 9 37 /example.txt\n", stream.get(StreamDescriptor.OUTPUT));
    }

    @Test
    void execute_simple_params_with_flags(){
        List<String> input = List.of("-l", "/example.txt");
        ICommand wc = new WcCommand(input);
        Stream stream = new StreamImpl();
        wc.execute(stream);
        assertEquals("", stream.get(StreamDescriptor.ERROR));
        assertEquals("3 /example.txt\n", stream.get(StreamDescriptor.OUTPUT));
    }

    @Test
    void execute_with_nonempty_stream_without_args(){
        List<String> input = List.of("");
        ICommand wc = new WcCommand(input);
        Stream stream = new StreamImpl();
        stream.put("Hello", StreamDescriptor.OUTPUT, false);
        wc.execute(stream);
        assertEquals("", stream.get(StreamDescriptor.ERROR));
        assertEquals("1 1 5", stream.get(StreamDescriptor.OUTPUT));
    }

    @Test
    void execute_with_nonempty_stream_with_flag(){
        List<String> input = List.of("-l");
        ICommand wc = new WcCommand(input);
        Stream stream = new StreamImpl();
        stream.put("Hello", StreamDescriptor.OUTPUT, false);
        wc.execute(stream);
        assertEquals("", stream.get(StreamDescriptor.ERROR));
        assertEquals("1", stream.get(StreamDescriptor.OUTPUT));
    }

//    @Test
//    void execute_two_files_without_flags(){
//        List<String> input = List.of("/example.txt", "/examples.txt");
//        ICommand wc = new WcCommand(input);
//        Stream stream = new StreamImpl();
//        wc.execute(stream);
//        assertEquals("", stream.get(StreamDescriptor.ERROR));
//        assertEquals("""
//                3 6 41 /example1.txt
//                3 9 37 /example.txt
//                6 15 81 итого
//                """, stream.get(StreamDescriptor.OUTPUT));
//
//    }

}