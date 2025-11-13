package com.taller.proyecto.gui;

import com.taller.proyecto.logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAsociarPiloto extends JPanel {

    private GestorCompetencia gestor;

    // Componentes de la UI
    private JComboBox<Piloto> cmbPiloto;
    private JComboBox<Escuderia> cmbEscuderia;
    private JTextField txtDesdeFecha;
    private JTextField txtHastaFecha;
    private JButton btnAsociar;

    public PanelAsociarPiloto(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        add(new JLabel("Asociar Piloto a Escudería (con validación de fechas)", SwingConstants.CENTER), BorderLayout.NORTH);

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar componentes
        cmbPiloto = new JComboBox<>();
        cmbEscuderia = new JComboBox<>();
        txtDesdeFecha = new JTextField("01/01/2025", 10);
        txtHastaFecha = new JTextField(10);
        
        // --- Poblar ComboBox de Pilotos ---
        for (Piloto p : gestor.getPilotos()) { //
            cmbPiloto.addItem(p);
        }
        cmbPiloto.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Piloto) {
                    setText(((Piloto) value).getApellido() + ", " + ((Piloto) value).getNombre());
                }
                return this;
            }
        });

        // --- Poblar ComboBox de Escuderías ---
        for (Escuderia e : gestor.getEscuderias()) { //
            cmbEscuderia.addItem(e);
        }
        cmbEscuderia.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Escuderia) {
                    setText(((Escuderia) value).getNombre());
                }
                return this;
            }
        });


        // Añadir componentes al panel con GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Piloto:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelForm.add(cmbPiloto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Escudería:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelForm.add(cmbEscuderia, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Desde Fecha (Ej: 01/01/2025):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panelForm.add(txtDesdeFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Hasta Fecha (Dejar vacío si sigue):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panelForm.add(txtHastaFecha, gbc);


        add(panelForm, BorderLayout.CENTER);

        // Botón de Registrar
        btnAsociar = new JButton("Asociar Piloto");
        add(btnAsociar, BorderLayout.SOUTH);

        // Acción del botón
        btnAsociar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asociarPiloto();
            }
        });
    }

    private void asociarPiloto() {
        try {
            // 1. Obtener datos de la UI
            Piloto piloto = (Piloto) cmbPiloto.getSelectedItem();
            Escuderia escuderia = (Escuderia) cmbEscuderia.getSelectedItem();
            String desdeFecha = txtDesdeFecha.getText().trim();
            String hastaFecha = txtHastaFecha.getText().trim();

            // 2. Validar que los campos no estén vacíos
            if (piloto == null || escuderia == null || desdeFecha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Piloto, Escudería y Fecha Desde son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Llamar a la lógica de negocio
            // Le pasamos el DNI del piloto y el NOMBRE de la escudería, como pide el método
            gestor.asociarPilotoAEscuderia(piloto.getDni(), escuderia.getNombre(), desdeFecha, hastaFecha);

            // 4. Mostrar éxito y limpiar formulario
            JOptionPane.showMessageDialog(this, "Asociación exitosa: " + piloto.getApellido() + " -> " + escuderia.getNombre(), "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (IllegalArgumentException ex) { //
            // Esta es la excepción que lanza tu método por superposición de fechas o si no encuentra algo
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtDesdeFecha.setText("01/01/2025");
        txtHastaFecha.setText("");
        cmbPiloto.setSelectedIndex(0);
        cmbEscuderia.setSelectedIndex(0);
    }
}
