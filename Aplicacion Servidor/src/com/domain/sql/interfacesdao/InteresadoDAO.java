/*
 * Interface que determina el comportamiento que debe tener cualquier DAO que
 * acceda a la tabla Interesado en la base de datos independientemente de la
 * base de datos que se implemente
 */
package com.domain.sql.interfacesdao;

import com.domain.sql.dto.InteresadoDTO;
import java.util.ArrayList;

/**
 *
 * @author Edu
 */
public interface InteresadoDAO {
    
    /**
     * Crea una nueva fila en la tabla Interesado de la base de datos
     * @param nombre Nombre completo del interesado
     * @param dni DNI único del interesado (clave primaria)
     * @param sexo Sexo del interesado, M ó F
     * @param ocupacion Profesión del interesado
     * @param fechanacimiento Fecha de nacimiento en formato dd/mm/aaaa
     * @return Un booleano informando el éxito de la operación
     */
    public boolean crearInteresado(String nombre, int dni, char sexo,
                                    String ocupacion, String fechanacimiento);
    
    /**
     * Borra la fila de la tabla Interesado en la base de datos a la que le 
     * corresponda la llave primaria dni
     * @param dni dni DNI único del interesado (clave primaria)
     * @return Un booleano informando el éxito de la operación
     */
    public boolean borrarInteresado(int dni);
    
    /**
     * Busca un interesado en la base de datos de acuerdo a su DNI
     * @param dni dni DNI único del interesado (clave primaria)
     * @return Un objeto InteresadoDTO representando la fila de la tabla en la
     * base de datos al que le corresponda la clave primaria dni
     */
    public InteresadoDTO obtenerInteresadoPorDNI(int dni);
    
    /**
     * Retorna todos los interesados registrados en la base de datos
     * @return Un ArrayList con todos los interesados registrados en la base de
     * datos encapsolados en objetos InteresadoDTO
     */
    public ArrayList<InteresadoDTO> obtenerTodos();
    
}
