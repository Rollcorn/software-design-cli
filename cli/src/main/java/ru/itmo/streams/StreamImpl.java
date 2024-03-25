package ru.itmo.streams;

import ru.itmo.utils.StreamDescriptor;

import java.util.HashMap;
import java.util.Map;

public class StreamImpl implements Stream {
    private Map<StreamDescriptor, String> data;

    public StreamImpl() {
        this.data = new HashMap<>();
    }

    public void remove(StreamDescriptor descriptor) {
        this.data.remove(descriptor);
    }

    public void put(String data, StreamDescriptor descriptor, Boolean isOverride) {
        if (Boolean.TRUE.equals(isOverride)) {
            this.data.remove(descriptor);
        }
        this.data.put(descriptor, this.data.getOrDefault(descriptor, "") + data);
    }

    public String get(StreamDescriptor descriptor) {
        return this.data.getOrDefault(descriptor, "");
    }
}
