import java.io.*;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


/**
 * Clase controladora Kayak que implementa la interfaz IReserva para gestionar las reservas y usuarios.
 */
public class Kayak implements IReserva {

    private List<Usuario> usuarios; // Lista para mantener el estado de todos los usuarios
    private List<Reserva> reservas; // Lista para mantener el estado de todas las reservas
    private Usuario usuarioActual; // Usuario actualmente autenticado
    private final String archivoUsuarios = "usuarios.csv";
    private final String archivoReservas = "reservas.csv";

    /**
     * Constructor para la clase Kayak que inicializa los archivos necesarios si no existen
     * y carga los datos de usuarios y reservas de los archivos CSV.
     * @throws IOException Si hay un error de entrada/salida al acceder a los archivos.
     */
    public Kayak() throws IOException {
        usuarios = cargarUsuarios(archivoUsuarios);
        reservas = cargarReservas(archivoReservas);
    }

    /**
     * Carga la lista de usuarios desde el archivo CSV.
     * @throws IOException Si ocurre un error de E/S al leer el archivo.
     */
    private void cargarUsuarios() throws IOException {
        List<Usuario> usuariosCargados = new ArrayList<>();
        Path path = Paths.get(archivoUsuarios);
        if (Files.exists(path)) {
            try (BufferedReader br = Files.newBufferedReader(path)) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 3) { // Asegúrese de que haya al menos 3 columnas
                        Usuario usuario = new Usuario(datos[0], datos[1], datos[2].equalsIgnoreCase("premium"));
                        usuariosCargados.add(usuario);
                    }
                }
                this.usuarios = usuariosCargados;
                System.out.println("Usuarios cargados exitosamente desde " + archivoUsuarios);
            }
        } else {
            throw new FileNotFoundException("El archivo " + archivoUsuarios + " no se encontró.");
        }
    }

    // Implementación de los métodos de la interfaz IReserva

    @Override
    public void login(String username, String password) {
        // Lógica para autenticar al usuario y asignarlo a usuarioActual
        Optional<Usuario> usuario = usuarios.stream()
                                            .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                                            .findFirst();
        if (usuario.isPresent()) {
            usuarioActual = usuario.get();
            System.out.println("Inicio de sesión exitoso para el usuario: " + username);
        } else {
            System.out.println("Inicio de sesión fallido.");
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @param username El nombre de usuario para el nuevo registro.
     * @param password La contraseña para el nuevo usuario.
     * @param tipo El tipo de usuario (base o premium).
     */
    @Override
    public void registroUsuario(String username, String password, String tipo) {
        // Verificar si el nombre de usuario ya existe en la lista de usuarios
        boolean usuarioExiste = usuarios.stream()
                                        .anyMatch(u -> u.getUsername().equals(username));
        if (usuarioExiste) {
            // Si el usuario ya existe, lanzar una excepción o manejarlo según sea necesario
            System.out.println("El nombre de usuario ya está en uso. Por favor, elige otro.");
            return;
        }

        // Si el tipo no es ni "base" ni "premium", manejar este caso también
        if (!tipo.equalsIgnoreCase("base") && !tipo.equalsIgnoreCase("premium")) {
            System.out.println("Tipo de usuario no válido. Debe ser 'base' o 'premium'.");
            return;
        }

        // Crear un nuevo usuario y agregarlo a la lista de usuarios
        boolean esPremium = tipo.equalsIgnoreCase("premium");
        Usuario nuevoUsuario = new Usuario(username, password, esPremium);
        usuarios.add(nuevoUsuario);

        // Guardar la lista de usuarios en el archivo CSV
        try {
            guardarUsuarios(); 
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el usuario en el archivo.");
        }

        System.out.println("Usuario registrado exitosamente: " + username);
    }

    /**
     * Cambia la contraseña del usuario actual.
     * @param nuevaPassword La nueva contraseña del usuario.
     */
    @Override
    public void cambiarPassword(String nuevaPassword) {
        // Verificar si hay un usuario actualmente autenticado
        if (usuarioActual == null) {
            System.out.println("No hay ningún usuario autenticado en este momento.");
            return;
        }

        // Actualizar la contraseña del usuario actual
        usuarioActual.setPassword(nuevaPassword);

        // Encontrar el usuario en la lista de usuarios y actualizar su información
        usuarios.stream()
                .filter(u -> u.getUsername().equals(usuarioActual.getUsername()))
                .findFirst()
                .ifPresent(u -> u.setPassword(nuevaPassword));

        // Opcional: Guardar la lista actualizada de usuarios en el archivo CSV
        try {
            guardarUsuarios(); // Este método necesita ser implementado para guardar la lista de usuarios en un archivo CSV
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar la nueva contraseña en el archivo.");
        }

        System.out.println("Contraseña actualizada exitosamente para el usuario: " + usuarioActual.getUsername());
    }

    /**
     * Cambia el tipo de usuario de base a premium y viceversa.
     */
    @Override
    public void cambiarTipoUsuario() {
        // Verifica si hay un usuario actualmente autenticado
        if (usuarioActual == null) {
            System.out.println("No hay ningún usuario autenticado en este momento.");
            return;
        }

        // Cambiar el tipo de usuario de base a premium y viceversa
        usuarioActual.setTipo(usuarioActual.getTipo().equals("base") ? "premium" : "base");

        // Actualizar la información del usuario en la lista y el archivo CSV
        actualizarUsuario(usuarioActual);
        System.out.println("Tipo de usuario cambiado exitosamente para el usuario: " + usuarioActual.getUsername());
    }

    /**
     * Actualiza la información de un usuario en el archivo CSV.
     * @param usuarioActualizado El usuario cuya información se debe actualizar.
     * @throws IOException Si ocurre un error de E/S al escribir en el archivo.
     */
    private void actualizarUsuario(Usuario usuarioActualizado) throws IOException {
        // Crea una lista temporal para almacenar los usuarios actualizados
        List<Usuario> usuariosActualizados = new ArrayList<>();

        // Lee el archivo existente y construye la lista actualizada
        Path path = Paths.get(archivoUsuarios);
        if (Files.exists(path)) {
            try (BufferedReader br = Files.newBufferedReader(path)) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    // Crea un nuevo usuario basado en la línea leída
                    Usuario usuario = new Usuario(datos[0], datos[1], datos[2].equalsIgnoreCase("premium"));
                    // Si el usuario leído coincide con el usuario actualizado, usa el usuario actualizado
                    if (usuario.getUsername().equals(usuarioActualizado.getUsername())) {
                        usuario = usuarioActualizado;
                    }
                    usuariosActualizados.add(usuario);
                }
            }
        } else {
            throw new FileNotFoundException("El archivo " + archivoUsuarios + " no se encontró.");
        }

        // Escribe la lista actualizada de usuarios de vuelta en el archivo
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Usuario usuario : usuariosActualizados) {
                String linea = usuario.getUsername() + "," + usuario.getPassword() + "," + (usuario.isPremium() ? "premium" : "base");
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Información del usuario actualizada con éxito en " + archivoUsuarios);
        }
    }

    /**
     * Guarda la lista actual de usuarios en el archivo CSV.
     * @throws IOException Si ocurre un error de E/S al escribir en el archivo.
     */
    private void guardarUsuarios() throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(archivoUsuarios))) {
            for (Usuario usuario : usuarios) {
                String linea = usuario.getUsername() + "," + usuario.getPassword() + "," + (usuario.isPremium() ? "premium" : "base");
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Usuarios guardados exitosamente en " + archivoUsuarios);
        }
    }

    /**
     * Crea una nueva reserva.
     * @param fechaVuelo La fecha del vuelo para la reserva.
     * @param tipoVuelo El tipo de vuelo (true para premium, false para normal).
     * @param cantidadBoletos La cantidad de boletos para la reserva.
     * @param aerolinea La aerolínea con la que se realiza la reserva.
     * @param username El nombre de usuario que realiza la reserva.
     */
    @Override
    public void reservacion(String fechaVuelo, boolean tipoVuelo, int cantidadBoletos, String aerolinea, String username) {
        // Crear una nueva reserva con los detalles proporcionados
        Reserva nuevaReserva = new Reserva(fechaVuelo, tipoVuelo, cantidadBoletos, aerolinea, username);

        // Añadir la reserva a la lista de reservas
        reservas.add(nuevaReserva);

        // Guardar la nueva lista de reservas en el archivo CSV
        guardarReservas();
        System.out.println("Reserva creada exitosamente para el usuario: " + username);
    }

    /**
     * Devuelve un resumen del itinerario de vuelo para el usuario actual.
     * @return Un string que representa el itinerario del usuario.
     */
    @Override
    public String itinerario() {
        // Recuperar el itinerario del usuario actual basado en sus reservas
        return usuarioActual != null ? generarItinerario(usuarioActual) : "No hay un usuario autenticado.";
    }

    /**
     * Guarda los detalles de todas las reservas en el sistema de persistencia.
     */
    @Override
    public void guardarReservacion() {
        // Guardar la lista de reservas en el archivo CSV
        guardarReservas();
    }

    /**
     * Recupera los detalles de una reserva del sistema de persistencia.
     */
    @Override
    public void leerReservacion() {
        // Leer las reservas del archivo CSV y actualizar la lista de reservas
        reservas = cargarReservas("reservas.csv");
    }

    /**
     * Guarda los detalles de todos los usuarios en el sistema de persistencia.
     */
    @Override
    public void guardarUsuario() {
        // Guardar la lista de usuarios en el archivo CSV
        guardarUsuarios();
    }

    /**
     * Recupera los detalles del usuario del sistema de persistencia.
     */
    @Override
    public void leerUsuario() {
        // Leer los usuarios del archivo CSV y actualizar la lista de usuarios
        usuarios = cargarUsuarios("usuarios.csv");
    }

    /**
     * Carga los datos de los usuarios y reservas de los archivos CSV.
     */
    private void cargarDatos() {
        cargarUsuarios();
        cargarReservas();
    }

    /**
     * Carga los usuarios desde el archivo CSV.
     */

    /**
     * Carga las reservas desde el archivo CSV.
     */
    private void cargarReservas() {
        Path path = Paths.get(archivoReservas);
        if (Files.exists(path)) {
            try (BufferedReader br = Files.newBufferedReader(path)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(",");
                    Reserva reserva = new Reserva(datos[1], Boolean.parseBoolean(datos[2]), Integer.parseInt(datos[3]), datos[4], datos[0]);
                    reservas.add(reserva);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Guarda las reservas en el archivo CSV.
     */
    private void guardarReservas() {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(archivoReservas))) {
            for (Reserva reserva : reservas) {
                bw.write(reserva.getUsername() + "," + reserva.getFechaVuelo() + "," + reserva.isTipoVuelo() + "," +
                        reserva.getCantidadBoletos() + "," + reserva.getAerolinea());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Carga los usuarios desde un archivo CSV.
     * @param archivo El nombre del archivo CSV a cargar.
     * @return Una lista de usuarios.
     * @throws IOException Si hay un error de entrada/salida al leer el archivo.
     */
    private List<Usuario> cargarUsuarios(String archivo) throws IOException {
        List<Usuario> usuariosCargados = new ArrayList<>();
        Path path = Paths.get(archivo);

        // Verifica si el archivo existe antes de intentar leerlo
        if (Files.exists(path)) {
            try (BufferedReader br = Files.newBufferedReader(path)) {
                String linea;

                // Omitir la línea del encabezado 
                br.readLine();

                // Leer el archivo línea por línea
                while ((linea = br.readLine()) != null) {
                    String[] datosUsuario = linea.split(",");

                    // Asegurarse de que la línea tenga la cantidad correcta de campos
                    if (datosUsuario.length == 2) {
                        String username = datosUsuario[0];
                        String password = datosUsuario[1];

                        // Crear un nuevo objeto de usuario y añadirlo a la lista
                        Usuario usuario = new Usuario(username, password);
                        usuariosCargados.add(usuario);
                    }
                }
            }
        } else {
            throw new FileNotFoundException("El archivo " + archivo + " no se encontró.");
        }

        return usuariosCargados;
    }

    /**
     * Carga las reservas desde un archivo CSV.
     * @param archivo El nombre del archivo CSV a cargar.
     * @return Una lista de reservas.
     * @throws IOException Si hay un error de entrada/salida al leer el archivo.
     */
    private List<Reserva> cargarReservas(String archivo) throws IOException {
        List<Reserva> reservasCargadas = new ArrayList<>();
        Path path = Paths.get(archivo);

        // Verifica si el archivo existe antes de intentar leerlo
        if (Files.exists(path)) {
            try (BufferedReader br = Files.newBufferedReader(path)) {
                String linea;

                // Omitir la línea del encabezado si existe
                br.readLine();

                // Leer el archivo línea por línea
                while ((linea = br.readLine()) != null) {
                    String[] datosReserva = linea.split(",");

                    // Asegurarse de que la línea tenga la cantidad correcta de campos
                    if (datosReserva.length >= 5) { // Ajustar según la cantidad de campos en tu archivo CSV
                        String fechaVuelo = datosReserva[0];
                        boolean tipoVuelo = Boolean.parseBoolean(datosReserva[1]);
                        int cantidadBoletos = Integer.parseInt(datosReserva[2]);
                        String aerolinea = datosReserva[3];
                        String username = datosReserva[4];
                        // Asumir que hay más campos si es necesario y convertirlos adecuadamente

                        // Crear un nuevo objeto de reserva y añadirlo a la lista
                        Reserva reserva = new Reserva(fechaVuelo, tipoVuelo, cantidadBoletos, aerolinea, username);
                        reservasCargadas.add(reserva);
                    }
                }
            }
        } else {
            throw new FileNotFoundException("El archivo " + archivo + " no se encontró.");
        }

        return reservasCargadas;
    }

    private String generarItinerario(Usuario usuario) {
        // Genera un resumen del itinerario basado en las reservas del usuario
        StringBuilder itinerario = new StringBuilder("Itinerario para " + usuario.getUsername() + ":\n");
        reservas.stream()
                .filter(reserva -> reserva.getUsername().equals(usuario.getUsername()))
                .forEach(reserva -> itinerario.append(reserva.toString()).append("\n"));
        return itinerario.toString();
    }

}
