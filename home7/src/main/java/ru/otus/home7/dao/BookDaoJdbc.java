package ru.otus.home7.dao;

import org.springframework.stereotype.Repository;
import ru.otus.home7.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookDaoJdbc implements Crud<Book> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }

    @Override
    public void create(Book elem) {
        em.persist(elem);
    }

    @Override
    public void delete(long id) {
        em.remove(em.getReference(Book.class, id));
    }

    @Override
    public Book readById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> readAll() {
        return em.createQuery("select b from Book b order by b.id", Book.class).getResultList();
    }

    @Override
    public void flush() {
        em.flush();
    }
}
