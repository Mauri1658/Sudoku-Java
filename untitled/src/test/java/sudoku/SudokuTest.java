package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.excepciones.MovimientoInvalidoException;
import sudoku.excepciones.EntradaFueraDeRangoException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {
    private Sudoku sudoku;

    @BeforeEach
    void setUp() {
        sudoku = new Sudoku();
    }

    @Test
    void testEsMovimientoValido() throws EntradaFueraDeRangoException {
        // Configurar un tablero parcial
        int[][] tableroInicial = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        sudoku.setTablero(tableroInicial);

        // Movimiento válido
        assertTrue(sudoku.esMovimientoValido(0, 2, 4));

        // Movimiento inválido (número repetido en fila)
        assertFalse(sudoku.esMovimientoValido(0, 2, 5));

        // Movimiento inválido (número repetido en columna)
        assertFalse(sudoku.esMovimientoValido(0, 2, 6));

        // Movimiento inválido (número repetido en subcuadrícula)
        assertFalse(sudoku.esMovimientoValido(0, 2, 8));
    }

    @Test
    void testEsMovimientoValidoFueraDeRango() {
        // Fila fuera de rango
        assertThrows(EntradaFueraDeRangoException.class, () -> sudoku.esMovimientoValido(-1, 0, 1));
        assertThrows(EntradaFueraDeRangoException.class, () -> sudoku.esMovimientoValido(9, 0, 1));

        // Columna fuera de rango
        assertThrows(EntradaFueraDeRangoException.class, () -> sudoku.esMovimientoValido(0, -1, 1));
        assertThrows(EntradaFueraDeRangoException.class, () -> sudoku.esMovimientoValido(0, 9, 1));

        // Valor fuera de rango
        assertThrows(EntradaFueraDeRangoException.class, () -> sudoku.esMovimientoValido(0, 0, 0));
        assertThrows(EntradaFueraDeRangoException.class, () -> sudoku.esMovimientoValido(0, 0, 10));
    }

    @Test
    void testColocarNumero() throws MovimientoInvalidoException, EntradaFueraDeRangoException {
        // Configurar tablero vacío
        int[][] tableroVacio = new int[9][9];
        sudoku.setTablero(tableroVacio);

        // Colocar número válido
        sudoku.colocarNumero(0, 0, 1);
        assertEquals(1, sudoku.getTablero()[0][0]);

        // Intentar colocar número inválido
        sudoku.colocarNumero(0, 1, 2);
        assertThrows(MovimientoInvalidoException.class, () -> sudoku.colocarNumero(0, 2, 1));
    }

    @Test
    void testEstaResuelto() {
        // Tablero vacío no está resuelto
        assertFalse(sudoku.estaResuelto());

        // Tablero completo y válido
        int[][] tableroCompleto = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        sudoku.setTablero(tableroCompleto);
        assertTrue(sudoku.estaResuelto());

        // Tablero completo pero inválido (repetido en primera fila)
        int[][] tableroInvalido = {
                {5, 5, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        sudoku.setTablero(tableroInvalido);
        assertFalse(sudoku.estaResuelto());
    }

    @Test
    void testMarcarCeldaComoFija() {
        sudoku.marcarCeldaComoFija(0, 0);
        assertTrue(sudoku.getCeldasFijas()[0][0]);

        // Fuera de rango no debería causar error
        sudoku.marcarCeldaComoFija(-1, 0);
        sudoku.marcarCeldaComoFija(0, -1);
        sudoku.marcarCeldaComoFija(9, 0);
        sudoku.marcarCeldaComoFija(0, 9);
    }
}