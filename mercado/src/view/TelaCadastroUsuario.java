package view;

import javax.swing.*;
import controller.UsuarioController;
import model.Usuario;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;



public class TelaCadastroUsuario extends JFrame {

    private JTextField tfNome;
    private JTextField tfCpf;
    private JCheckBox cbAdministrador;
    private UsuarioController usuarioController;

    public TelaCadastroUsuario() throws ParseException {
        this.usuarioController = new UsuarioController();

        setTitle("Cadastro de Usuário");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(255, 249, 196)); // amarelo claro
        initComponents();
        setVisible(true);
    }

    private void initComponents() throws ParseException {

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

        MaskFormatter mascaraCPF = new MaskFormatter("###.###.###-##");
        mascaraCPF.setPlaceholderCharacter('_');
        tfCpf = new JFormattedTextField(mascaraCPF);

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
