/**
 * Clase que representa un usuario en el sistema de reservas de vuelos.
 */
public class Usuario {
    private String username; // Identificador único del usuario
    private String password; // Contraseña del usuario
    private boolean isPremium; // Indicador de si el usuario es premium o no

    /**
     * Constructor para la clase Usuario.
     *
     * @param username El identificador único del usuario para operaciones de login.
     * @param password La contraseña para la autenticación del usuario.
     * @param isPremium Indica si el usuario tiene un plan premium.
     */
    public Usuario(String username, String password, boolean isPremium) {
        this.username = username;
        this.password = password;
        this.isPremium = isPremium;
    }

    // Getters y setters

    /**
     * Devuelve el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece un nuevo nombre de usuario.
     *
     * @param username El nuevo nombre de usuario a establecer.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Devuelve la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece una nueva contraseña para el usuario.
     *
     * @param password La nueva contraseña a establecer.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Comprueba si el usuario es premium.
     *
     * @return true si el usuario es premium, false en caso contrario.
     */
    public boolean isPremium() {
        return isPremium;
    }

    /**
     * Establece el estado premium del usuario.
     *
     * @param isPremium true si el usuario es premium, false en caso contrario.
     */
    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    /**
     * Método para verificar las credenciales del usuario.
     *
     * @param inputUsername El nombre de usuario ingresado.
     * @param inputPassword La contraseña ingresada.
     * @return true si las credenciales coinciden, false en caso contrario.
     */
    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    /**
     * Método para cerrar sesión del usuario.
     * Puede realizar operaciones necesarias para cerrar la sesión, como limpiar estados o cachés.
     */
    public void logout() {
        // Lógica para manejar el cierre de sesión del usuario
        // Por ejemplo, puedes resetear atributos o realizar operaciones de limpieza
        System.out.println("Usuario " + username + " ha cerrado sesión.");
    }

    /**
     * Método para representar los detalles del usuario como una cadena de texto.
     * 
     * @return Una representación en cadena de texto del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
               "username='" + username + '\'' +
               ", isPremium=" + isPremium +
               '}';
    }
}
