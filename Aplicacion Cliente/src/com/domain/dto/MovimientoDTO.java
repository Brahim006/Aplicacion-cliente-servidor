/*
 * DTO que representa una fila de la tabla Movimiento en la base de datos
 */
package com.domain.dto;

/**
 *
 * @author Edu
 */
public class MovimientoDTO {
    
    public String areaOrigen, areaDestino, motivo, fecha;
    public int idTramite;
    
    @Override
    public String toString(){
        
        return areaOrigen + "," + areaDestino + "," + idTramite + "," + fecha +
               "," + motivo; 
        
    }
    
    //Setters y Getters

    public String getAreaOrigen() {
        return areaOrigen;
    }

    public void setAreaOrigen(String areaOrigen) {
        this.areaOrigen = areaOrigen;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    
    public int getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }
    
    
    
}
