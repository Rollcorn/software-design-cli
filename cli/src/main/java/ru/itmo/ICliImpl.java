package ru.itmo;

import ru.itmo.processing.IPipelineExecutor;
import ru.itmo.processing.PipelineExecutorImpl;
import ru.itmo.processing.parser.IParser;
import ru.itmo.processing.parser.ParserSimple;
import ru.itmo.processing.parser.SubstituteSimple;
import ru.itmo.streams.Stream;

import java.util.HashMap;
import java.util.Scanner;

public class ICliImpl implements ICli {
    IParser parser;

    @Override
    public void run() {
        // ExternService -> getGlobalArgs -> executeGlobalProgram
        HashMap<String, String> globalArgs = new HashMap<>();
        globalArgs.put("path", "$PATH:/some/path");

        // читаем вход в цикле до 'exit'
        IPipelineExecutor pipelineExecutor = PipelineExecutorImpl.getPipelineExecutorImpl(
                new ParserSimple(),
                globalArgs,
                new SubstituteSimple()
        );


        while (!pipelineExecutor.isFinished()) {
            System.out.print(">>");
            Scanner in = new Scanner(System.in);
            String inputString = in.next();
//            e.readParam(inputString);
            // выполняем команды
            Stream str = pipelineExecutor.execute(inputString);
            System.out.println("str result");

            // решаю как выводить
        }
    }
}
