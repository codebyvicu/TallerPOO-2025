package com.taller.proyecto.gui;

import com.taller.proyecto.logica.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelVisorDatos extends JPanel {

    private GestorCompetencia gestor;

    public PanelVisorDatos(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout());

        JTabbedPane tabbedPaneInterno = new JTabbedPane();

        // Añadimos las sub-pestañas
        tabbedPaneInterno.addTab("Países", crearPanelVisorPaises());
        tabbedPaneInterno.addTab("Pilotos (DNI)", crearPanelVisorPilotos());
        
        // --- CORRECCIÓN DE TIPEADO AQUÍ ---
        tabbedPaneInterno.addTab("Mecánicos (DNI)", crearPanelVisorMecanicos()); // Sin 'ç'
        // --- FIN CORRECCIÓN ---
        
        tabbedPaneInterno.addTab("Escuderías (ID)", crearPanelVisorEscuderias());
        tabbedPaneInterno.addTab("Autos", crearPanelVisorAutos());

        add(tabbedPaneInterno, BorderLayout.CENTER);
    }

    // --- Panel para ver PAÍSES ---
    private JComponent crearPanelVisorPaises() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %-30s\n", "ID País", "Descripción"));
        sb.append("--------------------------------------------\n");
        
        List<Pais> paises = gestor.getPaises(); //
        for (Pais p : paises) {
            sb.append(String.format("%-10d | %-30s\n", p.getIdPais(), p.getDescripcion()));
        }
        
        textArea.setText(sb.toString());
        textArea.setCaretPosition(0); 
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // --- Panel para ver PILOTOS (con DNI) ---
    private JComponent crearPanelVisorPilotos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-12s | %-20s | %-20s\n", "DNI", "Nombre", "Apellido"));
        sb.append("------------------------------------------------------\n");
        
        List<Piloto> pilotos = gestor.getPilotos(); //
        for (Piloto p : pilotos) {
            sb.append(String.format("%-12s | %-20s | %-20s\n", p.getDni(), p.getNombre(), p.getApellido()));
        }
        
        textArea.setText(sb.toString());
        textArea.setCaretPosition(0); 
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // --- INICIO CORRECCIÓN DE TIPEADO ---

    // --- Panel para ver MECÁNICOS (con DNI) ---
    private JComponent crearPanelVisorMecanicos() { // Sin 'ç'
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-12s | %-20s | %-20s | %-12s | %-5s\n", "DNI", "Nombre", "Apellido", "Especialidad", "Años Exp."));
        sb.append("----------------------------------------------------------------------------------\n");
        
        List<Mecanico> mecanicos = gestor.getMecanicos(); // Sin 'ç'
        for (Mecanico m : mecanicos) {
            sb.append(String.format("%-12s | %-20s | %-20s | %-12s | %-5d\n", 
                m.getDni(), m.getNombre(), m.getApellido(), m.getEspecialidad().toString(), m.añosExperiencia()));
        }
        
        textArea.setText(sb.toString());
        textArea.setCaretPosition(0); 
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    // --- FIN CORRECCIÓN DE TIPEADO ---

    // --- Panel para ver ESCUDERÍAS (con ID) ---
    private JComponent crearPanelVisorEscuderias() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %-20s | %-20s\n", "ID Escud.", "Nombre", "País"));
        sb.append("------------------------------------------------------\n");
        
        List<Escuderia> escuderias = gestor.getEscuderias(); //
        for (Escuderia e : escuderias) {
            String pais = (e.getPais() != null) ? e.getPais().getDescripcion() : "N/A";
            sb.append(String.format("%-10d | %-20s | %-20s\n", e.getIdEscuderia(), e.getNombre(), pais));
        }
        
        textArea.setText(sb.toString());
        textArea.setCaretPosition(0); 
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    // --- Panel para ver AUTOS ---
    private JComponent crearPanelVisorAutos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s | %-15s | %-20s\n", "Modelo", "Motor", "Escudería"));
        sb.append("----------------------------------------------------------\n");
        
        List<Escuderia> escuderias = gestor.getEscuderias(); //
        for (Escuderia e : escuderias) {
            List<Auto> autos = e.getAuto(); //
            
            if (autos != null) {
                for (Auto a : autos) {
                    sb.append(String.format("%-15s | %-15s | %-20s\n", 
                        a.getModelo(), //
                        a.getMotor(),  //
                        e.getNombre()));//
                }
            }
        }
        
        textArea.setText(sb.toString());
        textArea.setCaretPosition(0); 
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}