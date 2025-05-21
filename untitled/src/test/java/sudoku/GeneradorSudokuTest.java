package sudoku;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GeneradorSudokuTest {

    @Test
    public void testGenerarTableroAleatoriedad() {
        boolean distintos = false;
        for (int intento = 0; intento < 10; intento++) {
            GeneradorSudoku gen1 = new GeneradorSudoku();
            GeneradorSudoku gen2 = new GeneradorSudoku();
            int[][] t1 = gen1.generarTablero("facil");
            int[][] t2 = gen2.generarTablero("facil");
            if (!tablerosIguales(t1, t2)) {
                distintos = true;
                break;
            }
        }
        assertTrue(distintos, "Se esperaba que al menos en un intento los tableros fueran diferentes");
    }

    private boolean tablerosIguales(int[][] t1, int[][] t2) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (t1[i][j] != t2[i][j]) return false;
            }
        }
        return true;
    }
}
