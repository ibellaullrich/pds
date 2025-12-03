package controller;

import java.sql.*;
import model.Usuario;

public class UsuarioController {

    public boolean cadastrarUsuario(Usuario usuario) {

        Connection conn = ConexaoBD.getConnection();

        if (conn == null) return false;

        String sql = "INSERT INTO usuario(nome, cpf, administrador) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setBoolean(3, usuario.isAdministrador());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }

    public Usuario validarLogin(String cpf) {

        Connection conn = ConexaoBD.getConnection();

        if (conn == null) return null;

        String sql = "SELECT * FROM usuario WHERE cpf = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));
                u.setAdministrador(rs.getBoolean("administrador"));
                return u;
            }

        } catch (SQLException e) {
            System.err.println("Erro no login: " + e.getMessage());
        }

        return null;
    }
}
