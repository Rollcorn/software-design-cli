package ru.itmo.streams;

import ru.itmo.utils.StreamDescriptor;

public interface Stream {
    void remove(StreamDescriptor descriptor);

    void put(String data, StreamDescriptor descriptor, Boolean isOverride);

    String get(StreamDescriptor descriptor);
}
