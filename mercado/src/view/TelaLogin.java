package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import controller.UsuarioController;
import model.Usuario;
import java.awt.*;
import java.text.ParseException;

import net.miginfocom.swing.MigLayout;

public class TelaLogin extends JFrame {

    private JFormattedTextField tfCpf;
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

        MaskFormatter mascaraCPF = null;
        try {
            mascaraCPF = new MaskFormatter("###.###.###-##");
            mascaraCPF.setPlaceholderCharacter('_');
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        tfCpf = new JFormattedTextField(mascaraCPF);
        tfCpf.setColumns(20);


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
            try {
				new TelaCadastroUsuario();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
    }

    private void login() {

        String cpf = tfCpf.getText().trim();

        if (cpf.contains("_")) {
            JOptionPane.showMessageDialog(this, "CPF incompleto!");
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
