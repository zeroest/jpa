package valuetype.embedded;

import lombok.*;

import javax.persistence.Embeddable;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private String city;
    private String street;
    private String zipcode;

    // 값 타입은 불변 객체로 관리할것
    // setter 와 같은 메서드를 제거해야함
//    public void updateCity(String city) {
//        this.city = city;
//    }

    public Address copy() {
        return new Address(this.city, this.street, this.zipcode);
    }

}
