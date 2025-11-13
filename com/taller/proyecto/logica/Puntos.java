package com.taller.proyecto.logica;

public enum Puntos {
    PRIMERO(25),
    SEGUNDO(18),
    TERCERO(15),
    CUARTO(12),
    QUINTO(10),
    SEXTO(8),
    SEPTIMO(6),
    OCTAVO(4),
    NOVENO(2),
    DECIMO(1);

    // Campo privado para guardar los puntos
    private final int puntos;
    // Constructor privado para asignar los puntos
    Puntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return this.puntos;
    }

    /**
     * Método estático auxiliar para obtener los puntos directamente desde un entero.
     * Esto reemplaza al método getPuntosPorPosicion(int pos) que estaba en GestorCompetencia.
     */
    public static int obtenerPuntos(int posicion) {
        switch (posicion) {
            case 1: return PRIMERO.getPuntos();
            case 2: return SEGUNDO.getPuntos();
            case 3: return TERCERO.getPuntos();
            case 4: return CUARTO.getPuntos();
            case 5: return QUINTO.getPuntos();
            case 6: return SEXTO.getPuntos();
            case 7: return SEPTIMO.getPuntos();
            case 8: return OCTAVO.getPuntos();
            case 9: return NOVENO.getPuntos();
            case 10: return DECIMO.getPuntos();
            default: return 0; // 0 puntos para cualquier otra posición
        }
    }
}