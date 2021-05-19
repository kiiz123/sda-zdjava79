package pl.sdacademy.hibernate.hello.workshop3;

import pl.sdacademy.hibernate.hello.common.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Workshop3 {
    public static void main(String[] args) {
        System.out.println("Podaj nazwę kontynentu:");
        final String continent = new Scanner(System.in).nextLine();

        final List<Country> country = getCountries(continent);
        final String countriesString = country.stream().map(Country::toString).collect(Collectors.joining("\n"));
        System.out.println(countriesString);
    }

    public static List<Country> getCountries(String continent) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloHibernatePU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String query = "SELECT c FROM Country c where continent= :continent ORDER BY name";

        try {
            return  entityManager.createQuery(query,Country.class).setParameter("continent",continent).getResultList();
        } finally {
            entityManagerFactory.close();
        }
    }

}
