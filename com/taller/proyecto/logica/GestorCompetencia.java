package com.taller.proyecto.logica;

import java.util.*;

/**
 * Clase principal que actúa como el cerebro de la lógica de negocio.
 * Centraliza todas las listas de datos (pilotos, escuderías, etc.) y
 * expone los métodos públicos para registrar nuevas entidades, gestionar
 * carreras y generar todos los informes requeridos por el sistema.
 */
public class GestorCompetencia {
    private List <Escuderia> escuderias;
    private List <Piloto> pilotos;
    private List <Mecanico> mecanicos;
    private List <Auto> autos;
    private List <Carrera> carreras;
    private List <Circuito> circuitos;
    private List <Pais> paises;
    private List <AutoPiloto> historialCarreras; // Esta parece no usarse, 'participaciones' sí
    private List <PilotoEscuderia> historialEscuderia;
    private List <AutoPiloto> participaciones; 

    /**
     * Constructor del Gestor. Inicializa todas las listas internas
     * como ArrayLists vacíos, preparando el sistema para la carga de datos.
     */
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

    // --- GETTERS PÚBLICOS ---
    
    /** @return La lista de Países cargados en el sistema. */
    public List<Pais> getPaises() {
        return paises;
    }
    /** @return La lista de Circuitos cargados en el sistema. */
    public List<Circuito> getCircuitos() {
        return circuitos;
    }
    /** @return La lista de Pilotos cargados en el sistema. */
    public List<Piloto> getPilotos() {
        return pilotos;
    }
    /** @return La lista de Mecánicos cargados en el sistema. */
    public List<Mecanico> getMecanicos() {
        return mecanicos;
    }
    /** @return La lista de Escuderías cargadas en el sistema. */
    public List<Escuderia> getEscuderias() {
        return escuderias;
    }
    /** @return La lista de Autos cargados en el sistema. */
    public List<Auto> getAutos() {
        return autos;
    }
    /** @return La lista de Carreras cargadas y planificadas. */
    public List<Carrera> getCarreras() {
        return carreras;
    }
    /** @return La lista de todas las participaciones (AutoPiloto) en carreras. */
    public List<AutoPiloto> getParticipaciones() {
        return participaciones;
    }

    // --- MÉTODOS DE REGISTRO ---

    /**
     * Registra un nuevo piloto en el sistema.
     * Valida que el DNI no esté duplicado en la lista de pilotos ni en la de mecánicos.
     *
     * @param dni DNI del piloto.
     * @param nombre Nombre del piloto.
     * @param apellido Apellido del piloto.
     * @param numeroCompetencia Número de competencia.
     * @param victorias Historial de victorias.
     * @param polePosition Historial de pole positions.
     * @param vueltasRapidas Historial de vueltas rápidas.
     * @param podios Historial de podios.
     * @param pais Objeto Pais de origen.
     * @throws PersonaRepetidaException Si el DNI ya pertenece a un Piloto o Mecánico.
     */
    public void registrarPiloto(String dni, String nombre, String apellido, int numeroCompetencia, int victorias, int polePosition, int vueltasRapidas, int podios, Pais pais) throws PersonaRepetidaException{
        
        // 1. Validar que el DNI no exista en la lista de MECÁNICOS
        for(Mecanico m : mecanicos) {
            if(m.getDni().equals(dni)) {
                throw new PersonaRepetidaException("Error: El DNI " + dni + " ya está registrado como Mecánico.");
            }
        }
        
        // 2. Validar que el DNI no exista en la lista de PILOTOS
        for(Piloto p : pilotos){
            if(p.getDni().equals(dni)){
                throw new PersonaRepetidaException("Este piloto ya se encuentra en la lista");
            }
        } 

        // 3. Crear el piloto si pasa ambas validaciones
        Piloto piloto = new Piloto(dni, nombre, apellido, numeroCompetencia, victorias, polePosition, vueltasRapidas, podios, pais);
        this.pilotos.add(piloto);
    }

