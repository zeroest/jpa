package me.zeroest.jpashop.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

}
