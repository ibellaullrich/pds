package controller;

import javax.swing.SwingUtilities;

import view.TelaLogin;

public class Main {
    public static void main(String[] args) {
        // Inicia a aplicação na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            new TelaLogin();
        });
    }
}