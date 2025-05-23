package supersub.mappedsuperclass;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
    create table Member (
       id bigint not null,
        createdAt timestamp,
        createdBy varchar(255),
        lastModifiedAt timestamp,
        lastModifiedBy varchar(255),
        email varchar(255),
        name varchar(255),
        primary key (id)
    )
    create table Seller (
       id bigint not null,
        createdAt timestamp,
        createdBy varchar(255),
        lastModifiedAt timestamp,
        lastModifiedBy varchar(255),
        name varchar(255),
        shopName varchar(255),
        primary key (id)
    )
*/
@MappedSuperclass
public class BaseEntity {

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

}
