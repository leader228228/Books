package ua.edu.sumdu.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.model.entity.Book;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Collection;

@Repository
public interface BookRepository extends CrudRepository<Book, BigInteger> {
    Collection<Book> findByAuthor(String author);
    Collection<Book> findByPublisher(String publisher);
    Collection<Book> findByPublishedAfter(Date published);
}
