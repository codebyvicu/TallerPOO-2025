package com.taller.proyecto.logica;

import java.util.*;


public class GestorCompetencia {
    private List <Escuderia> escuderias;
    private List <Piloto> pilotos;
    private List <Mecanico> mecanicos;
    private List <Auto> autos;
    private List <Carrera> carreras;
    private List <Circuito> circuitos;
    private List <Pais> paises;
    private List <AutoPiloto> historialCarreras;
    private List <PilotoEscuderia> historialEscuderia;
    private List <AutoPiloto> participaciones; 

    public GestorCompetencia(){
        this.escuderias = new ArrayList <>();
        this.pilotos = new ArrayList <>();
        this.mecanicos = new ArrayList <>();
        this.autos = new ArrayList <>();
        this.carreras = new ArrayList <>();
        this.circuitos = new ArrayList <>();
        this.paises = new ArrayList <>();
        this.historialCarreras = new ArrayList <>();
        this.historialEscuderia = new ArrayList <>();
        this.participaciones = new ArrayList <>();
    }

    public List<Pais> getPaises() {
        return paises;
    }
    public List<Circuito> getCircuitos() {
        return circuitos;
    }
    public List<Piloto> getPilotos() {
        return pilotos;
    }
    public List<Mecanico> getMecanicos() {
        return mecanicos;
    }
    public List<Escuderia> getEscuderias() {
        return escuderias;
    }
    public List<Auto> getAutos() {
        return autos;
    }
    public List<Carrera> getCarreras() {
        return carreras;
    }
    public List<AutoPiloto> getParticipaciones() {
        return participaciones;
    }

    //Metodos necesarios para registrar nuevos objetos con sus respectivas excepciones y controles:
    public void registrarPiloto(String dni, String nombre, String apellido, int numeroCompetencia, int victorias, int polePosition, int vueltasRapidas, int podios, Pais pais) throws PersonaRepetidaException{
        
        // 1. Validar que el DNI no exista en la lista de PILOTOS
        for(Piloto p : pilotos){
            if(p.getDni().equals(dni)){
                throw new PersonaRepetidaException("Este piloto ya se encuentra en la lista");
            }
        } 

        // 2. Validar que el DNI no exista en la lista de MECÁNICOS
        for(Mecanico m : mecanicos) {
            if(m.getDni().equals(dni)) {
                throw new PersonaRepetidaException("Error: El DNI " + dni + " ya está registrado como Mecánico.");
            }
        }

        Piloto piloto = new Piloto(dni, nombre, apellido, numeroCompetencia, victorias, polePosition, vueltasRapidas, podios, pais);
        this.pilotos.add(piloto);
    }

    public void registrarAuto(String modelo, String motor) throws AutoRepetidoException{
        // Ya no validamos si el modelo está repetido,
        // porque es normal que haya dos autos del mismo modelo (ej: A524)
        Auto auto = new Auto(modelo, motor);
        this.autos.add(auto);
    }

    public void registrarMecanico(String dni, String nombre, String apellido, Pais pais, Especialidad especialidad, int añosExperiencia) throws PersonaRepetidaException{
        
        // 1. Validar que el DNI no exista en la lista de PILOTOS
        for(Piloto p : pilotos) {
            if(p.getDni().equals(dni)) {
                throw new PersonaRepetidaException("Error: El DNI " + dni + " ya está registrado como Piloto.");
            }
        }
        // 2. Validar que el DNI no exista en la lista de MECÁNICOS
        for(Mecanico m : mecanicos){
            if(m.getDni().equals(dni)){
                throw new PersonaRepetidaException("Este mecánico ya se encuentra en la lista");
            }
        }
        
        // 3. Crear el mecánico si pasa ambas validaciones
        Mecanico mecanico = new Mecanico(dni, nombre, apellido, pais, especialidad, añosExperiencia);
        this.mecanicos.add(mecanico);
    }

