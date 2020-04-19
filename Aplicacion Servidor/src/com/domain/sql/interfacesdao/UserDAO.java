/*
 * Interface que determina el comportamiento que debe tener cualquier DAO que
 * acceda a la tabla User en la base de datos independientemente de la base de
 * datos que se implemente
 */
package com.domain.sql.interfacesdao;

import com.domain.sql.dto.UserDTO;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Edu
 */
public interface UserDAO {
    
    /**
     * Debe retornar el usuario por su nickname el cual es llave primaria dentro
     * de la base de datos.
     * @param nickName
     * @return 
     */
    public UserDTO consultarUsuarioPorNickName(String nickName);
    
    /**
     * Crea un usuario recibiendo sin excepciones todos los datos necesarios 
     * para llenar una fila de la tabla User, respentando a la llave primaria
     * nickname que debe ser {unica dentro de la tabla y el área que es llave
     * foránea de la tabla Area, por lo que debe existir en ésta.
     * @param nombre
     * @param nickName
     * @param area
     * @param password
     * @param dni
     * @param creador
     * @param finalizador
     * @param sexo
     * @param fechaNacimiento
     * @return 
     */
    public boolean crearUsuario(String nombre, String nickName, String area,
                String password, int dni, boolean creador, boolean finalizador,
                char sexo, String fechaNacimiento);
    
    /**
     * Borra un usuario de la tabla User buscándolo a travez de la llave primaria
     * nickname de valor único en la tabla, devuelve un booleano para informar
     * si la oparación fue exitosa o no.
     * @param nickName
     * @return 
     */
    public boolean borrarUsuario(String nickName);
    
    /**
     * Modifica ambos permisos de creador y finalizador especificando el 
     * nickname del usuario afectado, devuelve un booleano para informar si la
     * oparación fue exitosa o no.
     * @param nickname
     * @param creador
     * @param finalizador
     * @return 
     */
    public boolean modificarPermisos(String nickname, boolean creador, 
                                     boolean finalizador);
    
    /**
     * Retorna una colección de todos los usuarios pertenecientes a una área
     * específica
     * @param area
     * @return 
     */
    public ArrayList<UserDTO> consultarPorArea(String area);
    
    /**
     * Retorna una colección con todos los usuarios registrados.
     * @return 
     */
    public ArrayList<UserDTO> consultarTodos();
    
}
