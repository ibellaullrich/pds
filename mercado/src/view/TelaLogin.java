package view;

import javax.swing.*;

import controller.UsuarioController;
import model.Usuario;

import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JFrame {
    private JTextField tfCpf;
    private UsuarioController usuarioController;

    public TelaLogin() {
        this.usuarioController = new UsuarioController();

        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblCpf = new JLabel("CPF:");
        tfCpf = new JTextField();

        JButton btnLogin = new JButton("Login");
        JButton btnCadastrar = new JButton("Cadastrar");

        painel.add(lblCpf);
        painel.add(tfCpf);
        painel.add(btnLogin);
        painel.add(btnCadastrar);

        add(painel);

        btnLogin.addActionListener(e -> login());
        btnCadastrar.addActionListener(e -> {
            dispose();
            new TelaCadastroUsuario();
        });
    }

    private void login() {
        String cpf = tfCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CPF!");
            return;
        }

        Usuario usuario = usuarioController.validarLogin(cpf);
        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuario.getNome() + "!");
            dispose();
            if (usuario.isAdministrador()) {
                // Aqui você pode abrir a tela de cadastro de produtos
                new TelaCadastroProduto(usuario);
            } else {
                // Cliente vai para a tela de compra
                new TelaCompra(usuario);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
        }
    }
}
