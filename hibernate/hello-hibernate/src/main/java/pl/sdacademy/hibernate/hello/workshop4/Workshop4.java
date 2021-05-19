package pl.sdacademy.hibernate.hello.workshop4;

import pl.sdacademy.hibernate.hello.common.City;
import pl.sdacademy.hibernate.hello.common.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Workshop4 {
    public static void main(String[] args) {
        System.out.println("Podaj kod kraju:");
        final String countryCode = new Scanner(System.in).nextLine();

        List<City> cities = getCities(countryCode);
        final String citiesString = cities.stream().map(City::toString).collect(Collectors.joining("\n"));
        System.out.println(citiesString);
    }

    public static List<City> getCities(String countryCode) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloHibernatePU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String query = "SELECT ci FROM City ci  JOIN  FETCH ci.country where ci.country.code = :countryCode ORDER BY ci.name";

        try {
            return  entityManager.createQuery(query,City.class).setParameter("countryCode",countryCode).getResultList();
        } finally {
            entityManagerFactory.close();
        }
    }
}
