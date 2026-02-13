/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.authswingorm.dao;
import com.senac.authswingorm.model.Usuario;


import com.senac.authswingorm.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * DAO com ORM:
 * - Não escrevemos INSERT/SELECT "na mão" como no JDBC.
 * - Usamos EntityManager + JPQL (consulta por classe/atributo).
 */
public class UsuarioDAO {

    /**
     * Salva um usuário no banco.
     * equivale ao INSERT (JDBC).
     */
    public void salvar(Usuario u) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            // ORM exige transação pra operações que alteram dados
            em.getTransaction().begin();

            // persist = "insere" no banco
            em.persist(u);

            em.getTransaction().commit();

        } catch (Exception e) {
            // Se deu problema, desfaz a transação
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;

        } finally {
            // Sempre fecha o em
            em.close();
        }
    }

    /**
     * Busca usuário pelo email.
     * equivale ao SELECT ... WHERE email = ?
     */
    public Usuario buscarPorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            // JPQL: consulta por CLASSE e ATRIBUTOS (não tabela/coluna)
            TypedQuery<Usuario> q = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :email",
                Usuario.class
            );

            // parâmetro nomeado
            q.setParameter("email", email);

            // getResultList para evitar exception de "não encontrado"
            var lista = q.getResultList();

            // se lista vazia, não existe usuário
            return lista.isEmpty() ? null : lista.get(0);

        } finally {
            em.close();
        }
    }
}
