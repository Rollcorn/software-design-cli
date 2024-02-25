package ru.itmo.streams;

import ru.itmo.utils.StreamDescriptor;

import java.util.Map;

public class StreamImpl implements Stream {
    /**
     * Descriptor of the stream. It is used to identify the stream.
     * Example: INPUT - for std input stream, ERROR - for std error stream, OUTPUT - for file "output.txt"
     */
//    private StreamDescriptor descriptor;
        private Map<StreamDescriptor, String> data;

//      чистит дату
        public void remove() {

        }

        public void put(String data, StreamDescriptor descriptor, Boolean isOverride) {
        }

        public String get(StreamDescriptor descriptor) {
            return "get";
        }

}
