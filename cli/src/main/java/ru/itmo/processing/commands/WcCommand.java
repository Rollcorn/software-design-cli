package ru.itmo.processing.commands;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WcCommand  implements ICommand {
    private List<String> args_;
    private List<String> flags_;
    HashMap<String, Integer> dataMap;

    /**
     * Create new object WcCommand without params
     */
    public WcCommand(){
        this.args_ = new ArrayList<>();
        this.flags_ = new ArrayList<>();
        dataMap = new HashMap<>();
        dataMap.put("totalLineCount", 0);
        dataMap.put("totalWordCount", 0);
        dataMap.put("totalByteCount", 0);
        dataMap.put("totalCharCount", 0);
    }

    /**
     * Create new object WcCommand with params
     * @param args argiments of command wc
     * @param flags flags of command wc
     */
    public WcCommand(List<String> args, List<String> flags){
        this.args_ = args;
        this.flags_ = flags;
        dataMap = new HashMap<>();
        dataMap.put("totalLineCount", 0);
        dataMap.put("totalWordCount", 0);
        dataMap.put("totalByteCount", 0);
        dataMap.put("totalCharCount", 0);
    }

    /**
     * Get arguments of command wc
     *
     * @return List(String)
     */
    public List<String> getArgs_() {
        return args_;
    }

    /**
     * Get flags of command wc
     *
     * @return List(String)
     */
    public List<String> getFlags_() {
        return flags_;
    }

    /**
     * Set arguments of command wc
     *
     * @param args set new arguments for command wc
     *
     */
    public void setArgs_(List<String> args) {
        this.args_ = args;
    }

    /**
     * Set flags of command wc
     *
     * @param args set new flags for command wc
     *
     */
    public void setFlags_(List<String> flags) {
        this.flags_ = flags;
    }

    public Integer getValueFromKey(String key){
        return dataMap.get(key);
    }

    public void setValueFromKey(String key, Integer value){
        dataMap.set(key, value);
    }

    private String check_valid_flags(List<String> flags_){
        for (String flag: flags_){
            if (!validFlags.contains(flag)){
                return flag;
            }
        }
        return "";
    }

    @Override
    public void execute(Stream stream){
        /*
        Если я передаю аргументы команде, то я игнорирую поток данных и
        обрабатываю уже файлы (в цикле, поскольку аргументов может быть
        больше 1го. При этом необходимо, чтобы в конце в поток было записано
        общее количество посчитанных данных
        Если у меня нет аргументов, то я беру данные из потока для анализа
        При этом я считаю сразу все, а потом уже в зависимости от флагов
        буду записывать в поток то, что нужно.
        При этом если файлов для анализа больше 1го, то в цикле я каждый
        раз делаю запись в поток данных, что получили. При этом, если файл
        не открылся в цикле, то его ошибку записываем в поток все равно,
        поэтому может получиться так, что идет строка норм с выводом, потом
        ошибка потом уже вывод total
         */

        if (check_valid_flags(this.flags_) != ""){
            String res = "wc: неверный ключ — " ++ check_valid_flags(this.flags_);
            stream.remove();
            stream.add(res);
            return;
        }

        if (args_.isEmpty()) {
            this.args_ = stream.getData();
        }

        stream.remove();
        for (String filename : args_){
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
                    curDataMap.put("charCount", curDataMap.get("charCount") + line.length);

                    List<String> words = line.split("\\s+");
                    curDataMap.put("wordCount", curDataMap.get("wordCount") + words.length);
                }

                String result_iteration = create_string(this.flags_, curDataMap) + filename;
                stream.add(result_iteration);

                dataMap.put("totalLineCount", dataMap.get("totalLineCount") + lineCount);
                dataMap.put("totalWordCount", dataMap.get("totalWordCount") + wordCount);
                dataMap.put("totalByteCount", dataMap.get("totalByteCount") + byteCount);
                dataMap.put("totalCharCount", dataMap.get("totalCharCount") + charCount);

            } catch (FileNotFoundException e) {

                Strind exception_text = "wc: " + filename + ": Нет такого файла или каталога";
                stream.add(exception_text);

            } //Можно ловить еще ошибки по типу отсутствия доступа к файлу
        }
        String result_iteration = create_string(this.flags_, this.dataMap) + "итого";
        stream.add(result_iteration);
        return;
    }

    private String create_string(List<String> flags_, HashMap<String, Integer> map_){
        String res = "";
        if (flags_.isEmpty()){
            String res += String.valueOf(map_.get("lineCount")) + " " + String.valueOf(map_.get("wordCount")) + " " + String.valueOf(map_.get("byteCount"));
            return res;
        }

        if (flags_.contains("-l")){
            res += String.valueOf(map_.get("lineCount")) + " ";
        }
        if (flags_.contains("-w")) {
            res += String.valueOf(map_.get("wordCount")) + " ";
        }
        if (flags_.contains("-c")) {
            res += String.valueOf(map_.get("byteCount")) + " ";
        }
        if (flags_.contains("-m")) {
            res += String.valueOf(map_.get("charCount")) + " ";
        }
        return res;
    }
}