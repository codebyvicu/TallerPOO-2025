package com.taller.proyecto.persistencia;

import com.taller.proyecto.logica.Auto;
import com.taller.proyecto.logica.Carrera;
import com.taller.proyecto.logica.Circuito;
import com.taller.proyecto.logica.Escuderia;
import com.taller.proyecto.logica.Especialidad;
import com.taller.proyecto.logica.Mecanico;
import com.taller.proyecto.logica.Pais;
import com.taller.proyecto.logica.Piloto;
import com.taller.proyecto.logica.PilotoEscuderia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class PasoDeArchivos{
    
    private static final String SEPARADOR = ","; 

    // =========================================================================
    // MÉTODOS AUXILIARES (HELPERS)
    // =========================================================================

    private static Pais buscarPaisPorId(List<Pais> paises, int id) {
        if (paises == null) return null;
        for (Pais p : paises) {
            if (p.getIdPais() == id) { 
                return p;
            }
        }
        return null; 
    }

    private static Circuito buscarCircuitoPorId(List<Circuito> circuitos, int id) {
        if (circuitos == null) return null;
        for (Circuito c : circuitos) {
            if (c.getIdCircuito() == id) { // <-- ESTE ES EL ARREGLO
                return c;
            }
        }
        return null; 
    }
    
    private static Piloto buscarPilotoPorDni(List<Piloto> pilotos, String dni) {
        if (pilotos == null) return null;
        for (Piloto p : pilotos) {
            if (p.getDni().equals(dni)) { 
                return p;
            }
        }
        return null;
    }
    
    private static Escuderia buscarEscuderiaPorId(List<Escuderia> escuderias, int id) {
        if (escuderias == null) return null;
        for (Escuderia e : escuderias) {
            if (e.getIdEscuderia() == id) { // <-- ESTE ES EL ARREGLO
                return e;
            }
        }
        return null;
    }
    
    private static Mecanico buscarMecanicoPorDni(List<Mecanico> mecanicos, String dni) {
        if (mecanicos == null) return null;
        for (Mecanico m : mecanicos) {
            if (m.getDni().equals(dni)) { 
                return m;
            }
        }
        return null;
    }

    private static int parseSafeInt(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return Integer.parseInt(value.trim());
    }
    
    // =========================================================================
    // MÉTODOS DE CARGA PRIMARIOS (SIN DEPENDENCIA)
    // =========================================================================

    // ... (leerPaisesDesdeCSV, leerAutosDesdeCSV, leerCircuitosDesdeCSV ya están definidos en tu archivo) ...
    // NOTA: El constructor de Circuito en tu archivo es: (String nombre, int longitud, Pais pais). 
    // La versión de carga abajo se adapta para buscar el Pais.

    // LEER PAISES DESDE CSV
    public static List<Pais> leerPaisesDesdeCSV(String path) throws IOException {
        List<Pais> paises = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            br.readLine(); // Saltear encabezado (ej: "idPais,descripcion")

            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");

                int idPais = Integer.parseInt(valores[0].trim());
                String descripcion = valores[1].trim();

                Pais p = new Pais(idPais, descripcion, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                paises.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paises;
    }

    // LEER AUTOS DESDE CSV
    public static List<Auto> leerAutosDesdeCSV(String path, List<Escuderia> escuderiasCargadas) {
    List<Auto> autos = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        String linea;
        br.readLine(); // Saltear el encabezado

        while ((linea = br.readLine()) != null) {
            
            // 1. Parsear la línea y manejar el array
            String[] valores = linea.split(SEPARADOR);
            
            if (valores.length < 4) {
                System.err.println("Advertencia: Línea incompleta en Autos CSV, saltando: " + linea);
                continue;
            }

            // 2. Extraer y limpiar los atributos
            String modelo = valores[1].trim();
            String motor = valores[2].trim();
            int escuderiaId = parseSafeInt(valores[3], -1);

            // 3. Crear el objeto Auto. Asumo un constructor que recibe modelo y motor, e inicializa sus listas de asociación vacías
            Auto nuevoAuto = new Auto(modelo, motor);
            autos.add(nuevoAuto);

            if (escuderiaId != -1) {
                Escuderia escuderia = buscarEscuderiaPorId(escuderiasCargadas, escuderiaId);
                if (escuderia != null) {
                    escuderia.agregarAuto(nuevoAuto); // Asocia el auto
                } else {
                    System.err.println("Advertencia: Auto " + modelo + " no asociado, escudería " + escuderiaId + " no encontrada.");
                }
            } 
        }
    } catch (IOException e) {
        System.err.println("Error al leer el archivo Autos.csv: " + e.getMessage());
        e.printStackTrace();
    }
    return autos;
    }

    // LEER CIRCUITOS DESDE CSV (AJUSTADO PARA INCLUIR PAIS)
    // Arreglo: Lee y asigna el idCircuito del CSV
    public static List<Circuito> leerCircuitosDesdeCSV(String path, List<Pais> paisesCargados) 
            throws IOException {
        
        List<Circuito> circuitos = new ArrayList<>();
        BufferedReader br = null;
        
        try (BufferedReader brActual = new BufferedReader(new FileReader(path))) {
            br = brActual;
            br.readLine(); // Saltar encabezado
            String linea = br.readLine();

            while (linea != null) {
                linea = linea.replace('"', ' ').trim();
                String[] valores = linea.split(SEPARADOR);
                
                if (valores.length < 4) { linea = br.readLine(); continue; }
                try {
             int idCircuito = parseSafeInt(valores[0], -1); // ARREGLO 1: Leer el ID
             String nombre = valores[1].trim(); 
             int longitud = parseSafeInt(valores[2], 0);
             int paisId = parseSafeInt(valores[3], -1);
    
             if (paisId == -1 || idCircuito == -1) { 
             linea = br.readLine(); 
            continue; 
           }
    
    Pais pais = buscarPaisPorId(paisesCargados, paisId); 

    if (pais == null) { linea = br.readLine(); continue; }
    
    Circuito nuevoCircuito = new Circuito(nombre, longitud, pais); 
    nuevoCircuito.setIdCircuito(idCircuito); // ARREGLO 3: Asignar el ID
    circuitos.add(nuevoCircuito);

                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en Circuitos: " + linea);
                }
                linea = br.readLine();
            }
        } finally {
            if (null != br) br.close();
        }
        return circuitos;
    }
    
    // ... (Carga de Escuderias y Personas sigue la misma lógica de búsqueda de País) ...

    // =========================================================================
    // MÉTODOS DE CARGA DE ASOCIACIONES
    // =========================================================================
    
    // LEER CARRERAS DESDE CSV (CON DEPENDENCIA DE CIRCUITO)
    public static List<Carrera> leerCarrerasDesdeCSV(String rutaArchivo, List<Circuito> circuitosCargados) throws IOException {
        
        List<Carrera> carreras = new ArrayList<>();
        BufferedReader br = null;
            
        try (BufferedReader brActual = new BufferedReader(new FileReader(rutaArchivo))) {
            br = brActual;
            br.readLine(); // Saltar encabezado
            String linea = br.readLine();

            while (linea != null) {
                linea = linea.replace('"', ' ').trim();
                String[] campos = linea.split(SEPARADOR);
                
                if (campos.length < 6) { 
                    linea = br.readLine();
                    continue;
                }

                try {
                    String fecha = campos[1].trim();
                    int numVueltas = Integer.parseInt(campos[2].trim());
                    String hora = campos[3].trim();
                    int circuitoId = Integer.parseInt(campos[5].trim()); 
                    
                    Circuito circuito = buscarCircuitoPorId(circuitosCargados, circuitoId);
                    
                    if (circuito == null) {
                        System.err.println("Advertencia: Carrera en línea " + linea + " no cargada. Circuito no encontrado.");
                        linea = br.readLine();
                        continue;
                    }
                    
                    Carrera nuevaCarrera = new Carrera(fecha, hora, circuito); 
                    nuevaCarrera.setNumeroVueltas(numVueltas); // Usamos setter para el atributo adicional

                    carreras.add(nuevaCarrera);

                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en línea: " + linea);
                }
                
                linea = br.readLine();
            }
        } finally {
            if (null != br) br.close();
        }
        return carreras;
    }

    // CARGAR ASOCIACIÓN ESCUDERIA-MECANICO
    public static void cargarAsociacionesEscuderiaMecanico(
        String rutaArchivo, 
        List<Mecanico> mecanicosCargados, 
        List<Escuderia> escuderiasCargadas
    ) throws IOException {

        BufferedReader br = null;

        try (BufferedReader brActual = new BufferedReader(new FileReader(rutaArchivo))) {
            br = brActual;
            br.readLine(); // Saltar encabezado
            String linea = br.readLine();
            
            while (linea != null) {
                linea = linea.replace('"', ' ').trim();
                String[] campos = linea.split(SEPARADOR);
                
                if (campos.length < 2) { // idEscuderia, dniMecanico
                    linea = br.readLine();
                    continue;
                }

                try {
                    int escuderiaId = Integer.parseInt(campos[0].trim());
                    String dniMecanico = campos[1].trim();
                    
                    Escuderia escuderia = buscarEscuderiaPorId(escuderiasCargadas, escuderiaId);
                    Mecanico mecanico = buscarMecanicoPorDni(mecanicosCargados, dniMecanico);

                    if (mecanico == null || escuderia == null) {
                        System.err.println("Advertencia: Asociación Mecánico-Escuderia saltada. Falta Mecánico/Escudería en la línea: " + linea);
                        linea = br.readLine();
                        continue;
                    }
                    
                    //Establecer bidireccionalidad (Actualiza las listas internas de las entidades)
                    escuderia.agregarMecanico(mecanico);
                    mecanico.agregarEscuderia(escuderia); 

                } catch (Exception e) {
                    System.err.println("Error procesando línea de asociación Mecánico-Escuderia: " + e.getMessage());
                }
                
                linea = br.readLine();
            }
        } finally {
            if (null != br) br.close();
        }
    }
    
    // CARGAR ASOCIACIÓN PILOTO-ESCUDERIA
    public static List<PilotoEscuderia> cargarAsociacionesPilotoEscuderia(
        String rutaArchivo, 
        List<Piloto> pilotosCargados, 
        List<Escuderia> escuderiasCargadas
    ) throws IOException {

        List<PilotoEscuderia> asociaciones = new ArrayList<>();
        BufferedReader br = null;

        try (BufferedReader brActual = new BufferedReader(new FileReader(rutaArchivo))) {
            br = brActual;
            br.readLine(); // Saltar encabezado
            String linea = br.readLine();
            
            while (linea != null) {
                linea = linea.replace('"', ' ').trim();
                String[] campos = linea.split(SEPARADOR);
                
                if (campos.length < 4) { // DNI, IdEscuderia, DesdeFecha, HastaFecha
                    linea = br.readLine();
                    continue;
                }

                try {
                    String dniPiloto = campos[0].trim();
                    int escuderiaId = Integer.parseInt(campos[1].trim());
                    String desdeFecha = campos[2].trim();
                    String hastaFecha = campos[3].trim();
                    
                    Piloto piloto = buscarPilotoPorDni(pilotosCargados, dniPiloto);
                    Escuderia escuderia = buscarEscuderiaPorId(escuderiasCargadas, escuderiaId);

                    if (piloto == null || escuderia == null) {
                        System.err.println("Advertencia: Asociación Piloto-Escuderia saltada. Falta Piloto/Escudería en la línea: " + linea);
                        linea = br.readLine();
                        continue;
                    }

                    PilotoEscuderia nuevaAsociacion = new PilotoEscuderia(
                        desdeFecha, hastaFecha, piloto, escuderia
                    );
                    
                    asociaciones.add(nuevaAsociacion);
                    
                    // Establecer bidireccionalidad
                    piloto.agregarPilotoEscuderia(nuevaAsociacion);
                    escuderia.agregarHistorialPiloto(nuevaAsociacion); 

                } catch (Exception e) {
                    System.err.println("Error procesando línea de asociación Piloto-Escuderia: " + e.getMessage());
                }
                
                linea = br.readLine();
            }
        } finally {
            if (null != br) br.close();
        }
        return asociaciones;
    }

    //LEER MECANICOS DESDE CSV
    public static List<Mecanico> cargarMecanicos(String rutaArchivo, List<Pais> paisesCargados) 
        throws IOException {

    List<Mecanico> mecanicos = new ArrayList<>();
    BufferedReader br = null;

    try (BufferedReader brActual = new BufferedReader(new FileReader(rutaArchivo))) {
        br = brActual;
        br.readLine(); // Saltar encabezado
        String linea = br.readLine();

        while (linea != null) {
            linea = linea.replace('"', ' ').trim();
            String[] campos = linea.split(SEPARADOR);

            if (campos.length < 6) { linea = br.readLine(); continue; } 

            try {
                String dni = campos[0].trim();
                String nombre = campos[1].trim();
                String apellido = campos[2].trim();

                int paisId = parseSafeInt(campos[3], -1); // <-- ARREGLO: Columna 3
                Especialidad especialidad = Especialidad.valueOf(campos[4].trim().toUpperCase()); // <-- OK
                int añosExperiencia = parseSafeInt(campos[5], 0); // <-- ARREGLO: Columna 5

                if (paisId == -1) { linea = br.readLine(); continue; }

                Pais pais = buscarPaisPorId(paisesCargados, paisId); 
                if (pais == null) { linea = br.readLine(); continue; }

                Mecanico nuevoMecanico = new Mecanico(
                    dni, nombre, apellido, pais, especialidad, añosExperiencia
                );

                mecanicos.add(nuevoMecanico);

            } catch (IllegalArgumentException e) {
                System.err.println("Error: Especialidad inválida en Mecanicos: " + linea + " (" + campos[4] + ")");
            }

            linea = br.readLine();
        }
    } finally {
        if (null != br) br.close();
    }
    return mecanicos;
}

    // CARGAR PILOTOS DESDE CSV (Ajustado)
    public static List<Piloto> leerPilotosDesdeCSV(String rutaArchivo, List<Pais> paisesCargados) 
            throws IOException {
        
        List<Piloto> pilotos = new ArrayList<>();
        BufferedReader br = null;
        
        try (BufferedReader brActual = new BufferedReader(new FileReader(rutaArchivo))) {
            br = brActual;
            br.readLine(); 
            String linea = br.readLine();
            
            while (linea != null) {
                linea = linea.replace('"', ' ').trim();
                String[] campos = linea.split(SEPARADOR);
                
                if (campos.length < 9) { linea = br.readLine(); continue; }

                try {
                    // Extracción con corrección de seguridad
                    String dni = campos[0].trim();
                    String nombre = campos[1].trim();
                    String apellido = campos[2].trim();
                    
                    // CORRECCIÓN: Uso de parseSafeInt en todas las columnas numéricas
                    int numComp = parseSafeInt(campos[3], 0);
                    int victorias = parseSafeInt(campos[4], 0);
                    int polePosition = parseSafeInt(campos[5], 0);
                    int vueltasRapidas = parseSafeInt(campos[6], 0);
                    int podios = parseSafeInt(campos[7], 0);
                    int paisId = parseSafeInt(campos[8], -1); 
                    
                    if (paisId == -1) { linea = br.readLine(); continue; }

                    // Búsqueda de País
                    Pais pais = buscarPaisPorId(paisesCargados, paisId); 
                    
                    if (pais == null) { linea = br.readLine(); continue; }

                    // Creación del objeto (9 argumentos)
                    Piloto nuevoPiloto = new Piloto(dni, nombre, apellido, numComp, victorias, polePosition, vueltasRapidas, podios, pais);
                    
                    pilotos.add(nuevoPiloto);

                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en línea: " + linea + ". Error: " + e.getMessage());
                } 
                
                linea = br.readLine();
            }
        } finally {
            if (null != br) br.close();
        }
        return pilotos;
    }

    //LEER ESCUDERIAS DESDE CSV
    public static List<Escuderia> leerEscuderiasDesdeCSV(String path, List<Pais> paisesCargados) 
            throws IOException {
            
        List<Escuderia> escuderias = new ArrayList<>();
        BufferedReader br = null;
        
        try (BufferedReader brActual = new BufferedReader(new FileReader(path))) {
            br = brActual;
            br.readLine(); // Saltar encabezado
            String linea = br.readLine();

            while (linea != null) {
                linea = linea.replace('"', ' ').trim();
                String[] campos = linea.split(SEPARADOR);
                
                // idEscuderia, nombre, pais (3 campos)
                if (campos.length < 3) { linea = br.readLine(); continue; }

                try {
                    // CORRECCIÓN: Extracción de 3 campos con IDs numéricos
                    int escuderiaId = parseSafeInt(campos[0], 0);
                    String nombre = campos[1].trim();
                    int paisId = parseSafeInt(campos[2], -1); 

                    if (escuderiaId == 0 || paisId == -1) { linea = br.readLine(); continue; }
                    
                    Pais pais = buscarPaisPorId(paisesCargados, paisId);
                    
                    if (pais == null) { linea = br.readLine(); continue; }

                    // Constructor: public Escuderia(int idEscuderia, String nombre, Pais pais)
                    Escuderia nuevaEscuderia = new Escuderia(nombre, pais, escuderiaId); 
                    escuderias.add(nuevaEscuderia);

                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en Escuderias: " + linea);
                }
                linea = br.readLine();
            }
        } finally {
            if (null != br) br.close();
        }
        return escuderias;
    }

}
