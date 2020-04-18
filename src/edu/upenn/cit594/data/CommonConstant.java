package edu.upenn.cit594.data;

import java.util.regex.Pattern;

public class CommonConstant {
    private static final String ZIP_CODE_REGEX = "\\d{5}";
    public static final Pattern ZIP_CODE_PATTERN = Pattern.compile(ZIP_CODE_REGEX);
    public static final String COMMA_REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    public static final String SPACE_REGEX = " ";
}
