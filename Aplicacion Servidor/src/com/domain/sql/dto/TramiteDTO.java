/*
 * DTO que representa una fila de la tabla Tramite en la base de datos
 */
package com.domain.sql.dto;

/**
 *
 * @author Edu
 */
public class TramiteDTO {
    
    private String asunto, areaCreadora, areaDestino, motivo, estado;
    private int dniInteresado, id;
    private String fechaCreacion, areaAcual;
    
    @Override
    public String toString(){
        
        return asunto + "," + id + "," + areaCreadora + "," +
                areaDestino + "," + areaAcual + "," + fechaCreacion + "," +
                dniInteresado + "," + motivo + "," + estado;
        
    }
    
    //Setters y Getters

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getAreaCreadora() {
        return areaCreadora;
    }

    public void setAreaCreadora(String areaCreadora) {
        this.areaCreadora = areaCreadora;
    }

    public String getAreaDestino() {
        return areaDestino;
    }

    public void setAreaDestino(String areaDestino) {
        this.areaDestino = areaDestino;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getDniInteresado() {
        return dniInteresado;
    }

    public void setDniInteresado(int dniInteresado) {
        this.dniInteresado = dniInteresado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getAreaAcual() {
        return areaAcual;
    }

    public void setAreaAcual(String areaAcual) {
        this.areaAcual = areaAcual;
    }

}
