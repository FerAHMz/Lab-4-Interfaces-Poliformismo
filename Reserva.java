import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa una reserva en el sistema de reservas de vuelos.
 */
public class Reserva {
    private Date fechaVuelo;
    private boolean tipoVuelo; // true para vuelo premium, false para vuelo normal
    private int cantidadBoletos;
    private String aerolinea;
    private String username;
    private String numeroTarjeta;
    private int cuotas;
    private String claseVuelo;
    private String numeroAsiento;
    private int cantidadMaletas;

    /**
     * Constructor para la clase Reserva.
     *
     * @param fechaVueloStr La fecha y hora del vuelo en formato de texto "dd/MM/yyyy HH:mm".
     * @param tipoVuelo     Si es vuelo premium o normal.
     * @param cantidadBoletos La cantidad de boletos reservados.
     * @param aerolinea     La aerolínea de la reserva.
     * @param username      El nombre de usuario que realiza la reserva.
     */
    public Reserva(String fechaVueloStr, boolean tipoVuelo, int cantidadBoletos, String aerolinea, String username) {
        this.fechaVuelo = parseFecha(fechaVueloStr); // Convierte la fecha de vuelo de String a Date
        this.tipoVuelo = tipoVuelo;
        this.cantidadBoletos = cantidadBoletos;
        this.aerolinea = aerolinea;
        this.username = username;
    }

    // Getters y setters para la clase Reserva

    /**
     * Obtiene la fecha del vuelo.
     * @return La fecha del vuelo.
     */
    public Date getFechaVuelo() {
        return fechaVuelo;
    }

    /**
     * Establece la fecha del vuelo.
     * @param fechaVuelo La fecha del vuelo a establecer.
     */
    public void setFechaVuelo(Date fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    /**
     * Verifica si el vuelo es de tipo premium.
     * @return true si el vuelo es premium, false en caso contrario.
     */
    public boolean isTipoVuelo() {
        return tipoVuelo;
    }

    /**
     * Establece el tipo de vuelo.
     * @param tipoVuelo true si el vuelo es premium, false en caso contrario.
     */
    public void setTipoVuelo(boolean tipoVuelo) {
        this.tipoVuelo = tipoVuelo;
    }

    /**
     * Obtiene la cantidad de boletos.
     * @return La cantidad de boletos.
     */
    public int getCantidadBoletos() {
        return cantidadBoletos;
    }

    /**
     * Establece la cantidad de boletos.
     * @param cantidadBoletos La cantidad de boletos a establecer.
     */
    public void setCantidadBoletos(int cantidadBoletos) {
        this.cantidadBoletos = cantidadBoletos;
    }

    /**
     * Obtiene la aerolínea.
     * @return La aerolínea.
     */
    public String getAerolinea() {
        return aerolinea;
    }

    /**
     * Establece la aerolínea.
     * @param aerolinea La aerolínea a establecer.
     */
    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    /**
     * Obtiene el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     * @param username El nombre de usuario a establecer.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el número de tarjeta.
     * @return El número de tarjeta.
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * Establece el número de tarjeta.
     * @param numeroTarjeta El número de tarjeta a establecer.
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * Obtiene el número de cuotas.
     * @return El número de cuotas.
     */
    public int getCuotas() {
        return cuotas;
    }

    /**
     * Establece el número de cuotas.
     * @param cuotas El número de cuotas a establecer.
     */
    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    /**
     * Obtiene la clase de vuelo.
     * @return La clase de vuelo.
     */
    public String getClaseVuelo() {
        return claseVuelo;
    }

    /**
     * Establece la clase de vuelo.
     * @param claseVuelo La clase de vuelo a establecer.
     */
    public void setClaseVuelo(String claseVuelo) {
        this.claseVuelo = claseVuelo;
    }

    /**
     * Obtiene el número de asiento.
     * @return El número de asiento.
     */
    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    /**
     * Establece el número de asiento.
     * @param numeroAsiento El número de asiento a establecer.
     */
    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    /**
     * Obtiene la cantidad de maletas.
     * @return La cantidad de maletas.
     */
    public int getCantidadMaletas() {
        return cantidadMaletas;
    }

    /**
     * Establece la cantidad de maletas.
     * @param cantidadMaletas La cantidad de maletas a establecer.
     */
    public void setCantidadMaletas(int cantidadMaletas) {
        this.cantidadMaletas = cantidadMaletas;
    }

    // Métodos adicionales

    /**
     * Guarda los detalles de la reserva en algún sistema de persistencia.
     */
    public void guardarReserva() {
        // Implementar la lógica para guardar la reserva en un archivo o base de datos
    }

    /**
     * Lee los detalles de una reserva desde algún sistema de persistencia.
     */
    public void leerReserva() {
        // Implementar la lógica para leer la reserva desde un archivo o base de datos
    }

    /**
     * Convierte la fecha de vuelo en formato String a un objeto Date.
     *
     * @return Objeto Date que representa la fecha y hora del vuelo.
     */
    private Date parseFechaVuelo() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.parse(fechaVuelo);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; 
        }
    }

    /**
     * Método para cancelar una reserva.
     * Este método debería actualizar el estado de la reserva a cancelada y gestionar la lógica necesaria.
     */
    public void cancelarReserva() {
        // Lógica para cancelar la reserva.
    }

    private Date parseFecha(String fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return String.format("Reserva{fechaVuelo='%s', tipoVuelo=%b, cantidadBoletos=%d, aerolinea='%s', username='%s', numeroTarjeta='%s', cuotas=%d, claseVuelo='%s', numeroAsiento='%s', cantidadMaletas=%d}", 
                             sdf.format(fechaVuelo), tipoVuelo, cantidadBoletos, aerolinea, username, numeroTarjeta, cuotas, claseVuelo, numeroAsiento, cantidadMaletas);
    }

}