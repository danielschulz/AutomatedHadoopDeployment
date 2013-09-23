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

    /**
     * The job's human-readable name for the web interface.
     */
    private static final String MAP_REDUCE_JOB_NAME =                           "count M&M colors";

    /**
     * The amount of reducers.
     */
    private static final int MAP_REDUCE_JOB_COUNT =                             5;

    /**
     * The mapper, combiner, and reducer phase classes.
     */
    private static final Class<? extends Mapper> MAPPER =                       Mapper.class;
    private static final Class<? extends Reducer> COMBINER =                    Reducer.class;
    private static final Class<? extends Reducer> REDUCER =                     Reducer.class;

    /**
     * The key/value output classes for the mapper.
     */
    private static final Class<? extends Writable> OUTPUT_MAP_KEY_CLASS =       Text.class;
    private static final Class<? extends Writable> OUTPUT_MAP_VALUE_CLASS =     IntWritable.class;

    /**
     * The key/value output classes for the reducer and combiner.
     */
    private static final Class<? extends Writable> OUTPUT_KEY_CLASS =           Text.class;
    private static final Class<? extends Writable> OUTPUT_VALUE_CLASS =         IntWritable.class;

    /**
     * Sets up the job with the parameters given above in constants.
     *
     * @param args The command line options for the mapper's input and reducer's output destinations
     * @return The exit code for the JVM: 0 for "everything is fine" and 1 otherwise
     * @throws IOException Thrown when the mappers input does not exist
     * @throws ClassNotFoundException Thrown when a classes definition is missing
     * @throws InterruptedException Thrown on interrupting the MapReduce job
     */
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

    /**
     * Setp up the job with the run methode above and return it's exit code from return.
     *
     * @param args The command line options for the mapper's input and reducer's output destinations
     * @throws Exception Any exception that may occur
     */
    public static void main(String[] args) throws Exception {
        final DriverClass driverClass = new DriverClass();
        final int result = ToolRunner.run(driverClass, args);

        System.exit(result);
    }
}
