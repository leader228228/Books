package ua.edu.sumdu.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
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
