package example.entity.generated_value_strategy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@TableGenerator(
        name = "SEQ_TABLE_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "SEQ", allocationSize = 1
)
public class StrategyTable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "SEQ_TABLE_GENERATOR")
    private Long id;

    private String name;

}
