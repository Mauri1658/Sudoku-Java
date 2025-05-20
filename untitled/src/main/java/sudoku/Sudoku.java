package sudoku;

import sudoku.excepciones.MovimientoInvalidoException;
import sudoku.excepciones.EntradaFueraDeRangoException;

public class Sudoku {
    private int[][] tablero;
    private boolean[][] celdasFijas;
    private static final int TAMANO = 9;
    private static final int SUBTAMANO = 3;

    public Sudoku() {
        tablero = new int[TAMANO][TAMANO];
        celdasFijas = new boolean[TAMANO][TAMANO];
    }

    public void generarTablero(String dificultad) {
        GeneradorSudoku generador = new GeneradorSudoku();
        generador.generarTablero(this, dificultad);
    }

    public boolean esMovimientoValido(int fila, int columna, int valor)
            throws EntradaFueraDeRangoException {
        // Validar rango de entrada
        if (fila < 0 || fila >= TAMANO || columna < 0 || columna >= TAMANO) {
            throw new EntradaFueraDeRangoException("Fila o columna fuera de rango (0-8)");
        }
        if (valor < 1 || valor > 9) {
            throw new EntradaFueraDeRangoException("Valor fuera de rango (1-9)");
        }

        // Verificar si la celda es fija
        if (celdasFijas[fila][columna]) {
            return false;
        }

        // Verificar fila
        for (int c = 0; c < TAMANO; c++) {
            if (tablero[fila][c] == valor && c != columna) {
                return false;
            }
        }

        // Verificar columna
        for (int r = 0; r < TAMANO; r++) {
            if (tablero[r][columna] == valor && r != fila) {
                return false;
            }
        }

        // Verificar subcuadrícula 3x3
        int subFilaInicio = (fila / SUBTAMANO) * SUBTAMANO;
        int subColInicio = (columna / SUBTAMANO) * SUBTAMANO;

        for (int r = subFilaInicio; r < subFilaInicio + SUBTAMANO; r++) {
            for (int c = subColInicio; c < subColInicio + SUBTAMANO; c++) {
                if (tablero[r][c] == valor && r != fila && c != columna) {
                    return false;
                }
            }
        }

        return true;
    }

    public void colocarNumero(int fila, int columna, int valor)
            throws MovimientoInvalidoException, EntradaFueraDeRangoException {
        if (esMovimientoValido(fila, columna, valor)) {
            tablero[fila][columna] = valor;
        } else {
            throw new MovimientoInvalidoException("Movimiento no válido según las reglas del Sudoku");
        }
    }

    public boolean estaResuelto() {
        // Verificar que todas las celdas están llenas
        for (int r = 0; r < TAMANO; r++) {
            for (int c = 0; c < TAMANO; c++) {
                if (tablero[r][c] == 0) {
                    return false;
                }
            }
        }

        // Verificar que todas las filas, columnas y subcuadrículas son válidas
        for (int i = 0; i < TAMANO; i++) {
            if (!esFilaValida(i) || !esColumnaValida(i) || !esSubcuadriculaValida(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean esFilaValida(int fila) {
        boolean[] numeros = new boolean[TAMANO + 1];
        for (int c = 0; c < TAMANO; c++) {
            int num = tablero[fila][c];
            if (num == 0 || numeros[num]) {
                return false;
            }
            numeros[num] = true;
        }
        return true;
    }

    private boolean esColumnaValida(int columna) {
        boolean[] numeros = new boolean[TAMANO + 1];
        for (int r = 0; r < TAMANO; r++) {
            int num = tablero[r][columna];
            if (num == 0 || numeros[num]) {
                return false;
            }
            numeros[num] = true;
        }
        return true;
    }

    private boolean esSubcuadriculaValida(int subGrid) {
        boolean[] numeros = new boolean[TAMANO + 1];
        int filaInicio = (subGrid / SUBTAMANO) * SUBTAMANO;
        int colInicio = (subGrid % SUBTAMANO) * SUBTAMANO;

        for (int r = filaInicio; r < filaInicio + SUBTAMANO; r++) {
            for (int c = colInicio; c < colInicio + SUBTAMANO; c++) {
                int num = tablero[r][c];
                if (num == 0 || numeros[num]) {
                    return false;
                }
                numeros[num] = true;
            }
        }
        return true;
    }

    public void mostrarTablero() {
        for (int r = 0; r < TAMANO; r++) {
            if (r % SUBTAMANO == 0 && r != 0) {
                System.out.println("------+-------+------");
            }
            for (int c = 0; c < TAMANO; c++) {
                if (c % SUBTAMANO == 0 && c != 0) {
                    System.out.print("| ");
                }
                System.out.print(tablero[r][c] == 0 ? "  " : tablero[r][c] + " ");
            }
            System.out.println();
        }
    }

    // Getters y Setters
    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public boolean[][] getCeldasFijas() {
        return celdasFijas;
    }

    public void setCeldasFijas(boolean[][] celdasFijas) {
        this.celdasFijas = celdasFijas;
    }

    public void marcarCeldaComoFija(int fila, int columna) {
        if (fila >= 0 && fila < TAMANO && columna >= 0 && columna < TAMANO) {
            celdasFijas[fila][columna] = true;
        }
    }
}