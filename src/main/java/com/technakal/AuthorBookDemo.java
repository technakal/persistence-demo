package com.technakal;

import com.technakal.entity.Author;
import com.technakal.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class AuthorBookDemo {

    private static final String PERSISTENCE_UNIT_NAME = "AuthorBook";

    private static EntityManagerFactory factory;

    public static void main(String[] args) {

        // create a factory for the persistence unit
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        // create an entity manager
        EntityManager em = factory.createEntityManager();

        // start transaction
        em.getTransaction().begin();

        // create Author

        Author author = new Author("Stephen", "King");
        System.out.println("technakal | Author: " + author);

        Book book1 = new Book("IT", "Viking", 1986);

        author.add(book1);

        for(Book book : author.getBooks()) {
            System.out.println("technakal | Book " + book);
        }

        // persist the author
        System.out.println("technakal | Saving the author and books...");
        em.persist(author);
        System.out.println("technakal | Author ID: " + author.getId() + " persisted.");
        em.getTransaction().commit();
        System.out.println("technakal | Author saved.");

        // close the transaction?
        System.out.println("technakal | Closing transaction.");
        em.close();
        System.out.println("technakal | Transaction closed.");

        System.out.println("\n");
        retrieveAuthor(1, factory);

        System.out.println("\n");
        retrieveBook(1, factory);

        System.out.println("\n");

        retrieveBookByQuery(factory);

        System.out.println("\n");
        System.out.println("technakal | Closing transaction...");
        System.out.println("technakal | Transaction closed...");
        System.out.println("technakal | Closing factory...");
        factory.close();

    }

    private static void retrieveBook(int id, EntityManagerFactory factory) {

        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        System.out.println("technakal | Attempting to retrieve book " + id + "...");
        Book book = em.find(Book.class, id);
        if (book != null) {
            System.out.println("technakal | Book retrieved.");
            System.out.println("technakal | Book: " + book);
            System.out.println("technakal | Checking for book's author.");
            System.out.println("technakal | Book written by : " + book.getAuthor() + "...");
        } else {
            System.out.println("technakal | Book not found.");
        }

        em.getTransaction().commit();
        em.close();

    }

    public static void retrieveAuthor(int id, EntityManagerFactory factory) {

        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        System.out.println("technakal | Attempting to retrieve author " + id + "...");
        Author author = em.find(Author.class, id);
        if (author != null) {
            System.out.println("technakal | Author retrieved.");
            System.out.println("technakal | Author: " + author);
            System.out.println("technakal | Checking for author books.");
            for (Book book : author.getBooks()) {
                System.out.println("technakal | Author wrote: " + book + "...");
            }
            System.out.println("technakal | No other author books.");
        } else {
            System.out.println("technakal | Author not found.");
        }

        em.getTransaction().commit();
        em.close();

    }

    public static void retrieveBookByQuery(EntityManagerFactory factory) {

        EntityManager em = factory.createEntityManager();

        System.out.println("technakal | Retrieving book by publication year...");

        em.getTransaction().begin();

        List<Book> results = em.createQuery("select b from Book b where publicationYear > 1930").getResultList();

        System.out.println("technakal | " + results.size() + " books retrieved.");
        System.out.println("technakal | Listing books...");
        for (Book book : results) {
            System.out.println("technakal | Book: " + book);
        }

        em.getTransaction().commit();
        em.close();

    }
}
