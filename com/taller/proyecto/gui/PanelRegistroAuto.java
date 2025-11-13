package com.taller.proyecto.gui;

import com.taller.proyecto.logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistroAuto extends JPanel {

    private GestorCompetencia gestor;

    // Componentes de la UI
    private JTextField txtModelo;
    private JTextField txtMotor;
    private JButton btnRegistrar;

    public PanelRegistroAuto(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        add(new JLabel("Formulario de Registro de Auto", SwingConstants.CENTER), BorderLayout.NORTH);

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar componentes
        txtModelo = new JTextField(20);
        txtMotor = new JTextField(20);

        // Añadir componentes al panel con GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelForm.add(txtModelo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Motor:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelForm.add(txtMotor, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Botón de Registrar
        btnRegistrar = new JButton("Registrar Auto");
        add(btnRegistrar, BorderLayout.SOUTH);

        // Acción del botón
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarAuto();
            }
        });
    }

    private void registrarAuto() {
        try {
            // 1. Obtener datos de la UI
            String modelo = txtModelo.getText().trim();
            String motor = txtMotor.getText().trim();

            // 2. Validar que los campos no estén vacíos
            if (modelo.isEmpty() || motor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Modelo y Motor son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 4. Llamar a la lógica de negocio
            // Nota: Aunque la firma del método incluye 'throws AutoRepetidoException',
            // la lógica interna que la lanzaba fue comentada para permitir la carga del CSV.
            // Mantenemos el try-catch por si esa lógica se restaura en el futuro.
            gestor.registrarAuto(modelo, motor);

            // 5. Mostrar éxito y limpiar formulario
            JOptionPane.showMessageDialog(this, "Auto " + modelo + " registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (AutoRepetidoException ex) {
            // Esta excepción (según el código actual) no debería saltar, pero la manejamos.
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtModelo.setText("");
        txtMotor.setText("");
    }
}