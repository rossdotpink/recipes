package tw.ross.recipes.recipe;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Entity
@Table(name = "recipe")
@Getter @Setter @NoArgsConstructor
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @NotNull
    private Integer serves;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ingredients;

    public void setTags(List<String> tags) {
        List<String> lowercaseTags = tags
                .stream()
                .map(String::toLowerCase)
                .toList();
        this.tags = lowercaseTags;
    }

    @NotEmpty
    private String instructions;
}