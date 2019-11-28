package com.technakal;

import com.technakal.entity.Author;
import com.technakal.entity.Book;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TestEntityManager testEntityManager;
    @Autowired private BookRepository bookRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(bookRepository).isNotNull();
    }

    @Test
    public void testFindByPublicationYear() {

        // create test entity
        Author author = new Author("Nathan", "Ballingrud");
        Book book = new Book();
        book.setTitle("The Visibile Filth");
        book.setAuthor(author);
        book.setPublisher("This Is Horror");
        book.setPublicationYear(2015);
        System.out.println("technakal | Created book: " + book);

        // persist entity
        System.out.println("technakal | Persisting book...");
        entityManager.persist(book);

        Optional<Book> actual = bookRepository.findByPublicationYear(book.getPublicationYear());

        System.out.println("technakal | Running tests...");
        assertThat(actual).isNotNull();
        assertEquals(book.getId(), actual.get().getId());
    }
}
