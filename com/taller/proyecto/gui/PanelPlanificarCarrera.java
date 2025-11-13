package com.taller.proyecto.gui;

import com.taller.proyecto.logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelPlanificarCarrera extends JPanel {

    private GestorCompetencia gestor;
    private PanelCarreras panelCarrerasRef; 

    // Componentes de la UI
    private JTextField txtFecha;
    private JTextField txtHora;
    private JComboBox<Circuito> cmbCircuito;
    private JButton btnPlanificar;

    // ESTE ES EL CONSTRUCTOR NUEVO
    public PanelPlanificarCarrera(GestorCompetencia gestor, PanelCarreras panelCarrerasRef) {
        this.gestor = gestor;
        this.panelCarrerasRef = panelCarrerasRef; 
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        add(new JLabel("Formulario de Planificación de Carrera", SwingConstants.CENTER), BorderLayout.NORTH);

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar componentes
        txtFecha = new JTextField(10);
        txtHora = new JTextField(5);
        cmbCircuito = new JComboBox<>();

        // Poblar el ComboBox de Circuitos
        for (Circuito c : gestor.getCircuitos()) { 
            cmbCircuito.addItem(c);
        }
        cmbCircuito.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Circuito) {
                    setText(((Circuito) value).getNombre());
                }
                return this;
            }
        });

        // Añadir componentes al panel
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Fecha (Ej: DD/MM/YYYY):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelForm.add(txtFecha, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Hora (Ej: HH:MM):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelForm.add(txtHora, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Circuito:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panelForm.add(cmbCircuito, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Botón de Registrar
        btnPlanificar = new JButton("Planificar Carrera");
        add(btnPlanificar, BorderLayout.SOUTH);

        // Acción del botón
        btnPlanificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planificarCarrera();
            }
        });
    }

    // ESTE MÉTODO AHORA LLAMA AL panelCarrerasRef
    private void planificarCarrera() {
        try {
            String fecha = txtFecha.getText().trim();
            String hora = txtHora.getText().trim();
            Circuito circuito = (Circuito) cmbCircuito.getSelectedItem();

            if (fecha.isEmpty() || hora.isEmpty() || circuito == null) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            gestor.planificarCarrera(fecha, hora, circuito); 

            JOptionPane.showMessageDialog(this, "Carrera en " + circuito.getNombre() + " planificada exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            
            // 5. Avisar al panel de carreras que actualice sus listas
            if (panelCarrerasRef != null) {
                panelCarrerasRef.actualizarListasDeCarreras();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtFecha.setText("");
        txtHora.setText("");
        cmbCircuito.setSelectedIndex(0);
    }
}