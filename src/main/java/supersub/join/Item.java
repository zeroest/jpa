package supersub.join;

import javax.persistence.*;

@Entity
@Table(name = "items")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //default
/*
    create table items (
       DTYPE varchar(31) not null,
        item_id bigint not null,
        name varchar(255),
        price bigint not null,
        author varchar(255),
        isbn varchar(255),
        artist varchar(255),
        actor varchar(255),
        director varchar(255),
        primary key (item_id)
    )
*/

@Inheritance(strategy = InheritanceType.JOINED)
/*
    create table items (
       DTYPE varchar(31) not null,
        item_id bigint not null,
        name varchar(255),
        price bigint not null,
        primary key (item_id)
    )
    create table Movie (
       actor varchar(255),
        director varchar(255),
        item_id bigint not null,
        primary key (item_id)
    )
    create table Book (
       author varchar(255),
        isbn varchar(255),
        item_id bigint not null,
        primary key (item_id)
    )
    create table Album (
       artist varchar(255),
        item_id bigint not null,
        primary key (item_id)
    )
*/

//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
/*
    create table Album (
       item_id bigint not null,
        name varchar(255),
        price bigint not null,
        artist varchar(255),
        primary key (item_id)
    )
    create table Book (
       item_id bigint not null,
        name varchar(255),
        price bigint not null,
        author varchar(255),
        isbn varchar(255),
        primary key (item_id)
    )
    create table items (
       item_id bigint not null,
        name varchar(255),
        price bigint not null,
        primary key (item_id)
    )
    create table Movie (
       item_id bigint not null,
        name varchar(255),
        price bigint not null,
        actor varchar(255),
        director varchar(255),
        primary key (item_id)
    )
*/
@DiscriminatorColumn(name="DTYPE") // 엔티티명으로 들어감 운영성에 좋음
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private long price;

}
