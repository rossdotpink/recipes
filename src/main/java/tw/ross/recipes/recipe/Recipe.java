package tw.ross.recipes.recipe;

import jakarta.persistence.Entity;

import jakarta.persistence.*;

import java.io.*;
import java.util.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "recipe")
@Getter @Setter @NoArgsConstructor
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private Integer serves;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ingredients;

    @NotEmpty
    private String instructions;

    public String toString() {
        return "Recipe called " + name;
    }


}