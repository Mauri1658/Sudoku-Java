package sudoku;

import sudoku.excepciones.EntradaFueraDeRangoException;
import sudoku.excepciones.MovimientoInvalidoException;
import sudoku.excepciones.SudokuException;
import java.util.Arrays;

public class Sudoku {
    private int[][] tablero;
    private boolean[][] celdasFijas;
    private static final int TAMANIO = 9;

    public Sudoku() {
        tablero = new int[TAMANIO][TAMANIO];
        celdasFijas = new boolean[TAMANIO][TAMANIO];
        for (int i = 0; i < TAMANIO; i++) {
            Arrays.fill(tablero[i], 0);
            Arrays.fill(celdasFijas[i], false);
        }
    }

    public void cargarTablero(int[][] tableroGenerado) {
        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                tablero[i][j] = tableroGenerado[i][j];
                celdasFijas[i][j] = tableroGenerado[i][j] != 0;
            }
        }
    }

    public boolean esMovimientoValido(int fila, int columna, int valor)
            throws EntradaFueraDeRangoException, MovimientoInvalidoException {
        validarRango(fila, columna, valor);

        if (celdasFijas[fila][columna]) {
            throw new MovimientoInvalidoException("No puedes modificar una celda fija.");
        }

        if (!filaValida(fila, columna, valor)) {
            throw new MovimientoInvalidoException("El número ya está en la fila.");
        }

        if (!columnaValida(fila, columna, valor)) {
            throw new MovimientoInvalidoException("El número ya está en la columna.");
        }

        if (!bloqueValido(fila, columna, valor)) {
            throw new MovimientoInvalidoException("El número ya está en el bloque 3x3.");
        }

        return true;
    }

    public void colocarNumero(int fila, int columna, int valor) throws SudokuException {
        if (celdasFijas[fila][columna]) {
            throw new MovimientoInvalidoException("No puedes modificar una celda fija.");
        }
        if (esMovimientoValido(fila, columna, valor)) {
            tablero[fila][columna] = valor;
        }
    }

    public void eliminarNumero(int fila, int columna) throws SudokuException {
        validarRango(fila, columna, 1); // valor solo para validar fila y columna
        if (celdasFijas[fila][columna]) {
            throw new MovimientoInvalidoException("No puedes modificar una celda fija.");
        }
        tablero[fila][columna] = 0;
    }

    private boolean esMovimientoValidoSinChequeoFijo(int fila, int columna, int valor)
            throws EntradaFueraDeRangoException, MovimientoInvalidoException {
        validarRango(fila, columna, valor);

        if (!filaValida(fila, columna, valor)) {
            throw new MovimientoInvalidoException("El número ya está en la fila.");
        }

        if (!columnaValida(fila, columna, valor)) {
            throw new MovimientoInvalidoException("El número ya está en la columna.");
        }

        if (!bloqueValido(fila, columna, valor)) {
            throw new MovimientoInvalidoException("El número ya está en el bloque 3x3.");
        }

        return true;
    }

    public boolean estaResuelto() {
        try {
            for (int i = 0; i < TAMANIO; i++) {
                for (int j = 0; j < TAMANIO; j++) {
                    int valor = tablero[i][j];
                    if (valor == 0) return false;

                    tablero[i][j] = 0;
                    if (!esMovimientoValidoSinChequeoFijo(i, j, valor)) {
                        tablero[i][j] = valor; // restaurar
                        return false;
                    }
                    tablero[i][j] = valor; // restaurar
                }
            }
        } catch (SudokuException e) {
            return false;
        }
        return true;
    }

    public void mostrarTablero() {
        for (int i = 0; i < TAMANIO; i++) {
            if (i % 3 == 0) System.out.println("+-------+-------+-------+");
            for (int j = 0; j < TAMANIO; j++) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(tablero[i][j] == 0 ? "  " : tablero[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }

    public int[][] getTablero() {
        return tablero;
    }

    public boolean[][] getCeldasFijas() {
        return celdasFijas;
    }

    // Métodos auxiliares privados

    private void validarRango(int fila, int columna, int valor) throws EntradaFueraDeRangoException {
        if (fila < 0 || fila >= TAMANIO || columna < 0 || columna >= TAMANIO) {
            throw new EntradaFueraDeRangoException("Fila o columna fuera del rango válido (0-8).");
        }
        if (valor < 1 || valor > 9) {
            throw new EntradaFueraDeRangoException("Valor debe estar entre 1 y 9.");
        }
    }

    private boolean filaValida(int fila, int columna, int valor) {
        for (int j = 0; j < TAMANIO; j++) {
            if (j != columna && tablero[fila][j] == valor) return false;
        }
        return true;
    }

    private boolean columnaValida(int fila, int columna, int valor) {
        for (int i = 0; i < TAMANIO; i++) {
            if (i != fila && tablero[i][columna] == valor) return false;
        }
        return true;
    }

    private boolean bloqueValido(int fila, int columna, int valor) {
        int inicioFila = (fila / 3) * 3;
        int inicioCol = (columna / 3) * 3;
        for (int i = inicioFila; i < inicioFila + 3; i++) {
            for (int j = inicioCol; j < inicioCol + 3; j++) {
                if (i == fila && j == columna) continue;
                if (tablero[i][j] == valor) return false;
            }
        }
        return true;
    }
}
