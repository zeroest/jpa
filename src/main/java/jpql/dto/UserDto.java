package jpql.dto;

import lombok.ToString;

@ToString
public class UserDto {

    public UserDto(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    private String userName;

    private int age;

}
