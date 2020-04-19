/*
 * DTO que representa una fila de la tabla User en la base de datos
 */
package com.domain.sql.dto;

/**
 *
 * @author Edu
 */
public class UserDTO {
    
    private String nombre, nickname, area, password;
    private int dni;
    private boolean creador, finalizador;
    private char sexo;
    private String fechaNacimiento;
    
    @Override
    public String toString(){
        
        return nombre + "," + nickname + "," + area + "," + password + "," +
                dni + "," + creador + "," + finalizador + "," + sexo + "," +
                fechaNacimiento;
        
    }
    
    //Setters y Getters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public boolean isCreador() {
        return creador;
    }

    public void setCreador(boolean creador) {
        this.creador = creador;
    }

    public boolean isFinalizador() {
        return finalizador;
    }

    public void setFinalizador(boolean finalizador) {
        this.finalizador = finalizador;
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
