/*
 * DTO que representa una fila de la tabla Interesado en la base de datos
 */
package com.domain.sql.dto;

/**
 *
 * @author Edu
 */
public class InteresadoDTO {
    
    private String nombre, ocupacion;
    private int dni;
    private char sexo;
    private String fechaNacimiento;
    
    @Override
    public String toString(){
        
        return nombre + "," + dni + "," + sexo + "," + fechaNacimiento + "," +
                ocupacion;
        
    }
    
    //Setters y Getters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    
    
}
