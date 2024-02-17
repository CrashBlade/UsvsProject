package org.example;

public class CorreoConContraseña {
    private String correo;
    private String contraseña;

    public CorreoConContraseña(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }
}
