package view;

import javax.swing.*;
import controller.UsuarioController;
import model.Usuario;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class TelaCadastroUsuario extends JFrame {

    private JTextField tfNome;
    private JTextField tfCpf;
    private JCheckBox cbAdministrador;
    private UsuarioController usuarioController;

    public TelaCadastroUsuario() {
        this.usuarioController = new UsuarioController();

        setTitle("Cadastro de Usuário");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(255, 249, 196)); // amarelo claro
        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        JPanel painel = new JPanel(new MigLayout(
                "wrap 2, inset 20",  // wrap 2 colunas
                "[right][grow]",     // primeira col fixa à direita, segunda flexível
                "[][][][]20[]"       // linhas automáticas + espaço antes dos botões
        ));

        painel.setBackground(new Color(255, 249, 196));

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCpf = new JLabel("CPF:");
        JLabel lblAdm = new JLabel("Administrador:");

        tfNome = new JTextField(20);
        tfCpf = new JTextField(20);
        cbAdministrador = new JCheckBox();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnVoltar = new JButton("Voltar");

        painel.add(lblNome);
        painel.add(tfNome, "growx");

        painel.add(lblCpf);
        painel.add(tfCpf, "growx");

        painel.add(lblAdm);
        painel.add(cbAdministrador);

        painel.add(btnCadastrar, "span 2, split 2, growx");
        painel.add(btnVoltar, "growx");

        getContentPane().add(painel);

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

        try {
            boolean sucesso = usuarioController.cadastrarUsuario(usuario);

            if (!sucesso) {
                throw new Exception("Falha ao inserir no banco.");
            }

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose();
            new TelaLogin();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }}
