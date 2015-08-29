package br.com.grupofortress.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class NativeSQL {

    protected EntityManager entityManager;

    public NativeSQL() {
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

    public Object contaClientesPorEmpresa(int empresa) {

        entityManager.getTransaction().begin();
        Query createQuery = entityManager.createNativeQuery("SELECT COUNT(cli_cidade) AS quantidade FROM CLIENTE WHERE(cli_empresa = '" + empresa + "') and cli_monitorado = 'True' ");
        return createQuery.getSingleResult();

    }
}
