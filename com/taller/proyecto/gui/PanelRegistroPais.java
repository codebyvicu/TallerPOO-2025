package com.taller.proyecto.gui;

import com.taller.proyecto.logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistroPais extends JPanel {

    private GestorCompetencia gestor;

    // Componentes de la UI
    private JTextField txtIdPais;
    private JTextField txtDescripcion;
    private JButton btnRegistrar;

    public PanelRegistroPais(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        add(new JLabel("Formulario de Registro de País", SwingConstants.CENTER), BorderLayout.NORTH);

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar componentes
        txtIdPais = new JTextField(5);
        txtDescripcion = new JTextField(20);

        // Añadir componentes al panel con GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID País (Número):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelForm.add(txtIdPais, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Descripción (Nombre):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelForm.add(txtDescripcion, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Botón de Registrar
        btnRegistrar = new JButton("Registrar País");
        add(btnRegistrar, BorderLayout.SOUTH);

        // Acción del botón
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPais();
            }
        });
    }

    private void registrarPais() {
        try {
            // 1. Obtener datos de la UI
            String descripcion = txtDescripcion.getText().trim();
            int id = Integer.parseInt(txtIdPais.getText().trim());

            // 2. Validar que los campos no estén vacíos
            if (descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID y Descripción son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Llamar a la lógica de negocio
            gestor.registrarPais(id, descripcion);

            // 4. Mostrar éxito y limpiar formulario
            JOptionPane.showMessageDialog(this, "País '" + descripcion + "' registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            
            // EXTRA: Actualizar los ComboBox de los otros paneles
            // (Esto es más complejo y requiere una referencia cruzada, pero por ahora funciona)

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: 'ID País' debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (PaisRepetidoException ex) { //
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtIdPais.setText("");
        txtDescripcion.setText("");
    }
}
