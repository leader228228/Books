package ua.edu.sumdu.model.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "cw_books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint not null auto_increment")
    private BigInteger id;
    private String name;
    private String author;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "cw_books_authors",
        joinColumns =
            {@JoinColumn(name = "book_id", nullable = false, referencedColumnName = "id", columnDefinition = "bigint")},
        inverseJoinColumns =
            {@JoinColumn(name = "author_id", nullable = false, referencedColumnName = "id", columnDefinition = "bigint")}
    )
    private Collection<Author> authors;
    private String publisher;
    private Date published;
    private int pages;
    private float price;
}
