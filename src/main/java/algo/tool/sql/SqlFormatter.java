package algo.tool.sql;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;

public class SqlFormatter {

    private static final Logger LOG = Logger.getLogger(SqlFormatter.class.getName());

    public String format(String sql) {
        if (sql == null || sql.isBlank()) {
            LOG.fine("Input SQL is blank, returning empty string");
            return "";
        }

        LOG.info("Formatting SQL, input length=" + sql.length());
        try {
            Statements statements = CCJSqlParserUtil.parseStatements(sql);
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < statements.size(); i++) {
                Statement statement = statements.get(i);
                if (statement == null) {
                    continue;
                }
                if (formatted.length() > 0) {
                    formatted.append(";\n");
                }
                formatted.append(SqlPrettyPrinter.format(statement));
            }
            String result = formatted.toString();
            LOG.info("SQL formatted successfully, output length=" + result.length());
            return result;
        } catch (JSQLParserException e) {
            LOG.log(Level.WARNING, "Failed to parse SQL, returning raw content", e);
            return sql;
        }
    }
}