    public void registrarEscuderia(String nombre, Pais pais, int idEscuderia) throws EscuderiaRepetidaException{
        for(Escuderia e : escuderias){
            if(e.getNombre().equals(nombre)){
                throw new EscuderiaRepetidaException("Esta escudería ya se encuentra en la lista");
            }
            if(e.getIdEscuderia() == idEscuderia){
                throw new EscuderiaRepetidaException("Este ID ya se encuentra en uso");
            }
        }
        Escuderia escuderia = new Escuderia(nombre, pais, idEscuderia);
        this.escuderias.add(escuderia);
    }

    public void registrarCircuito(int idCircuito, String nombre, int longitud, Pais pais, Carrera carrera) throws CircuitoRepetidoException{
        for(Circuito c : circuitos){
            if(c.getNombre().equals(nombre)){
                throw new CircuitoRepetidoException("Este circuito ya se encuentra en la lista");
            }
        }
        Circuito circuito = new Circuito(nombre, longitud, pais, carrera);
        circuito.setIdCircuito(idCircuito);
        this.circuitos.add(circuito);
    }

    public void registrarPais(int idPais, String descripcion) throws PaisRepetidoException{
        for(Pais p : paises){
            if(p.getIdPais() == idPais){
                throw new PaisRepetidoException("Este País ya se encuentra en la lista. Revisar ID.");
            }
        }
        Pais pais = new Pais(idPais, descripcion);
        this.paises.add(pais);
    }

    //Registramos nueva participacion de piloto en carrera
    public void registrarParticipacion(Piloto piloto, Carrera carrera, String modelo) throws ParticipacionDuplicadaException {

        // 1. VALIDAR PILOTO (Regla PDF: "que un piloto esté asignado a más de un auto...")
        // Primero, validamos que este piloto no esté ya inscrito en esta carrera con CUALQUIER auto.
        for (AutoPiloto p : participaciones) {
            if (p.getPiloto().equals(piloto) && p.getCarrera().equals(carrera)) {
                throw new ParticipacionDuplicadaException("El Piloto " + piloto.getApellido() + " ya está inscrito en la carrera " + carrera.getCircuito().getNombre());
            }
        }

        // 2. VALIDAR AUTO (Regla PDF: "Que un auto no sea asignado a más de un piloto...")
        
        // Primero, creamos una lista de todos los autos (objetos) de ese modelo 
        // que ya están ocupados en ESTA carrera.
        List<Auto> autosOcupadosEnEstaCarrera = new ArrayList<>();
        for (AutoPiloto p : participaciones) {
            // Si la participación es en esta carrera Y es del modelo que buscamos...
            if (p.getCarrera().equals(carrera) && p.getAuto().getModelo().equals(modelo)) {
                autosOcupadosEnEstaCarrera.add(p.getAuto()); // ...lo añadimos a la lista de ocupados.
            }
        }

        // Ahora, buscamos en la lista general de autos (this.autos) 
        // un auto que sea de ese modelo PERO que NO esté en la lista de ocupados.
        Auto autoEncontrado = null;
        for (Auto auto : this.autos) {
            if (auto.getModelo().equals(modelo) && !autosOcupadosEnEstaCarrera.contains(auto)) {
                autoEncontrado = auto; // ¡Encontramos un auto libre!
                break; // Salimos del bucle
            }
        }

        // 3. PROCESAR RESULTADO DE BÚSQUEDA
        if (autoEncontrado == null) {
            // Si no encontramos un auto, puede ser por dos motivos:
            if (autosOcupadosEnEstaCarrera.isEmpty()) {
                 // 1. Porque no existe ningún auto con ese modelo (ej: "SF-99")
                 // Usamos IllegalArgumentException para no añadir "throws Exception"
                 throw new IllegalArgumentException("No se encontró un auto con el modelo especificado: " + modelo);
            } else {
                 // 2. Porque sí existen, pero los 2 (o más) que hay ya están ocupados.
                 throw new IllegalArgumentException("Error: No hay más autos modelo '" + modelo + "' disponibles para esta carrera.");
            }
        }

        // 4. CREAR PARTICIPACIÓN
        // Si llegamos aquí, 'autoEncontrado' es un auto específico,
        // de ese modelo y libre para esta carrera.
        AutoPiloto nuevaParticipacion = new AutoPiloto(piloto, autoEncontrado, carrera);
        participaciones.add(nuevaParticipacion);
    }

