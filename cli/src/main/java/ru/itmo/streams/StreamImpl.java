package ru.itmo.streams;

import ru.itmo.utils.StreamDescriptor;

import java.util.HashMap;
import java.util.Map;

public class StreamImpl implements Stream {
    /**
     * Descriptor of the stream. It is used to identify the stream.
     * Example: INPUT - for std input stream, ERROR - for std error stream, OUTPUT - for file "output.txt"
     */
        private Map<StreamDescriptor, String> data;

        public StreamImpl() {
           this.data = new HashMap<>();
        }

        public void remove(StreamDescriptor descriptor) {
                this.data.remove(descriptor);
        }

        public void put(String data, StreamDescriptor descriptor, Boolean isOverride) {
                if (isOverride || !this.data.containsKey(descriptor)) {
                        this.data.put(descriptor, data);
                }
        }

        public String get(StreamDescriptor descriptor) {
                return this.data.getOrDefault(descriptor, "");
        }

}
