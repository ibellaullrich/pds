package view;

import javax.swing.*;
import controller.UsuarioController;
import model.Usuario;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class TelaLogin extends JFrame {

    private JTextField tfCpf;
    private UsuarioController usuarioController;

    public TelaLogin() {
        this.usuarioController = new UsuarioController();

        setTitle("Login");
        setSize(400, 210);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(255, 249, 196));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        JPanel painel = new JPanel(new MigLayout(
                "wrap 2, inset 20",
                "[right][grow]",
                "[][]20[]"
        ));
        painel.setBackground(new Color(255, 249, 196));

        JLabel lblCpf = new JLabel("CPF:");
        tfCpf = new JTextField(20);

        JButton btnLogin = new JButton("Login");
        JButton btnCadastrar = new JButton("Cadastrar");

        painel.add(lblCpf);
        painel.add(tfCpf, "growx");

        painel.add(btnLogin, "span 2, split 2, growx");
        painel.add(btnCadastrar, "growx");

        getContentPane().add(painel);

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
                new TelaCadastroProduto(usuario);
            } else {
                new TelaCompra(usuario);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
        }
    }
}
