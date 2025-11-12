package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import model.Produto;
import model.Usuario;
import controller.ProdutoController;

public class TelaCadastroProduto extends JFrame {

    private Usuario usuario;
    private ProdutoController produtoController;

    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField tfNome, tfPreco, tfQuantidade;

    public TelaCadastroProduto(Usuario usuario) {
        this.usuario = usuario;
        this.produtoController = new ProdutoController();

        setTitle("Cadastro de Produtos - Administrador: " + usuario.getNome());
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarProdutos();
        setVisible(true);
    }

    private void initComponents() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

   
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        tfNome = new JTextField();
        tfPreco = new JTextField();
        tfQuantidade = new JTextField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(tfNome);
        formPanel.add(new JLabel("Preço:"));
        formPanel.add(tfPreco);
        formPanel.add(new JLabel("Quantidade:"));
        formPanel.add(tfQuantidade);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnSair = new JButton("Sair");

        JPanel botoes = new JPanel();
        botoes.add(btnAdicionar);
        botoes.add(btnEditar);
        botoes.add(btnRemover);
        botoes.add(btnSair);

        painel.add(formPanel, BorderLayout.NORTH);
        painel.add(botoes, BorderLayout.CENTER);


        modelo = new DefaultTableModel(new String[]{"ID", "Nome", "Preço", "Quantidade"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        painel.add(scroll, BorderLayout.SOUTH);

        add(painel);

    
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnEditar.addActionListener(e -> editarProduto());
        btnRemover.addActionListener(e -> removerProduto());
        btnSair.addActionListener(e -> sair());
    }

    private void carregarProdutos() {
        modelo.setRowCount(0);
        List<Produto> produtos = produtoController.listarProdutos();
        for (Produto p : produtos) {
            modelo.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getPreco(),
                p.getQuantidade()
            });
        }
    }

    private void adicionarProduto() {
        try {
            String nome = tfNome.getText();
            double preco = Double.parseDouble(tfPreco.getText());
            int quantidade = Integer.parseInt(tfQuantidade.getText());

            Produto produto = new Produto(0, nome, preco, quantidade);
            boolean sucesso = produtoController.adicionarProduto(produto);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!");
                carregarProdutos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar produto.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço e quantidade devem ser numéricos.");
        }
    }

    private void editarProduto() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        String nome = tfNome.getText();
        double preco = Double.parseDouble(tfPreco.getText());
        int quantidade = Integer.parseInt(tfQuantidade.getText());

        Produto produto = new Produto(id, nome, preco, quantidade);
        boolean sucesso = produtoController.atualizarProduto(produto);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            carregarProdutos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar produto.");
        }
    }

    private void removerProduto() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.");
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        boolean sucesso = produtoController.removerProduto(id);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
            carregarProdutos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao remover produto.");
        }
    }

    private void sair() {
        dispose();
        new TelaLogin();
    }

    private void limparCampos() {
        tfNome.setText("");
        tfPreco.setText("");
        tfQuantidade.setText("");
    }
}
