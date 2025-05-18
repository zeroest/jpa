package example.entity.generated_value_strategy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@SequenceGenerator(
        name = "SEQ_SEQUENCE_GENERATOR",
        sequenceName = "SEQ",
        initialValue = 1, allocationSize = 10
)
public class StrategySequence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SEQUENCE_GENERATOR")
    private Long id;

    private String name;

}
