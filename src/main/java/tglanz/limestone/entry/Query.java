package tglanz.limestone.entry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class Query {
    public static void main(String[] args)
            throws Exception {
        String modelPath = "src/test/resources/model.jsonx";
        String query = "select * from lineitem limit 1";

        Properties properties = new Properties();
        properties.put("model", modelPath);

        try (Connection connection = DriverManager.getConnection("jdbc:calcite:", properties)) {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            System.out.println(rs);
        }
    }
}
