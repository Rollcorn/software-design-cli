package ru.itmo.processing;

import ru.itmo.streams.Stream;

import java.io.IOException;

public interface IPipelineExecutor {
    boolean isFinished();

    /**
     * Выполняет пайплайн.
     *
     * @param inputString Входная строка для выполнения пайплайна.
     * @throws RuntimeException Если произошла ошибка при выполнении пайплайна.
     */
    Stream execute(String inputString) throws IOException;
}
