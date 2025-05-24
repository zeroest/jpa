package valuetype.embedded;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/*
Hibernate:

    create table Member (
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
@Entity
public class MemberOrigin {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String city;
    private String street;
    private String zipcode;

}
