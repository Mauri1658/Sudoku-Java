package sudoku;

import java.util.Scanner;
import sudoku.excepciones.MovimientoInvalidoException;
import sudoku.excepciones.EntradaFueraDeRangoException;

public class JuegoSudoku {
    private Sudoku sudoku;
    private Scanner scanner;

    public JuegoSudoku() {
        sudoku = new Sudoku();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("¡Bienvenido al Sudoku!");
        System.out.println("Seleccione la dificultad:");
        System.out.println("1. Fácil");
        System.out.println("2. Medio");
        System.out.println("3. Difícil");

        int opcion = scanner.nextInt();
        String dificultad;

        switch (opcion) {
            case 1:
                dificultad = "facil";
                break;
            case 2:
                dificultad = "medio";
                break;
            case 3:
                dificultad = "dificil";
                break;
            default:
                dificultad = "facil";
        }

        sudoku.generarTablero(dificultad);
        jugar();
    }

    void jugar() {
        while (!sudoku.estaResuelto()) {
            sudoku.mostrarTablero();

            System.out.println("Ingrese fila (0-8), columna (0-8) y valor (1-9), separados por espacios:");
            System.out.println("O ingrese 0 para salir");

            int fila = scanner.nextInt();
            if (fila == 0) {
                System.out.println("Juego terminado.");
                return;
            }

            int columna = scanner.nextInt();
            int valor = scanner.nextInt();

            try {
                sudoku.colocarNumero(fila, columna, valor);
            } catch (MovimientoInvalidoException | EntradaFueraDeRangoException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        sudoku.mostrarTablero();
        System.out.println("¡Felicidades! Has resuelto el Sudoku correctamente.");
    }

    public static void main(String[] args) {
        JuegoSudoku juego = new JuegoSudoku();
        juego.iniciar();
    }
}