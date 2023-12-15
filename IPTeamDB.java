import java.sql.*;

public class IPTeamDB {

    // JDBC URL, username, and password of MySQL server
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chan";
    private static final String USER = "root";
    private static final String PASS = "charna@1234gH";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            ResultSet rs;

            // Example of SELECT operation
            sql = "SELECT * FROM TeamMember";
            rs = stmt.executeQuery(sql);
            System.out.println("SELECT Result:");
            while (rs.next()) {
                int memberID = rs.getInt("MemberID");
                String memberName = rs.getString("MemberName");
                int deptID = rs.getInt("DeptID");
                System.out.println("MemberID: " + memberID + ", MemberName: " + memberName + ", DeptID: " + deptID);
            }

            // Example of INSERT operation
            sql = "INSERT INTO TeamMember (MemberID, MemberName, DeptID) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, 6);
                preparedStatement.setString(2, "NewMember");
                preparedStatement.setInt(3, 2);
                preparedStatement.executeUpdate();
            }

            // Example of UPDATE operation
            sql = "UPDATE TeamMember SET MemberName = ? WHERE MemberID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, "UpdatedMember");
                preparedStatement.setInt(2, 6);
                preparedStatement.executeUpdate();
            }

            // Example of DELETE operation
            sql = "DELETE FROM TeamMember WHERE MemberID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, 6);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Finally block used to close resources
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
