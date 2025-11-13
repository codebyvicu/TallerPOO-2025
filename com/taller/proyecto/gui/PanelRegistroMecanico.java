package com.taller.proyecto.gui;

import com.taller.proyecto.logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistroMecanico extends JPanel {

    private GestorCompetencia gestor;

    // Componentes de la UI
    private JTextField txtDni, txtNombre, txtApellido, txtAñosExp;
    private JComboBox<Pais> cmbPais;
    private JComboBox<Especialidad> cmbEspecialidad; // Usamos el Enum
    private JButton btnRegistrar;

    public PanelRegistroMecanico(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        add(new JLabel("Formulario de Registro de Mecánico", SwingConstants.CENTER), BorderLayout.NORTH);

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar componentes
        txtDni = new JTextField(20);
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtAñosExp = new JTextField(5);
        cmbPais = new JComboBox<>();
        cmbEspecialidad = new JComboBox<>();

        // Poblar el ComboBox de Países (usa el getter del gestor)
        for (Pais p : gestor.getPaises()) {
            cmbPais.addItem(p);
        }
        // Renderer para mostrar el nombre del país
        cmbPais.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Pais) {
                    setText(((Pais) value).getDescripcion());
                }
                return this;
            }
        });

        // Poblar el ComboBox de Especialidad (usa los valores del Enum)
        for (Especialidad e : Especialidad.values()) {
            cmbEspecialidad.addItem(e);
        }


        // Añadir componentes al panel con GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelForm.add(txtDni, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelForm.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panelForm.add(txtApellido, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("País:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panelForm.add(cmbPais, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelForm.add(new JLabel("Especialidad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panelForm.add(cmbEspecialidad, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; panelForm.add(new JLabel("Años de Experiencia:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; panelForm.add(txtAñosExp, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Botón de Registrar
        btnRegistrar = new JButton("Registrar Mecánico");
        add(btnRegistrar, BorderLayout.SOUTH);

        // Acción del botón
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarMecanico();
            }
        });
    }

    private void registrarMecanico() {
        try {
            // 1. Obtener datos de la UI
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            Pais pais = (Pais) cmbPais.getSelectedItem();
            Especialidad especialidad = (Especialidad) cmbEspecialidad.getSelectedItem();

            // 2. Validar y parsear números
            int aniosExp = Integer.parseInt(txtAñosExp.getText().trim());

            // 3. Validar que los campos no estén vacíos
            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || pais == null || especialidad == null) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 4. Llamar a la lógica de negocio
            gestor.registrarMecanico(dni, nombre, apellido, pais, especialidad, aniosExp);

            // 5. Mostrar éxito y limpiar formulario
            JOptionPane.showMessageDialog(this, "Mecánico " + apellido + " registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: 'Años de Experiencia' debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (PersonaRepetidaException ex) { //
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtAñosExp.setText("");
        cmbPais.setSelectedIndex(0);
        cmbEspecialidad.setSelectedIndex(0);
    }
}