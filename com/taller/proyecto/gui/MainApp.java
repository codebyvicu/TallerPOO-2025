package com.taller.proyecto.gui;
import com.taller.proyecto.logica.*;
import com.taller.proyecto.persistencia.PasoDeArchivos;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class MainApp extends JFrame {

    private GestorCompetencia gestor;

    public MainApp() {
        // 1. Crear la instancia del gestor
        this.gestor = new GestorCompetencia();

        // 2. Cargar todos los datos de los CSV en el gestor
        // Este método es fundamental para que la app tenga datos
        try {
            cargarDatosIniciales(gestor);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error crítico al cargar archivos CSV: " + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Si no se pueden cargar los datos, cerramos la app
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado durante la carga: " + e.getMessage(),
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }


        // 3. Configurar la ventana principal (JFrame)
        setTitle("Gestor de Competencias F1 - Taller POO");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla

        // 4. Crear el panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // 5. Añadir las pestañas (Panels)
        // Pasamos el gestor (ya cargado con datos) a cada panel
        tabbedPane.addTab("Ranking Pilotos", new PanelRanking(gestor));
        tabbedPane.addTab("Registrar Piloto", new PanelRegistroPiloto(gestor));
        tabbedPane.addTab("Registrar Mecánico", new PanelRegistroMecanico(gestor));
        tabbedPane.addTab("Registrar Auto", new PanelRegistroAuto(gestor));
        tabbedPane.addTab("Registrar Escudería", new PanelRegistroEscuderia(gestor));
        tabbedPane.addTab("Registrar Circuito", new PanelRegistroCircuito(gestor));
        tabbedPane.addTab("Registrar País", new PanelRegistroPais(gestor));
        tabbedPane.addTab("Informes", new PanelInformes(gestor));
        tabbedPane.addTab("Gestión de Carreras", new PanelCarreras(gestor));
        tabbedPane.addTab("Planificar Carrera", new PanelPlanificarCarrera(gestor));
        tabbedPane.addTab("Asociar Piloto-Escudería", new PanelAsociarPiloto(gestor));
        
        // --- TAREAS PENDIENTES ---
        // Aquí deberías añadir el resto de los paneles que crees:
        // tabbedPane.addTab("Registrar Mecánico", new PanelRegistroMecanico(gestor));
        // tabbedPane.addTab("Planificar Carrera", new PanelPlanificarCarrera(gestor));
        // tabbedPane.addTab("Cargar Resultados", new PanelResultados(gestor));
        // ... etc.

        // Añadimos el panel de pestañas al JFrame
        add(tabbedPane);
    }

    /**
     * Método crucial para leer los CSV y poblar el GestorCompetencia.
     * Utiliza los métodos de PasoDeArchivos y los de registrar de GestorCompetencia.
     */
    private void cargarDatosIniciales(GestorCompetencia gestor) throws IOException, PaisRepetidoException, PersonaRepetidaException, EscuderiaRepetidaException, AutoRepetidoException, CircuitoRepetidoException {
        
        // NOTA: Esta implementación asume que los getters necesarios existen en GestorCompetencia
        // (Ver la explicación al final)
        
        String pathBase = "datos/";

        // 1. Cargar Países (son independientes)
        List<Pais> paises = PasoDeArchivos.leerPaisesDesdeCSV(pathBase + "Paises.csv");
        for (Pais p : paises) {
            gestor.registrarPais(p.getIdPais(), p.getDescripcion());
        }
        System.out.println("Cargados " + paises.size() + " países.");

        // 2. Cargar Circuitos (dependen de País)
        // Necesitamos la lista de países que AHORA tiene el gestor
        List<Circuito> circuitos = PasoDeArchivos.leerCircuitosDesdeCSV(pathBase + "Circuitos.csv", gestor.getPaises());
        for (Circuito c : circuitos) {
            // El constructor de registrarCircuito es (nombre, longitud, pais, carrera)
            // Lo adaptamos. Asumimos que la carrera se asigna después.
            gestor.registrarCircuito(c.getIdCircuito(),c.getNombre(), c.getLongitud(), c.getPais(), null);
        }
        System.out.println("Cargados " + circuitos.size() + " circuitos.");


        // 3. Cargar Pilotos (dependen de País)
        List<Piloto> pilotos = PasoDeArchivos.leerPilotosDesdeCSV(pathBase + "Pilotos.csv", gestor.getPaises());
        for (Piloto p : pilotos) {
            gestor.registrarPiloto(p.getDni(), p.getNombre(), p.getApellido(), 
                                   p.getNumeroCompetencia(), p.getVictorias(), p.getPolePosition(),
                                   p.getVueltasRapidas(), p.getPodios(), p.getPais());
        }
        System.out.println("Cargados " + pilotos.size() + " pilotos.");

        // 4. Cargar Mecánicos (dependen de País)
        List<Mecanico> mecanicos = PasoDeArchivos.cargarMecanicos(pathBase + "Mecanicos.csv", gestor.getPaises());
        for (Mecanico m : mecanicos) {
            gestor.registrarMecanico(m.getDni(), m.getNombre(), m.getApellido(), 
                                     m.getPais(), m.getEspecialidad(), m.añosExperiencia());
        }
        System.out.println("Cargados " + mecanicos.size() + " mecánicos.");


        // 5. Cargar Escuderías (dependen de País)
         List<Escuderia> escuderias = PasoDeArchivos.leerEscuderiasDesdeCSV(pathBase + "Escuderias.csv", gestor.getPaises());
     for (Escuderia e : escuderias) {
         gestor.registrarEscuderia(e.getNombre(), e.getPais(), e.getIdEscuderia());
     }
    System.out.println("Cargadas " + escuderias.size() + " escuderías.");


        // 6. Cargar Autos (son independientes en tu CSV)
        List<Auto> autos = PasoDeArchivos.leerAutosDesdeCSV(pathBase + "Autos.csv", gestor.getEscuderias()); 
    // --- FIN DEL ARREGLO ---
    for (Auto a : autos) {
        gestor.registrarAuto(a.getModelo(), a.getMotor());
    }
    System.out.println("Cargados " + autos.size() + " autos.");


        // 7. Cargar Carreras (dependen de Circuito)
        List<Carrera> carreras = PasoDeArchivos.leerCarrerasDesdeCSV(pathBase + "Carreras.csv", gestor.getCircuitos());
        for (Carrera c : carreras) {
             // Usamos el método planificarCarrera que tienes en el gestor
            Carrera nuevaCarrera = gestor.planificarCarrera(c.getFechaRealizacion(), c.getHoraRealizacion(), c.getCircuito());
            nuevaCarrera.setNumeroVueltas(c.getNumeroVueltas()); // Asignamos las vueltas
        }
        System.out.println("Cargadas " + carreras.size() + " carreras.");

        // 8. Cargar Asociaciones (Piloto-Escuderia y Mecanico-Escuderia)
        // Estas son más complejas porque modifican objetos existentes
        
        PasoDeArchivos.cargarAsociacionesPilotoEscuderia(pathBase + "piloto_escuderias.csv", gestor.getPilotos(), gestor.getEscuderias());
        PasoDeArchivos.cargarAsociacionesEscuderiaMecanico(pathBase + "escuderia_mecanicos.csv", gestor.getMecanicos(), gestor.getEscuderias());
        
        System.out.println("Carga de datos iniciales completa.");
    }


    /**
     * Método Main para ejecutar la aplicación.
     */
    public static void main(String[] args) {
        // Asegura que la GUI se cree en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }
}