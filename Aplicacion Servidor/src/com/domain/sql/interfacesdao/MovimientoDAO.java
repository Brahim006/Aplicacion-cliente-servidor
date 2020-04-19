/*
 * Interface que determina el comportamiento que debe tener cualquier DAO que
 * acceda a la tabla Movimiento en la base de datos independientemente de la 
 * base de datos que se implemente
 */
package com.domain.sql.interfacesdao;

import java.util.ArrayList;
import com.domain.sql.dto.MovimientoDTO;

/**
 *
 * @author Edu
 */
public interface MovimientoDAO {
    
    /**
     * Inserta una fila en la tabla de Movimientos en la base de datos, el área
     * de origen y la de destino no pueden ser las mismas
     * @param areaOrigen Area en la que se inició el movimiento
     * @param areaDestino Area a la que se envía el trámite
     * @param idTramite ID del trámite al que se le asocia este movimiento
     * @param fecha fecha del movimiento
     * @param motivo Descripción del movimiento
     * @return Un booleano confirmando el éxito de la operación
     */
    public boolean crearMovimiento(String areaOrigen, String areaDestino,
            int idTramite, String fecha, String motivo);
    
    /**
     * Busca en la base de datos todos los movimientos que tengan esa área de 
     * destino
     * @param area Area de destino sobre la que se quiere consultar
     * @return Un ArrayList con DTOs que representan a todos los trámites que
     * cumplen con el criterio de la búsqueda
     */
    public ArrayList<MovimientoDTO> obtenerPorAreaDeDestino(String area);
    
    /**
     * Busca en la base de datos todos los movimientos que tengan esa área de 
     * origen
     * @param area Area de origen sobre la que se quiere consultar
     * @return Un ArrayList con DTOs que representan a todos los trámites que
     * cumplen con el criterio de la búsqueda
     */
    public ArrayList<MovimientoDTO> obtenerPorAreaDeOrigen(String area);
    
    /**
     * Busca en la base de datos todos los movimientos que hayan sido creados en
     * la fecha especificada
     * @param fecha Fecha en formato dd/mm/aaaa sobre la que se quiera consultar
     * @return Un ArrayList con DTOs que representan a todos los trámites que
     * cumplen con el criterio de la búsqueda
     */
    public ArrayList<MovimientoDTO> obtenerPorFecha(String fecha);
    
    /**
     * Consulta todos los movimientos hechos
     * @return Un ArrayList con DTOs que representan todos los movimientos
     */
    public ArrayList<MovimientoDTO> obtenerTodos();
    
    /**
     * Borra todos los movimientos para una fecha específica
     * @param fecha fecha en formato dd/mm/aaaa sobre la que desean borrar los
     * movimientos
     * @return Un booleano confirmando el éxito de la operación
     */
    public boolean borrarPorFecha(String fecha);
    
    public ArrayList<MovimientoDTO> obtenerPorIdTramite(int idTramite);
    
}
