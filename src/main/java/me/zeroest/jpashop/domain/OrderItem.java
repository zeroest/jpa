package me.zeroest.jpashop.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "member_id")
    private Long memberId;

    private int orderPrice;

    private int count;

}
