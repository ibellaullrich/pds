package model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<Produto> produtos;

    public Carrinho() {
        produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto p) {
        if (p == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        produtos.add(p);
    }

    public void removerProduto(Produto p) {
        if (p == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        if (!produtos.contains(p)) {
            throw new IllegalStateException("Produto não está no carrinho");
        }
        produtos.remove(p);
    }

    public double calcularTotal() {
        double total = 0;
        try {
            for (Produto p : produtos) {
                if (p != null) {
                    total += p.getPreco();
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao calcular total: " + e.getMessage());
        }
        return total;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}