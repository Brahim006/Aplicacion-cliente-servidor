/*
 * Interface que determina el comportamiento que debe tener cualquier DAO que
 * acceda a la tabla Tramites en la base de datos independientemente de la base 
 * de datos que se implemente
 */
package com.domain.sql.interfacesdao;

import com.domain.sql.dto.TramiteDTO;
import java.util.ArrayList;

/**
 *
 * @author Edu
 */
public interface TramiteDAO {
    
    /**
     * Crea un trámite y lo inserta en la tabla Tramite de la base de datos
     * @param asunto Nombre del trámite
     * @param id Clave primaria para identificar el trámite
     * @param areaDestino Área a la que se pretende que llegue el trámite
     * @param areaCreadora Área en la que se expedió el trámite
     * @param dniInteresado DNI del interesado
     * @param motivo Breve descripción del trámte
     * @param fechaCreacion Fecha de creación del trámite en formato dd/mm/aaaa
     * @param estado Determina el estado según las reglas de negocio
     * @return Un booleando confirmando el éxito de la operación
     */
    public boolean crearTramite(String asunto, int id, String areaDestino,
            String areaCreadora, int dniInteresado, String motivo,
            String fechaCreacion, String estado, String areaActual);
    
    /**
     * Borra la fila de la tabla Tramite que coincida con el id
     * @param id Id del trámite a borrar de la base de datos
     * @return Un booleando confirmando el éxito de la operación
     */
    public boolean borrarTramite(int id);
    
    /**
     * Obtener de la base de datos un trámite según su id
     * @param id Id del trámite a retornar
     * @return Un objeto TramiteDTO representando el trámite buscado
     */
    public TramiteDTO obtenerTramitePorId(int id);
    
    /**
     * Obtener de la base de datos un trámite según el DNI del interesado
     * @param dni DNI del interesado al que se le asocia el trámite
     * @return Un objeto TramiteDTO representando el trámite buscado
     */
    public TramiteDTO obtenerTramitePorInteresado(int dni);
    
    /**
     * Busca en la base de datos todos los trámites según su área de destino
     * @param area Area de destino sobre la que se quiere consultar todos los
     * trámites
     * @return Un ArrayList que contiene objetos TramiteDTO que cumplen con
     * los parámetros de la búsqueda
     */
    public ArrayList<TramiteDTO> obtenerTramitesPorAreaDestino(String area);
    
    /**
     * Busca en la base de datos todos los trámites según su área de origen
     * @param area Area de origen sobre la que se quiere consultar todos los
     * trámites
     * @return Un ArrayList que contiene objetos TramiteDTO que cumplen con
     * los parámetros de la búsqueda
     */
    public ArrayList<TramiteDTO> obtenerTramitesPorAreaCreadora(String area);
    
    /**
     * Busca en la base de datos todos los trámites según su área actual
     * @param area Area actual sobre la que se quiere consultar todos los
     * trámites
     * @return Un ArrayList que contiene objetos TramiteDTO que cumplen con
     * los parámetros de la búsqueda
     */
    public ArrayList<TramiteDTO> obtenerTramitesPorAreaActual(String area);
    
    /**
     * Busca en la base de datos todos los trámites según su fecha de creación
     * @param fecha La fecha en formato dd/mm/aaaa sobre la que se quiere 
     * consultar todos los trámites
     * @return Un ArrayList que contiene objetos TramiteDTO que cumplen con
     * los parámetros de la búsqueda
     */
    public ArrayList<TramiteDTO> obtenerTramitesPorFecha(String fecha);
    
    /**
     * Modifica el estado de un trámite en la base de datos
     * @param id Id del trámite al cual se le quiere modificar el estado
     * @param estado Estado nuevo según las reglas de negocio
     * @return Un booleando confirmando el éxito de la operación
     */
    public boolean modificarEstado(int id, String estado);
    
    /**
     * Modifica el área actual de un trámite según su id
     * @param id Id del trámite al que se le quiere modificar el area actual
     * @param area Area que se quiere declarar como actual
     * @return Un booleando confirmando el éxito de la operación
     */
    public boolean modificarAreaActual(int id, String area);
    
}
