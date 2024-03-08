package ru.itmo.processing.commands;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.itmo.streams.Stream;
import ru.itmo.utils.StreamDescriptor;

import java.io.*;
import java.util.*;


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
            if (item.startsWith("-")) { flags.add(item); }
            else { args.add(item); }
        }
    }

    private String checkValidFlags(List<String> flags) {
        List<String> validFlags = Arrays.asList("-b", "-n", "-e");
        for (String flag : flags) {
            if (!validFlags.contains(flag)) { return flag; }
        }
        return "";
    }

    private int choseNumeric(List<String> flags) {
        int last_b = flags.lastIndexOf("-b");
        int last_n = flags.lastIndexOf("-n");

        if (last_n == -1 & last_b == -1) { return 0; }
        else if (last_n > last_b) { return 1; }
        else { return 2; }
    }

    private void catDataInputFile(Stream stream){
        stream.remove(StreamDescriptor.OUTPUT);

        for (String filename : args) {
            try (InputStream is = getClass().getResourceAsStream(filename)) {
                if (is == null) {
                    throw new FileNotFoundException();
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line = br.readLine();
                StringBuilder sb = new StringBuilder();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                    if (line != null) {
                        sb.append("\n");
                    }
                }
                doFlags(sb.toString(), choseNumeric(flags), stream);

            } catch (FileNotFoundException e) {
                stream.put("cat: " + filename + ": Нет такого файла или каталога",
                        StreamDescriptor.ERROR, false
                );
            } catch (Exception e) {
                stream.put("Something goes wrong on execution command" + getClass().getName(), StreamDescriptor.ERROR, false);
            }
        }

    }

    private void catDataStream(Stream stream){
        stream.remove(StreamDescriptor.OUTPUT);
        try (BufferedReader br = new BufferedReader(new StringReader(args.get(0)))) {
            String line;
            while ((line = br.readLine()) != null) {
                doFlags(line, choseNumeric(flags), stream);
            }
            stream.put("\n", StreamDescriptor.OUTPUT, false);
        } catch (Exception e) {
            String res = "cat: что-то с текстом не то" + checkValidFlags(flags);
            stream.put(res, StreamDescriptor.ERROR, false);
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

        if (args.isEmpty() || Objects.equals(args.get(0), "")){
            args.clear();
            args.add(stream.get(StreamDescriptor.OUTPUT));
            catDataStream(stream);
        }
        else{
            catDataInputFile(stream);
        }
    }

    private void doFlags(String string, int i, Stream stream) {
        String[] lines = string.split("\n");
        switch (i) {
            case 0:
                stream.put(string, StreamDescriptor.OUTPUT, false);
                break;
            case 1:
                for (int j = 0; j < lines.length; j++) {
                    stream.put((j + 1 + "\t" + lines[j]), StreamDescriptor.OUTPUT, false);
                    if (j + 1 != lines.length) {
                        stream.put("\n", StreamDescriptor.OUTPUT, false);
                    }
                }
                break;
            case 2:
                int count = 1;
                for (String line : lines) {
                    if (!line.isEmpty()) {
                        stream.put((count + "\t" + line), StreamDescriptor.OUTPUT, false);
                        count++;
                    } else {
                        stream.put("$", StreamDescriptor.OUTPUT, false);
                    }
                }
                break;
        }
    }
}
