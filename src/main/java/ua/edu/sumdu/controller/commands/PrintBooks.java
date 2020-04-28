package ua.edu.sumdu.controller.commands;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ua.edu.sumdu.controller.SearchBy;
import ua.edu.sumdu.model.BookRepository;
import ua.edu.sumdu.model.entity.Book;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Date;
import java.util.Collection;
import java.util.concurrent.Callable;

@Component
@Command(
    name = "find",
    description = "Use this command to search the books by some criteria",
    mixinStandardHelpOptions = true
)
@Getter
@Setter
public class PrintBooks implements Callable<Integer> {

    private static final Logger LOGGER = Logger.getLogger(PrintBooks.class);

    private final BookRepository bookRepository;

    @Option(names = {"-author", "-a"}, description = "The author name for book set filtering")
    private String author;

    @Option(names = {"-since"}, description = "The date for book set filtering")
    private Date since;

    @Option(names = {"-publisher", "-p"}, description = "The company name which issued a book(s)")
    private String publisher;

    @Option(
        names = {"-searchBy", "-by", "-criteria", "-c"},
        description = "A criteria by which books will be searched. Valid values are: ${COMPLETION-CANDIDATES}",
        required = true
    )
    private SearchBy searchBy;

    PrintBooks(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Collection<Book> executeSearch() {
        Collection<Book> resultSet;
        LOGGER.debug("searching by " + searchBy);
        switch (searchBy) {
            case DATE:
                resultSet = bookRepository.findByPublishedAfter(since);
                break;
            case AUTHOR:
                resultSet = bookRepository.findByAuthor(author);
                break;
            case PUBLISHER:
                resultSet = bookRepository.findByPublisher(publisher);
                break;
            default:
                String errMsg = "Unexpected exception, searchBy parameter is not specified: " + this;
                LOGGER.error(errMsg);
                throw new UncheckedIOException(new IOException(errMsg));
        }
        LOGGER.debug(resultSet.size() + " books found");
        return resultSet;
    }

    @Override
    public Integer call() {
        System.out.println("Books:");
        executeSearch().forEach(System.out::println);
        return CommandExecutionCode.OK.getCode();
    }
}
