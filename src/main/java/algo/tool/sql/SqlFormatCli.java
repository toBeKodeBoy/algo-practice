package algo.tool.sql;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SqlFormatCli {

    private static final Logger LOG = Logger.getLogger(SqlFormatCli.class.getName());

    private SqlFormatCli() {}

    public static void main(String[] args) {
        Path input = Path.of(SqlFormatPaths.INPUT_FILE);
        Path output = Path.of(SqlFormatPaths.OUTPUT_FILE);
        try {
            new SqlFormatService().formatFile(input, output);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Failed to format SQL file: input=" + input + ", output=" + output, e);
            System.exit(1);
        }
    }
}
