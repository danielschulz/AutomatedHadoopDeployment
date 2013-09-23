package de.inovex.academy.hadoop;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
 * The mapper class first called on the raw inputs. It reads the M&M data as is and return it color-wise to it's
 * combiner.
 */
public class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, IntWritable> {

    /**
     * Reads the raw M&M data with a dummy key and the data in value.
     *
     * @param key Will be dropped
     * @param value The Text from the data file
     * @param context The execution's mapper context
     * @throws IOException Any IOException that may occur
     * @throws InterruptedException Any InterruptedException that may occur
     */
    @Override
    public void map(final LongWritable key, final Text value, final Context context)
            throws IOException, InterruptedException {

        if (null != value && StringUtils.isNotEmpty(value.toString())) {

            // the column texts of the input splits to this mapper
            final String[] splitColumns = value.toString().split(Central.RAW_COLUMN_DEVIDER_CHAR);
            final String rawColor = splitColumns[0];

            if (StringUtils.isNotEmpty(rawColor)) {

                // color information of this split
                final Text color = new Text(rawColor);
                context.write(color, Central.SINGLE_RESULT);
            }
        }
    }
}
