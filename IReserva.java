/**
 * Interfaz IReserva que define las operaciones para la gestión de reservas y usuarios en el sistema.
 */
public interface IReserva {

    /**
     * Permite a un usuario iniciar sesión en el sistema.
     * @param username El nombre de usuario para iniciar sesión.
     * @param password La contraseña del usuario.
     */
    void login(String username, String password);

    /**
     * Registra un nuevo usuario en el sistema.
     * @param username El nombre de usuario para el nuevo registro.
     * @param password La contraseña para el nuevo usuario.
     * @param tipo El tipo de usuario (base o premium).
     */
    void registroUsuario(String username, String password, String tipo);

    /**
     * Permite a un usuario cambiar su contraseña.
     * @param nuevaPassword La nueva contraseña del usuario.
     */
    void cambiarPassword(String nuevaPassword);

    /**
     * Cambia el tipo de usuario (de base a premium o viceversa).
     */
    void cambiarTipoUsuario();

    /**
     * Crea una nueva reserva con los detalles proporcionados.
     * @param fechaVuelo La fecha del vuelo para la reserva.
     * @param tipoVuelo El tipo de vuelo (true para premium, false para normal).
     * @param cantidadBoletos La cantidad de boletos para la reserva.
     * @param aerolinea La aerolínea con la que se realiza la reserva.
     * @param username El nombre de usuario que realiza la reserva.
     */
    void reservacion(String fechaVuelo, boolean tipoVuelo, int cantidadBoletos, String aerolinea, String username);

    /**
     * Confirma una reserva hecha, procesando el pago y finalizando los detalles de la reserva.
     * @param numeroTarjeta El número de la tarjeta de crédito para el pago.
     * @param cuotas El número de cuotas para el pago.
     * @param claseVuelo La clase del vuelo (económica, negocios, etc.).
     * @param numeroAsiento El número de asiento en el vuelo.
     * @param cantidadMaletas La cantidad de maletas que se llevarán en el vuelo.
     */
    void confirmacion(String numeroTarjeta, int cuotas, String claseVuelo, String numeroAsiento, int cantidadMaletas);

    /**
     * Devuelve un resumen del itinerario de vuelo para el usuario.
     * @return Un string que representa el itinerario del usuario.
     */
    String itinerario();

    /**
     * Guarda los detalles de la reserva en el sistema de persistencia.
     */
    void guardarReservacion();

    /**
     * Recupera los detalles de una reserva del sistema de persistencia.
     */
    void leerReservacion();

    /**
     * Guarda los detalles del usuario en el sistema de persistencia.
     */
    void guardarUsuario();

    /**
     * Recupera los detalles del usuario del sistema de persistencia.
     */
    void leerUsuario();
}

