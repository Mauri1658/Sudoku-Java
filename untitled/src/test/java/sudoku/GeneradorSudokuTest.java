package sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeneradorSudokuTest {
    private GeneradorSudoku generador = new GeneradorSudoku();

    @Test
    void testGenerarTablero() {
        Sudoku sudoku = new Sudoku();

        // Probar diferentes dificultades
        String[] dificultades = {"facil", "medio", "dificil", "invalida"};
        for (String dificultad : dificultades) {
            generador.generarTablero(sudoku, dificultad);
            assertNotNull(sudoku.getTablero());
            assertNotNull(sudoku.getCeldasFijas());

            // Verificar que hay celdas vacías
            boolean tieneVacias = false;
            for (int[] fila : sudoku.getTablero()) {
                for (int celda : fila) {
                    if (celda == 0) {
                        tieneVacias = true;
                        break;
                    }
                }
                if (tieneVacias) break;
            }
            assertTrue(tieneVacias);
        }
    }

    @Test
    void testEsNumeroValido() {
        int[][] tablero = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {0, 0, 0, 0, 6, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Número válido
        assertTrue(generador.esNumeroValido(tablero, 0, 2, 4));

        // Número inválido (repetido en fila)
        assertFalse(generador.esNumeroValido(tablero, 0, 2, 5));

        // Número inválido (repetido en columna)
        assertFalse(generador.esNumeroValido(tablero, 3, 0, 5));

        // Número inválido (repetido en subcuadrícula)
        assertFalse(generador.esNumeroValido(tablero, 0, 2, 8));
    }

    @Test
    void testEliminarCeldas() {
        Sudoku sudoku = new Sudoku();
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

        // Eliminar 10 celdas
        generador.eliminarCeldas(sudoku, 10);

        int celdasVacias = 0;
        for (int[] fila : sudoku.getTablero()) {
            for (int celda : fila) {
                if (celda == 0) celdasVacias++;
            }
        }

        assertEquals(10, celdasVacias);
    }
}