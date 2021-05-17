package pl.sdacademy.jdbc.hello.workshop2;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Workshop2 {
    public static void main(String[] args) {
        System.out.println("Podaj nazwę kontynentu:");
        final String continent = new Scanner(System.in).nextLine();

        List<String> countries = getCountries(continent);
        final String countriesString = String.join("\n", countries);
        System.out.println(countriesString);
    }

    private static List<String> getCountries(String continent) {
        List<String> result = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "Pomidor1#")) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select name from country where continent = ? order by name;")) {
                ResultSet rs = preparedStatement.executeQuery();
                preparedStatement.setString(1,continent);
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
