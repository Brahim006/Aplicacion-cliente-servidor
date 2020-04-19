/*
 * DAO que se encarga de resolver los métodos exigidos por la interface 
 * InteresadoDAO implementando la sintaxis para una base de datos de tipo HSQLDB
 */
package com.domain.sql.daohsqldblimple;

import com.domain.sql.dto.InteresadoDTO;
import com.domain.sql.interfacesdao.InteresadoDAO;
import com.domain.sql.ConnectionPool;
import com.domain.sql.dto.InteresadoDTO;
import com.domain.utils.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class InteresadoDAOHsqlDBImple implements InteresadoDAO{

    @Override
    public boolean crearInteresado(String nombre, int dni, char sexo,
                                    String ocupacion, String fechanacimiento) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "INSERT INTO interesado (nombre, dni, sexo, ocupacion,"
                    + " fechanacimiento) VALUES ('" + nombre + "', " + dni +
                    ", '" + Character.toString(sexo) + "', '" + ocupacion + 
                    "', '" + UDate.stringToDate(fechanacimiento).toString() + "')";
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1){
                con.rollback();
                throw new SQLException("Problema al crear Interesado");
            } else con.commit();
            
            return true;
            
        } catch (SQLException e) {
            return false;
        } finally {
            
            try {
                
                if(pstm != null) pstm.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException e) {
            }
            
        }
    
    } // fin crearInteresado

    @Override
    public boolean borrarInteresado(int dni) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "DELETE FROM interesado WHERE dni = " + dni;
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1){
                con.rollback();
                throw new SQLException("Problema al crear Interesado");
            } else con.commit();
            
            return true;
            
        } catch (SQLException e) {
            return false;
        } finally {
            
            try {
                
                if(pstm != null) pstm.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException e) {
            }
            
        }
        
    } // fin borrarInteresado

    @Override
    public InteresadoDTO obtenerInteresadoPorDNI(int dni) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM interesado WHERE dni = " + dni;
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            InteresadoDTO dto = new InteresadoDTO();
            
            rs.next();
            dto.setNombre(rs.getString("nombre"));
            dto.setDni(rs.getInt("dni"));
            dto.setSexo(rs.getString("sexo").charAt(0));
            dto.setOcupacion(rs.getString("ocupacion"));
            dto.setFechaNacimiento
                            (UDate.dateToString(rs.getDate("fechanacimiento")));
            
            return dto;
            
        } catch (SQLException e) {
            return null;
        } finally {
            
            try {
                
                ConnectionPool.getPool().releaseConnection(con);
                if(pstm != null) pstm.close();
                if(rs != null) rs.close();
                
            } catch (SQLException e) {
            }
            
        }
    
    } // fin obtenerInteresadoPorDNI

    @Override
    public ArrayList<InteresadoDTO> obtenerTodos() {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM interesado";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<InteresadoDTO> coll = new ArrayList<>();
            InteresadoDTO dto;
            
            while(rs.next()){
                
                dto = new InteresadoDTO();
                dto.setNombre(rs.getString("nombre"));
                dto.setDni(rs.getInt("dni"));
                dto.setSexo(rs.getString("sexo").charAt(0));
                dto.setOcupacion(rs.getString("ocupacion"));
                dto.setFechaNacimiento
                            (UDate.dateToString(rs.getDate("fechanacimiento")));
                
                coll.add(dto);
                
            }
            
            return coll;
            
        } catch (SQLException e) {
            return null;
        } finally {
            
            try {
                
                ConnectionPool.getPool().releaseConnection(con);
                if(pstm != null) pstm.close();
                if(rs != null) rs.close();
                
            } catch (SQLException e) {
            }
            
        }
        
    } // fin obtenerTodos
    
}
