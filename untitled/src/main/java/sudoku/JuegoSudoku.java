package sudoku;

import java.util.Scanner;
import sudoku.excepciones.SudokuException;

public class JuegoSudoku {
    Sudoku sudoku;
    GeneradorSudoku generador;
    private Scanner scanner;

    public JuegoSudoku() {
        sudoku = new Sudoku();
        generador = new GeneradorSudoku();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("Bienvenido al Sudoku!");
        System.out.print("Selecciona dificultad (facil, medio, dificil): ");
        String dificultad = scanner.nextLine().trim().toLowerCase();

        while (!dificultad.equals("facil") && !dificultad.equals("medio") && !dificultad.equals("dificil")) {
            System.out.println("Dificultad inválida. Introduce facil, medio o dificil.");
            dificultad = scanner.nextLine().trim().toLowerCase();
        }

        int[][] tableroGenerado = generador.generarTablero(dificultad);
        sudoku.cargarTablero(tableroGenerado);

        boolean terminado = false;

        while (!terminado) {
            sudoku.mostrarTablero();
            System.out.println("Introduce movimiento: fila(1-9) columna(1-9) valor(1-9), o 0 0 0 para salir:");
            int fila = scanner.nextInt() - 1;
            int columna = scanner.nextInt() - 1;
            int valor = scanner.nextInt();

            if (fila == -1 && columna == -1 && valor == 0) {
                System.out.println("Saliendo del juego...");
                break;
            }

            try {
                sudoku.colocarNumero(fila, columna, valor);
                if (sudoku.estaResuelto()) {
                    sudoku.mostrarTablero();
                    System.out.println("¡Felicidades! Has completado el Sudoku.");
                    terminado = true;
                }
            } catch (SudokuException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        JuegoSudoku juego = new JuegoSudoku();
        juego.iniciar();
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public GeneradorSudoku getGenerador() {
        return generador;
    }


}
