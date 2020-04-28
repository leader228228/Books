package ua.edu.sumdu.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;

@Entity
@Table(name = "cw_authors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint not null auto_increment")
    private BigInteger id;
    private String firstName;
    private String lastName;
    private String middleName;
    @ManyToMany(mappedBy = "authors", cascade = CascadeType.PERSIST)
    private Collection<Book> books;
}
