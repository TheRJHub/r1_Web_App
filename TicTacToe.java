import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean playerTurn = true;
    private boolean vsComputer = false;
    private JLabel status;
    private String playerSymbol = "X", computerSymbol = "O";

    public TicTacToe() {
        setTitle("🎮 Tic Tac Toe | RajatKumarDas");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = new JMenu("🎮 Mode");
        JMenuItem pvp = new JMenuItem("Player vs Player");
        JMenuItem pvc = new JMenuItem("Player vs Computer");
        pvp.addActionListener(e -> resetGame(false));
        pvc.addActionListener(e -> resetGame(true));
        modeMenu.add(pvp);
        modeMenu.add(pvc);
        menuBar.add(modeMenu);
        setJMenuBar(menuBar);

        // Game Grid
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        panel.setBackground(Color.black);
        Font font = new Font("Arial", Font.BOLD, 40);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.decode("#1DB954"));
                buttons[i][j].setForeground(Color.white);
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }

        // Status label
        status = new JLabel("🔵 Player X's turn", SwingConstants.CENTER);
        status.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        status.setOpaque(true);
        status.setBackground(Color.black);
        status.setForeground(Color.white);
        status.setPreferredSize(new Dimension(400, 50));

        // Footer
        JLabel footer = new JLabel("© 2025 RajatKumarDas", SwingConstants.CENTER);
        footer.setFont(new Font("Verdana", Font.PLAIN, 12));
        footer.setForeground(Color.white);
        footer.setBackground(Color.black);
        footer.setOpaque(true);
        footer.setPreferredSize(new Dimension(400, 30));

        add(status, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void resetGame(boolean vsComp) {
        vsComputer = vsComp;
        playerTurn = true;
        status.setText("🔵 Player X's turn");
        for (JButton[] row : buttons)
            for (JButton btn : row)
                btn.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (!src.getText().equals("")) return;
        src.setText(playerTurn ? playerSymbol : computerSymbol);
        if (checkWin()) return;
        playerTurn = !playerTurn;

        if (vsComputer && !playerTurn) {
            computerMove();
        } else {
            status.setText("🔵 Player " + (playerTurn ? "X" : "O") + "'s turn");
        }
    }

    private void computerMove() {
        Random rand = new Random();
        int i, j;
        do {
            i = rand.nextInt(3);
            j = rand.nextInt(3);
        } while (!buttons[i][j].getText().equals(""));
        buttons[i][j].setText(computerSymbol);
        if (checkWin()) return;
        playerTurn = true;
        status.setText("🔵 Player X's turn");
    }

    private boolean checkWin() {
        String winner = "";
        for (int i = 0; i < 3; i++) {
            // Row
            if (!buttons[i][0].getText().equals("") &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][0].getText().equals(buttons[i][2].getText())) {
                winner = buttons[i][0].getText();
            }
            // Column
            if (!buttons[0][i].getText().equals("") &&
                buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[0][i].getText().equals(buttons[2][i].getText())) {
                winner = buttons[0][i].getText();
            }
        }

        // Diagonals
        if (!buttons[0][0].getText().equals("") &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText()))
            winner = buttons[0][0].getText();

        if (!buttons[0][2].getText().equals("") &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText()))
            winner = buttons[0][2].getText();

        if (!winner.equals("")) {
            status.setText("🎉 " + (winner.equals(playerSymbol) ? "You Win!" : (vsComputer ? "Computer Wins!" : "Player " + winner + " Wins!")));
            disableAll();
            return true;
        }

        // Draw check
        boolean draw = true;
        for (JButton[] row : buttons)
            for (JButton btn : row)
                if (btn.getText().equals(""))
                    draw = false;
        if (draw) {
            status.setText("⚖️ It's a Draw!");
            return true;
        }

        return false;
    }

    private void disableAll() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                btn.setEnabled(false);
    }
    public static void main(String[] args) {
        System.out.println("Tic-Tac-Toe Starting...!");
        new TicTacToe();
    }
}