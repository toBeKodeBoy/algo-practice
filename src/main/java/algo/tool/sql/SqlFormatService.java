package algo.tool.sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

public class SqlFormatService {

    private static final Logger LOG = Logger.getLogger(SqlFormatService.class.getName());

    private final SqlFormatter formatter;

    public SqlFormatService() {
        this(new SqlFormatter());
    }

    SqlFormatService(SqlFormatter formatter) {
        this.formatter = formatter;
    }

    public void formatFile(Path input, Path output) throws IOException {
        Path normalizedInput = validatePath(input);
        Path normalizedOutput = validatePath(output);

        LOG.info("Reading SQL from " + normalizedInput);
        String content = Files.readString(normalizedInput, StandardCharsets.UTF_8);
        String formatted = formatter.format(content);
        writeAtomically(normalizedOutput, formatted);
        LOG.info("Wrote formatted SQL to " + normalizedOutput + ", output length=" + formatted.length());
    }

    private static Path validatePath(Path path) throws IOException {
        if (path.toString().contains("..")) {
            throw new IOException("Path traversal is not allowed: " + path);
        }
        return path.toAbsolutePath().normalize();
    }

    private static void writeAtomically(Path output, String content) throws IOException {
        Path parent = output.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path tempFile = Files.createTempFile(parent != null ? parent : Path.of("."), "sqlfmt-", ".tmp");
        boolean moved = false;
        try {
            Files.writeString(tempFile, content, StandardCharsets.UTF_8);
            try {
                Files.move(tempFile, output, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(tempFile, output, StandardCopyOption.REPLACE_EXISTING);
            }
            moved = true;
        } finally {
            if (!moved) {
                Files.deleteIfExists(tempFile);
            }
        }
    }
}
