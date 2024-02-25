package ru.itmo.processing;


import ru.itmo.processing.commands.ICommand;
import ru.itmo.processing.parser.IParser;
import ru.itmo.streams.Stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineExecutorImpl implements IPipelineExecutor {
    static private PipelineExecutorImpl executor;
    /**
     * Парсер для разбора команд.
     */
    private final IParser parser;
    /**
     * Глобальные аргументы для выполнения пайплайна.
     */
    private Map<String, String> globalArgs;
    private Stream stream; // мапа с потоками? | он назначается каждой команде?

    /**
     * Создает новый экземпляр PipelineExecutorImpl.
     *
     * @param parser Парсер для разбора команд.
     * @param params Глобальные параметры для выполнения пайплайна.
     */
    private PipelineExecutorImpl(IParser parser, HashMap<String, String> params) {
        this.parser = parser;
        this.globalArgs = params;
    }

    /**
     * Возвращает экземпляр PipelineExecutorImpl.
     *
     * @param parser Парсер для разбора команд.
     * @param params Глобальные параметры для выполнения пайплайна.
     * @return Экземпляр PipelineExecutorImpl.
     */
    static public PipelineExecutorImpl getPipelineExecutorImpl(IParser parser, HashMap<String, String> params) {
        if (executor == null) {
            executor = new PipelineExecutorImpl(parser, params);
        }
        return executor;
    }



    @Override
    public void execute(String inputString) {
        parser.substitute("inputString", globalArgs);
        List<ICommand> commands = parser.parse();

        for (ICommand command : commands) {
            command.execute();
        }

    }
}
