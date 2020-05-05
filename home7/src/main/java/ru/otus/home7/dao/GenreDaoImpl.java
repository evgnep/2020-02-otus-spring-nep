package ru.otus.home7.dao;

import org.springframework.stereotype.Repository;
import ru.otus.home7.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
class GenreDaoImpl implements GenreDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre readByName(String name) {
        var query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public long count() {
        return em.createQuery("select count(g) from Genre g", Long.class).getSingleResult();
    }

    @Override
    public Genre save(Genre elem) {
        if (elem.getId() == 0)
            em.persist(elem);
        else
            elem = em.merge(elem);
        return elem;
    }

    @Override
    public void delete(long id) {
        em.remove(em.getReference(Genre.class, id));
    }

    @Override
    public Genre readById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> readAll() {
        return em.createQuery("select g from Genre g order by g.id", Genre.class).getResultList();
    }

    @Override
    public void flush() {
        em.flush();
    }
}
