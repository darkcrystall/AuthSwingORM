/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.authswingorm.model;

/**
 *
 * @author Dalvana
 */


import jakarta.persistence.*;

/**
 * Entity = classe que representa a TABELA no banco.
 *
 * Classe Usuario  <-->  tabela usuarios
 * atributos        <-->  colunas
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    // @Id = chave primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    // coluna nome (não pode ser null)
    @Column(nullable = false, length = 80)
    private String nome;

    // coluna email (não pode ser null e é UNIQUE)
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    // coluna senha_hash (a gente NÃO salva senha normal)
    @Column(name = "senha_hash", nullable = false, length = 100)
    private String senhaHash;

    // Construtor vazio é obrigatório pro JPA
    public Usuario() {}

    // Construtor útil pro cadastro
    public Usuario(String nome, String email, String senhaHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
    }

    // getters/setters
    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
}

