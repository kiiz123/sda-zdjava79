package pl.sdacademy.jdbc.hello.workshop3;

import pl.sdacademy.jdbc.hello.common.City;

import java.sql.*;
import java.util.Scanner;

public class Workshop3b {
    public static void main(String[] args) throws SQLException {
        System.out.println("Podaj nazwę miasta:");
        final String name = new Scanner(System.in).nextLine();

        System.out.println("Podaj kod kraju:");
        final String countryCode = new Scanner(System.in).nextLine();

        System.out.println("Podaj jednostkę administracyjną:");
        final String district = new Scanner(System.in).nextLine();

        System.out.println("Podaj liczbę mieszkańców:");
        final int population = new Scanner(System.in).nextInt();

        final City city = new City(name, countryCode, district, population);
        boolean result = addCity(city);
        System.out.println(result);
    }

    private static boolean addCity(City city) throws SQLException {
        boolean exists = false;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "Pomidor1#")) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select exists ( select 1 from city where name=? and CountryCode=? );")) {
                preparedStatement.setString(1, city.getName());
                preparedStatement.setString(2, city.getCountryCode());
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                exists = rs.getBoolean(1);
            }
            if (exists) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("update city set district=?,population=? where name=? and CountryCode=? ")) {
                    preparedStatement.setString(1, city.getDistrict());
                    preparedStatement.setInt(2, city.getPopulation());
                    preparedStatement.setString(3, city.getName());
                    preparedStatement.setString(4, city.getCountryCode());
                    int rs = preparedStatement.executeUpdate();
                }
            }
            if (!exists) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("insert into CITY (Name, countrycode,district,population) values ( ? ,?, ?, ?);")) {
                    preparedStatement.setString(1, city.getName());
                    preparedStatement.setString(2, city.getCountryCode());
                    preparedStatement.setString(3, city.getDistrict());
                    preparedStatement.setInt(4, city.getPopulation());
                    int rs = preparedStatement.executeUpdate();
                    if (rs == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return !exists;
    }

}