    //Método para planificar una carrera:
    public Carrera planificarCarrera(String fecha, String hora, Circuito circuito){

        Carrera nuevaCarrera = new Carrera(fecha, hora, circuito);
    
        this.carreras.add(nuevaCarrera);
        return nuevaCarrera;
    }

    //Método para registrar el resultado de la carrera.
    public void registrarResultadoCarrera(Carrera carrera, Piloto piloto, int posicion, boolean vueltaRapida) {
    AutoPiloto participacion = null;
    
    // 1. (Lógica para buscar la participación)
    for (AutoPiloto ap : this.participaciones) {
        if (ap.getPiloto().equals(piloto) && ap.getCarrera().equals(carrera)) {
            participacion = ap;
            break;
        }
    }
    if (participacion != null) {
        // 2. Asignar resultados a la participación
        participacion.setPosicionFinal(posicion);
        participacion.setVueltaRapida(vueltaRapida);

        // 3. Actualizar estadísticas globales del Piloto
        if (posicion == 1) {
            piloto.setVictorias(piloto.getVictorias() + 1);
        }
        if (posicion >= 1 && posicion <= 3) {
            piloto.setPodios(piloto.getPodios() + 1);
        }
        if (vueltaRapida) {
            piloto.setVueltasRapidas(piloto.getVueltasRapidas() + 1);
        }

        // 4. CALCULAR Y SUMAR PUNTOS (Usando el Enum y el nuevo método)
        
        // Llama al enum para obtener los puntos
        int puntosGanados = PosicionPuntuable.obtenerPuntos(posicion); 
        
        // Llama al nuevo método de Piloto
        piloto.sumarPuntos(puntosGanados); 

    } else {
        System.err.println("Error: No se encontró la participación del piloto " 
            + piloto.getApellido() + " en la carrera.");
    }
 }
   
  public List<Piloto> generarRankingPilotos() {
    // 1. Creamos una copia de la lista original para no modificarla
    List<Piloto> ranking = new ArrayList<>(this.pilotos);
    // 2. Usamos Collections.sort() con un Comparator personalizado
    Collections.sort(ranking, new Comparator<Piloto>() {
        @Override
        public int compare(Piloto p1, Piloto p2) {
            // orden descendente (el más alto primero)
            return Integer.compare(p2.getPuntosAcumulados(), p1.getPuntosAcumulados());
        }
    });
    return ranking;
}

/**
 * Método auxiliar para buscar un piloto en la lista por su DNI.
 * @param dni El DNI del piloto a buscar.
 * @return El objeto Piloto, o null si no se encuentra.
 */
public Piloto buscarPilotoPorDni(String dni) {
    for (Piloto p : this.pilotos) {
        // Comparamos el DNI usando el getter de la clase Persona
        if (p.getDni().equals(dni)) { 
            return p;
        }
    }
    return null; // No se encontró
}

/**
 * Muestra el histórico de un piloto específico buscado por DNI.
 * Cumple con la segunda parte de "...o de un piloto determinado"
 * @param dni El DNI del piloto a consultar.
 */
public String mostrarHistoricoPiloto(String dni) {
    Piloto pilotoBuscado = buscarPilotoPorDni(dni);
    
    if (pilotoBuscado != null) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- HISTÓRICO DE " + pilotoBuscado.getApellido().toUpperCase() + " ---\n");
        sb.append("  > Victorias: " + pilotoBuscado.getVictorias() + "\n");
        sb.append("  > Podios: " + pilotoBuscado.getPodios() + "\n");
        sb.append("  > Vueltas Rápidas: " + pilotoBuscado.getVueltasRapidas() + "\n");
        sb.append("  > Puntos Totales: " + pilotoBuscado.getPuntosAcumulados() + "\n");
        return sb.toString();
    } else {
        return "Error: No se pudo encontrar un piloto con el DNI: " + dni;
    }
}

