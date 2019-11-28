package com.technakal;

import com.technakal.entity.Author;
import com.technakal.entity.Book;
import com.technakal.repository.AuthorRepository;
import com.technakal.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.text.html.Option;
import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories
public class SpringJPADemo {

    private static final Logger log = LoggerFactory.getLogger(SpringJPADemo.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringJPADemo.class, args);
    }

    @Bean
    public CommandLineRunner demo(AuthorRepository authorRepository) {
        return (args) -> {
            // create author
            Author author = new Author();
            author.setFirstName("Dathan");
            author.setLastName("Auerbach");

            // create book
            Book book1 = new Book();
            book1.setTitle("Penpal");
            book1.setPublisher("1000Vultures");
            book1.setPublicationYear(2012);

            author.add(book1);

            author = authorRepository.save(author);

            Optional<Author> authorRead = authorRepository.findByLastName("Auerbach");

            authorRead.ifPresent(authorRetrieved -> System.out.println("technakal | Author: " + authorRetrieved));
        };
    }
}
