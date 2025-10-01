package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaCadastroUsuario extends JFrame {
	private JTextField textField;
	private JTextField textField_1;

    public TelaCadastroUsuario() {
        setTitle("Cadastro de Usuário - Supermercado");
        setSize(479, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color fundo = new Color(255, 204, 204); // rosa claro

        // Painel principal com padding
        JPanel main = new JPanel();
        main.setBackground(new Color(255, 228, 225));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        getContentPane().add(main);
        main.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Nome:");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
        lblNewLabel.setBounds(16, 19, 71, 34);
        main.add(lblNewLabel);
        
        textField = new JTextField();
        textField.setBounds(108, 23, 186, 34);
        main.add(textField);
        textField.setColumns(10);
        
        JRadioButton rdbtnNewRadioButton = new JRadioButton("Administrador");
        rdbtnNewRadioButton.setBackground(new Color(255, 228, 225));
        rdbtnNewRadioButton.setBounds(16, 139, 109, 23);
        main.add(rdbtnNewRadioButton);
        
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 21));
        lblCpf.setBounds(16, 64, 71, 34);
        main.add(lblCpf);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(108, 68, 186, 34);
        main.add(textField_1);
        
        JButton btnNewButton = new JButton("Salvar");
        btnNewButton.setBounds(59, 200, 118, 23);
        main.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Voltar");
        btnNewButton_1.setBounds(245, 200, 118, 23);
        main.add(btnNewButton_1);
    }

    // Teste independente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaCadastroUsuario().setVisible(true);
        });
    }
}
