package ru.itmo;

import jdk.jshell.spi.ExecutionControl;

public interface ICli {
    void run() throws ExecutionControl.NotImplementedException;
}
