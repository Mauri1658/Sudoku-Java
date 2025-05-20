package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sudoku.excepciones.MovimientoInvalidoException;
import sudoku.excepciones.EntradaFueraDeRangoException;

public class SudokuGUI extends JFrame {
    private Sudoku sudoku;
    JButton[][] botones;
    JComboBox<String> dificultadComboBox;
    JButton nuevoJuegoButton;
    JButton verificarButton;

    public SudokuGUI() {
        sudoku = new Sudoku();
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Panel de controles
        JPanel controlPanel = new JPanel();
        dificultadComboBox = new JComboBox<>(new String[]{"Fácil", "Medio", "Difícil"});
        nuevoJuegoButton = new JButton("Nuevo Juego");
        verificarButton = new JButton("Verificar Solución");

        nuevoJuegoButton.addActionListener(e -> iniciarNuevoJuego());
        verificarButton.addActionListener(e -> verificarSolucion());

        controlPanel.add(new JLabel("Dificultad:"));
        controlPanel.add(dificultadComboBox);
        controlPanel.add(nuevoJuegoButton);
        controlPanel.add(verificarButton);

        add(controlPanel, BorderLayout.NORTH);

        // Panel del tablero
        JPanel tableroPanel = new JPanel(new GridLayout(9, 9));
        tableroPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botones = new JButton[9][9];

        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 20));

                // Añadir bordes para las subcuadrículas 3x3
                if ((fila / 3) * 3 + 3 == 9 && (columna / 3) * 3 + 3 == 9) {
                    button.setBorder(BorderFactory.createMatteBorder(
                            1, 1, 2, 2, Color.BLACK));
                } else if ((fila / 3) * 3 + 3 == 9) {
                    button.setBorder(BorderFactory.createMatteBorder(
                            1, 1, 2, 1, Color.BLACK));
                } else if ((columna / 3) * 3 + 3 == 9) {
                    button.setBorder(BorderFactory.createMatteBorder(
                            1, 1, 1, 2, Color.BLACK));
                } else {
                    button.setBorder(BorderFactory.createMatteBorder(
                            1, 1, 1, 1, Color.BLACK));
                }

                final int f = fila;
                final int c = columna;

                button.addActionListener(e -> manejarClickCelda(f, c));
                botones[fila][columna] = button;
                tableroPanel.add(button);
            }
        }

        add(tableroPanel, BorderLayout.CENTER);

        // Iniciar un juego por defecto
        iniciarNuevoJuego();
    }

    void iniciarNuevoJuego() {
        String dificultad = dificultadComboBox.getSelectedItem().toString().toLowerCase();
        sudoku.generarTablero(dificultad);
        actualizarTablero();
    }

    private void actualizarTablero() {
        int[][] tablero = sudoku.getTablero();
        boolean[][] celdasFijas = sudoku.getCeldasFijas();

        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                JButton button = botones[fila][columna];
                if (tablero[fila][columna] == 0) {
                    button.setText("");
                    button.setEnabled(true);
                    button.setBackground(Color.WHITE);
                } else {
                    button.setText(String.valueOf(tablero[fila][columna]));
                    if (celdasFijas[fila][columna]) {
                        button.setEnabled(false);
                        button.setBackground(new Color(240, 240, 240));
                    } else {
                        button.setEnabled(true);
                        button.setBackground(Color.WHITE);
                    }
                }
            }
        }
    }

    private void manejarClickCelda(int fila, int columna) {
        String input = JOptionPane.showInputDialog(this, "Ingrese un número (1-9):");
        if (input != null && !input.isEmpty()) {
            try {
                int valor = Integer.parseInt(input);
                sudoku.colocarNumero(fila, columna, valor);
                actualizarTablero();

                if (sudoku.estaResuelto()) {
                    JOptionPane.showMessageDialog(this, "¡Felicidades! Has resuelto el Sudoku correctamente.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido (1-9).", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (MovimientoInvalidoException | EntradaFueraDeRangoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void verificarSolucion() {
        if (sudoku.estaResuelto()) {
            JOptionPane.showMessageDialog(this, "¡Correcto! El tablero está resuelto adecuadamente.");
        } else {
            JOptionPane.showMessageDialog(this, "El tablero aún no está resuelto correctamente.", "Atención", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuGUI gui = new SudokuGUI();
            gui.setVisible(true);
        });
    }
}