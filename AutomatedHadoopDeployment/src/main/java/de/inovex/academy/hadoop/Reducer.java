package de.inovex.academy.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.Iterator;

/**
 * The reducer and combiner class. It takes the mapper's output and counts the occurrences of M&M colors.
 */
public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {

    /**
     * Takes the M&M color as key and the current count as values. In the combiner phase all values will be one.
     * In reducer and combiner phase they will be added up.
     *
     * @param key The color of the M&M
     * @param values The current count of this color
     * @param context The reducer's / combiner's context
     * @throws IOException Any IOException that may occur
     * @throws InterruptedException Any InterruptedException that may occur
     */
    @Override
    protected void reduce(final Text key, final Iterable<IntWritable> values, final Context context)
            throws IOException, InterruptedException {

        if (null != values) {

            final Iterator<IntWritable> valueIterator = values.iterator();
            int sum = 0;

            // iterate over all values
            while (valueIterator.hasNext()){
                IntWritable next = valueIterator.next();

                // aggregate sum
                if (null != next) {
                    sum += (next.get());
                }
            }

            // write value sum for key
            final IntWritable res = new IntWritable(sum);
            context.write(key, res);
        }
    }
}
