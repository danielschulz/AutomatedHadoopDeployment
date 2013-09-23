package de.inovex.academy.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Central {

    // constants
    /**
     * The char the keys/values and values/values are separated by.
     */
    protected static final String RAW_COLUMN_DEVIDER_CHAR =         "\t";

    /**
     * The mapper's output for each key to the combiner.
     */
    protected static final IntWritable SINGLE_RESULT =              new IntWritable(1);

    /**
     * A pattern for the OpenStreet map data to identify GDP indicators.
     */
    private static final Pattern SPLIT_FILE_NAME_TO_STATE_PATTERN = Pattern.compile("^(\\w+(\\-\\w+)*)+\\-nodes\\.txt$");

    /**
     * Extracts the states name from the mapper's context.
     *
     * @param context The context of the mapper
     * @return The lowercase name with the dashes like "bayern" or "baden-wuerttemberg"
     * @see /de/inovex/academy/hadoop/MapperTest.java
     */
    @SuppressWarnings("UnusedDeclaration")
    static String getStateName(final Mapper.Context context) {

        if (null != context && null != context.getInputSplit() &&
                context.getInputSplit() instanceof FileSplit &&
                null != ((FileSplit) context.getInputSplit()).getPath() &&
                null != ((FileSplit) context.getInputSplit()).getPath().getName()) {

            return extractStateNameByPattern(((FileSplit) context.getInputSplit()).getPath().getName());
        }

        return null;
    }

    /**
     * Extracts the states name given the correct value column.
     *
     * @param fileName The columns full string ending with "-nodes.txt"
     * @return The lowercase name with the dashes like "bayern" or "baden-wuerttemberg"
     * @see /de/inovex/academy/hadoop/MapperTest.java
     */
    static String extractStateNameByPattern(final String fileName) {
        final Matcher matcher = SPLIT_FILE_NAME_TO_STATE_PATTERN.matcher(fileName);
        String res = null;
        matcher.find();
        final String cand = matcher.group(1);
        if (null != cand) {
            res = cand;
        }

        return res;
    }
}
