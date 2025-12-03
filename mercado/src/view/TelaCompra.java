package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.CarrinhoController;
import controller.ProdutoController;
import model.Produto;
import model.Usuario;

import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class TelaCompra extends JFrame {
    private Usuario usuario;
    private ProdutoController produtoController;
    private CarrinhoController carrinhoController;
    private JTable tabelaProdutos;
    private JLabel totalLabel;

    public TelaCompra(Usuario usuario) {
    	getContentPane().setBackground(SystemColor.info);
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
        getContentPane().setLayout(new MigLayout("", "[584px]", "[427px][33px]"));

        tabelaProdutos = new JTable();
        JScrollPane scroll = new JScrollPane(tabelaProdutos);
        getContentPane().add(scroll, "cell 0 0,growx,aligny top");

        JPanel painelInferior = new JPanel();
        painelInferior.setBackground(SystemColor.info);
        totalLabel = new JLabel("Total: R$ 0.00");
        JButton btnAdicionar = new JButton("Adicionar ao Carrinho");
        JButton btnRemover = new JButton("Remover do Carrinho");
        JButton btnNota = new JButton("Emitir Nota Fiscal");
        JButton btnSair = new JButton("Sair");
        painelInferior.setLayout(new MigLayout("", "[69px][135px][135px][113px][51px]", "[23px]"));

        painelInferior.add(totalLabel, "cell 0 0,alignx left,aligny center");
        painelInferior.add(btnAdicionar, "cell 1 0,alignx left,aligny top");
        painelInferior.add(btnRemover, "cell 2 0,alignx left,aligny top");
        painelInferior.add(btnNota, "cell 3 0,alignx left,aligny top");
        painelInferior.add(btnSair, "cell 4 0,alignx left,aligny top");
        getContentPane().add(painelInferior, "cell 0 1,growx,aligny top");

        btnAdicionar.addActionListener(e -> adicionarProdutoSelecionado());
        btnRemover.addActionListener(e -> removerProdutoSelecionado());
        btnNota.addActionListener(e -> emitirNota());
        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin();
        });
    }

    private void carregarProdutos() {
        try {
            String[] colunas = {"ID", "Nome", "Preço", "Quantidade"};
            DefaultTableModel model = new DefaultTableModel(colunas, 0);
            for (Produto p : produtoController.listarProdutos()) {
                Object[] linha = {p.getId(), p.getNome(), p.getPreco(), p.getQuantidade()};
                model.addRow(linha);
            }
            tabelaProdutos.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar produtos: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private Produto getProdutoSelecionado() {
        try {
            int linha = tabelaProdutos.getSelectedRow();
            if (linha == -1) {
                return null;
            }

            int id = (int) tabelaProdutos.getValueAt(linha, 0);
            String nome = (String) tabelaProdutos.getValueAt(linha, 1);
            double preco = (double) tabelaProdutos.getValueAt(linha, 2);
            int quantidade = (int) tabelaProdutos.getValueAt(linha, 3);

            return new Produto(id, nome, preco, quantidade);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao obter produto selecionado: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void adicionarProdutoSelecionado() {
        try {
            Produto p = getProdutoSelecionado();
            if (p == null) {
                throw new IllegalStateException("Selecione um produto");
            }
            if (p.getQuantidade() <= 0) {
                throw new IllegalStateException("Produto sem estoque disponível");
            }

            carrinhoController.adicionarProduto(p);
            carregarProdutos();
            atualizarTotal();
            JOptionPane.showMessageDialog(this, 
                "Produto adicionado ao carrinho!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao adicionar produto: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerProdutoSelecionado() {
        try {
            Produto p = getProdutoSelecionado();
            if (p == null) {
                throw new IllegalStateException("Selecione um produto");
            }

            carrinhoController.removerProduto(p);
            carregarProdutos();
            atualizarTotal();
            JOptionPane.showMessageDialog(this, 
                "Produto removido do carrinho!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao remover produto: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTotal() {
        try {
            totalLabel.setText(String.format("Total: R$ %.2f", carrinhoController.calcularTotal()));
        } catch (Exception e) {
            totalLabel.setText("Total: R$ 0.00");
            System.err.println("Erro ao atualizar total: " + e.getMessage());
        }
    }

    private void emitirNota() {
        try {
            if (carrinhoController.getProdutosCarrinho().isEmpty()) {
                throw new IllegalStateException("Carrinho vazio. Adicione produtos antes de emitir a nota.");
            }

            StringBuilder nota = new StringBuilder();
            nota.append("----- NOTA FISCAL -----\n");
            nota.append("Cliente: ").append(usuario.getNome()).append("\n");
            nota.append("CPF: ").append(usuario.getCpf()).append("\n\n");
            nota.append("Produtos:\n");

            for (Produto p : carrinhoController.getProdutosCarrinho()) {
                nota.append(String.format("%s - R$ %.2f\n", p.getNome(), p.getPreco()));
            }

            nota.append("\nTotal: R$ ").append(String.format("%.2f", carrinhoController.calcularTotal()));

            JOptionPane.showMessageDialog(this, 
                nota.toString(), 
                "Nota Fiscal", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao emitir nota: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}