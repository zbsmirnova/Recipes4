package recipes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Embeddable
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "directions")
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String value;

    public Direction(String value) {
        this.value = value;
    }
}