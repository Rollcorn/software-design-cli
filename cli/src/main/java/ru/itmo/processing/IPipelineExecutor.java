package ru.itmo.processing;

public interface IPipelineExecutor {

    /**
     * Выполняет пайплайн.
     *
     * @param inputString Входная строка для выполнения пайплайна.
     * @throws RuntimeException Если произошла ошибка при выполнении пайплайна.
     */
    void execute(String inputString);
}