/**
 * Muestra el histórico de victorias y podios de TODOS los pilotos.
 * Cumple con la primera parte de "Histórico de podios y victorias de cada piloto..."
 */
public String mostrarHistoricoGeneralPilotos() {
    StringBuilder sb = new StringBuilder();
    sb.append("--- HISTÓRICO GENERAL DE PILOTOS ---\n\n");
    
    for (Piloto p : this.pilotos) {
        sb.append("Piloto: " + p.getNombre() + " " + p.getApellido() + "\n");
        sb.append("  > Victorias: " + p.getVictorias() + "\n");
        sb.append("  > Podios: " + p.getPodios() + "\n");
        sb.append("  > Vueltas Rápidas: " + p.getVueltasRapidas() + "\n");
        sb.append("---------------------------------\n");
    }
    return sb.toString();
 }

 /** Informe de autos utilizados por escudería en diferentes carreras
 * Genera un informe detallando qué modelos de autos de cada escudería
 * fueron utilizados en qué carreras y por quién.
 */
public String generarInformeAutosPorEscuderia() {
    StringBuilder sb = new StringBuilder();
    sb.append("--- INFORME DE AUTOS UTILIZADOS POR ESCUDERÍA ---\n\n");

    for (Escuderia escuderia : this.escuderias) {
        sb.append("[ESCUDERÍA] " + escuderia.getNombre().toUpperCase() + "\n");
        
        List<String> usosUnicos = new ArrayList<>();
        boolean foundUsage = false;

        // Asumo que tu clase Escuderia tiene getAuto()
        for (Auto autoPropio : escuderia.getAuto()) {
            
            // Asumo que 'participaciones' está en GestorCompetencia
            for (AutoPiloto participacion : this.participaciones) {
                
                if (participacion.getAuto().equals(autoPropio)) {
                    
                    foundUsage = true;
                    Carrera carrera = participacion.getCarrera();
                    Piloto piloto = participacion.getPiloto();
                    String claveUso = autoPropio.getModelo() + "::" + carrera.getCircuito().getNombre();
                    
                    if (!usosUnicos.contains(claveUso)) {
                        String detalleUso = String.format(
                            "  -> Modelo: %s | Carrera: %s (%s) | Piloto: %s %s\n",
                            autoPropio.getModelo(),
                            carrera.getCircuito().getNombre(),
                            carrera.getFechaRealizacion(),
                            piloto.getNombre(),
                            piloto.getApellido()
                        );
                        sb.append(detalleUso);
                        usosUnicos.add(claveUso);
                    }
                }
            }
        }
        
        if (!foundUsage) {
            sb.append("  No se registraron usos de sus autos en carreras.\n");
        }
        sb.append("\n"); // Espacio entre escuderías
    }
    return sb.toString();
}

/**Informe de años de experiencia y especialidad de mecánicos por escudería:
 * Genera un informe que muestra los años de experiencia y la especialidad
 * de cada mecánico, agrupados por la escudería a la que pertenecen.
 */
