package br.com.grupofortress.dao;

import br.com.grupofortress.model.Cliente;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.grupofortress.model.Evento;

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

    public void clientesSemComunicação(String clicodigo) {
        
         try {
        entityManager.createQuery("UPDATE TOP (200) CLIENTE SET cli_ultima_comunicacao = '2011-08-24 14:20:05.190' WHERE (cli_codigo = '2')");
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
