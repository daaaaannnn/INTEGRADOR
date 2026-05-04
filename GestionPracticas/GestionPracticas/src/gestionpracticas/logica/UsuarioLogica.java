package gestionpracticas.logica;

import gestionpracticas.dao.UsuarioDAO;
import gestionpracticas.modelo.Usuario;

public class UsuarioLogica {
    
    private final UsuarioDAO dao;
    
    public UsuarioLogica() {
        this.dao = new UsuarioDAO();
    }
    
    public Usuario iniciarSesion(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        
        return dao.validarLogin(email, password);
    }
}