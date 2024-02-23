package ru.itmo;

import jdk.jshell.spi.ExecutionControl;
import ru.itmo.processing.commands.ICommand;

import java.util.List;
import java.util.Scanner;

public class ICliImpl implements ICli {
    @Override
    public void run() throws ExecutionControl.NotImplementedException {
        // читаем вход в цикле до 'exit'
        Scanner in = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            String input = in.next();
            // парсим команды -> получаем команды
            List<ICommand> coms;

            // выполняем команды
            while (true) {

            }
        }
    }
}
