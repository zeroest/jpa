package jpql.entity;

import lombok.*;

import javax.persistence.Embeddable;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Ads {

    public Ads(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private String city;
    private String street;
    private String zipcode;

}
