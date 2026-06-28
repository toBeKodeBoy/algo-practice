package algo.tool.sql;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SqlFormatterTest {

  private final SqlFormatter formatter = new SqlFormatter();

  @Test
  void formatsMinifiedSelect() throws Exception {
    String input = "select a,b from t where x=1";
    String formatted = formatter.format(input);

    assertTrue(formatted.contains("\n"), "formatted SQL should contain line breaks");
    assertTrue(formatted.contains("SELECT"), "keywords should be uppercased");
    assertTrue(formatted.contains("FROM"));
    assertTrue(formatted.contains("WHERE"));

    CCJSqlParserUtil.parseStatements(formatted);
  }

  @Test
  void formatsEmptyString() {
    assertEquals("", formatter.format(""));
    assertEquals("", formatter.format("   "));
    assertEquals("", formatter.format("\n\t"));
  }

  @Test
  void invalidSqlReturnsRaw() {
    String input = "SELEC * FORM bad";
    assertEquals(input, formatter.format(input));
  }
}
