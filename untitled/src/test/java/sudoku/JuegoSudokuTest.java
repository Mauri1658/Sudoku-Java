package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.excepciones.MovimientoInvalidoException;
import sudoku.excepciones.EntradaFueraDeRangoException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class JuegoSudokuTest {
    private JuegoSudoku juego;
    private Sudoku sudokuMock;

    @BeforeEach
    void setUp() {
        sudokuMock = new Sudoku() {
            @Override
            public boolean estaResuelto() {
                return false;
            }

            @Override
            public void mostrarTablero() {
                // No hacer nada para pruebas
            }
        };
        juego = new JuegoSudoku();
    }

    @Test
    void testIniciarDificultad() {
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        juego.iniciar();

        // Verificar que se llamó a generarTablero con "facil"
        // (Necesitarías un mock para verificar esto completamente)
    }

    @Test
    void testJugarMovimientoValido() throws MovimientoInvalidoException, EntradaFueraDeRangoException {
        String input = "1 1 5\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Usar un Sudoku mock que acepte el movimiento
        Sudoku sudokuMock = new Sudoku() {
            @Override
            public void colocarNumero(int fila, int columna, int valor) {
                // No lanzar excepción
            }
        };
        juego = new JuegoSudoku();

        // Necesitarías una forma de inyectar el mock en JuegoSudoku
        // juego.setSudoku(sudokuMock);

        assertDoesNotThrow(() -> juego.jugar());
    }

    @Test
    void testJugarMovimientoInvalido() throws MovimientoInvalidoException, EntradaFueraDeRangoException {
        String input = "1 1 5\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Usar un Sudoku mock que rechace el movimiento
        Sudoku sudokuMock = new Sudoku() {
            @Override
            public void colocarNumero(int fila, int columna, int valor) throws MovimientoInvalidoException {
                throw new MovimientoInvalidoException("Movimiento inválido");
            }
        };
        juego = new JuegoSudoku();

        // Necesitarías una forma de inyectar el mock en JuegoSudoku
        // juego.setSudoku(sudokuMock);

        assertDoesNotThrow(() -> juego.jugar());
    }

    @Test
    void testJugarEntradaInvalida() {
        String input = "a b c\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertDoesNotThrow(() -> juego.jugar());
    }

    @Test
    void testJugarSalir() {
        String input = "0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertDoesNotThrow(() -> juego.jugar());
    }
}