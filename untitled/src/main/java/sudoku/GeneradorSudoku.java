package sudoku;

import java.util.Random;

public class GeneradorSudoku {
    private static final int TAMANIO = 9;
    private int[][] tablero;
    private Random rand;

    public GeneradorSudoku() {
        tablero = new int[TAMANIO][TAMANIO];
        rand = new Random(System.nanoTime()); // Semilla variable para aleatoriedad
    }

    private boolean generarCompleto(int fila, int columna) {
        if (fila == TAMANIO) return true;
        int siguienteFila = (columna == TAMANIO - 1) ? fila + 1 : fila;
        int siguienteColumna = (columna + 1) % TAMANIO;

        int[] numeros = numerosAleatorios();

        for (int num : numeros) {
            if (esValido(fila, columna, num)) {
                tablero[fila][columna] = num;
                if (generarCompleto(siguienteFila, siguienteColumna)) {
                    return true;
                }
                tablero[fila][columna] = 0;
            }
        }
        return false;
    }

    public int[][] generarTablero(String dificultad) {
        limpiarTablero();
        generarCompleto(0, 0);

        int celdasVaciar = switch (dificultad.toLowerCase()) {
            case "facil" -> 30;
            case "medio" -> 40;
            case "dificil" -> 50;
            default -> 30;
        };

        vaciarCeldas(celdasVaciar);

        return tablero;
    }

    private void limpiarTablero() {
        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                tablero[i][j] = 0;
            }
        }
    }

    private void vaciarCeldas(int cantidad) {
        int vaciadas = 0;
        while (vaciadas < cantidad) {
            int fila = rand.nextInt(TAMANIO);
            int col = rand.nextInt(TAMANIO);
            if (tablero[fila][col] != 0) {
                tablero[fila][col] = 0;
                vaciadas++;
            }
        }
    }

    private int[] numerosAleatorios() {
        int[] nums = new int[TAMANIO];
        for (int i = 0; i < TAMANIO; i++) {
            nums[i] = i + 1;
        }
        for (int i = TAMANIO - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        return nums;
    }

    private boolean esValido(int fila, int columna, int valor) {
        for (int i = 0; i < TAMANIO; i++) {
            if (tablero[fila][i] == valor) return false;
            if (tablero[i][columna] == valor) return false;
        }
        int inicioFila = (fila / 3) * 3;
        int inicioCol = (columna / 3) * 3;
        for (int i = inicioFila; i < inicioFila + 3; i++) {
            for (int j = inicioCol; j < inicioCol + 3; j++) {
                if (tablero[i][j] == valor) return false;
            }
        }
        return true;
    }
}