public String generarInformeMecanicosPorEscuderia() {
    StringBuilder sb = new StringBuilder();
    sb.append("--- INFORME DE EXPERIENCIA Y ESPECIALIDAD POR ESCUDERÍA ---\n\n");

    for (Escuderia escuderia : this.escuderias) {
        sb.append("[ESCUDERÍA] " + escuderia.getNombre().toUpperCase() + "\n");
        
        List<Mecanico> listaMecanicos = escuderia.getMecanicos();
        
        if (listaMecanicos.isEmpty()) {
            sb.append("  No hay mecánicos asociados actualmente a esta escudería.\n");
            continue;
        }

        for (Mecanico mecanico : listaMecanicos) {
            String detalleMecanico = String.format(
                "  -> Mecánico: %s %s | Especialidad: %s | Experiencia: %d años\n",
                mecanico.getNombre(),
                mecanico.getApellido(),
                mecanico.getEspecialidad().toString(),
                mecanico.añosExperiencia()
            );
            sb.append(detalleMecanico);
        }
         sb.append("\n"); // Espacio entre escuderías
    }
    return sb.toString();
  }

  /**
 * Calcula la cantidad de veces que un piloto específico corrió en un circuito determinado.
 * @param dniPiloto El DNI del piloto a buscar.
 * @param nombreCircuito El nombre del circuito a buscar.
 * @return La cantidad de carreras que cumplen la condición.
 */
public int contarCarrerasPorPilotoYCircuito(String dniPiloto, String nombreCircuito) {
    int contador = 0;
    
    // 1. Validar la existencia de Piloto (opcional, pero recomendable)
    Piloto pilotoBuscado = buscarPilotoPorDni(dniPiloto); 
    if (pilotoBuscado == null) {
        System.err.println("Error: Piloto con DNI " + dniPiloto + " no encontrado.");
        return 0;
    }

    // 2. Iterar sobre todas las participaciones registradas en el sistema
    for (AutoPiloto participacion : this.participaciones) {
        
        // 3. Obtener referencias para la validación
        Carrera carrera = participacion.getCarrera();
        
        // Se asume que Circuito tiene un método getNombre()
        String circuitoDeLaCarrera = carrera.getCircuito().getNombre();
        
        // 4. Aplicar doble condición
        // Condición A: El Piloto de la participación es el Piloto buscado.
        boolean condicionPiloto = participacion.getPiloto().equals(pilotoBuscado);
        
        // Condición B: El nombre del Circuito de la carrera coincide con el Circuito buscado.
        boolean condicionCircuito = circuitoDeLaCarrera.equalsIgnoreCase(nombreCircuito);
        
        // 5. Contar si ambas condiciones se cumplen
        if (condicionPiloto && condicionCircuito) {
            contador++;
        }
    }

    return contador;
}

/**
 * Calcula la cantidad total de carreras que se han planificado o corrido
 * en un circuito determinado.
 * @param nombreCircuito El nombre del circuito a buscar.
 * @return La cantidad de carreras asociadas a ese circuito.
 */
