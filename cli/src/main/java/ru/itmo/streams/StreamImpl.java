package ru.itmo.streams;

import ru.itmo.utils.StreamDescriptor;

public class StreamImpl implements Stream {
    /**
     * Descriptor of the stream. It is used to identify the stream.
     * Example: INPUT - for std input stream, ERROR - for std error stream, OUTPUT - for file "output.txt"
     */
    private StreamDescriptor descriptor;

}
