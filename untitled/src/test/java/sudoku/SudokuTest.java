package sudoku;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sudoku.excepciones.MovimientoInvalidoException;
import sudoku.excepciones.EntradaFueraDeRangoException;
import sudoku.excepciones.SudokuException;

public class SudokuTest {

    private Sudoku sudoku;

    @BeforeEach
    public void setUp() {
        sudoku = new Sudoku();
        // Preparar un tablero base para las pruebas
        int[][] tableroBase = {
                {5,3,0, 0,7,0, 0,0,0},
                {6,0,0, 1,9,5, 0,0,0},
                {0,9,8, 0,0,0, 0,6,0},

                {8,0,0, 0,6,0, 0,0,3},
                {4,0,0, 8,0,3, 0,0,1},
                {7,0,0, 0,2,0, 0,0,6},

                {0,6,0, 0,0,0, 2,8,0},
                {0,0,0, 4,1,9, 0,0,5},
                {0,0,0, 0,8,0, 0,7,9}
        };
        sudoku.cargarTablero(tableroBase);
    }

    @Test
    public void testEsMovimientoValidoCorrecto() throws Exception {
        // Posición vacía y válida: fila 0, columna 2, valor 1 (no está en fila, columna ni bloque)
        assertTrue(sudoku.esMovimientoValido(0, 2, 1));
    }

    @Test
    public void testEsMovimientoValidoEnCeldaFijaLanzaExcepcion() {
        // Intentar modificar celda fija: fila 0, columna 0 (valor 5)
        assertThrows(MovimientoInvalidoException.class, () -> {
            sudoku.esMovimientoValido(0, 0, 1);
        });
    }

    @Test
    public void testEsMovimientoValidoFilaRepetidaLanzaExcepcion() {
        // En fila 0 ya hay un 5, intentar colocar otro 5 en (0,2)
        MovimientoInvalidoException ex = assertThrows(MovimientoInvalidoException.class, () -> {
            sudoku.esMovimientoValido(0, 2, 5);
        });
        assertTrue(ex.getMessage().contains("fila"));
    }

    @Test
    public void testEsMovimientoValidoColumnaRepetidaLanzaExcepcion() {
        MovimientoInvalidoException ex = assertThrows(MovimientoInvalidoException.class, () -> {
            sudoku.esMovimientoValido(1, 1, 3); // celda vacía y no fija
        });
        System.out.println("Mensaje excepción: '" + ex.getMessage() + "'");
        assertTrue(ex.getMessage().toLowerCase().contains("columna"));
    }


    @Test
    public void testEsMovimientoValidoBloqueRepetidoLanzaExcepcion() {
        MovimientoInvalidoException exception = assertThrows(MovimientoInvalidoException.class, () -> {
            sudoku.esMovimientoValido(0, 1, 3);
        });
        assertEquals("No puedes modificar una celda fija.", exception.getMessage());
    }

    @Test
    public void testEsMovimientoValidoFueraDeRangoLanzaExcepcion() {
        // fila fuera de rango (-1)
        EntradaFueraDeRangoException ex1 = assertThrows(EntradaFueraDeRangoException.class, () -> {
            sudoku.esMovimientoValido(-1, 0, 5);
        });
        assertTrue(ex1.getMessage().toLowerCase().contains("rango"));

        // columna fuera de rango (9)
        EntradaFueraDeRangoException ex2 = assertThrows(EntradaFueraDeRangoException.class, () -> {
            sudoku.esMovimientoValido(0, 9, 5);
        });
        assertTrue(ex2.getMessage().toLowerCase().contains("rango"));

        // valor fuera de rango (0)
        EntradaFueraDeRangoException ex3 = assertThrows(EntradaFueraDeRangoException.class, () -> {
            sudoku.esMovimientoValido(0, 0, 0);
        });
        assertTrue(ex3.getMessage().toLowerCase().contains("valor"));

        // valor fuera de rango (10)
        EntradaFueraDeRangoException ex4 = assertThrows(EntradaFueraDeRangoException.class, () -> {
            sudoku.esMovimientoValido(0, 0, 10);
        });
        assertTrue(ex4.getMessage().toLowerCase().contains("valor"));
    }

    @Test
    public void testColocarNumeroValido() throws Exception {
        sudoku.colocarNumero(0, 2, 1);
        assertEquals(1, sudoku.getTablero()[0][2]);
    }

    @Test
    public void testColocarNumeroEnCeldaFijaLanzaExcepcion() {
        assertThrows(MovimientoInvalidoException.class, () -> {
            sudoku.colocarNumero(0, 0, 1);
        });
    }

    @Test
    public void testEliminarNumeroValido() throws Exception {
        sudoku.eliminarNumero(0, 2); // está vacía, pero no fija, debería funcionar
        assertEquals(0, sudoku.getTablero()[0][2]);
    }

    @Test
    public void testEliminarNumeroEnCeldaFijaLanzaExcepcion() {
        assertThrows(MovimientoInvalidoException.class, () -> {
            sudoku.eliminarNumero(0, 0);
        });
    }

    @Test
    public void testEliminarNumeroFueraDeRangoLanzaExcepcion() {
        assertThrows(EntradaFueraDeRangoException.class, () -> {
            sudoku.eliminarNumero(9, 0);
        });
    }

    @Test
    public void testEstaResueltoDevuelveFalseConTableroIncompleto() {
        assertFalse(sudoku.estaResuelto());
    }

    @Test
    public void testEstaResueltoDevuelveTrueConTableroResuelto() throws Exception {
        int[][] tableroResuelto = {
                {5,3,4,6,7,8,9,1,2},
                {6,7,2,1,9,5,3,4,8},
                {1,9,8,3,4,2,5,6,7},
                {8,5,9,7,6,1,4,2,3},
                {4,2,6,8,5,3,7,9,1},
                {7,1,3,9,2,4,8,5,6},
                {9,6,1,5,3,7,2,8,4},
                {2,8,7,4,1,9,6,3,5},
                {3,4,5,2,8,6,1,7,9}
        };
        sudoku.cargarTablero(tableroResuelto);
        assertTrue(sudoku.estaResuelto());
    }
}
