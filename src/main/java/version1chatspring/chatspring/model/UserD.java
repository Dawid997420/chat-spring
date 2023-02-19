package version1chatspring.chatspring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@Table(name = "UserD")
public class UserD {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;


    private String username ;

    private String password ;

    private String photo;


}
