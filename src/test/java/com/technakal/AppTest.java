package com.technakal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.technakal.entity.Author;
import com.technakal.entity.Book;
import com.technakal.repository.AuthorRepository;
import com.technakal.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Optional;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AppTest 
{

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TestEntityManager testEntityManager;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookRepository bookRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(authorRepository).isNotNull();
        assertThat(bookRepository).isNotNull();
    }

    @Test
    public void testCascade() {
// create test entity
        Author author = new Author("Nathan", "Ballingrud");
        System.out.println("technakal | Created author: " + author);
        Book book = new Book();
        book.setTitle("The Visibile Filth");
        book.setAuthor(author);
        book.setPublisher("This Is Horror");
        book.setPublicationYear(2015);
        System.out.println("technakal | Created book: " + book);

        // persist entity
        System.out.println("technakal | Persisting data...");
        entityManager.persist(book);

        Optional<Book> actualBook = bookRepository.findByPublicationYear(book.getPublicationYear());
        Author actualAuthor = actualBook.get().getAuthor();

        System.out.println();
        assertThat(actualBook).isNotNull();
        assertThat(actualAuthor).isNotNull();
        assertEquals(book.getAuthor(), actualAuthor);
        assertEquals(actualBook.get().getAuthor(), author);
    }
}
