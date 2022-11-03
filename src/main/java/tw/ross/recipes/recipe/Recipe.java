package tw.ross.recipes.recipe;

import jakarta.persistence.Entity;

import jakarta.persistence.*;

import java.io.*;
import java.time.*;
import java.util.*;

import lombok.*;

@Entity
@Table(name = "recipe")
@Getter @Setter @NoArgsConstructor
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int serves;

    @ElementCollection
    private Set<String> ingredients;
    private String instructions;

    public String toString() {
        return "Recipe called " + name;
    }


}