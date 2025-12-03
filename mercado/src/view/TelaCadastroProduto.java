package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import model.Produto;
import model.Usuario;
import controller.ProdutoController;
import net.miginfocom.swing.MigLayout;

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
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(255, 249, 196)); // Amarelo claro
        initComponents();
        carregarProdutos();
        setVisible(true);
    }

    private void initComponents() {

        JPanel painel = new JPanel(new MigLayout(
                "wrap 1, inset 15",      // Uma coluna principal
                "[grow]",               // coluna flexível
                "[][][grow]"           // 3 blocos: formulário, botões, tabela
        ));
        painel.setBackground(new Color(255, 249, 196));

        // ----------------------------------------------------------
        // Formulário
        // ----------------------------------------------------------
        JPanel formPanel = new JPanel(new MigLayout(
                "wrap 2, inset 10",
                "[right][grow]",
                "[][][]"
        ));
        formPanel.setBackground(new Color(255, 249, 196));

        tfNome = new JTextField(20);
        tfPreco = new JTextField(20);
        tfQuantidade = new JTextField(20);

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(tfNome, "growx");

        formPanel.add(new JLabel("Preço:"));
        formPanel.add(tfPreco, "growx");

        formPanel.add(new JLabel("Quantidade:"));
        formPanel.add(tfQuantidade, "growx");

        painel.add(formPanel, "growx");

        // ----------------------------------------------------------
        // Botões
        // ----------------------------------------------------------
        JPanel botoes = new JPanel(new MigLayout(
                "inset 5, wrap 4",
                "[grow][grow][grow][grow]",
                "[]"
        ));
        botoes.setBackground(new Color(255, 249, 196));

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnSair = new JButton("Sair");

        botoes.add(btnAdicionar, "growx");
        botoes.add(btnEditar, "growx");
        botoes.add(btnRemover, "growx");
        botoes.add(btnSair, "growx");

        painel.add(botoes, "growx");

        // ----------------------------------------------------------
        // Tabela
        // ----------------------------------------------------------
        modelo = new DefaultTableModel(new String[]{"ID", "Nome", "Preço", "Quantidade"}, 0);
        tabela = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabela);
        painel.add(scroll, "grow");

        getContentPane().add(painel);

        // Eventos
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
            String nome = tfNome.getText().trim();
            double preco = Double.parseDouble(tfPreco.getText());
            int quantidade = Integer.parseInt(tfQuantidade.getText());

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome não pode estar vazio!");
                return;
            }

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

        try {
            int id = (int) modelo.getValueAt(linha, 0);
            String nome = tfNome.getText().trim();
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

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço e quantidade devem ser numéricos.");
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
