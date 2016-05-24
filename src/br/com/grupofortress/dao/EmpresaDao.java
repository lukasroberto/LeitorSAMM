package br.com.grupofortress.dao;

import br.com.grupofortress.model.Empresa;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmpresaDao {

    protected EntityManager entityManager;

    public EmpresaDao() {
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

    public Empresa getById(final Long id) {
        return entityManager.find(Empresa.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Empresa> findAll() {
        return entityManager.createQuery("FROM " + Empresa.class.getName())
                .getResultList();
        
    }

    public void persist(Empresa empresa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(empresa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Empresa empresa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(empresa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Empresa empresa) {
        try {
            entityManager.getTransaction().begin();
            empresa = entityManager.find(Empresa.class, empresa.getEmp_id());
            entityManager.remove(empresa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final Long id) {
        try {
            Empresa empresa = getById(id);
            remove(empresa);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ArrayList<Empresa> arrayEmpresa = new ArrayList<>();

    public void listaDeEmpresas() {
        for (Empresa empresa : this.findAll()) {
            arrayEmpresa.add(empresa);
        }

    }

//    public void atualizaUltimaComunicacaoCLiente(String data, int cli_codigo) {
//        entityManager.getTransaction().begin();
//        Query createQuery = entityManager.createQuery("UPDATE " + Cliente.class.getName() + " SET cli_ultima_comunicacao = '" + data + "' WHERE (cli_codigo = '" + cli_codigo + "')");
//        createQuery.executeUpdate();
//        entityManager.getTransaction().commit();
//    }
//    public List<Cliente> getClientesSemComunicacao(int empresa) {
//        int dias = Integer.parseInt(Propriedades.getProp().getProperty("dias"));
//        int horas = Integer.parseInt(Propriedades.getProp().getProperty("horas"));
//
//        entityManager.getTransaction().begin();
//        Query createQuery = entityManager.createQuery("FROM " + Cliente.class.getName() + " WHERE (cli_empresa = '" + empresa + "') AND (cli_monitorado = 'true')\n"
//                + "AND (cli_ultima_comunicacao < '" + Universal.getInstance().getDataAtualMenosDiaMenosHora(dias, horas) + "') ORDER BY cli_ultima_comunicacao DESC");
//        return createQuery.getResultList();
//    }
}
