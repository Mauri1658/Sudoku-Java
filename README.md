# 🧩 Sudoku Game - Java Edition

Un clásico juego de Sudoku con generación aleatoria de tableros, tres niveles de dificultad y dos modos de juego: interfaz gráfica (Swing) y versión de consola.

# 🎯 Características

- 🎮 Dos modos de juego:

    ✨ Interfaz gráfica intuitiva (GUI)

    ⌨️ Versión para consola (ideal para puristas)

- 📊 Tres niveles de dificultad: Fácil, Medio, Difícil

- 🔢 Generación inteligente: Tableros únicos y resolubles

- 🚫 Validación en tiempo real: Detecta movimientos inválidos

- 🏆 Sistema de victoria: Te avisa cuando resuelves el puzzle

# 🕹️ Cómo jugar

## Versión GUI (Interfaz Gráfica)

1. Ejecuta SudokuGUI.java

2. Selecciona dificultad: Fácil, Medio o Difícil

3. Haz clic en una celda vacía

4. Ingresa un número del 1 al 9

5. Usa "Verificar Solución" para comprobar tu progreso

## Versión Consola

1. Ejecuta JuegoSudoku.java

2. Elige dificultad (1: Fácil, 2: Medio, 3: Difícil)

3. Ingresa coordenadas y valor (ej: 4 5 7 para fila 4, columna 5, valor 7)

5. Introduce 0 para salir

# 🛠️ Estructura del código

```plaintext
src/
├── sudoku/
│   ├── GeneradorSudoku.java    # Genera puzzles aleatorios
│   ├── JuegoSudoku.java        # Versión consola
│   ├── Sudoku.java             # Lógica principal
│   ├── SudokuGUI.java          # Interfaz gráfica
│   └── excepciones/            # Manejo de errores
│       ├── EntradaFueraDeRangoException.java
│       ├── MovimientoInvalidoException.java
│       └── SudokuException.java

```

# 💻 Tecnologías utilizadas

- ☕ Java SE (versión 8 o superior)

- 🖥️ Swing (para la interfaz gráfica)

- 🧠 Algoritmos recursivos (para generación y resolución)

# 📥 Instalación

1. Clona el repositorio:

```bash
git clone [https://github.com/Mauri1658/Sudoku-Java.git]
```

2. Compila y ejecuta

