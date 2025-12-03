package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import model.Produto;
import model.Usuario;
import controller.ProdutoController;
import net.miginfocom.swing.MigLayout;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class TelaCadastroProduto extends JFrame {

    private Usuario usuario;
    private ProdutoController produtoController;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JTextField tfNome;
    private JFormattedTextField tfPreco, tfQuantidade;

    public TelaCadastroProduto(Usuario usuario) {
        this.usuario = usuario;
        this.produtoController = new ProdutoController();

        setTitle("Cadastro de Produtos - Administrador: " + usuario.getNome());
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(255, 249, 196));
        initComponents();
        carregarProdutos();
        setVisible(true);
    }

    private void initComponents() {

        JPanel painel = new JPanel(new MigLayout(
                "wrap 1, inset 15",
                "[grow]",
                "[][][grow]"
        ));
        painel.setBackground(new Color(255, 249, 196));

        JPanel formPanel = new JPanel(new MigLayout(
                "wrap 2, inset 10",
                "[right][grow]",
                "[][][]"
        ));
        formPanel.setBackground(new Color(255, 249, 196));

        // -----------------------------------------
        // CAMPO NOME (SOMENTE LETRAS)
        // -----------------------------------------
        tfNome = new JTextField(20);
        tfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != ' ' && c != 'á' && c != 'é' &&
                    c != 'í' && c != 'ó' && c != 'ú' && c != 'Á' && c != 'É' &&
                    c != 'Í' && c != 'Ó' && c != 'Ú' && c != 'ã' && c != 'õ' &&
                    c != 'ç' && c != 'Ã' && c != 'Õ' && c != 'Ç') {
                    e.consume();
                }
            }
        });

        // -----------------------------------------
        // CAMPO PREÇO (DECIMAL FLEXÍVEL)
        // -----------------------------------------
        NumberFormatter mascaraPreco = new NumberFormatter(new DecimalFormat("#0.00"));
        mascaraPreco.setValueClass(Double.class);
        mascaraPreco.setAllowsInvalid(true);
        mascaraPreco.setCommitsOnValidEdit(true);

        tfPreco = new JFormattedTextField(mascaraPreco);
        tfPreco.setColumns(20);

        // -----------------------------------------
        // CAMPO QUANTIDADE (SOMENTE NÚMEROS)
        // -----------------------------------------
        MaskFormatter mascaraQtd = null;
        try {
            mascaraQtd = new MaskFormatter("####");
            mascaraQtd.setPlaceholderCharacter(' ');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tfQuantidade = new JFormattedTextField(mascaraQtd);
        tfQuantidade.setColumns(20);

        // adiciona no painel
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(tfNome, "growx");

        formPanel.add(new JLabel("Preço:"));
        formPanel.add(tfPreco, "growx");

        formPanel.add(new JLabel("Quantidade:"));
        formPanel.add(tfQuantidade, "growx");

        painel.add(formPanel, "growx");

        // -----------------------------------------
        // BOTÕES
        // -----------------------------------------
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

        // -----------------------------------------
        // TABELA
        // -----------------------------------------
        modelo = new DefaultTableModel(new String[]{"ID", "Nome", "Preço", "Quantidade"}, 0);
        tabela = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabela);
        painel.add(scroll, "grow");

        getContentPane().add(painel);

        // eventos
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnEditar.addActionListener(e -> editarProduto());
        btnRemover.addActionListener(e -> removerProduto());
        btnSair.addActionListener(e -> sair());
    }

    // -----------------------------------------
    // LEITURA SEGURA DOS CAMPOS FORMATADOS
    // -----------------------------------------
    private Double lerPrecoSeguro() {
        try {
            Object v = tfPreco.getValue();
            if (v instanceof Number) return ((Number) v).doubleValue();

            String s = tfPreco.getText().trim();
            if (s.isEmpty()) return null;

            s = s.replaceAll("[^0-9,.-]", "");
            s = s.replace(',', '.');

            return Double.parseDouble(s);

        } catch (Exception e) {
            return null;
        }
    }

    private Integer lerQuantidadeSeguro() {
        try {
            Object v = tfQuantidade.getValue();
            if (v instanceof Number) return ((Number) v).intValue();

            String s = tfQuantidade.getText().trim();
            if (s.isEmpty()) return null;

            s = s.replaceAll("\\D", "");
            if (s.isEmpty()) return null;

            return Integer.parseInt(s);

        } catch (Exception e) {
            return null;
        }
    }

    // -----------------------------------------
    // CARREGAR TABELA
    // -----------------------------------------
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

    // -----------------------------------------
    // ADICIONAR PRODUTO
    // -----------------------------------------
    private void adicionarProduto() {
        String nome = tfNome.getText().trim();
        Double preco = lerPrecoSeguro();
        Integer quantidade = lerQuantidadeSeguro();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome não pode estar vazio!");
            return;
        }
        if (preco == null) {
            JOptionPane.showMessageDialog(this, "Preço inválido!");
            return;
        }
        if (quantidade == null) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
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
    }

    // -----------------------------------------
    // EDITAR PRODUTO
    // -----------------------------------------
    private void editarProduto() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);

        String nome = tfNome.getText().trim();
        Double preco = lerPrecoSeguro();
        Integer quantidade = lerQuantidadeSeguro();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome não pode estar vazio!");
            return;
        }
        if (preco == null) {
            JOptionPane.showMessageDialog(this, "Preço inválido!");
            return;
        }
        if (quantidade == null) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
            return;
        }

        Produto produto = new Produto(id, nome, preco, quantidade);
        boolean sucesso = produtoController.atualizarProduto(produto);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            carregarProdutos();
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar produto.");
        }
    }

    // -----------------------------------------
    // REMOVER PRODUTO
    // -----------------------------------------
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
            limparCampos();
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
        tfPreco.setValue(null);
        tfQuantidade.setValue(null);
    }
}
