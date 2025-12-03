package controller;

import model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    public boolean adicionarProduto(Produto produto) {
        Connection conn = ConexaoBD.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO produto(nome, preco, quantidade) VALUES (?, ?, ?)")) {

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getQuantidade());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarProduto(Produto produto) {
        Connection conn = ConexaoBD.getConnection();
        if (conn == null) return false;

        String sql = "UPDATE produto SET nome=?, preco=?, quantidade=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getQuantidade());
            ps.setInt(4, produto.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }



    public boolean removerProduto(int id) {
        Connection conn = ConexaoBD.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM produto WHERE id=?")) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao remover produto: " + e.getMessage());
            return false;
        }
    }

    public List<Produto> listarProdutos() {
        List<Produto> lista = new ArrayList<>();
        Connection conn = ConexaoBD.getConnection();
        if (conn == null) return lista;

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM produto");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }

        return lista;
    }

	public void atualizarEstoque(int id, int i) {
		// TODO Auto-generated method stub
		
	}
}
