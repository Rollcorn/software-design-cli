package ru.itmo.processing;


import ru.itmo.processing.commands.ExitCommand;
import ru.itmo.processing.commands.IChangeContext;
import ru.itmo.processing.commands.ICommand;
import ru.itmo.processing.parser.IParser;
import ru.itmo.processing.parser.ISubstitute;
import ru.itmo.streams.Stream;
import ru.itmo.streams.StreamImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineExecutorImpl implements IPipelineExecutor {
    static private PipelineExecutorImpl executor;
    private ISubstitute substitutor;
    boolean isFinished;

    //    Парсер для разбора команд.
    private final IParser parser;


    // Глобальные аргументы для выполнения пайплайна.
    private Map<String, String> globalArgs;
//    private Stream stream; // мапа с потоками? | он назначается каждой команде?

    /**
     * Создает новый экземпляр PipelineExecutorImpl.
     *
     * @param parser Парсер для разбора команд.
     * @param params Глобальные параметры для выполнения пайплайна.
     */
    private PipelineExecutorImpl(IParser parser, HashMap<String, String> params, ISubstitute substitutor) {
        this.parser = parser;
        this.globalArgs = params;
        this.substitutor = substitutor;
    }

    /**
     * Возвращает экземпляр PipelineExecutorImpl.
     *
     * @param parser           Парсер для разбора команд.
     * @param params           Глобальные параметры для выполнения пайплайна.
     * @param substitutor
     * @return Экземпляр PipelineExecutorImpl.
     */
    static public PipelineExecutorImpl getPipelineExecutorImpl(IParser parser,
                                                               HashMap<String, String> params,
                                                               ISubstitute substitutor) {
        if (executor == null) {
            executor = new PipelineExecutorImpl(parser, params, substitutor);
        }
        return executor;
    }


    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public Stream execute(String inputString) throws IOException {
        String inputWithArgs = substitutor.substitute(inputString, globalArgs);
        List<ICommand> commands = parser.parse(inputWithArgs);
        Stream curStream = new StreamImpl();
        for (ICommand command : commands) {
            if (command instanceof ExitCommand) {
                isFinished = true;
                break;
            }
            if (command instanceof IChangeContext) {
                ((IChangeContext) command).modifyContext(globalArgs);
            }

            command.execute(curStream);
        }
        return curStream;
    }
}
