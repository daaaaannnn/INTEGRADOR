package gestionpracticas.logica;

import gestionpracticas.modelo.Usuario;
import com.gestionpracticas.util.Utilidades;
import gestionpracticas.dao.UsuarioDAO;
import java.util.List;

public class UsuarioLogica {

    private final UsuarioDAO dao;

    public UsuarioLogica() {
        this.dao = new UsuarioDAO();
    }

    public boolean registrarUsuario(Usuario u) {
        if (Utilidades.esVacio(u.getNombre()))    throw new IllegalArgumentException("El nombre es obligatorio.");
        if (Utilidades.esVacio(u.getApellido()))  throw new IllegalArgumentException("El apellido es obligatorio.");
        if (!Utilidades.esEmailValido(u.getEmail())) throw new IllegalArgumentException("El email no es válido.");
        if (Utilidades.esVacio(u.getContrasena())) throw new IllegalArgumentException("La contraseña es obligatoria.");
        if (Utilidades.esVacio(u.getTipoUsuario())) throw new IllegalArgumentException("El tipo de usuario es obligatorio.");
        u.setEstado("ACTIVO");
        return dao.insertar(u);
    }

    public Usuario iniciarSesion(String email, String contrasena) {
        if (!Utilidades.esEmailValido(email)) throw new IllegalArgumentException("Email inválido.");
        if (Utilidades.esVacio(contrasena))   throw new IllegalArgumentException("Contraseña requerida.");
        return dao.login(email, contrasena);
    }

    public List<Usuario> obtenerTodos() {
        return dao.listarTodos();
    }

    public Usuario buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean actualizarUsuario(Usuario u) {
        if (Utilidades.esVacio(u.getNombre()))   throw new IllegalArgumentException("El nombre es obligatorio.");
        if (Utilidades.esVacio(u.getApellido())) throw new IllegalArgumentException("El apellido es obligatorio.");
        return dao.actualizar(u);
    }

    public boolean desactivarUsuario(int id) {
        return dao.eliminar(id);
    }
}
