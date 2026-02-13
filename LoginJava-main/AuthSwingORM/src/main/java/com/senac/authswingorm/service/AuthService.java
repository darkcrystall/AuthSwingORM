/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.authswingorm.service;



import com.senac.authswingorm.dao.UsuarioDAO;
import com.senac.authswingorm.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Service = regras do sistema.
 * Aqui entram:
 * - validações (Regex)
 * - criptografia (BCrypt)
 * - decisões (pode cadastrar? pode logar?)
 */
public class AuthService {

    private final UsuarioDAO dao = new UsuarioDAO();

    // Regex de email simples e didática
    private static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Regex de senha forte:
    // - (?=.*[A-Z]) tem pelo menos 1 letra maiúscula
    // - (?=.*\\d)   tem pelo menos 1 número
    // - .{8,}       mínimo 8 caracteres
    private static final String REGEX_SENHA = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

    /**
     * Cadastra usuário:
     * 1) valida campos
     * 2) verifica se email já existe
     * 3) gera hash BCrypt
     * 4) salva via ORM
     *
     * Retorna:
     * - null se deu certo
     * - mensagem de erro se deu ruim
     */
    public String cadastrar(String nome, String email, String senha) {

        // Normaliza entradas
        nome = (nome == null) ? "" : nome.trim();
        email = (email == null) ? "" : email.trim().toLowerCase();

        // 1) valida nome
        if (nome.length() < 2) return "Nome inválido (mínimo 2 letras).";

        // 2) valida email com regex
        if (!email.matches(REGEX_EMAIL)) return "E-mail inválido.";

        // 3) valida senha com regex
        if (!senha.matches(REGEX_SENHA)) {
            return "Senha fraca: mínimo 8, 1 maiúscula e 1 número.";
        }

        // 4) verifica se já existe usuário com o email
        if (dao.buscarPorEmail(email) != null) return "Este e-mail já está cadastrado.";

        // 5) BCrypt: gera hash seguro
        // gensalt(10) = "custo" (quanto mais, mais seguro e mais lento)
        String hash = BCrypt.hashpw(senha, BCrypt.gensalt(10));

        // 6) salva no banco (ORM)
        dao.salvar(new Usuario(nome, email, hash));

        return null; // OK
    }

    /**
     * Login:
     * 1) valida formato do email (regex)
     * 2) busca no banco (ORM)
     * 3) compara senha digitada com o hash do banco (BCrypt.checkpw)
     *
     * Retorna:
     * - Usuario se autenticou
     * - null se falhou
     */
    public Usuario login(String email, String senha) {

        email = (email == null) ? "" : email.trim().toLowerCase();

        // se o formato do email é inválido, nem tenta banco
        if (!email.matches(REGEX_EMAIL)) return null;

        // busca no banco
        Usuario u = dao.buscarPorEmail(email);

        // se não achou usuário, login falha
        if (u == null) return null;

        // BCrypt compara senha digitada com hash do banco
        boolean ok = BCrypt.checkpw(senha, u.getSenhaHash());

        return ok ? u : null;
    }
}
