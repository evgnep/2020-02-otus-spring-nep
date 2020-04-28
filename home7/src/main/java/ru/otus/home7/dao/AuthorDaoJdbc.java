package ru.otus.home7.dao;

import org.springframework.stereotype.Repository;
import ru.otus.home7.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Author readByName(String name) {
        var query = em.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public long count() {
        return em.createQuery("select count(a) from Author a", Long.class).getSingleResult();
    }

    @Override
    public void create(Author elem) {
        em.persist(elem);
    }

    @Override
    public void delete(long id) {
        em.remove(em.getReference(Author.class, id));
    }

    @Override
    public Author readById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> readAll() {
        return em.createQuery("select a from Author a order by a.id", Author.class).getResultList();
    }

    @Override
    public void flush() {
        em.flush();
    }
}
