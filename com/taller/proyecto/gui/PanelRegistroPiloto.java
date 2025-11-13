package com.taller.proyecto.gui;
import com.taller.proyecto.logica.GestorCompetencia;
import com.taller.proyecto.logica.Pais;
import com.taller.proyecto.logica.PersonaRepetidaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistroPiloto extends JPanel {

    private GestorCompetencia gestor;

    // Componentes de la UI
    private JTextField txtDni, txtNombre, txtApellido, txtNumComp, txtVictorias, txtPoles, txtVueltasRapidas, txtPodios;
    private JComboBox<Pais> cmbPais;
    private JButton btnRegistrar;

    public PanelRegistroPiloto(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        add(new JLabel("Formulario de Registro de Piloto", SwingConstants.CENTER), BorderLayout.NORTH);

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
        txtNumComp = new JTextField(5);
        txtVictorias = new JTextField(5);
        txtPoles = new JTextField(5);
        txtVueltasRapidas = new JTextField(5);
        txtPodios = new JTextField(5);
        cmbPais = new JComboBox<>();

        // Poblar el ComboBox de Países
        // Esto requiere el getter getPaises() en GestorCompetencia
        for (Pais p : gestor.getPaises()) {
            cmbPais.addItem(p);
        }
        // Para que el JComboBox muestre el nombre del país y no el objeto
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
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelForm.add(txtDni, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelForm.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panelForm.add(txtApellido, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("País:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panelForm.add(cmbPais, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelForm.add(new JLabel("N° Competencia:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panelForm.add(txtNumComp, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; panelForm.add(new JLabel("Victorias:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; panelForm.add(txtVictorias, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panelForm.add(new JLabel("Pole Positions:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; panelForm.add(txtPoles, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panelForm.add(new JLabel("Vueltas Rápidas:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; panelForm.add(txtVueltasRapidas, gbc);

        gbc.gridx = 0; gbc.gridy = 8; panelForm.add(new JLabel("Podios:"), gbc);
        gbc.gridx = 1; gbc.gridy = 8; panelForm.add(txtPodios, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Botón de Registrar
        btnRegistrar = new JButton("Registrar Piloto");
        add(btnRegistrar, BorderLayout.SOUTH);

        // Acción del botón
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPiloto();
            }
        });
    }

    private void registrarPiloto() {
        try {
            // 1. Obtener datos de la UI
            String dni = txtDni.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            Pais pais = (Pais) cmbPais.getSelectedItem();

            // 2. Validar y parsear números
            int numComp = Integer.parseInt(txtNumComp.getText());
            int victorias = Integer.parseInt(txtVictorias.getText());
            int poles = Integer.parseInt(txtPoles.getText());
            int vueltasRapidas = Integer.parseInt(txtVueltasRapidas.getText());
            int podios = Integer.parseInt(txtPodios.getText());

            // 3. Validar que los campos no estén vacíos
            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || pais == null) {
                JOptionPane.showMessageDialog(this, "DNI, Nombre, Apellido y País no pueden estar vacíos.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 4. Llamar a la lógica de negocio
            gestor.registrarPiloto(dni, nombre, apellido, numComp, victorias, poles, vueltasRapidas, podios, pais);

            // 5. Mostrar éxito y limpiar formulario
            JOptionPane.showMessageDialog(this, "Piloto " + apellido + " registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (NumberFormatException ex) {
            // Error si los números están mal escritos
            JOptionPane.showMessageDialog(this, "Error: Los campos numéricos (victorias, podios, etc.) deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (PersonaRepetidaException ex) {
            // Excepción personalizada de tu lógica
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Cualquier otro error
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtNumComp.setText("0");
        txtVictorias.setText("0");
        txtPoles.setText("0");
        txtVueltasRapidas.setText("0");
        txtPodios.setText("0");
        cmbPais.setSelectedIndex(0);
    }
}
