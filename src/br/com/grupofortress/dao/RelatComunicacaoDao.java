package br.com.grupofortress.dao;

import br.com.grupofortress.controller.Universal;
import br.com.grupofortress.model.RelatComunicacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RelatComunicacaoDao {

    protected EntityManager entityManager;

    public RelatComunicacaoDao() {
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

    public RelatComunicacao getById(final Long id) {
        return entityManager.find(RelatComunicacao.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<RelatComunicacao> findAll() {
        return entityManager.createQuery("FROM " + RelatComunicacao.class.getName())
                .getResultList();
    }

    public void persist(RelatComunicacao relatComunicacao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(relatComunicacao);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(RelatComunicacao relatComunicacao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(relatComunicacao);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(RelatComunicacao relatComunicacao) {
        try {
            entityManager.getTransaction().begin();
            relatComunicacao = entityManager.find(RelatComunicacao.class, relatComunicacao.getCom_cli_codigo());
            entityManager.remove(relatComunicacao);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final Long id) {
        try {
            RelatComunicacao relatComunicacao = getById(id);
            remove(relatComunicacao);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void atualizaLogRelatComunicacao(String data, Long cli_codigo) {
        entityManager.getTransaction().begin();
        String dataAtual = Universal.getInstance().getDataHoraAtual("dd/MM/yyyy HH:mm:ss");
        Query createQuery = entityManager.createQuery("UPDATE " + RelatComunicacao.class.getName() + " SET com_data_atual = '" + dataAtual + "'WHERE COM_CLI_CODIGO = '" + cli_codigo + "' AND com_data_ultima_comunicacao = '" + data + "'");
        createQuery.executeUpdate();
        entityManager.getTransaction().commit();
    }

    public boolean verificaSeRelatComunicacaoJaExiste(String data, Long cli_codigo) {

        entityManager.getTransaction().begin();
        Query createQuery = entityManager.createQuery("FROM " + RelatComunicacao.class.getName() + " WHERE (com_data_ultima_comunicacao = '" + data + "') AND (com_cli_codigo = '" + cli_codigo + "')");
        if (!createQuery.getResultList().isEmpty()) {
            return true;
        }
        return false;
    }
}
