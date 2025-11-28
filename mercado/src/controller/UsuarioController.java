package controller;

import java.sql.*;
import model.Usuario;

public class UsuarioController {

    public boolean cadastrarUsuario(Usuario usuario) {
        // ✅ Corrigido: usando 'usuarios' (plural) como no UsuarioDAO
        String sql = "INSERT INTO usuarios (nome, cpf, administrador) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setBoolean(3, usuario.isAdministrador());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario validarLogin(String cpf) {
        // ✅ Corrigido: usando 'usuarios' (plural) como no UsuarioDAO
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getBoolean("administrador")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}