    /**
     * Registra un nuevo auto en el sistema.
     * No valida modelos duplicados, ya que es una condición normal (ej. 2 autos "A524").
     *
     * @param modelo El modelo del auto (ej. "SF-24").
     * @param motor El fabricante del motor (ej. "Ferrari").
     * @throws AutoRepetidoException (Actualmente no se lanza, pero la firma se mantiene).
     */
    public void registrarAuto(String modelo, String motor) throws AutoRepetidoException{
        // Ya no validamos si el modelo está repetido,
        // porque es normal que haya dos autos del mismo modelo (ej: A524)
        Auto auto = new Auto(modelo, motor);
        this.autos.add(auto);
    }

    /**
     * Registra un nuevo mecánico en el sistema.
     * Valida que el DNI no esté duplicado en la lista de mecánicos ni en la de pilotos.
     *
     * @param dni DNI del mecánico.
     * @param nombre Nombre del mecánico.
     * @param apellido Apellido del mecánico.
     * @param pais Objeto Pais de origen.
     * @param especialidad Enum Especialidad del mecánico (MOTOR, CHASIS, etc.).
     * @param añosExperiencia Años de experiencia.
     * @throws PersonaRepetidaException Si el DNI ya pertenece a un Piloto o Mecánico.
     */
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

    /**
     * Registra una nueva escudería en el sistema.
     * Valida que el Nombre y el ID de la escudería sean únicos.
     *
     * @param nombre Nombre de la escudería (ej. "Ferrari").
     * @param pais Objeto Pais de origen.
     * @param idEscuderia ID numérico único.
     * @throws EscuderiaRepetidaException Si el Nombre o el ID ya existen.
     */
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

    /**
     * Registra un nuevo circuito en el sistema.
     * Valida que el nombre del circuito sea único.
     *
     * @param idCircuito ID numérico único.
     * @param nombre Nombre del circuito (ej. "Circuito de Monza").
     * @param longitud Longitud en metros.
     * @param pais Objeto Pais donde se ubica.
     * @param carrera (Opcional, usualmente null en el registro) Carrera asociada.
     * @throws CircuitoRepetidoException Si el nombre del circuito ya existe.
     */
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

    /**
     * Registra un nuevo país en el sistema.
     * Valida que el ID del país sea único.
     *
     * @param idPais ID numérico único.
     * @param descripcion Nombre del país (ej. "Argentina").
     * @throws PaisRepetidoException Si el ID del país ya existe.
     */
    public void registrarPais(int idPais, String descripcion) throws PaisRepetidoException{
        for(Pais p : paises){
            if(p.getIdPais() == idPais){
                throw new PaisRepetidoException("Este País ya se encuentra en la lista. Revisar ID.");
            }
        }
        Pais pais = new Pais(idPais, descripcion);
        this.paises.add(pais);
    }

    // --- MÉTODOS DE GESTIÓN DE CARRERAS ---

    /**
     * Inscribe la participación de un piloto con un auto en una carrera.
     * Valida que el piloto no esté ya inscrito en esa carrera.
     * Valida que haya un auto del modelo solicitado disponible para esa carrera.
     * Cumple con los requisitos del taller.
     *
     * @param piloto Objeto Piloto a inscribir.
     * @param carrera Objeto Carrera en la que participa.
     * @param modelo String del modelo de auto solicitado (ej. "A524").
     * @throws ParticipacionDuplicadaException Si el piloto ya está inscrito en esa carrera.
     * @throws IllegalArgumentException Si no se encuentra un auto de ese modelo o si todos están ocupados.
     */
    public void registrarParticipacion(Piloto piloto, Carrera carrera, String modelo) throws ParticipacionDuplicadaException {

        // 1. VALIDAR PILOTO (Regla PDF: "que un piloto esté asignado a más de un auto...")
        for (AutoPiloto p : participaciones) {
            if (p.getPiloto().equals(piloto) && p.getCarrera().equals(carrera)) {
                throw new ParticipacionDuplicadaException("El Piloto " + piloto.getApellido() + " ya está inscrito en la carrera " + carrera.getCircuito().getNombre());
            }
        }

        // 2. VALIDAR AUTO (Regla PDF: "Que un auto no sea asignado a más de un piloto...")
        List<Auto> autosOcupadosEnEstaCarrera = new ArrayList<>();
        for (AutoPiloto p : participaciones) {
            if (p.getCarrera().equals(carrera) && p.getAuto().getModelo().equals(modelo)) {
                autosOcupadosEnEstaCarrera.add(p.getAuto()); // ...lo añadimos a la lista de ocupados.
            }
        }

        Auto autoEncontrado = null;
        for (Auto auto : this.autos) {
            if (auto.getModelo().equals(modelo) && !autosOcupadosEnEstaCarrera.contains(auto)) {
                autoEncontrado = auto; // ¡Encontramos un auto libre!
                break; // Salimos del bucle
            }
        }

        // 3. PROCESAR RESULTADO DE BÚSQUEDA
        if (autoEncontrado == null) {
            if (autosOcupadosEnEstaCarrera.isEmpty()) {
                 throw new IllegalArgumentException("No se encontró un auto con el modelo especificado: " + modelo);
            } else {
                 throw new IllegalArgumentException("Error: No hay más autos modelo '" + modelo + "' disponibles para esta carrera.");
            }
        }

        // 4. CREAR PARTICIPACIÓN
        AutoPiloto nuevaParticipacion = new AutoPiloto(piloto, autoEncontrado, carrera);
        participaciones.add(nuevaParticipacion);
    }

