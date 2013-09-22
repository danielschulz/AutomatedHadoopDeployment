package de.inovex.academy.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.Iterator;

public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {

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
