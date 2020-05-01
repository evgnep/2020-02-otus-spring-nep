package ru.otus.home7.dao;

import org.springframework.stereotype.Repository;
import ru.otus.home7.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
class BookDaoImpl implements Dao<Book> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }

    @Override
    public Book save(Book elem) {
        if (elem.getId() == 0)
            em.persist(elem);
        else
            elem = em.merge(elem);
        return elem;
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
