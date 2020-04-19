/*
 * DTO que representa una fila de la tabla Area en la base de datos
 */
package com.domain.dto;

/**
 *
 * @author Edu
 */
public class AreaDTO {
    
    private String nombre;
    private String fechaCreacion;
    
    @Override
    public String toString(){
        
        return nombre + "," + fechaCreacion;
        
    }
    
    //Setters y Getters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
}
