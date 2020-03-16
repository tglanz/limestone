package org.tglanz.limestone.tpc;

import org.apache.commons.cli.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationArguments {

    // default benchmark argument value
    public static final String DefaultBenchmark = "ds";

    // default query-path argument value
    public static final String DefaultQueryPath = "tpc/src/main/resources/ds-queries/-3.sql";

    // default scale argument value
    public static final String DefaultScale = "1";

    // a delimited string of all possible benchmarks
    public static final String BenchmarkChoices = Stream.of(Benchmark.values())
            .map(Object::toString)
            .collect(Collectors.joining(", "));

    public final String queryPath;
    public final Benchmark benchmark;
    public final float scale;

    private ApplicationArguments(String queryPath, Benchmark benchmark, float scale) {
        this.queryPath = queryPath;
        this.benchmark = benchmark;
        this.scale = scale;
    }

    private static Options createCommandLineOptions() {
        Options options = new Options();

        options.addOption("h", "help", false, "show help message");

        options.addOption("b", "benchmark", true, String.format(
                "determines which benchmark to run. default: %s, choices: %s",
                DefaultBenchmark, BenchmarkChoices));

        options.addOption("q", "query-path", true, String.format(
                "determines a file path the query to run. default: %s",
                DefaultQueryPath));

        options.addOption("s", "scale", true, String.format(
                "determines the benchmark's scale. default: %s",
                DefaultScale));

        return options;
    }

    public static ApplicationArguments parse(String[] args) {
        Options options = createCommandLineOptions();

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("help")) {
                new HelpFormatter().printHelp("[OPTIONS]", options);
                return null;
            }

            Benchmark benchmark = Benchmark.valueOf(
                    commandLine.getOptionValue("benchmark", DefaultBenchmark).toUpperCase());

            String queryPath = commandLine.getOptionValue("query-path", DefaultQueryPath);

            float scale = Float.parseFloat(commandLine.getOptionValue("scale", DefaultScale));

            return new ApplicationArguments(queryPath, benchmark, scale);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            new HelpFormatter().printHelp("[OPTIONS]", options);
            return null;
        }
    }
}
