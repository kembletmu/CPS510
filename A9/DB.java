import java.sql.*;

public class DB {

    private static final String URL = "jdbc:sqlite:hotel.db";

    // Get a connection and turn on foreign keys
    public static Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    // Run a batch of non-SELECT statements (CREATE, DROP, INSERT, UPDATE, DELETE)
    public static void runBatch(String[] sqlStatements) {
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);
            try (Statement stmt = conn.createStatement()) {
                for (String raw : sqlStatements) {
                    if (raw == null) continue;
                    String sql = raw.trim();
                    if (sql.isEmpty()) continue;
                    stmt.executeUpdate(sql);
                }
            }
            conn.commit();
            System.out.println("Batch executed successfully.");
        } catch (SQLException e) {
            System.out.println("Error executing batch:");
            System.out.println(e.getMessage());
        }
    }

    // Run a single non-SELECT statement
    public static void runUpdate(String sql) {
        runBatch(new String[]{sql});
    }

    // Run a SELECT and print the results to the console
    public static void runQuery(String sql) {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();

            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= cols; i++) {
                    row.append(md.getColumnName(i))
                       .append("=")
                       .append(rs.getString(i))
                       .append("  ");
                }
                System.out.println(row);
            }
            System.out.println("Query finished.\n");
        } catch (SQLException e) {
            System.out.println("Error running query:");
            System.out.println(e.getMessage());
        }
    }
}
