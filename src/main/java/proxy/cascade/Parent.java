package proxy.cascade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private String name;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Child> children = new ArrayList<>();

    void addChild(Child child) {
        this.children.add(child);
        child.setParent(this);
    }

}