    /**
     * Planifica una nueva carrera y la añade a la lista de carreras del sistema.
     * Cumple con el requisito del taller.
     *
     * @param fecha Fecha de la carrera (ej. "DD/MM/YYYY").
     * @param hora Hora de la carrera (ej. "HH:MM").
     * @param circuito Objeto Circuito donde se correrá.
     * @return El objeto Carrera recién creado (útil para la GUI).
     */
    public Carrera planificarCarrera(String fecha, String hora, Circuito circuito){
        Carrera nuevaCarrera = new Carrera(fecha, hora, circuito);
        this.carreras.add(nuevaCarrera);
        return nuevaCarrera;
    }

    /**
     * Registra el resultado final de un piloto en una carrera.
     * Busca la participación correspondiente y actualiza sus datos (posición, vuelta rápida)
     * y las estadísticas del piloto (victorias, podios, puntos).
     * Cumple con los requisitos del taller.
     *
     * @param carrera Objeto Carrera del resultado.
     * @param piloto Objeto Piloto del resultado.
     * @param posicion Posición final (1, 2, 3...).
     * @param vueltaRapida true si logró la vuelta rápida, false si no.
     */
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

            // 4. CALCULAR Y SUMAR PUNTOS
            int puntosGanados = PosicionPuntuable.obtenerPuntos(posicion); 
            piloto.sumarPuntos(puntosGanados); 

        } else {
            System.err.println("Error: No se encontró la participación del piloto " 
                + piloto.getApellido() + " en la carrera.");
        }
    }
   
    // --- MÉTODOS DE INFORMES Y CONSULTAS ---

    /**
     * Genera la lista de pilotos ordenada por puntos acumulados (descendente).
     * Cumple con el requisito del taller.
     * @return Una nueva lista (`ArrayList`) de Pilotos ordenada para el ranking.
     */
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
     * Genera un informe en String del histórico (victorias, podios, etc.) de un piloto específico.
     * Cumple con el requisito del taller.
     * @param dni El DNI del piloto a consultar.
     * @return Un String con el informe, o un mensaje de error si no se encuentra.
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
     * Genera un informe en String del histórico (victorias, podios, etc.) de TODOS los pilotos.
     * Cumple con el requisito del taller.
     * @return Un String con el informe general.
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

    /**
     * Genera un informe de autos utilizados por escudería en diferentes carreras.
     * Itera sobre las participaciones para ver qué autos de cada escudería se usaron.
     * Cumple con el requisito del taller.
     * @return Un String con el informe.
     */
    public String generarInformeAutosPorEscuderia() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- INFORME DE AUTOS UTILIZADOS POR ESCUDERÍA ---\n\n");

        for (Escuderia escuderia : this.escuderias) {
            sb.append("[ESCUDERÍA] " + escuderia.getNombre().toUpperCase() + "\n");
            
            List<String> usosUnicos = new ArrayList<>();
            boolean foundUsage = false;

            for (Auto autoPropio : escuderia.getAuto()) {
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

    /**
     * Genera un informe de años de experiencia y especialidad de mecánicos por escudería.
     * Cumple con el requisito del taller.
     * @return Un String con el informe.
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
     * Cumple con el requisito del taller.
     * @param dniPiloto El DNI del piloto a buscar.
     * @param nombreCircuito El nombre del circuito a buscar.
     * @return La cantidad de carreras que cumplen la condición.
     */
    public int contarCarrerasPorPilotoYCircuito(String dniPiloto, String nombreCircuito) {
        int contador = 0;
        
        Piloto pilotoBuscado = buscarPilotoPorDni(dniPiloto); 
        if (pilotoBuscado == null) {
            System.err.println("Error: Piloto con DNI " + dniPiloto + " no encontrado.");
            return 0;
        }

        for (AutoPiloto participacion : this.participaciones) {
            
            Carrera carrera = participacion.getCarrera();
            String circuitoDeLaCarrera = carrera.getCircuito().getNombre();
            
            boolean condicionPiloto = participacion.getPiloto().equals(pilotoBuscado);
            boolean condicionCircuito = circuitoDeLaCarrera.equalsIgnoreCase(nombreCircuito);
            
            if (condicionPiloto && condicionCircuito) {
                contador++;
            }
        }

        return contador;
    }

    /**
     * Calcula la cantidad total de carreras que se han planificado o corrido
     * en un circuito determinado.
     * Cumple con el requisito del taller.
     * @param nombreCircuito El nombre del circuito a buscar.
     * @return La cantidad de carreras asociadas a ese circuito.
     */
    public int contarCarrerasPorCircuito(String nombreCircuito) {
        int contador = 0;
        
        for (Carrera carrera : this.carreras) {
            Circuito circuitoDeLaCarrera = carrera.getCircuito();
            
            if (circuitoDeLaCarrera != null && circuitoDeLaCarrera.getNombre().equalsIgnoreCase(nombreCircuito)) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Genera un informe detallado de los resultados de las carreras
     * que ocurrieron dentro de un rango de fechas específico.
     * Cumple con el requisito del taller.
     *
     * @param fechaInicio La fecha de inicio del rango (ej. "01/01/2025").
     * @param fechaFin La fecha de fin del rango (ej. "31/12/2025").
     * @return Un String con el informe formateado.
     */
    public String generarInformeResultadosPorFecha(String fechaInicio, String fechaFin) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- INFORME DE RESULTADOS POR RANGO DE FECHAS ---\n");
        sb.append(" (Rango: " + fechaInicio + " a " + fechaFin + ")\n\n");
        
        boolean encontrado = false;

        for (Carrera carrera : this.carreras) {
            
            String fechaCarrera = carrera.getFechaRealizacion();
            
            // Se asume una comparación de Strings.
            if (fechaCarrera.compareTo(fechaInicio) >= 0 && fechaCarrera.compareTo(fechaFin) <= 0) {
                
                encontrado = true;
                sb.append("--- CARRERA: " + carrera.getCircuito().getNombre().toUpperCase() + " ---\n");
                sb.append("FECHA: " + fechaCarrera + " | VUELTAS: " + carrera.getNumeroVueltas() + "\n");
                sb.append("----------------------------------------\n");
                
                List<AutoPiloto> resultadosCarrera = new ArrayList<>();
                for (AutoPiloto participacion : this.participaciones) {
                    if (participacion.getCarrera().equals(carrera)) {
                        resultadosCarrera.add(participacion);
                    }
                }
                
                resultadosCarrera.sort(Comparator.comparingInt(AutoPiloto::getPosicionFinal));
                
                if (resultadosCarrera.isEmpty()) {
                     sb.append("  * No hay pilotos inscritos en esta carrera.\n\n");
                
                } else if (resultadosCarrera.get(0).getPosicionFinal() == 0) {
                     sb.append("  * Resultados aún no registrados para esta carrera.\n");
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
                    sb.append("\n"); 
                }
            }
        }
        
        if (!encontrado) {
             sb.append(" No se encontraron carreras en el rango de fechas especificado.\n");
             sb.append(" (NOTA: Asegúrese de usar el formato DD/MM/YYYY, ej: 10/03/2025)\n");
        }
        
        return sb.toString(); 
    }

    /**
     * Busca una Escudería en la lista interna 'this.escuderias' por su nombre.
     * @param nombre El nombre exacto de la escudería a buscar.
     * @return El objeto Escuderia si se encuentra, o null en caso contrario.
     */
    public Escuderia buscarEscuderiaPorNombre(String nombre) {
        if (this.escuderias == null) {
            return null;
        }
        
        for (Escuderia e : this.escuderias) {
            if (e.getNombre().equalsIgnoreCase(nombre)) { 
                return e; 
            }
        }
        
        return null; 
    }

    /**
     * Valida que el nuevo rango de fechas de asociación de escudería no se superponga
     * con ninguna asociación existente en el historial del piloto.
     * Cumple con el requisito del taller.
     * @param piloto El Piloto a validar.
     * @param nuevaDesdeFecha La nueva fecha de inicio.
     * @param nuevaHastaFecha La nueva fecha de fin (puede ser vacía o null).
     * @throws IllegalArgumentException Si se detecta una superposición de fechas.
     */
    public void validarSuperposicionEscuderia(Piloto piloto, String nuevaDesdeFecha, String nuevaHastaFecha) 
        throws IllegalArgumentException {
        
        String nuevaHasta = (nuevaHastaFecha == null || nuevaHastaFecha.isEmpty()) ? "9999/12/31" : nuevaHastaFecha;

        // Itera sobre el historial existente del piloto (asegurándose de que no sea nulo)
        if (piloto.getHistorialEscuderia() != null) {
            for (PilotoEscuderia existente : piloto.getHistorialEscuderia()) {
                
                String existenteDesde = existente.getDesdeFecha();
                String existenteHasta = existente.getHastaFecha();
                
                String existenteHastaComparable = (existenteHasta == null || existenteHasta.isEmpty()) ? "9999/12/31" : existenteHasta;

                // Lógica de Superposición: [A, B] y [C, D] se solapan si A <= D AND C <= B
                boolean seSolapan = 
                    (nuevaDesdeFecha.compareTo(existenteHastaComparable) <= 0) && 
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
    }

    /**
     * Asocia un piloto a una escudería para un período de tiempo, validando que no
     * haya superposición con el historial de escuderías del piloto.
     * @param dniPiloto DNI del piloto a asociar.
     * @param nombreEscuderia Nombre de la escudería.
     * @param desdeFecha Fecha de inicio de la asociación (ej: "01/01/2025").
     * @param hastaFecha Fecha de fin de la asociación (puede ser vacía).
     * @throws IllegalArgumentException Si hay superposición de fechas o si las entidades no existen.
     */
    public void asociarPilotoAEscuderia(String dniPiloto, String nombreEscuderia, String desdeFecha, String hastaFecha) throws IllegalArgumentException{
        
        Piloto piloto = buscarPilotoPorDni(dniPiloto); 
        Escuderia escuderia = buscarEscuderiaPorNombre(nombreEscuderia); 
        
        if (piloto == null) {
            throw new IllegalArgumentException("Error: Piloto con DNI " + dniPiloto + " no encontrado.");
        }
        if (escuderia == null) {
            throw new IllegalArgumentException("Error: Escudería " + nombreEscuderia + " no encontrada.");
        }
        
        // VALIDACIÓN DE REGLA DE NEGOCIO (Superposición de Fechas)
        validarSuperposicionEscuderia(piloto, desdeFecha, hastaFecha);
        
        // CREACIÓN Y ESTABLECIMIENTO DE ASOCIACIÓN
        PilotoEscuderia nuevaAsociacion = new PilotoEscuderia(desdeFecha, hastaFecha, piloto, escuderia);
        
        piloto.agregarPilotoEscuderia(nuevaAsociacion);
        escuderia.agregarHistorialPiloto(nuevaAsociacion);
        this.historialEscuderia.add(nuevaAsociacion);

        System.out.println("Asociación exitosa: Piloto " + piloto.getApellido() + " vinculado a " + escuderia.getNombre() + " de " + desdeFecha + " a " + hastaFecha + ".");
    }
}