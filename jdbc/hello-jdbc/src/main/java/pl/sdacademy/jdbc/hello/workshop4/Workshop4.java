package pl.sdacademy.jdbc.hello.workshop4;

import pl.sdacademy.jdbc.hello.common.City;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Workshop4 {
    public static void main(String[] args) throws SQLException {
        List<City> cities = List.of(
                new City("Aaaaaa", "POL", "Mazowieckie", 100),
                new City("Bbbbbb", "POL", "Mazowieckie", 200),
                new City("Cccccc", "XYZ", "Mazowieckie", 200)
        );

        addCities(cities);
    }

    private static void addCities(List<City> cities) throws SQLException {
        try (final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "Pomidor1#")) {
            connection.setAutoCommit(false);
            try (final PreparedStatement statement = connection.prepareStatement(
                    "insert into city(name, countrycode, district, population) values (?,?,?,?);"
            )) {
                for (City city : cities) {
                    statement.setString(1, city.getName());
                    statement.setString(2, city.getCountryCode());
                    statement.setString(3, city.getDistrict());
                    statement.setInt(4, city.getPopulation());
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();
            } catch (SQLException sqlException) {
                connection.rollback();
                throw sqlException;
            }
        }
    }

}
