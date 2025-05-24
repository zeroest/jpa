package jpql.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class Tem {

    public Tem(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Mem> mems = new ArrayList<>();

}
