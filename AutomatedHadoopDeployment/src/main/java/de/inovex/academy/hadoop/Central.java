package de.inovex.academy.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Central {

    // constants
    protected static final String RAW_COLUMN_DEVIDER_CHAR =         "\t";
    protected static final IntWritable SINGLE_RESULT =              new IntWritable(1);
    private static final Pattern SPLIT_FILE_NAME_TO_STATE_PATTERN = Pattern.compile("^(\\w+(\\-\\w+)*)+\\-nodes\\.txt$");

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
