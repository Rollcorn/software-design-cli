package ru.itmo.processing.commands;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.itmo.streams.Stream;
import ru.itmo.utils.StreamDescriptor;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


@NoArgsConstructor
@AllArgsConstructor
public class CatCommand implements ICommand {
    private List<String> args;
    private List<String> flag;

    private String checkValidFlags(List<String> flags) {
        List<String> validFlags = Arrays.asList("-b", "-n", "-e");
        for (String flag : flags) {
            if (!validFlags.contains(flag)) {
                return flag;
            }
        }
        return "";
    }

    private int choseNumeric(List<String> flags) {
        int last_b = flags.lastIndexOf("-b");
        int last_n = flags.lastIndexOf("-n");
        if (last_n == -1 & last_b == -1) {
            return 0;
        } else if (last_n > last_b) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void execute(Stream stream) {
        if (!checkValidFlags(this.flag).isEmpty()) {
            String res = "cat: неверный ключ — " + checkValidFlags(this.flag);
            stream.put(res, StreamDescriptor.ERROR, false);
//            return stream;
        }

        if (this.args.isEmpty()) {
            //Просто выводим строку из Stream output
//            this.args_ = stream.get(StreamDescriptor.OUTPUT);
//            for (String word: this.args_){
            try (Scanner scanner = new Scanner(stream.get(StreamDescriptor.OUTPUT))) {
                int i = 1;
                while (scanner.hasNextLine()) {
                    stream.put((Integer.toString(i) + scanner.nextLine()), StreamDescriptor.OUTPUT, false);
                    i++;
                }
            } catch (Exception e) {
                String res = "cat: что-то с текстом не то" + checkValidFlags(this.flag);
                stream.put(res, StreamDescriptor.ERROR, false);
            }
//            }
        } else {
            stream.remove(StreamDescriptor.OUTPUT);
            for (String filename : args) {
                try {
                    File file = new File(filename);
                    FileInputStream fis = new FileInputStream(file);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                    String line;
                    int i = 1;
                    while ((line = br.readLine()) != null) {
                        stream.put((Integer.toString(i) + line), StreamDescriptor.OUTPUT, false);
                        i++;
                    }

                } catch (FileNotFoundException e) {
                    String exception_text = "cat: " + filename + ": Нет такого файла или каталога";
                    stream.put(exception_text, StreamDescriptor.ERROR, false);
                } catch (Exception e) {
                    stream.put("SORRY", StreamDescriptor.ERROR, false);
                }
            }
        }
//        return stream;
    }
}