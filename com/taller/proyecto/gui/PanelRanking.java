package com.taller.proyecto.gui;

import com.taller.proyecto.logica.GestorCompetencia;
import com.taller.proyecto.logica.Piloto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelRanking extends JPanel {

    private GestorCompetencia gestor;
    private JTextArea txtAreaRanking;
    private JButton btnActualizar;

    public PanelRanking(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(10, 10)); // Layout principal
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen

        // 1. Título
        add(new JLabel("Ranking de Pilotos (ordenado por Puntos Acumulados)", SwingConstants.CENTER), BorderLayout.NORTH);

        // 2. Área de Texto para mostrar el ranking
        txtAreaRanking = new JTextArea();
        txtAreaRanking.setEditable(false);
        txtAreaRanking.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtAreaRanking); // Para poder scrollear
        add(scrollPane, BorderLayout.CENTER);

        // 3. Botón de Actualizar
        btnActualizar = new JButton("Actualizar Ranking");
        add(btnActualizar, BorderLayout.SOUTH);

        // 4. Acción del Botón
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarRanking();
            }
        });

        // 5. Carga inicial
        actualizarRanking();
    }

    private void actualizarRanking() {
        // Llama al método de la lógica
        List<Piloto> ranking = gestor.generarRankingPilotos();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-4s %-20s %-20s %-10s %-10s %-10s\n", 
            "Pos", "Apellido", "Nombre", "Puntos", "Victorias", "Podios"));
        sb.append("--------------------------------------------------------------------------------\n");

        int pos = 1;
        for (Piloto p : ranking) {
            sb.append(String.format("%-4d %-20s %-20s %-10d %-10d %-10d\n",
                    pos++,
                    p.getApellido(),
                    p.getNombre(),
                    p.getPuntosAcumulados(),
                    p.getVictorias(),
                    p.getPodios()
            ));
        }

        txtAreaRanking.setText(sb.toString());
        txtAreaRanking.setCaretPosition(0); // Volver al inicio del texto
    }
}