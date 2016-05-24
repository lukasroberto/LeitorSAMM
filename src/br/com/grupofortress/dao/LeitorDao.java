package br.com.grupofortress.dao;

import br.com.grupofortress.model.Cliente;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.grupofortress.model.Evento;
import javax.persistence.Query;

public class LeitorDao {

    protected EntityManager entityManager;

    public LeitorDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("WeHaveSciencePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Evento getById(final Long id) {
        return entityManager.find(Evento.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Evento> findAll() {
        return entityManager.createQuery("FROM " + Evento.class.getName())
                .getResultList();
    }

    public void persist(Evento leitorTxt) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(leitorTxt);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Evento leitorTxt) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(leitorTxt);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Evento leitorTxt) {
        try {
            entityManager.getTransaction().begin();
            leitorTxt = entityManager.find(Evento.class, leitorTxt.getEve_id());
            entityManager.remove(leitorTxt);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final Long id) {
        try {
            Evento leitorTxt = getById(id);
            remove(leitorTxt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
        public List<Object> getClientesNaoCadastrados() {

        entityManager.getTransaction().begin();
            Query createQuery = entityManager.createQuery("SELECT eve_codigo_cliente FROM " + Evento.class.getName()+
                                                           " WHERE NOT EXISTS (SELECT cli_codigo FROM "+ Cliente.class.getName() +
                                                           " WHERE cli_codigo = eve_codigo_cliente)\n" +
                                                           " GROUP BY eve_codigo_cliente");
        return createQuery.getResultList();
    }
}