public int contarCarrerasPorCircuito(String nombreCircuito) {
    int contador = 0;
    
    // 1. Iterar sobre todas las carreras planificadas o registradas en el sistema
    for (Carrera carrera : this.carreras) {
        
        // 2. Obtener el objeto Circuito asociado a esta Carrera
        Circuito circuitoDeLaCarrera = carrera.getCircuito();
        
        // 3. Validar si el objeto Circuito existe y si su nombre coincide
        if (circuitoDeLaCarrera != null && circuitoDeLaCarrera.getNombre().equalsIgnoreCase(nombreCircuito)) {
            contador++;
        }
    }
    return contador;
  }

  public String generarInformeResultadosPorFecha(String fechaInicio, String fechaFin) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- INFORME DE RESULTADOS POR RANGO DE FECHAS ---\n");
        sb.append(" (Rango: " + fechaInicio + " a " + fechaFin + ")\n\n");
        
        boolean encontrado = false;

        for (Carrera carrera : this.carreras) {
            
            String fechaCarrera = carrera.getFechaRealizacion();
            
            // Usamos la misma lógica de comparación de Strings que ya tenías
            if (fechaCarrera.compareTo(fechaInicio) >= 0 && fechaCarrera.compareTo(fechaFin) <= 0) {
                
                encontrado = true;
                sb.append("--- CARRERA: " + carrera.getCircuito().getNombre().toUpperCase() + " ---\n");
                sb.append("FECHA: " + fechaCarrera + " | VUELTAS: " + carrera.getNumeroVueltas() + "\n");
                sb.append("----------------------------------------\n");
                
                // 4. Obtener y ordenar los resultados
                List<AutoPiloto> resultadosCarrera = new ArrayList<>();
                for (AutoPiloto participacion : this.participaciones) {
                    if (participacion.getCarrera().equals(carrera)) {
                        resultadosCarrera.add(participacion);
                    }
                }
                
                // Ordenar por posición final (ascendente)
                resultadosCarrera.sort(Comparator.comparingInt(AutoPiloto::getPosicionFinal));
                
                // 5. Imprimir Resultados
                if (resultadosCarrera.isEmpty()) {
                     sb.append("  * No hay pilotos inscritos en esta carrera.\n\n");
                
                } else if (resultadosCarrera.get(0).getPosicionFinal() == 0) {
                     sb.append("  * Resultados aún no registrados para esta carrera.\n");
                     // Imprime los inscritos aunque no tengan posición
                     for (AutoPiloto resultado : resultadosCarrera) {
                         sb.append("  - (Inscrito) " + resultado.getPiloto().getNombre() + " " + resultado.getPiloto().getApellido() + "\n");
                     }
                     sb.append("\n");

                } else {
                    for (AutoPiloto resultado : resultadosCarrera) {
                        if (resultado.getPosicionFinal() > 0) {
                             String vueltaRapida = resultado.getVueltaRapida() ? " (VUELTA RÁPIDA)" : "";
                             sb.append(String.format("  %-2d. %s %s - Auto: %s%s\n", 
                                 resultado.getPosicionFinal(),
                                 resultado.getPiloto().getNombre(),
                                 resultado.getPiloto().getApellido(),
                                 resultado.getAuto().getModelo(),
                                 vueltaRapida
                             ));
                        }
                    }
                    sb.append("\n"); // Espacio después de cada carrera
                }
            }
        }
        
        if (!encontrado) {
             sb.append(" No se encontraron carreras en el rango de fechas especificado.\n");
             sb.append(" (NOTA: Asegúrese de usar el formato DD/MM/YYYY, ej: 10/03/2025)\n");
        }
        
        return sb.toString(); // Devolvemos el String
    }



/**
 * Busca una Escudería en la lista interna 'this.escuderias' por su nombre.
 * * @param nombre El nombre exacto de la escudería a buscar.
 * @return El objeto Escuderia si se encuentra, o null en caso contrario.
 */
public Escuderia buscarEscuderiaPorNombre(String nombre) {
    // 1. Verificar si la lista está inicializada
    if (this.escuderias == null) {
        return null;
    }
    
    // 2. Recorrer la lista de escuderías
    for (Escuderia e : this.escuderias) {
        // 3. Comparar el nombre de la escudería actual con el nombre buscado
        if (e.getNombre().equalsIgnoreCase(nombre)) { 
            // Usamos equalsIgnoreCase() para que la búsqueda no distinga mayúsculas/minúsculas
            return e; // Retorna la escudería si se encuentra
        }
    }
    
    // 4. Si el bucle termina sin encontrar coincidencias
    return null; 
}

/**
 * Valida que el nuevo rango de fechas de asociación de escudería no se superponga
 * con ninguna asociación existente en el historial del piloto.
 * ASUME que todas las fechas ya están en el formato comparable YYYY/MM/DD (o YYYYMMDD).
 */
