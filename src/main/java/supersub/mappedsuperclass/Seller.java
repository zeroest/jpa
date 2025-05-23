package supersub.mappedsuperclass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Seller extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String shopName;

}
