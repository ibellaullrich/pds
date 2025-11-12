package controller;

//UsuarioController.java
import java.sql.*;

import model.Usuario;

public class UsuarioController {

 public boolean cadastrarUsuario(Usuario usuario) {
     String sql = "INSERT INTO usuario (nome, cpf, administrador) VALUES (?, ?, ?)";
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
     String sql = "SELECT * FROM usuario WHERE cpf = ?";
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
