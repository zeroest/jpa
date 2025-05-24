package proxy.cascade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Entity
public class Child {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

//    public void setParent(Parent parent) {
//        this.parent = parent;
//        parent.addChild(this);
//    }

}
