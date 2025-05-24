package jpql.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class Odr {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private int orderAmt;

    @Embedded
    private Ads address;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Prd product;

}
