package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;
import ru.itmo.utils.StreamDescriptor;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CatCommand implements ICommand {
    private List<String> args_;
    private List<String> flag_;

    public CatCommand() {
        this.args_ = new ArrayList<>();
        this.flag_ = new ArrayList<>();
    }

    public CatCommand(List<String> args_, List<String> flag_) {
        this.args_ = args_;
        this.flag_ = flag_;
    }

    public List<String> getArgs_() {
        return args_;
    }

    public void setArgs_(List<String> args_) {
        this.args_ = args_;
    }

    public List<String> getFlag_() {
        return flag_;
    }

    public void setFlag_(List<String> flag_) {
        this.flag_ = flag_;
    }

    private String check_valid_flags(List<String> flag_) {
        List<String> validFlags = Arrays.asList("-b", "-n", "-e");
        for (String flag : flag_) {
            if (!validFlags.contains(flag)) {
                return flag;
            }
        }
        return "";
    }

    private int chose_numeric(List<String> flag_) {
        int last_b = flag_.lastIndexOf("-b");
        int last_n = flag_.lastIndexOf("-n");
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
        if (!check_valid_flags(this.flag_).isEmpty()) {
            String res = "cat: неверный ключ — " + check_valid_flags(this.flag_);
            stream.put(res, StreamDescriptor.ERROR, false);
//            return stream;
        }

        if (this.args_.isEmpty()) {
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
                String res = "cat: что-то с текстом не то" + check_valid_flags(this.flag_);
                stream.put(res, StreamDescriptor.ERROR, false);
            }
//            }
        } else {
            stream.remove(StreamDescriptor.OUTPUT);
            for (String filename : args_) {
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