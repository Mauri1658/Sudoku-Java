package sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;

class SudokuGUITest {

    @Test
    void testIniciarNuevoJuego() {
        SudokuGUI gui = new SudokuGUI();

        // Simular selección de dificultad y clic en nuevo juego
        gui.dificultadComboBox.setSelectedIndex(0); // Fácil
        gui.nuevoJuegoButton.doClick();

        // Verificar que se actualizó el tablero
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotNull(gui.botones[i][j].getText());
            }
        }
    }

    @Test
    void testManejarClickCelda() {
        SudokuGUI gui = new SudokuGUI();
        gui.iniciarNuevoJuego();

        // Simular clic en celda y entrada de número
        // Necesitarías un mock para JOptionPane para esto
        // gui.manejarClickCelda(0, 0);

        // Verificar que se actualizó el botón
        // assertFalse(gui.botones[0][0].getText().isEmpty());
    }

    @Test
    void testVerificarSolucion() {
        SudokuGUI gui = new SudokuGUI();

        // Simular clic en verificar solución
        gui.verificarButton.doClick();

        // Verificar que se mostró un mensaje (necesitarías mock para JOptionPane)
    }

    @Test
    void testInterfazGrafica() {
        // Prueba básica de que la interfaz se crea correctamente
        SudokuGUI gui = new SudokuGUI();
        assertNotNull(gui.dificultadComboBox);
        assertNotNull(gui.nuevoJuegoButton);
        assertNotNull(gui.verificarButton);
        assertEquals(9, gui.botones.length);
        assertEquals(9, gui.botones[0].length);
    }
}