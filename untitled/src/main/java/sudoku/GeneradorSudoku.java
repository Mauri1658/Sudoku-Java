package sudoku;

import java.util.Random;

public class GeneradorSudoku {
    private static final int FACIL = 30;
    private static final int MEDIO = 40;
    private static final int DIFICIL = 50;
    private Random random = new Random();

    public void generarTablero(Sudoku sudoku, String dificultad) {
        // Primero generamos un tablero completo válido
        generarTableroCompleto(sudoku);

        // Luego eliminamos celdas según la dificultad
        int celdasAEliminar;
        switch (dificultad.toLowerCase()) {
            case "facil":
                celdasAEliminar = FACIL;
                break;
            case "medio":
                celdasAEliminar = MEDIO;
                break;
            case "dificil":
                celdasAEliminar = DIFICIL;
                break;
            default:
                celdasAEliminar = FACIL;
        }

        eliminarCeldas(sudoku, celdasAEliminar);
    }

    private void generarTableroCompleto(Sudoku sudoku) {
        int[][] tablero = new int[9][9];
        resolverTablero(tablero, 0, 0);
        sudoku.setTablero(tablero);
    }

    private boolean resolverTablero(int[][] tablero, int fila, int columna) {
        if (fila == 9) {
            return true;
        }

        if (columna == 9) {
            return resolverTablero(tablero, fila + 1, 0);
        }

        if (tablero[fila][columna] != 0) {
            return resolverTablero(tablero, fila, columna + 1);
        }

        // Generar números aleatorios del 1 al 9 en orden aleatorio
        int[] numeros = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(numeros);

        for (int num : numeros) {
            if (esNumeroValido(tablero, fila, columna, num)) {
                tablero[fila][columna] = num;

                if (resolverTablero(tablero, fila, columna + 1)) {
                    return true;
                }

                tablero[fila][columna] = 0; // Backtracking
            }
        }

        return false;
    }

    boolean esNumeroValido(int[][] tablero, int fila, int columna, int num) {
        // Verificar fila
        for (int c = 0; c < 9; c++) {
            if (tablero[fila][c] == num) {
                return false;
            }
        }

        // Verificar columna
        for (int r = 0; r < 9; r++) {
            if (tablero[r][columna] == num) {
                return false;
            }
        }

        // Verificar subcuadrícula 3x3
        int subFilaInicio = (fila / 3) * 3;
        int subColInicio = (columna / 3) * 3;

        for (int r = subFilaInicio; r < subFilaInicio + 3; r++) {
            for (int c = subColInicio; c < subColInicio + 3; c++) {
                if (tablero[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    void eliminarCeldas(Sudoku sudoku, int celdasAEliminar) {
        int[][] tablero = sudoku.getTablero();
        boolean[][] celdasFijas = new boolean[9][9];

        int eliminadas = 0;
        while (eliminadas < celdasAEliminar) {
            int fila = random.nextInt(9);
            int columna = random.nextInt(9);

            if (tablero[fila][columna] != 0) {
                tablero[fila][columna] = 0;
                celdasFijas[fila][columna] = false;
                eliminadas++;
            } else {
                celdasFijas[fila][columna] = true;
            }
        }

        // Marcar las celdas no eliminadas como fijas
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (tablero[r][c] != 0) {
                    sudoku.marcarCeldaComoFija(r, c);
                }
            }
        }

        sudoku.setTablero(tablero);
        sudoku.setCeldasFijas(celdasFijas);
    }

    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}