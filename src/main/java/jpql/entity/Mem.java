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
public class Mem {

    public Mem(String username) {
        this.username = username;
    }

    public Mem(String username, int age, Ads address, Tem team) {
        this.username = username;
        this.age = age;
        this.address = address;
        this.team = team;
    }

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String username;

    private int age;

    @Enumerated(EnumType.STRING)
    private MemType type;

    @Embedded
    private Ads address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Tem team;

}
