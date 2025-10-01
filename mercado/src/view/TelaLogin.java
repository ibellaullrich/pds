package view;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JFrame {
    private JTextField txtCpf;
    private JButton btnEntrar;
    private JButton btnCadastrar;
    private ButtonGroup grupoAdmin;
    private JTextField textField;
    private JCheckBox chckbxNewCheckBox;
    private JCheckBox chckbxNewCheckBox_1;

    public TelaLogin() {
    	setBackground(new Color(240, 240, 240));
        setTitle("Login - Supermercado");
        setSize(448, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(false);

        // Painel principal
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 228, 225)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label e campo Nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 21));
        lblNome.setBounds(41, 39, 71, 42);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.setLayout(null);
        panel.add(lblNome );
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // Label e campo CPF
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 21));
        lblCpf.setBounds(38, 114, 63, 27);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblCpf);

        txtCpf = new JTextField();
        txtCpf.setBounds(110, 51, 189, 27);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(txtCpf);

        // Botões
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(36, 189, 132, 23);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(btnEntrar);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(216, 189, 132, 23);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(btnCadastrar);

        // Administrador?
        JLabel lblAdmin = new JLabel("Administrador?");
        lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAdmin.setBounds(41, 259, 95, 27);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblAdmin);

        grupoAdmin = new ButtonGroup();

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        getContentPane().add(panel, BorderLayout.CENTER);
        
        textField = new JTextField();
        textField.setBounds(113, 115, 189, 27);
        panel.add(textField);
        
        chckbxNewCheckBox = new JCheckBox("Sim");
        chckbxNewCheckBox.setBackground(new Color(255, 228, 225));
        chckbxNewCheckBox.setBounds(161, 263, 66, 23);
        panel.add(chckbxNewCheckBox);
        
        chckbxNewCheckBox_1 = new JCheckBox("Não");
        chckbxNewCheckBox_1.setBackground(new Color(255, 228, 225));
        chckbxNewCheckBox_1.setBounds(254, 262, 56, 23);
        panel.add(chckbxNewCheckBox_1);

        // Ações dos botões
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e, AbstractButton rbSim, JTextComponent txtNome) {
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                boolean isAdmin = rbSim.isSelected();

                if (nome.isEmpty() || cpf.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Login realizado!\nNome: " + nome +
                            "\nCPF: " + cpf +
                            "\nAdministrador: " + (isAdmin ? "Sim" : "Não"));
                }
            }

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
        });

        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Abrindo tela de cadastro de usuário...");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}
