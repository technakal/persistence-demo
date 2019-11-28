package com.technakal.repository;

import com.technakal.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    Optional<Book> findByPublicationYear(int year);

}
