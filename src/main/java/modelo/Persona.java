/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author miguel
 */
public class Persona {
    private int id;
    private String nombres; // Cambiado de 'nom' a 'nombres'
    private String correo;
    private String telefono; // Cambiado de 'tel' a 'telefono'

    // Constructor sin parámetros
    public Persona() {}

    // Constructor para crear una nueva Persona (sin ID)
    public Persona(String nombres, String correo, String telefono) {
        this.nombres = nombres;
        this.correo = correo;
        this.telefono = telefono;
    }

    // Constructor para editar una Persona existente (con ID)
    public Persona(int id, String nombres, String correo, String telefono) {
        this.id = id;
        this.nombres = nombres;
        this.correo = correo;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}