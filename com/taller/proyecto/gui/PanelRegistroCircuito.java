package com.taller.proyecto.gui;

import com.taller.proyecto.logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistroCircuito extends JPanel {

    private GestorCompetencia gestor;

    // Componentes de la UI
    private JTextField txtIdCircuito;
    private JTextField txtNombre;
    private JTextField txtLongitud;
    private JComboBox<Pais> cmbPais;
    private JButton btnRegistrar;

    public PanelRegistroCircuito(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        add(new JLabel("Formulario de Registro de Circuito", SwingConstants.CENTER), BorderLayout.NORTH);

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar componentes
        txtIdCircuito = new JTextField(5);
        txtNombre = new JTextField(20);
        txtLongitud = new JTextField(10);
        cmbPais = new JComboBox<>();

        // Poblar el ComboBox de Países
        for (Pais p : gestor.getPaises()) { //
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


        // Añadir componentes al panel con GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID Circuito (Número):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelForm.add(txtIdCircuito, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelForm.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Longitud (en metros):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panelForm.add(txtLongitud, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("País:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panelForm.add(cmbPais, gbc);


        add(panelForm, BorderLayout.CENTER);

        // Botón de Registrar
        btnRegistrar = new JButton("Registrar Circuito");
        add(btnRegistrar, BorderLayout.SOUTH);

        // Acción del botón
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCircuito();
            }
        });
    }

    private void registrarCircuito() {
        try {
            // 1. Obtener datos de la UI
            String nombre = txtNombre.getText().trim();
            Pais pais = (Pais) cmbPais.getSelectedItem();
            int id = Integer.parseInt(txtIdCircuito.getText().trim());
            int longitud = Integer.parseInt(txtLongitud.getText().trim());

            // 2. Validar que los campos no estén vacíos
            if (nombre.isEmpty() || pais == null) {
                JOptionPane.showMessageDialog(this, "ID, Nombre, Longitud y País son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Llamar a la lógica de negocio
            // Pasamos 'null' para el parámetro 'carrera', ya que no se asigna al crearlo
            gestor.registrarCircuito(id, nombre, longitud, pais, null);

            // 4. Mostrar éxito y limpiar formulario
            JOptionPane.showMessageDialog(this, "Circuito " + nombre + " registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: 'ID Circuito' y 'Longitud' deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (CircuitoRepetidoException ex) { //
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtIdCircuito.setText("");
        txtNombre.setText("");
        txtLongitud.setText("");
        cmbPais.setSelectedIndex(0);
    }
}
