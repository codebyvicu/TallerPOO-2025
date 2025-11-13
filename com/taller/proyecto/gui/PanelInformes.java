package com.taller.proyecto.gui;

import com.taller.proyecto.logica.GestorCompetencia;
import javax.swing.*;
import java.awt.*;

public class PanelInformes extends JPanel {

    private GestorCompetencia gestor;
    private JTextArea txtAreaInformes;

    // Campos de clase (para que sean visibles en todos los métodos)
    private JTextField txtDni;
    private JTextField txtCircuito;
    private JButton btnHistPiloto;
    private JButton btnContarCircuito;
    private JButton btnPilotoEnCircuito; 
    
    // --- INICIO DE LO NUEVO ---
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JButton btnInformePorFecha;
    // --- FIN DE LO NUEVO ---


    public PanelInformes(GestorCompetencia gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Panel de Botones y Consultas
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BoxLayout(panelControles, BoxLayout.Y_AXIS));

        // --- Panel de Informes Generales ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Informes Generales"));
        
        JButton btnMecanicos = new JButton("Informe Mecánicos");
        JButton btnAutos = new JButton("Informe Autos");
        JButton btnHistGeneral = new JButton("Histórico Pilotos (Gral)");

        panelBotones.add(btnMecanicos);
        panelBotones.add(btnAutos);
        panelBotones.add(btnHistGeneral);
        
        panelControles.add(panelBotones);

        // --- Panel de Consultas Específicas ---
        JPanel panelConsultasContainer = new JPanel();
        panelConsultasContainer.setLayout(new BoxLayout(panelConsultasContainer, BoxLayout.Y_AXIS));
        panelConsultasContainer.setBorder(BorderFactory.createTitledBorder("Consultas Específicas"));

        // Fila 1: Consulta DNI
        JPanel panelConsultaDNI = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelConsultaDNI.add(new JLabel("DNI Piloto:"));
        txtDni = new JTextField(10); 
        panelConsultaDNI.add(txtDni);
        btnHistPiloto = new JButton("Histórico Piloto (DNI)"); 
        panelConsultaDNI.add(btnHistPiloto);
        
        panelConsultasContainer.add(panelConsultaDNI);

        // Fila 2: Consulta Circuito
        JPanel panelConsultaCircuito = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelConsultaCircuito.add(new JLabel("Circuito:"));
        txtCircuito = new JTextField(15); 
        panelConsultaCircuito.add(txtCircuito);
        btnContarCircuito = new JButton("Contar Carreras (Circuito)");
        panelConsultaCircuito.add(btnContarCircuito);
        btnPilotoEnCircuito = new JButton("Consultar Piloto en Circuito");
        panelConsultaCircuito.add(btnPilotoEnCircuito);

        panelConsultasContainer.add(panelConsultaCircuito);

        // --- INICIO DE LO NUEVO ---
        // Fila 3: Consulta por Fechas
        JPanel panelConsultaFechas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelConsultaFechas.add(new JLabel("Fecha Desde:"));
        txtFechaDesde = new JTextField("01/01/2025", 10); // Texto de ejemplo
        panelConsultaFechas.add(txtFechaDesde);
        
        panelConsultaFechas.add(new JLabel("Fecha Hasta:"));
        txtFechaHasta = new JTextField("31/12/2025", 10); // Texto de ejemplo
        panelConsultaFechas.add(txtFechaHasta);
        
        btnInformePorFecha = new JButton("Generar Informe por Fecha");
        panelConsultaFechas.add(btnInformePorFecha);
        
        panelConsultasContainer.add(panelConsultaFechas); // Añadimos la Fila 3
        // --- FIN DE LO NUEVO ---

        panelControles.add(panelConsultasContainer);
        add(panelControles, BorderLayout.NORTH);

        // 2. Área de Texto para mostrar los informes
        txtAreaInformes = new JTextArea();
        txtAreaInformes.setEditable(false);
        txtAreaInformes.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtAreaInformes);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Lógica de los Botones (Action Listeners)

        btnMecanicos.addActionListener(e -> {
            String informe = gestor.generarInformeMecanicosPorEscuderia();
            txtAreaInformes.setText(informe);
            txtAreaInformes.setCaretPosition(0);
        });

        btnAutos.addActionListener(e -> {
            String informe = gestor.generarInformeAutosPorEscuderia();
            txtAreaInformes.setText(informe);
            txtAreaInformes.setCaretPosition(0);
        });

        btnHistGeneral.addActionListener(e -> {
            String informe = gestor.mostrarHistoricoGeneralPilotos();
            txtAreaInformes.setText(informe);
            txtAreaInformes.setCaretPosition(0);
        });

        btnHistPiloto.addActionListener(e -> {
            String dni = txtDni.getText().trim();
            if (dni.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un DNI.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String informe = gestor.mostrarHistoricoPiloto(dni);
            txtAreaInformes.setText(informe);
            txtAreaInformes.setCaretPosition(0);
        });

        btnContarCircuito.addActionListener(e -> {
            String circuito = txtCircuito.getText().trim();
            if (circuito.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un nombre de circuito.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int cantidad = gestor.contarCarrerasPorCircuito(circuito);
            JOptionPane.showMessageDialog(this, 
                "Cantidad de carreras en '" + circuito + "': " + cantidad, 
                "Resultado Consulta", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        btnPilotoEnCircuito.addActionListener(e -> {
            String dni = txtDni.getText().trim();
            String circuito = txtCircuito.getText().trim();
            if (dni.isEmpty() || circuito.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, ingrese un DNI y un nombre de Circuito.", 
                    "Datos incompletos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            int cantidad = gestor.contarCarrerasPorPilotoYCircuito(dni, circuito);
            JOptionPane.showMessageDialog(this, 
                "El piloto (DNI: " + dni + ") corrió " + cantidad + " veces en el circuito '" + circuito + "'.", 
                "Resultado Consulta", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        // --- INICIO DE LO NUEVO ---
        // Acción para el nuevo botón de Fechas
        btnInformePorFecha.addActionListener(e -> {
            String fechaDesde = txtFechaDesde.getText().trim();
            String fechaHasta = txtFechaHasta.getText().trim();

            if (fechaDesde.isEmpty() || fechaHasta.isEmpty()) {
                 JOptionPane.showMessageDialog(this, 
                    "Por favor, ingrese ambas fechas (Desde y Hasta).", 
                    "Datos incompletos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Advertencia: La comparación de fechas como Strings (compareTo)
            // solo funciona si el formato es AAAA/MM/DD.
            // Para el formato DD/MM/YYYY, los resultados pueden ser inesperados.
            // (Se mantiene la lógica de tu GestorCompetencia)
            
            String informe = gestor.generarInformeResultadosPorFecha(fechaDesde, fechaHasta);
            txtAreaInformes.setText(informe);
            txtAreaInformes.setCaretPosition(0);
        });
        // --- FIN DE LO NUEVO ---
    }
}