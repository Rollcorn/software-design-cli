package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;
import ru.itmo.utils.StreamDescriptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WcCommand  implements ICommand {
    private List<String> input;
    private List<String> args;
    private List<String> flags;
    private HashMap<String, Integer> dataMap;

    public WcCommand(List<String> input){
        this.input = input;
        this.args = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.dataMap = new HashMap<>();
        dataMap.put("totalLineCount", 0);
        dataMap.put("totalWordCount", 0);
        dataMap.put("totalByteCount", 0);
        dataMap.put("totalCharCount", 0);
    }

    public List<String> getInput() {
        return input;
    }

    public void setInput(List<String> input) {
        this.input = input;
    }

    public HashMap<String, Integer> getDataMap(){
        return dataMap;
    }

    public void setDataMap(HashMap<String, Integer> dataMapNew){
        this.dataMap = dataMapNew;
    }

    public List<String> getArgs() {
        return args;
    }
    public void setArgs(List<String> argsNew){
        this.args = argsNew;
    }
    public List<String> getFlags() {
        return flags;
    }
    public void setFlags(List<String> flagsNew){
        this.flags = flagsNew;
    }

    private void separateInput(){
        for(String item: input){
            if (item.startsWith("-")){
                flags.add(item);
            } else {
                args.add(item);
            }
        }
    }

    public Integer getValueFromKey(String key){
        return dataMap.get(key);
    }

    public void setValueFromKey(String key, Integer value){
        this.dataMap.put(key, value);
    }

    private String check_valid_flags(List<String> flags){
        List<String> validFlags = Arrays.asList("-l", "-w", "-c", "-m");
        for (String flag: flags){
            if (!validFlags.contains(flag)){
                return flag;
            }
        }
        return "";
    }

    @Override
    public void execute(Stream stream){
        separateInput();

        if (!check_valid_flags(this.flags).isEmpty()){
            String res = "wc: неверный ключ — " + check_valid_flags(this.flags);
            stream.put(res, StreamDescriptor.ERROR, true);
            return;
        }

        if (args.isEmpty()) {
            this.args.add(stream.get(StreamDescriptor.OUTPUT));
        }

        stream.remove(StreamDescriptor.OUTPUT);
        for (String filename : args){
            try{
                File file = new File(filename);
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                HashMap<String, Integer> curDataMap = new HashMap<>();
                curDataMap.put("lineCount", 0);
                curDataMap.put("wordCount", 0);
                curDataMap.put("byteCount", 0);
                curDataMap.put("charCount", 0);

                String line;
                while ((line = br.readLine()) != null){
                    curDataMap.put("lineCount", curDataMap.get("lineCount") + 1);
                    curDataMap.put("byteCount", curDataMap.get("byteCount") + line.getBytes().length);
                    curDataMap.put("charCount", curDataMap.get("charCount") + line.length());

                    List<String> words = List.of(line.split("\\s+"));
                    curDataMap.put("wordCount", curDataMap.get("wordCount") + words.toArray().length);
                }

                String result_iteration = create_string(this.flags, curDataMap) + filename;
                stream.put(result_iteration, StreamDescriptor.OUTPUT, false);

                dataMap.put("totalLineCount", dataMap.get("totalLineCount") + curDataMap.get("lineCount"));
                dataMap.put("totalWordCount", dataMap.get("totalWordCount") + curDataMap.get("wordCount"));
                dataMap.put("totalByteCount", dataMap.get("totalByteCount") + curDataMap.get("byteCount"));
                dataMap.put("totalCharCount", dataMap.get("totalCharCount") + curDataMap.get("charCount"));

            } catch (FileNotFoundException e) {

                String exception_text = "wc: " + filename + ": Нет такого файла или каталога";
                stream.put(exception_text, StreamDescriptor.ERROR, false);

            } catch (Exception e) {
                stream.put("SORRY", StreamDescriptor.ERROR, false);
            }//Можно ловить еще ошибки по типу отсутствия доступа к файлу
        }
        String result_iteration = create_string(this.flags, this.dataMap) + "итого";
        stream.put(result_iteration, StreamDescriptor.OUTPUT, false);
//        return stream;
    }

    private String create_string(List<String> flags_, HashMap<String, Integer> map_) {
        String res = "";
        if (flags_.isEmpty()) {
            res += map_.get("lineCount") + " " + map_.get("wordCount") + " " + map_.get("byteCount");
            return res;
        }

        if (flags_.contains("-l")) {
            res += map_.get("lineCount") + " ";
        }
        if (flags_.contains("-w")) {
            res += map_.get("wordCount") + " ";
        }
        if (flags_.contains("-c")) {
            res += map_.get("byteCount") + " ";
        }
        if (flags_.contains("-m")) {
            res += map_.get("charCount") + " ";
        }
        return res;
    }
}