/*
 * DAO que se encarga de resolver los métodos exigidos por la interface AreaDAO
 * implementando la sintaxis para una base de datos de tipo HSQLDB
 */
package com.domain.sql.daohsqldblimple;

import com.domain.sql.dto.AreaDTO;
import com.domain.sql.interfacesdao.AreaDAO;
import com.domain.sql.ConnectionPool;
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
public class AreaDAOHsqlDBImple implements AreaDAO{

    @Override
    public boolean crearArea(String nombre, String fechacreacion) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "INSERT INTO area (nombre, fechacreacion) " +
                    "VALUES ('" + nombre + "', '" +
                    UDate.stringToDate(fechacreacion).toString() + "')";
            
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1){
                con.rollback();
                throw new SQLException("Resultado inesperado");
            } else con.commit();
            
            return true;
            
        } catch (SQLException e) {
            return false;
        } finally {
            
            try {
                if(pstm != null) pstm.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException ex) {
                
            }
            
        }
    
    } // fin crearArea

    @Override
    public boolean borrarArea(String nombre) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "DELETE FROM area WHERE nombre = '" + nombre + "'";
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1){
                con.rollback();
                throw new SQLException("Resultado inesperado");
            } else con.commit();
            
            return true;
            
        } catch (SQLException e) {
            return false;
        } finally {
            
            try {
                if(pstm != null) pstm.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException ex) {
                
            }
            
        }
    
    } // fin borrarArea

    @Override
    public AreaDTO buscarAreaPorNombre(String nombre) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM area WHERE nombre = '" + nombre + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            AreaDTO dto = new AreaDTO();
            
            rs.next();
            dto.setNombre(rs.getString("nombre"));
            dto.setFechaCreacion
                            (UDate.dateToString(rs.getDate("fechacreacion")));
            
            return dto;
            
        } catch (SQLException e) {
            return null;
        } finally {
            
            try {
                if(pstm != null) pstm.close();
                if(rs != null) rs.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException ex) {
                
            }
            
        }
        
    } // fin buscarAreaPorNombre

    @Override
    public ArrayList<AreaDTO> obtenerTodas() {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM area";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<AreaDTO> coll = new ArrayList<>();
            AreaDTO dto;
            
            while(rs.next()){
                
                dto = new AreaDTO();
                dto.setNombre(rs.getString("nombre"));
                dto.setFechaCreacion
                            (UDate.dateToString(rs.getDate("fechacreacion")));
                
                coll.add(dto);
            }
            
            return coll;
            
        } catch (SQLException e) {
            return null;
        } finally {
            
            try {
                if(pstm != null) pstm.close();
                if(rs != null) rs.close();
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (SQLException ex) {
                
            }
            
        }
    
    } // fin obtenerTodas
    
}
