package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 */
public abstract class Reader<V> implements MappableByInteger<V> {
    private static final String FILE_ERR_MSG = "property value file must exist and be readable";
    private static final String ZIP_CODE_REGEX = "\\d{5}";
    protected static final Pattern ZIP_CODE_PATTERN = Pattern.compile(ZIP_CODE_REGEX);
    protected static final String COMMA_REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    protected static final String SPACE_REGEX = " ";
    protected Scanner readIn;
    protected FileReader file;

    /**
     * @param filename
     */
    public Reader(String filename) {
        try {
            file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            readIn = new Scanner(file);
        } catch (IOException e) {
            System.out.println(FILE_ERR_MSG);
            System.exit(4);
        }
    }

    /**
     * @param map
     * @param key
     * @param element
     * @param <E>
     */
    public <E> void updateIntegerListMap(Map<Integer, List<E>> map, Integer key, E element) {
        if (map.containsKey(key)) {
            map.get(key).add(element);
        } else {
            List<E> objectList = new LinkedList<>();
            objectList.add(element);
            map.put(key, objectList);
        }
    }
}