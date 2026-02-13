
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.authswingorm.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe utilitária que cria o "motor" do JPA (EntityManagerFactory)
 * e entrega EntityManager (uma "sessão" de conversa com o banco).
 *
 * Pense assim:
 * - EntityManagerFactory = fábrica (caro de criar, cria 1 vez)
 * - EntityManager = "conversa" (cria e fecha toda hora)
 */
public class JPAUtil {

    // Cria UMA fábrica com base no persistence.xml (nome authPU)
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("authPU");

    /**
     * Entrega um EntityManager novo.
     * Sempre feche (em.close()) quando terminar.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
