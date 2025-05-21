package sudoku;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sudoku.excepciones.SudokuException;

public class JuegoSudokuTest {

    private JuegoSudoku juego;
    private Sudoku sudoku;

    @BeforeEach
    public void setUp() {
        juego = new JuegoSudoku();
        sudoku = juego.getSudoku();  // Asegúrate que JuegoSudoku tiene este getter
    }

    @Test
    public void testIniciarJuegoConTableroGenerado() {
        int[][] tablero = juego.getGenerador().generarTablero("facil");
        sudoku.cargarTablero(tablero);

        assertNotNull(sudoku.getTablero());
        assertFalse(sudoku.estaResuelto());
    }

    @Test
    public void testColocarNumeroValido() throws Exception {
        int[][] tablero = juego.getGenerador().generarTablero("facil");
        sudoku.cargarTablero(tablero);

        boolean colocado = false;

        outer:
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!sudoku.getCeldasFijas()[i][j] && sudoku.getTablero()[i][j] == 0) {
                    for (int val = 1; val <= 9; val++) {
                        try {
                            if (sudoku.esMovimientoValido(i, j, val)) {
                                sudoku.colocarNumero(i, j, val);
                                assertEquals(val, sudoku.getTablero()[i][j]);
                                colocado = true;
                                break outer;  // Salimos de todos los bucles tras colocar el primer válido
                            }
                        } catch (Exception e) {
                            // Ignorar excepciones y probar siguiente valor
                        }
                    }
                }
            }
        }
        assertTrue(colocado, "Debe haberse colocado un número válido en el tablero");
    }

    @Test
    public void testEstaResueltoDetectaFinal() throws Exception {
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
