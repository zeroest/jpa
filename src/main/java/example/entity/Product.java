package example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "products")
    private List<Member> members = new ArrayList<>();

}
