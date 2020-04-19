/*
 * Interface que determina el comportamiento que debe tener cualquier DAO que
 * acceda a la tabla Area en la base de datos independientemente de la base de
 * datos que se implemente
 */
package com.domain.sql.interfacesdao;

import java.util.ArrayList;
import com.domain.sql.dto.AreaDTO;

/**
 *
 * @author Edu
 */
public interface AreaDAO {
    
    /**
     * Crea una fila en la tabla Area con los argumentos especificados
     * @param nombre  nombre del área a crear
     * @param fechacreacion  fecha del área a crear
     * @return retorna un booleano confirmando si la operación fue exitosa
     */
    public boolean crearArea(String nombre, String fechacreacion);
    
    /**
     * Borra la fila dentro de la tabla Área en la base de datos cuya columna
     * nombre sea la especificada
     * @param nombre nombre del área a borrar
     * @return retorna un booleano confirmando si la operación fue exitosa
     */
    public boolean borrarArea(String nombre);
    
    /**
     * Busca la fila cuya columna 'nombre' coincide con la búsqueda
     * @param nombre nombre del área que se desea obtener
     * @return Un objeto AreaDTO cuyos atribustos coinciden con la fila corres
     * pondiente
     */
    public AreaDTO buscarAreaPorNombre(String nombre);
    
    /**
     * Devuelve todas las áreas dentro de un ArrayList
     * @return Un ArrayList que contiene los DTOs correspondientes a cada fila 
     * de la tabla Area
     */
    public ArrayList<AreaDTO> obtenerTodas();
    
}
