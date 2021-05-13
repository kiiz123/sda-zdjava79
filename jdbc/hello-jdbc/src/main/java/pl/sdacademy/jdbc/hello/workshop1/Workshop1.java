package pl.sdacademy.jdbc.hello.workshop1;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Workshop1 {
    public static void main(String[] args) {
        List<String> countries = getCountries();
        final String countriesString = String.join("\n", countries);
        System.out.println(countriesString);
    }

    private static List<String> getCountries() {
        //throw new UnsupportedOperationException("TODO");
        List<String> result = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "Pomidor1#")) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select name from country where continent = 'Europe' order by name;")) {
                ResultSet rs = preparedStatement.executeQuery();
                while ( rs.next() ) {
                    result.add(rs.getString("Name"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
