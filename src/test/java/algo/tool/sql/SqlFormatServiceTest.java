package algo.tool.sql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SqlFormatServiceTest {

  private final SqlFormatService service = new SqlFormatService();

  @TempDir
  Path tempDir;

  @Test
  void formatsValidSqlFile() throws IOException {
    Path input = tempDir.resolve("input.sql");
    Path output = tempDir.resolve("output.sql");
    Files.writeString(input, "select a,b from t where x=1");

    service.formatFile(input, output);

    assertTrue(Files.exists(output));
    String result = Files.readString(output);
    assertTrue(result.contains("SELECT"));
    assertTrue(result.contains("\n"));
  }

  @Test
  void handlesEmptyFile() throws IOException {
    Path input = tempDir.resolve("input.sql");
    Path output = tempDir.resolve("output.sql");
    Files.writeString(input, "");

    service.formatFile(input, output);

    assertTrue(Files.exists(output));
    assertEquals("", Files.readString(output));
  }

  @Test
  void invalidSqlCopiesRaw() throws IOException {
    String raw = "SELEC * FORM bad";
    Path input = tempDir.resolve("input.sql");
    Path output = tempDir.resolve("output.sql");
    Files.writeString(input, raw);

    service.formatFile(input, output);

    assertEquals(raw, Files.readString(output));
  }

  @Test
  void inputFileNotFound() {
    Path input = tempDir.resolve("missing.sql");
    Path output = tempDir.resolve("output.sql");

    assertThrows(IOException.class, () -> service.formatFile(input, output));
    assertFalse(Files.exists(output));
  }

  @Test
  void rejectsPathTraversal() {
    Path input = tempDir.resolve("..").resolve("outside.sql");
    Path output = tempDir.resolve("output.sql");

    assertThrows(IOException.class, () -> service.formatFile(input, output));
    assertFalse(Files.exists(output));
  }
}
