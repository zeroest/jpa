package supersub.join;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // DTYPE 에 엔티티 대신에 들어갈 벨류값
public class Book extends Item {

    private String author;

    private String isbn;

}
