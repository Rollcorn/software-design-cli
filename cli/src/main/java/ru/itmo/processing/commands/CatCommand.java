package ru.itmo.processing.commands;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.itmo.streams.Stream;
import ru.itmo.utils.StreamDescriptor;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


@NoArgsConstructor
@AllArgsConstructor
public class CatCommand implements ICommand {
    private List<String> args;
    private List<String> flags;
    private List<String> input;

    public CatCommand(List<String> input) {
        this.input = input;
        this.args = new ArrayList<>();
        this.flags = new ArrayList<>();
    }

    private void separateInput() {
        for (String item : input) {
            if (item.startsWith("-")) {
                flags.add(item);
            } else {
                args.add(item);
            }
        }
    }

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
        separateInput();

        if (!checkValidFlags(this.flags).isEmpty()) {
            String res = "cat: неверный ключ — " + checkValidFlags(this.flags);
            stream.put(res, StreamDescriptor.ERROR, false);
            return;
        }

        if (this.args.isEmpty()) {
            try (Scanner scanner = new Scanner(stream.get(StreamDescriptor.OUTPUT))) {
                int i = 1;
                while (scanner.hasNextLine()) {
                    stream.put((Integer.toString(i) + scanner.nextLine()), StreamDescriptor.OUTPUT, false);
                    i++;
                }
            } catch (Exception e) {
                String res = "cat: что-то с текстом не то" + checkValidFlags(this.flags);
                stream.put(res, StreamDescriptor.ERROR, false);
            }
        } else {
            stream.remove(StreamDescriptor.OUTPUT);
            for (String filename : args) {
                try (InputStream is = getClass().getResourceAsStream(filename);
                     BufferedReader br = new BufferedReader(new InputStreamReader(is))
                ) {

                    String line = br.readLine();
                    int i = 1;
                    while (line != null) {
                        stream.put(line, StreamDescriptor.OUTPUT, false);
                        line = br.readLine();
                        if (line != null) {
                            stream.put("\n", StreamDescriptor.OUTPUT, false);
                        }
                    }

                } catch (FileNotFoundException e) {
                    String exception_text = "cat: " + filename + ": Нет такого файла или каталога";
                    stream.put(exception_text, StreamDescriptor.ERROR, false);
                } catch (Exception e) {
                    stream.put("SORRY", StreamDescriptor.ERROR, false);
                }
            }
        }
    }
}
