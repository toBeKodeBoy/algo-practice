package algo.tool.sql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

public final class SqlPrettyPrinter {

    private static final String INDENT = "  ";

    private static final Pattern CLAUSE_BREAK =
            Pattern.compile(
                    "(?i)\\b(SELECT|FROM|WHERE|GROUP BY|HAVING|ORDER BY|LIMIT|OFFSET|"
                            + "INSERT INTO|VALUES|UPDATE|SET|DELETE FROM|"
                            + "LEFT OUTER JOIN|RIGHT OUTER JOIN|FULL OUTER JOIN|"
                            + "LEFT JOIN|RIGHT JOIN|FULL JOIN|INNER JOIN|CROSS JOIN|JOIN|ON|UNION ALL|UNION)\\b");

    private SqlPrettyPrinter() {}

    public static String format(Statement statement) {
        StringBuilder buffer = new StringBuilder();
        StatementDeParser statementDeParser = new StatementDeParser(buffer);
        statement.accept(statementDeParser);
        return beautify(buffer.toString());
    }

    private static String beautify(String sql) {
        Matcher matcher = CLAUSE_BREAK.matcher(sql);
        StringBuilder result = new StringBuilder();
        int lastEnd = 0;
        boolean firstClause = true;
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                if (!firstClause) {
                    result.append('\n').append(INDENT);
                }
                result.append(sql, lastEnd, matcher.start());
            }
            result.append(matcher.group().toUpperCase());
            lastEnd = matcher.end();
            firstClause = false;
        }
        if (lastEnd < sql.length()) {
            if (!firstClause) {
                result.append('\n').append(INDENT);
            }
            result.append(sql.substring(lastEnd));
        }
        return result.toString().trim();
    }
}
