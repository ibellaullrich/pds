package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.CarrinhoController;
import controller.ProdutoController;
import model.Produto;
import model.Usuario;

import java.awt.*;
import java.awt.event.*;

public class TelaCompra extends JFrame {
    private Usuario usuario;
    private ProdutoController produtoController;
    private CarrinhoController carrinhoController;
    private JTable tabelaProdutos;
    private JLabel totalLabel;

    public TelaCompra(Usuario usuario) {
        this.usuario = usuario;
        this.produtoController = new ProdutoController();
        this.carrinhoController = new CarrinhoController();

        setTitle("Tela de Compra - Cliente: " + usuario.getNome());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarProdutos();
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tabela de produtos
        tabelaProdutos = new JTable();
        JScrollPane scroll = new JScrollPane(tabelaProdutos);
        add(scroll, BorderLayout.CENTER);

        // Painel inferior com total e botões
        JPanel painelInferior = new JPanel();
        totalLabel = new JLabel("Total: R$ 0.00");
        JButton btnAdicionar = new JButton("Adicionar ao Carrinho");
        JButton btnRemover = new JButton("Remover do Carrinho");
        JButton btnNota = new JButton("Emitir Nota Fiscal");
        JButton btnSair = new JButton("Sair");

        painelInferior.add(totalLabel);
        painelInferior.add(btnAdicionar);
        painelInferior.add(btnRemover);
        painelInferior.add(btnNota);
        painelInferior.add(btnSair);
        add(painelInferior, BorderLayout.SOUTH);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> adicionarProdutoSelecionado());
        btnRemover.addActionListener(e -> removerProdutoSelecionado());
        btnNota.addActionListener(e -> emitirNota());
        btnSair.addActionListener(e -> {
            dispose(); // fecha a tela
            new TelaLogin(); // volta para login
        });
    }

    private void carregarProdutos() {
        String[] colunas = {"ID", "Nome", "Preço", "Quantidade"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        for (Produto p : produtoController.listarProdutos()) {
            Object[] linha = {p.getId(), p.getNome(), p.getPreco(), p.getQuantidade()};
            model.addRow(linha);
        }
        tabelaProdutos.setModel(model);
    }

    private Produto getProdutoSelecionado() {
        int linha = tabelaProdutos.getSelectedRow();
        if (linha == -1) return null;

        int id = (int) tabelaProdutos.getValueAt(linha, 0);
        String nome = (String) tabelaProdutos.getValueAt(linha, 1);
        double preco = (double) tabelaProdutos.getValueAt(linha, 2);
        int quantidade = (int) tabelaProdutos.getValueAt(linha, 3);

        return new Produto(id, nome, preco, quantidade);
    }

    private void adicionarProdutoSelecionado() {
        Produto p = getProdutoSelecionado();
        if (p != null && p.getQuantidade() > 0) {
            carrinhoController.adicionarProduto(p);
            carregarProdutos();
            atualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto disponível!");
        }
    }

    private void removerProdutoSelecionado() {
        Produto p = getProdutoSelecionado();
        if (p != null) {
            carrinhoController.removerProduto(p);
            carregarProdutos();
            atualizarTotal();
        }
    }

    private void atualizarTotal() {
        totalLabel.setText(String.format("Total: R$ %.2f", carrinhoController.calcularTotal()));
    }

    private void emitirNota() {
        StringBuilder nota = new StringBuilder();
        nota.append("----- NOTA FISCAL -----\n");
        nota.append("Cliente: ").append(usuario.getNome()).append("\n");
        nota.append("CPF: ").append(usuario.getCpf()).append("\n\n");
        nota.append("Produtos:\n");

        for (Produto p : carrinhoController.getProdutosCarrinho()) {
            nota.append(String.format("%s - R$ %.2f\n", p.getNome(), p.getPreco()));
        }

        nota.append("\nTotal: R$ ").append(String.format("%.2f", carrinhoController.calcularTotal()));

        JOptionPane.showMessageDialog(this, nota.toString(), "Nota Fiscal", JOptionPane.INFORMATION_MESSAGE);
    }
}
