/*
 * DAO que se encarga de resolver los métodos exigidos por la interface UserDAO
 * implementando la sintaxis para una base de datos de tipo HSQLDB
 */
package com.domain.sql.daohsqldblimple;

import com.domain.sql.dto.UserDTO;
import com.domain.sql.interfacesdao.UserDAO;
import com.domain.sql.ConnectionPool;
import com.domain.utils.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Edu
 */
public class UserDAOHsqldbImple implements UserDAO{

    @Override
    public UserDTO consultarUsuarioPorNickName(String nickName) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {

            String sql = "SELECT * FROM user " +
                    "WHERE nickname = '" + nickName + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            rs.next();
            
            // preparo el DTO a retornar
            
            UserDTO dto = new UserDTO();
            
            dto.setNombre(rs.getString("nombre"));
            dto.setDni(rs.getInt("dni"));
            dto.setNickname(rs.getString("nickName"));
            dto.setArea(rs.getString("area"));
            dto.setSexo(rs.getString("sexo").charAt(0));
            dto.setPassword(rs.getString("password"));
            dto.setFechaNacimiento
                            (UDate.dateToString(rs.getDate("fechanacimiento")));
            dto.setCreador(rs.getBoolean("creador"));
            dto.setFinalizador(rs.getBoolean("finalizador"));
            
            con.commit();
            return dto;
            
        } catch (SQLException sqe) {
            return null;
        } catch(Exception e){
            return null;
        } 
        finally {
            
            try {
                if(pstm != null) pstm.close();
                if(rs != null) rs.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException ex) {
                
            }
            
        }
        
    } // fin consultarUsuarioPorNickname

    @Override
    public boolean crearUsuario(String nombre, String nickName, String area,
            String password, int dni, boolean creador, boolean finalizador,
            char sexo, String fechaNacimiento) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "INSERT INTO user (nombre, nickname, area, password, "
                    + "dni, creador, finalizador, sexo, fechanacimiento) " +
                    "VALUES ('" + nombre + "', '" + nickName + "', '" + area + 
                    "', '" + password + "', " + Integer.toString(dni) + ", " +
                    Boolean.toString(creador) + ", " +
                    Boolean.toString(finalizador) + ", '" + 
                    Character.toString(sexo) + "', '" +
                    UDate.stringToDate(fechaNacimiento).toString() + "')";
            
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo == 1) con.commit();
            else {
                con.rollback();
                throw new SQLException("Resultado inesperado");
            }
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            
            try {
                
                if(pstm != null) pstm.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException e) {
            }
            
        }
    
    } // fin crearUsuario

    @Override
    public boolean borrarUsuario(String nickName) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "DELETE FROM user WHERE nickname = '" + nickName + "'";
            
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1) {
                con.rollback();
                throw new SQLException("Error al borrar");
            } else{
                con.commit();
                return true;
            }
            
        } catch (SQLException sqe) {
            return false;
        } finally {
            
            try {
                if(pstm != null) pstm.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException e) {
            }
            
        }
        
    } // fin borrarUsuario

    @Override
    public boolean modificarPermisos(String nickname, boolean creador,
            boolean finalizador) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "UPDATE user SET creador = " + Boolean.toString(creador)
                    + " WHERE nickname = '" + nickname + "'";
            
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1) {
               con.rollback();
               throw new SQLException("Problema al modificar booleanos");
            }
            
            sql = "UPDATE user SET finalizador = " + Boolean.toString(finalizador)
                    + " WHERE nickname = '" + nickname + "'";
            
            pstm = con.prepareStatement(sql);
            rtdo = pstm.executeUpdate();
            
            if(rtdo > 1) {
               con.rollback();
               throw new SQLException("Problema al modificar booleanos");
            }
            else {
                con.commit();
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            
            try {
                if(pstm != null) pstm.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException sqle) {
            }
            
        }
    
    } // fin modificarPermisos

    @Override
    public ArrayList<UserDTO> consultarPorArea(String area) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM user WHERE area = '" + area + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<UserDTO> coll = new ArrayList<>();
            UserDTO dto = null;
            
            while(rs.next()){
                
                dto = new UserDTO();
                
                dto.setNombre(rs.getString("nombre"));
                dto.setNickname(rs.getString("nickname"));
                dto.setArea(area);
                dto.setDni(rs.getInt("dni"));
                dto.setCreador(rs.getBoolean("creador"));
                dto.setFinalizador(rs.getBoolean("finalizador"));
                dto.setSexo(rs.getString("sexo").charAt(0));
                dto.setFechaNacimiento
                    (UDate.dateToString(rs.getDate("fechanacimiento")));
                
                coll.add(dto);
                
            }
            
            return coll;
            
        } catch (SQLException e) {
            return null;
        } finally {
            
            try {
                if(pstm != null) pstm.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException sqle) {
            }
            
        }
        
    } //fin consultarPorArea

    @Override
    public ArrayList<UserDTO> consultarTodos() {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM user";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<UserDTO> coll = new ArrayList<>();
            UserDTO dto = null;
            
            while(rs.next()){
                
                dto = new UserDTO();
                
                dto.setNombre(rs.getString("nombre"));
                dto.setNickname(rs.getString("nickname"));
                dto.setArea(rs.getString("area"));
                dto.setDni(rs.getInt("dni"));
                dto.setCreador(rs.getBoolean("creador"));
                dto.setFinalizador(rs.getBoolean("finalizador"));
                dto.setSexo(rs.getString("sexo").charAt(0));
                dto.setFechaNacimiento
                    (UDate.dateToString(rs.getDate("fechanacimiento")));
                
                coll.add(dto);
                
            }
            
            return coll;
            
        } catch (SQLException e) {
            return null;
        } finally {
            
            try {
                if(pstm != null) pstm.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException sqle) {
            }
            
        }
    
    } // fin consultarTodos
    
}
