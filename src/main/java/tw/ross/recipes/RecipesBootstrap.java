//package tw.ross.recipes.recipe;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.ejb.Startup;
//import jakarta.inject.Singleton;
//import jakarta.persistence.*;
//
//import java.time.*;
//
//@Singleton
//@Startup
//public class RecipesBootstrap {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @PostConstruct
//    public void init() {
//        entityManager.persist(new Videogame("Overwatch", 59, LocalDate.of(2020, 12, 12)));
//        entityManager.persist(new Videogame("Fortnite", 22, LocalDate.of(2021, 12, 12)));
//
//    }
//}