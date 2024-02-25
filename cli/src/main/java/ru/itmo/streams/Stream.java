package ru.itmo.streams;

import ru.itmo.utils.StreamDescriptor;

public interface Stream {
    public void remove(StreamDescriptor descriptor);

    public void put(String data, StreamDescriptor descriptor, Boolean isOverride);

    public String get(StreamDescriptor descriptor);
}
