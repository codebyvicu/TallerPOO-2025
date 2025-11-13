package com.taller.proyecto.persistencia;

import com.taller.proyecto.logica.Auto;
import com.taller.proyecto.logica.Carrera;
import com.taller.proyecto.logica.Circuito;
import com.taller.proyecto.logica.Escuderia;
import com.taller.proyecto.logica.Mecanico;
import com.taller.proyecto.logica.Pais;
import com.taller.proyecto.logica.Piloto;
import com.taller.proyecto.logica.PilotoEscuderia;
import java.io.IOException;
import java.util.*;

public class GestionDeArchivos {
    public class GestorCompetencia {
    private List <Escuderia> escuderias;
    private List <Piloto> pilotos;
    private List <Mecanico> mecanicos;
    private List <Auto> autos;
    private List <Carrera> carreras;
    private List <Circuito> circuitos;
    private List <Pais> paises;
    private List <PilotoEscuderia> piloto_escuderias;
    private List <PilotoEscuderia> historialEscuderia;

    public GestorCompetencia(){
        this.escuderias = new ArrayList <>();
        this.pilotos = new ArrayList <>();
        this.mecanicos = new ArrayList <>();
        this.autos = new ArrayList <>();
        this.carreras = new ArrayList <>();
        this.circuitos = new ArrayList <>();
        this.paises = new ArrayList <>();
        this.piloto_escuderias = new ArrayList<>();
    }

    //Métodos para leer los archivos csv:
    public void cargarDatos(){
        String pathPaises = "datos/Paises.csv";
        String pathCircuitos = "datos/Circuitos.csv";
        String pathAutos = "datos/Autos.csv";
        String pathMecanicos = "datos/Mecanicos.csv";
        String pathPilotos = "datos/Pilotos.csv";
        String pathCarreras = "datos/Carreras.csv";
        String pathPiloto_escuderias = "datos/piloto_escuderias.csv"; // <-- ARREGLO: Faltaba .csv
        String pathEscuderia_mecanicos = "datos/escuderia_mecanicos.csv"; // <-- ARREGLO: Faltaba .csv y 'e'
        String pathEscuderias = "datos/Escuderias.csv"; // <-- ARREGLO: Faltaba .csv

    try {
        this.paises = PasoDeArchivos.leerPaisesDesdeCSV(pathPaises);
        
        // --- INICIO DEL ARREGLO ---
        // 1. Cargamos Escuderías PRIMERO
        this.escuderias = PasoDeArchivos.leerEscuderiasDesdeCSV(pathEscuderias, this.paises);
        
        // 2. Ahora cargamos Autos, pasándole las escuderías
        this.autos = PasoDeArchivos.leerAutosDesdeCSV(pathAutos, this.escuderias);
        // --- FIN DEL ARREGLO ---
        
        this.circuitos = PasoDeArchivos.leerCircuitosDesdeCSV(pathCircuitos, this.paises);
        this.mecanicos = PasoDeArchivos.cargarMecanicos(pathMecanicos, this.paises);
        this.pilotos = PasoDeArchivos.leerPilotosDesdeCSV(pathPilotos, this.paises);
        this.carreras = PasoDeArchivos.leerCarrerasDesdeCSV(pathCarreras, this.circuitos);
        
        // 8. ASOCIACIÓN ESCUDERÍA-MECÁNICO
        PasoDeArchivos.cargarAsociacionesEscuderiaMecanico(pathEscuderia_mecanicos, this.mecanicos, this.escuderias);
        // 9. ASOCIACIÓN PILOTO-ESCUDERÍA
        this.historialEscuderia = PasoDeArchivos.cargarAsociacionesPilotoEscuderia(pathPiloto_escuderias, this.pilotos, this.escuderias);
        
        } catch (IOException e) {
        System.err.println("ERROR CRÍTICO: No se pudieron cargar los datos.");
        }
    } } }