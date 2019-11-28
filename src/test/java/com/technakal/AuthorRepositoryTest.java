package com.technakal;

import com.technakal.entity.Author;
import com.technakal.repository.AuthorRepository;
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
public class AuthorRepositoryTest {

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TestEntityManager testEntityManager;
    @Autowired private AuthorRepository authorRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(authorRepository).isNotNull();
    }

    @Test
    public void testFindByLastName() {

        Author author = new Author("Nathan", "Ballingrud");
        System.out.println("technakal | Author: " + author);

        System.out.println("technakal | Persisting author.");
        entityManager.persist(author);

        System.out.println("technakal | Retrieving author.");
        Optional<Author> actual = authorRepository.findByLastName("Ballingrud");

        System.out.println("technakal | Running tests...");
        assertThat(actual).isNotNull();
        assertEquals(author.getId(), actual.get().getId());

    }


}
