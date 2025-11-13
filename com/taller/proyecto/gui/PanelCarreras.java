package com.taller.proyecto.gui;

import com.taller.proyecto.logica.*;

import javax.swing.*;
import java.awt.*;

public class PanelCarreras extends JPanel {

    private GestorCompetencia gestor;

    // Componentes para la pestaña 1: Inscribir
    private JComboBox<Piloto> cmbPilotoInsc;
    private JComboBox<Carrera> cmbCarreraInsc;
    private JTextField txtModeloAuto;
    private JButton btnInscribir;

    // Componentes para la pestaña 2: Cargar Resultados
    private JComboBox<Piloto> cmbPilotoRes;
    private JComboBox<Carrera> cmbCarreraRes;
    private JTextField txtPosicion;
    private JCheckBox chkVueltaRapida;
    private JButton btnCargarResultado;

    public PanelCarreras(GestorCompetencia gestor) {
        this.gestor = gestor;
        // Layout principal de este panel
        setLayout(new BorderLayout());

        // 1. Crear el sistema de pestañas interno
        JTabbedPane tabbedPaneInterno = new JTabbedPane();

        // 2. Crear Pestaña 1: "Inscribir Piloto"
        tabbedPaneInterno.addTab("Inscribir Piloto a Carrera", crearPanelInscripcion());

        // 3. Crear Pestaña 2: "Cargar Resultados"
        tabbedPaneInterno.addTab("Cargar Resultados de Carrera", crearPanelResultados());

        // 4. Añadir el panel de pestañas interno al panel principal
        add(tabbedPaneInterno, BorderLayout.CENTER);
    }

    // --- Método para crear el panel de Inscripción ---
    private JPanel crearPanelInscripcion() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar componentes
        cmbPilotoInsc = new JComboBox<>();
        cmbCarreraInsc = new JComboBox<>();
        txtModeloAuto = new JTextField(20);
        btnInscribir = new JButton("Inscribir Piloto");

        // Poblar ComboBoxes
        poblarComboBoxPilotos(cmbPilotoInsc);
        poblarComboBoxCarreras(cmbCarreraInsc);

        // Añadir componentes al panel
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Seleccionar Piloto:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(cmbPilotoInsc, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Seleccionar Carrera:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(cmbCarreraInsc, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Modelo del Auto (Ej: A524):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(txtModeloAuto, gbc);

        gbc.gridx = 1; gbc.gridy = 3; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnInscribir, gbc);

        // Acción del botón
        btnInscribir.addActionListener(e -> inscribirPiloto());

        return panel;
    }

    // --- Método para crear el panel de Carga de Resultados ---
    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar componentes
        cmbPilotoRes = new JComboBox<>();
        cmbCarreraRes = new JComboBox<>();
        txtPosicion = new JTextField(5);
        chkVueltaRapida = new JCheckBox("¿Logró la vuelta rápida?");
        btnCargarResultado = new JButton("Cargar Resultado");

        // Poblar ComboBoxes (reutiliza los mismos datos)
        poblarComboBoxPilotos(cmbPilotoRes);
        poblarComboBoxCarreras(cmbCarreraRes);

        // Añadir componentes al panel
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Seleccionar Piloto:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(cmbPilotoRes, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Seleccionar Carrera:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(cmbCarreraRes, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Posición Final (1, 2, 3...):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(txtPosicion, gbc);

        gbc.gridx = 1; gbc.gridy = 3; panel.add(chkVueltaRapida, gbc);

        gbc.gridx = 1; gbc.gridy = 4; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnCargarResultado, gbc);
        
        // Acción del botón
        btnCargarResultado.addActionListener(e -> cargarResultado());

        return panel;
    }

    // --- MÉTODOS DE LÓGICA DE BOTONES ---

    private void inscribirPiloto() {
        try {
            Piloto piloto = (Piloto) cmbPilotoInsc.getSelectedItem();
            Carrera carrera = (Carrera) cmbCarreraInsc.getSelectedItem();
            String modelo = txtModeloAuto.getText().trim();

            if (piloto == null || carrera == null || modelo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un piloto, una carrera y un modelo de auto.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Llamamos a la lógica que ya arreglamos
            gestor.registrarParticipacion(piloto, carrera, modelo); //

            JOptionPane.showMessageDialog(this, "¡Piloto " + piloto.getApellido() + " inscrito en " + carrera.getCircuito().getNombre() + "!", "Inscripción Exitosa", JOptionPane.INFORMATION_MESSAGE);
            txtModeloAuto.setText("");

        } catch (ParticipacionDuplicadaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error: Duplicado", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error: Auto no válido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarResultado() {
        try {
            Piloto piloto = (Piloto) cmbPilotoRes.getSelectedItem();
            Carrera carrera = (Carrera) cmbCarreraRes.getSelectedItem();
            int posicion = Integer.parseInt(txtPosicion.getText().trim());
            boolean vueltaRapida = chkVueltaRapida.isSelected();

            if (piloto == null || carrera == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un piloto y una carrera.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Llamamos a la lógica del gestor
            gestor.registrarResultadoCarrera(carrera, piloto, posicion, vueltaRapida);

            JOptionPane.showMessageDialog(this, "Resultado cargado para " + piloto.getApellido() + ".\n¡El ranking de pilotos ha sido actualizado!", "Resultado Cargado", JOptionPane.INFORMATION_MESSAGE);
            txtPosicion.setText("");
            chkVueltaRapida.setSelected(false);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La posición debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // --- MÉTODOS DE UTILIDAD (POBLAR COMBOBOXES) ---

    private void poblarComboBoxPilotos(JComboBox<Piloto> comboBox) {
        for (Piloto p : gestor.getPilotos()) {
            comboBox.addItem(p);
        }
        // Usamos un Renderer para que se vea "Apellido, Nombre"
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Piloto) {
                    Piloto p = (Piloto) value;
                    setText(p.getApellido() + ", " + p.getNombre());
                }
                return this;
            }
        });
    }

    private void poblarComboBoxCarreras(JComboBox<Carrera> comboBox) {
        for (Carrera c : gestor.getCarreras()) {
            comboBox.addItem(c);
        }
        // Usamos un Renderer para que se vea "Fecha - Circuito"
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Carrera) {
                    Carrera c = (Carrera) value;
                    // Asumimos que circuito no es null
                    setText(c.getFechaRealizacion() + " - " + c.getCircuito().getNombre());
                }
                return this;
            }
        });
    }
}