public void validarSuperposicionEscuderia(Piloto piloto, String nuevaDesdeFecha, String nuevaHastaFecha) 
    throws IllegalArgumentException {
    
    // Si la fecha de fin es nula/vacía, se usa una cadena futura comparable (ej: "9999/12/31")
    String nuevaHasta = (nuevaHastaFecha == null || nuevaHastaFecha.isEmpty()) ? "9999/12/31" : nuevaHastaFecha;

    // Iterar sobre el historial existente del piloto
    for (PilotoEscuderia existente : piloto.getHistorialEscuderia()) {
        
        // Se usan las fechas directamente del objeto existente
        String existenteDesde = existente.getDesdeFecha();
        String existenteHasta = existente.getHastaFecha();
        
        // Manejar el caso donde la fecha de fin existente es nula/vacía
        String existenteHastaComparable = (existenteHasta == null || existenteHasta.isEmpty()) ? "9999/12/31" : existenteHasta;

        // Lógica de Superposición: [A, B] y [C, D] se solapan si A <= D AND C <= B
        // A = nuevaDesdeFecha, B = nuevaHasta
        // C = existenteDesde, D = existenteHastaComparable
        
        boolean seSolapan = 
            // NuevaDesde (A) <= ExistenteHasta (D)
            (nuevaDesdeFecha.compareTo(existenteHastaComparable) <= 0) && 
            // ExistenteDesde (C) <= NuevaHasta (B)
            (existenteDesde.compareTo(nuevaHasta) <= 0);

        if (seSolapan) {
            throw new IllegalArgumentException(
                "Error de asociación: El nuevo rango (" + nuevaDesdeFecha + " a " + nuevaHastaFecha + 
                ") se superpone con la asociación existente (" + existente.getDesdeFecha() + " a " + existente.getHastaFecha() + 
                ") para el piloto " + piloto.getApellido() + ". Un piloto solo puede estar en una escudería a la vez."
            );
        }
    }
}

 /**
 * Asocia un piloto a una escudería para un período de tiempo, validando que no
 * haya superposición con el historial de escuderías del piloto.
 * * @param dniPiloto DNI del piloto a asociar.
 * @param nombreEscuderia Nombre de la escudería.
 * @param desdeFecha Fecha de inicio de la asociación (ej: "01/01/2025").
 * @param hastaFecha Fecha de fin de la asociación (puede ser nula/vacía/NaN).
 * @throws IllegalArgumentException Si hay superposición de fechas o si las entidades no existen.
 */
public void asociarPilotoAEscuderia(String dniPiloto, String nombreEscuderia, String desdeFecha, String hastaFecha) throws IllegalArgumentException{
    
    // Asumo que tienes helpers para buscar por identificador:
    Piloto piloto = buscarPilotoPorDni(dniPiloto); 
    Escuderia escuderia = buscarEscuderiaPorNombre(nombreEscuderia); 
    
    if (piloto == null) {
        throw new IllegalArgumentException("Error: Piloto con DNI " + dniPiloto + " no encontrado.");
    }
    if (escuderia == null) {
        throw new IllegalArgumentException("Error: Escudería " + nombreEscuderia + " no encontrada.");
    }
    
    // VALIDACIÓN DE REGLA DE NEGOCIO (Superposición de Fechas)
    
    // Se llama al método de validación de fechas crucial
    validarSuperposicionEscuderia(piloto, desdeFecha, hastaFecha);
    
    
    // CREACIÓN Y ESTABLECIMIENTO DE ASOCIACIÓN

    // 1. Crear la nueva instancia de la clase asociativa
    PilotoEscuderia nuevaAsociacion = new PilotoEscuderia(desdeFecha, hastaFecha, piloto, escuderia);
    
    // 2. Establecer la bidireccionalidad: actualizar las listas internas de Piloto y Escudería
    
    // A. Añadir al historial del Piloto
    piloto.agregarPilotoEscuderia(nuevaAsociacion);
    
    // B. Añadir al historial de la Escudería
    escuderia.agregarHistorialPiloto(nuevaAsociacion);
    
    // 3. Opcional: Añadir a la lista central del Gestor (si la tienes, ej: this.historialEscuderia)
    this.historialEscuderia.add(nuevaAsociacion);

    System.out.println("Asociación exitosa: Piloto " + piloto.getApellido() + " vinculado a " + escuderia.getNombre() + " de " + desdeFecha + " a " + hastaFecha + ".");
  }
}