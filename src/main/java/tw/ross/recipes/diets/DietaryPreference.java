package tw.ross.recipes.diets;

import lombok.*;

import jakarta.persistence.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "diet")
@Getter @Setter @NoArgsConstructor
public class DietaryPreference {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

//    @Override
//    public String toString() {
//        return "Person{" + "id=" + id + ", age=" + age + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
//                + '\'' + '}';
//    }
}