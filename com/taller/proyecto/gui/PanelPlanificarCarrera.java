package com.taller.proyecto.gui;

import com.taller.proyecto.logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelPlanificarCarrera extends JPanel {

    private GestorCompetencia gestor;

    // Componentes de la UI
    private JTextField txtFecha;
    private JTextField txtHora;
    private JComboBox<Circuito> cmbCircuito;
    private JButton btnPlanificar;

    public PanelPlanificarCarrera(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        add(new JLabel("Formulario de Planificación de Carrera", SwingConstants.CENTER), BorderLayout.NORTH);

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar componentes
        txtFecha = new JTextField(10);
        txtHora = new JTextField(5);
        cmbCircuito = new JComboBox<>();

        // Poblar el ComboBox de Circuitos
        // Usamos los circuitos que ya están registrados en el gestor
        for (Circuito c : gestor.getCircuitos()) { //
            cmbCircuito.addItem(c);
        }
        // Renderer para mostrar el nombre del circuito
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


        // Añadir componentes al panel con GridBagLayout
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

    private void planificarCarrera() {
        try {
            // 1. Obtener datos de la UI
            String fecha = txtFecha.getText().trim();
            String hora = txtHora.getText().trim();
            Circuito circuito = (Circuito) cmbCircuito.getSelectedItem();

            // 2. Validar que los campos no estén vacíos
            if (fecha.isEmpty() || hora.isEmpty() || circuito == null) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Llamar a la lógica de negocio
            gestor.planificarCarrera(fecha, hora, circuito);

            // 4. Mostrar éxito y limpiar formulario
            JOptionPane.showMessageDialog(this, "Carrera en " + circuito.getNombre() + " planificada exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            
            // NOTA: Para que esta nueva carrera aparezca en los desplegables de "Gestión de Carreras",
            // la aplicación tendría que ser reiniciada, o tendríamos que implementar
            // un sistema más complejo para actualizar los ComboBox de los otros paneles.

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
