package view;

import javax.swing.*;

import controller.UsuarioController;
import model.Usuario;

import java.awt.*;
import java.awt.event.*;

public class TelaCadastroUsuario extends JFrame {
    private JTextField tfNome;
    private JTextField tfCpf;
    private JCheckBox cbAdministrador;
    private UsuarioController usuarioController;

    public TelaCadastroUsuario() {
        this.usuarioController = new UsuarioController();

        setTitle("Cadastro de Usuário");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCpf = new JLabel("CPF:");
        JLabel lblAdm = new JLabel("Administrador:");
        tfNome = new JTextField();
        tfCpf = new JTextField();
        cbAdministrador = new JCheckBox();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnVoltar = new JButton("Voltar");

        painel.add(lblNome);
        painel.add(tfNome);
        painel.add(lblCpf);
        painel.add(tfCpf);
        painel.add(lblAdm);
        painel.add(cbAdministrador);
        painel.add(btnCadastrar);
        painel.add(btnVoltar);

        add(painel);

        btnCadastrar.addActionListener(e -> cadastrarUsuario());
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaLogin();
        });
    }

    private void cadastrarUsuario() {
        String nome = tfNome.getText().trim();
        String cpf = tfCpf.getText().trim();
        boolean adm = cbAdministrador.isSelected();

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setAdministrador(adm);

        boolean sucesso = usuarioController.cadastrarUsuario(usuario);
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose();
            new TelaLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário!");
        }
    }
}
