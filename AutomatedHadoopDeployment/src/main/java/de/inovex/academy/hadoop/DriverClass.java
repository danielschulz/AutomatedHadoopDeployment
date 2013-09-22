package de.inovex.academy.hadoop;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class DriverClass extends Configured implements Tool {

    private static final int MAP_REDUCE_JOB_COUNT =                             5;
    private static final String MAP_REDUCE_JOB_NAME =                           "count M&M colors";

    private static final Class<? extends Mapper> MAPPER =                       Mapper.class;
    private static final Class<? extends Reducer> COMBINER =                    Reducer.class;
    private static final Class<? extends Reducer> REDUCER =                     Reducer.class;

    private static final Class<? extends Writable> OUTPUT_MAP_KEY_CLASS =       Text.class;
    private static final Class<? extends Writable> OUTPUT_MAP_VALUE_CLASS =     IntWritable.class;
    private static final Class<? extends Writable> OUTPUT_KEY_CLASS =           Text.class;
    private static final Class<? extends Writable> OUTPUT_VALUE_CLASS =         IntWritable.class;

    @Override
    public int run(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        final Job job = new Job();

        job.setJarByClass(Mapper.class);
        job.setJobName(MAP_REDUCE_JOB_NAME);
        job.setNumReduceTasks(MAP_REDUCE_JOB_COUNT);

        FileInputFormat.addInputPath(job,   new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(MAPPER);
        job.setCombinerClass(COMBINER);
        job.setReducerClass(REDUCER);

        job.setMapOutputKeyClass(OUTPUT_MAP_KEY_CLASS);
        job.setMapOutputValueClass(OUTPUT_MAP_VALUE_CLASS);

        job.setOutputKeyClass(OUTPUT_KEY_CLASS);
        job.setOutputValueClass(OUTPUT_VALUE_CLASS);

        // run MR
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        final DriverClass driverClass = new DriverClass();
        final int result = ToolRunner.run(driverClass, args);

        System.exit(result);
    }
}
