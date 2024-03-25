package ru.itmo.processing.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.streams.Stream;
import ru.itmo.utils.StreamDescriptor;

import java.io.*;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
public class WcCommand  implements ICommand {

    //Getters and setters
    //Fields of class
    private List<String> input;
    private List<String> args;
    private List<String> flags;
    private HashMap<String, Integer> dataMap;

    //Constructor
    public WcCommand(List<String> input){
        this.input = input;
        this.args = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.dataMap = new HashMap<>();
        dataMap.put("lineCount", 0);
        dataMap.put("wordCount", 0);
        dataMap.put("byteCount", 0);
        dataMap.put("charCount", 0);
    }

    public Integer getValueFromKey(String key){ return dataMap.get(key); }

    public void setValueFromKey(String key, Integer value){ this.dataMap.put(key, value); }

    //Methods
    private void separateInput(){
        for(String item: input){
            if (item.startsWith("-")){ flags.add(item);
            }
            else { args.add(item); }
        }
    }

    private String check_valid_flags(List<String> flags){
        List<String> validFlags = Arrays.asList("-l", "-w", "-c", "-m");
        for (String flag: flags){
            if (!validFlags.contains(flag)){ return flag; }
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

        if (args.isEmpty() || Objects.equals(args.get(0), "")) {
            args.clear();
            args.add(stream.get(StreamDescriptor.OUTPUT));
            readDataStream(stream);
        }
        else { readDataInputFile(stream); }
    }

    private HashMap<String, Integer> createNewHashMap(){
        HashMap<String, Integer> curDataMap = new HashMap<>();
        curDataMap.put("lineCount", 0);
        curDataMap.put("wordCount", 0);
        curDataMap.put("byteCount", 0);
        curDataMap.put("charCount", 0);
        return curDataMap;
    }

    private void updateHashMap(HashMap<String, Integer> curDataMap, String line){
        curDataMap.put("lineCount", curDataMap.get("lineCount") + 1);
        curDataMap.put("byteCount", curDataMap.get("byteCount") + line.getBytes().length);
        curDataMap.put("charCount", curDataMap.get("charCount") + line.length());

        List<String> words = List.of(line.split("\\s+"));
        curDataMap.put("wordCount", curDataMap.get("wordCount") + words.toArray().length);
    }

    private void updateHashMap(HashMap<String, Integer> dataMap, HashMap<String, Integer> curDataMap){
        dataMap.put("lineCount", dataMap.get("lineCount") + curDataMap.get("lineCount"));
        dataMap.put("wordCount", dataMap.get("wordCount") + curDataMap.get("wordCount"));
        dataMap.put("byteCount", dataMap.get("byteCount") + curDataMap.get("byteCount"));
        dataMap.put("charCount", dataMap.get("charCount") + curDataMap.get("charCount"));
    }

    private void readDataStream(Stream stream){

        stream.remove(StreamDescriptor.OUTPUT);

        try (BufferedReader br = new BufferedReader(new StringReader(args.get(0)))) {

            String line;
            HashMap<String, Integer> curDataMap = createNewHashMap();

            while ((line = br.readLine()) != null) {
                updateHashMap(curDataMap, line);

                String resultIteration = create_string(this.flags, curDataMap);
                stream.put(resultIteration, StreamDescriptor.OUTPUT, false);
            }

        } catch (IOException e) {
            stream.put(e.getMessage(), StreamDescriptor.ERROR, true);
        }
    }

    private void readDataInputFile(Stream stream){
        stream.remove(StreamDescriptor.OUTPUT);
        for (String filename : args){
            try (InputStream is = getClass().getResourceAsStream(filename)) {

                if (is == null) {
                    throw new FileNotFoundException();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                HashMap<String, Integer> curDataMap = createNewHashMap();

                while ((line = reader.readLine()) != null){
                    updateHashMap(curDataMap, line);
                }

                String result_iteration = create_string(this.flags, curDataMap) + " " + filename + "\n";
                stream.put(result_iteration, StreamDescriptor.OUTPUT, false);

                updateHashMap(dataMap, curDataMap);

            } catch (FileNotFoundException e) {

                String exception_text = "wc: " + filename + ": Нет такого файла или каталога";
                stream.put(exception_text, StreamDescriptor.ERROR, false);
                return;

            } catch (Exception e) {
                stream.put("SORRY", StreamDescriptor.ERROR, false);
                return;
            }
        }
        if (args.size() > 1){
            String result_iteration = create_string(this.flags, this.dataMap) + " итого\n";
            stream.put(result_iteration, StreamDescriptor.OUTPUT, false);
        }
    }

    private String create_string(List<String> flags_, HashMap<String, Integer> map_) {
        String res = "";

        if (flags_.isEmpty()) {
            res += map_.get("lineCount") + " " + map_.get("wordCount") + " " + map_.get("byteCount");
            return res;
        }

        if (flags_.contains("-l")) { res += map_.get("lineCount"); }
        if (flags_.contains("-w")) { res += map_.get("wordCount"); }
        if (flags_.contains("-c")) { res += map_.get("byteCount"); }
        if (flags_.contains("-m")) { res += map_.get("charCount"); }

        return res;
    }
}