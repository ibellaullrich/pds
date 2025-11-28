package controller;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("Testando conexão com o banco de dados...");
        
        Connection conn = ConexaoBD.getConnection();
        
        if (conn != null) {
            System.out.println("✅ Conexão estabelecida com sucesso!");
            try {
                conn.close();
                System.out.println("✅ Conexão fechada corretamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ Falha na conexão!");
            System.out.println("\nVerifique:");
            System.out.println("1. MySQL está rodando?");
            System.out.println("2. Banco 'supermercado' existe?");
            System.out.println("3. Usuário/senha estão corretos?");
            System.out.println("4. Driver JDBC foi adicionado ao projeto?");
        }
    }
}