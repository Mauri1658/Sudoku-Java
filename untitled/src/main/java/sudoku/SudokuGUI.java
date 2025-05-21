package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import sudoku.excepciones.SudokuException;

public class SudokuGUI extends JFrame {
    private Sudoku sudoku;
    private GeneradorSudoku generador;
    private JTextField[][] celdas;
    private static final int TAMANIO = 9;

    public SudokuGUI() {
        sudoku = new Sudoku();
        generador = new GeneradorSudoku();
        sudoku.cargarTablero(generador.generarTablero("facil"));

        setTitle("Juego Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(TAMANIO, TAMANIO));
        celdas = new JTextField[TAMANIO][TAMANIO];

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                celdas[i][j] = new JTextField();
                celdas[i][j].setHorizontalAlignment(JTextField.CENTER);
                celdas[i][j].setFont(new Font("Arial", Font.BOLD, 20));

                int valor = sudoku.getTablero()[i][j];
                if (valor != 0) {
                    celdas[i][j].setText(String.valueOf(valor));
                    celdas[i][j].setEditable(false);
                    celdas[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    celdas[i][j].setText("");
                    celdas[i][j].setEditable(true);
                    celdas[i][j].setBackground(Color.WHITE);

                    final int fila = i;
                    final int col = j;

                    celdas[i][j].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if (c < '1' || c > '9') {
                                e.consume(); // Solo permitir números 1-9
                            }
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {
                            String text = celdas[fila][col].getText();
                            if (text.isEmpty()) {
                                try {
                                    sudoku.eliminarNumero(fila, col);
                                    celdas[fila][col].setBackground(Color.WHITE);
                                } catch (SudokuException ex) {
                                    // No debería ocurrir
                                }
                                return;
                            }
                            try {
                                int valor = Integer.parseInt(text);
                                sudoku.colocarNumero(fila, col, valor);
                                celdas[fila][col].setBackground(Color.WHITE);
                            } catch (SudokuException ex) {
                                celdas[fila][col].setBackground(Color.PINK);
                            }
                        }
                    });
                }
                panel.add(celdas[i][j]);
            }
        }

        JButton btnComprobar = new JButton("Comprobar");
        btnComprobar.addActionListener(e -> {
            if (sudoku.estaResuelto()) {
                JOptionPane.showMessageDialog(this, "¡Felicidades! Sudoku completado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "El Sudoku no está completo o tiene errores.");
            }
        });

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.addActionListener(e -> {
            sudoku.cargarTablero(generador.generarTablero("facil"));
            actualizarTablero();
        });

        JPanel botones = new JPanel();
        botones.add(btnComprobar);
        botones.add(btnReiniciar);

        add(panel, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void actualizarTablero() {
        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                int valor = sudoku.getTablero()[i][j];
                celdas[i][j].setText(valor == 0 ? "" : String.valueOf(valor));
                if (sudoku.getCeldasFijas()[i][j]) {
                    celdas[i][j].setEditable(false);
                    celdas[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    celdas[i][j].setEditable(true);
                    celdas[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuGUI gui = new SudokuGUI();
            gui.setVisible(true);
        });
    }
}
