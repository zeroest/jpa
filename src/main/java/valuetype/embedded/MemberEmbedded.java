package valuetype.embedded;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
Hibernate:

    create table address (
       member_id bigint not null,
        city varchar(255),
        street varchar(255),
        zipcode varchar(255)
    )
Hibernate:

    create table favorite_food (
       member_id bigint not null,
        food_name varchar(255)
    )

Hibernate:

    create table MemberEmbedded (
       member_id bigint not null,
        city varchar(255),
        street varchar(255),
        zipcode varchar(255),
        username varchar(255),
        work_city varchar(255),
        work_street varchar(255),
        work_zipcode varchar(255),
        endDate timestamp,
        startDate timestamp,
        primary key (member_id)

Hibernate:

    create table MemberOrigin (
       member_id bigint not null,
        city varchar(255),
        endDate timestamp,
        startDate timestamp,
        street varchar(255),
        username varchar(255),
        zipcode varchar(255),
        primary key (member_id)
    )
*/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class MemberEmbedded {

    public MemberEmbedded(String username, Period workPeriod, Address homeAddress, Address workAddress) {
        this.username = username;
        this.workPeriod = workPeriod;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
    }

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "work_city")),
            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode"))
    })
    private Address workAddress;

    @ElementCollection(fetch = FetchType.LAZY) // default LAZY
    @CollectionTable(name = "favorite_food", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

//    @OrderColumn(name = "address_history_order")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "address", joinColumns = @JoinColumn(name = "member_id"))
    private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(
//            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "member_id")
    private List<AddressEntity> addressEntities = new ArrayList<>();

    public void addFavoriteFood(String foodName) {
        this.favoriteFoods.add(foodName);
    }

    public void addAddress(Address address) {
        this.addressHistory.add(address);
    }

}
