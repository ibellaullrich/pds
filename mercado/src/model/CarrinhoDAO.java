package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import controller.ConexaoBD;

public class CarrinhoDAO {

    public boolean registrarCompra(Usuario usuario, List<Produto> produtos, double total) {
        String sqlCompra = "INSERT INTO compras (nome_cliente, cpf_cliente, total) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO itens_compra (compra_id, produto_id, quantidade, preco) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection()) {
            conn.setAutoCommit(false);

        
            PreparedStatement stmtCompra = conn.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS);
            stmtCompra.setString(1, usuario.getNome());
            stmtCompra.setString(2, usuario.getCpf());
            stmtCompra.setDouble(3, total);
            stmtCompra.executeUpdate();

            ResultSet rs = stmtCompra.getGeneratedKeys();
            rs.next();
            int compraId = rs.getInt(1);

       
            PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
            for (Produto p : produtos) {
                stmtItem.setInt(1, compraId);
                stmtItem.setInt(2, p.getId());
                stmtItem.setInt(3, 1); 
                stmtItem.setDouble(4, p.getPreco());
                stmtItem.addBatch();
            }
            stmtItem.executeBatch();

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
