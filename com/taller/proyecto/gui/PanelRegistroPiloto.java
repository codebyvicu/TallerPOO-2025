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
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            Pais pais = (Pais) cmbPais.getSelectedItem();
            
            // Obtenemos los números como texto primero
            String numCompStr = txtNumComp.getText().trim();
            String victoriasStr = txtVictorias.getText().trim();
            String polesStr = txtPoles.getText().trim();
            String vueltasRapidasStr = txtVueltasRapidas.getText().trim();
            String podiosStr = txtPodios.getText().trim();

            // 2. Validar que los campos no estén vacíos
            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || pais == null ||
                numCompStr.isEmpty() || victoriasStr.isEmpty() || polesStr.isEmpty() ||
                vueltasRapidasStr.isEmpty() || podiosStr.isEmpty()) {
                
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // --- INICIO DE VALIDACIONES DE FORMATO ---

            // 3. Validar DNI (SOLO NÚMEROS Y PUNTOS)
            if (!esDNIValido(dni)) {
                JOptionPane.showMessageDialog(this, "Error: El DNI debe contener solo números y puntos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. Validar Nombre y Apellido (SOLO LETRAS)
            if (!esSoloTexto(nombre) || !esSoloTexto(apellido)) {
                JOptionPane.showMessageDialog(this, "Error: Nombre y Apellido deben contener solo letras.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 5. Validar campos numéricos
            int numComp, victorias, poles, vueltasRapidas, podios;
            try {
                numComp = Integer.parseInt(numCompStr);
                victorias = Integer.parseInt(victoriasStr);
                poles = Integer.parseInt(polesStr);
                vueltasRapidas = Integer.parseInt(vueltasRapidasStr);
                podios = Integer.parseInt(podiosStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Los campos numéricos (N° Comp., Victorias, etc.) deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // --- FIN DE VALIDACIONES DE FORMATO ---

            // 6. Llamar a la lógica de negocio (Sabiendo que todos los datos son válidos)
            gestor.registrarPiloto(dni, nombre, apellido, numComp, victorias, poles, vueltasRapidas, podios, pais); //

            // 7. Mostrar éxito y limpiar formulario
            JOptionPane.showMessageDialog(this, "Piloto " + apellido + " registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (PersonaRepetidaException ex) {
            // Esto salta si el DNI ya existe en un Piloto o Mecánico
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

    /**
     * Método auxiliar para validar que un DNI contenga solo números y puntos.
     * @param dni El string a validar.
     * @return true si es válido, false si contiene letras u otros símbolos.
     */
    private boolean esDNIValido(String dni) {
        // Esta regex valida que solo haya números (0-9) y el caracter punto (.).
        return dni.matches("^[0-9.]+$");
    }

    /**
     * Método auxiliar para validar que un string contenga solo letras,
     * espacios y caracteres acentuados comunes.
     * @param texto El string a validar.
     * @return true si solo contiene letras, false si contiene números o símbolos.
     */
    private boolean esSoloTexto(String texto) {
        // Esta es una expresión regular (regex) que valida letras
        // (incluyendo áéíóúñ) y espacios.
        return texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

} 