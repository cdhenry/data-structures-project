package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class Reader {
    private static final String FILE_ERR_MSG = "property value file must exist and be readable";
    protected Scanner readIn;
    protected FileReader file;

    Reader(String filename) {
        try {
            FileReader file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            readIn = new Scanner(file);
        } catch (IOException e) {
            System.out.println(FILE_ERR_MSG);
            System.exit(4);
        }
    }

    public <V> void updateMap(Map<Integer, List<V>> map, Integer key, V value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<V> objectList = new LinkedList<>();
            objectList.add(value);
            map.put(key, objectList);
        }
    }
}