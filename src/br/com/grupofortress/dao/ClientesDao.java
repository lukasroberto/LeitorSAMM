package br.com.grupofortress.dao;

import br.com.grupofortress.model.Cliente;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ClientesDao {

    protected EntityManager entityManager;

    public ClientesDao() {
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

    public Cliente getById(final Long id) {
        return entityManager.find(Cliente.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Cliente> findAll() {
        return entityManager.createQuery("FROM " + Cliente.class.getName())
                .getResultList();
    }

    public void persist(Cliente cliente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Cliente cliente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Cliente cliente) {
        try {
            entityManager.getTransaction().begin();
            cliente = entityManager.find(Cliente.class, cliente.getCli_codigo());
            entityManager.remove(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final Long id) {
        try {
            Cliente cliente = getById(id);
            remove(cliente);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clientesSemComunicação(String data, int cli_codigo) {
            entityManager.getTransaction().begin();
        Query createQuery = entityManager.createQuery("UPDATE "+Cliente.class.getName()+" SET cli_monitorado = '1', cli_ultima_comunicacao = '"+data+"' WHERE (cli_codigo = '"+cli_codigo+"')");
        createQuery.executeUpdate();
        entityManager.getTransaction().commit();
    }
    
        public void verificaClientesSemComunicação(String data, int cli_codigo) {
            entityManager.getTransaction().begin();
        Query createQuery = entityManager.createQuery("SELECT cli_codigo, cli_nome, cli_empresa, cli_monitorado, cli_ultima_comunicacao\n" +
                "FROM CLIENTE WHERE (cli_empresa <> 'guardian') AND (cli_monitorado = 'true')\n"+
                "AND (cli_ultima_comunicacao < '10-06-2015 10:00') ORDER BY cli_ultima_comunicacao DESC");
        
        entityManager.getTransaction().commit();
    }
}
