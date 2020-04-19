/*
 * DAO que se encarga de resolver los métodos exigidos por la interface 
 * MovimientoDAO implementando la sintaxis para una base de datos de tipo HSQLDB
 */
package com.domain.sql.daohsqldblimple;

import com.domain.sql.dto.MovimientoDTO;
import com.domain.sql.interfacesdao.MovimientoDAO;
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
public class MovimientoDAOHsqlDBImple implements MovimientoDAO{

    @Override
    public boolean crearMovimiento(String areaOrigen, String areaDestino,
                                int idTramite, String fecha, String motivo) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "INSERT INTO movimiento (areaorigen, areadestino, " +
                    "idtramite, motivo, fecha) VALUES ('" + areaOrigen + "', '" 
                    + areaDestino + "', " + idTramite + ", '" + motivo + "', '"
                    + UDate.stringToDate(fecha).toString() + "')";
            
            pstm = con.prepareStatement(sql);
            
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1){
                con.rollback();
                throw new 
                    SQLException("Resultado inesperado al añadir movimiento");
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
    
    } // fin crearMovimiento

    @Override
    public ArrayList<MovimientoDTO> obtenerPorAreaDeDestino(String area) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM movimiento WHERE areadestino = '" +
                    area + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<MovimientoDTO> coll = new ArrayList<>();
            MovimientoDTO dto;
            
            while(rs.next()){
                
                dto = new MovimientoDTO();
                
                dto.setAreaDestino(rs.getString("areadestino"));
                dto.setAreaOrigen(rs.getString("areaorigen"));
                dto.setIdTramite(rs.getInt("idtramite"));
                dto.setMotivo(rs.getString("motivo"));
                dto.setFecha(UDate.dateToString(rs.getDate("fecha")));
                
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
                
            } catch (SQLException e) {
                
            }
            
        }
    
    } // fin obtenerPorAreaDeDestino

    @Override
    public ArrayList<MovimientoDTO> obtenerPorAreaDeOrigen(String area) {

        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM movimiento WHERE areaorigen = '" +
                    area + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<MovimientoDTO> coll = new ArrayList<>();
            MovimientoDTO dto;
            
            while(rs.next()){
                
                dto = new MovimientoDTO();
                
                dto.setAreaDestino(rs.getString("areadestino"));
                dto.setAreaOrigen(rs.getString("areaorigen"));
                dto.setIdTramite(rs.getInt("idtramite"));
                dto.setMotivo(rs.getString("motivo"));
                dto.setFecha(UDate.dateToString(rs.getDate("fecha")));
                
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
                
            } catch (SQLException e) {
                
            }
            
        }
    
    } // fin obtenerPorAreaDeOrigen

    @Override
    public ArrayList<MovimientoDTO> obtenerPorFecha(String fecha) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM movimiento WHERE fecha = '" +
                    UDate.stringToDate(fecha).toString() + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<MovimientoDTO> coll = new ArrayList<>();
            MovimientoDTO dto;
            
            while(rs.next()){
                
                dto = new MovimientoDTO();
                
                dto.setAreaDestino(rs.getString("areadestino"));
                dto.setAreaOrigen(rs.getString("areaorigen"));
                dto.setIdTramite(rs.getInt("idtramite"));
                dto.setMotivo(rs.getString("motivo"));
                dto.setFecha(UDate.dateToString(rs.getDate("fecha")));
                
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
                
            } catch (SQLException e) {
                
            }
            
        }
    
    } // fin obtenerPorFecha

    @Override
    public ArrayList<MovimientoDTO> obtenerTodos() {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM movimiento";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<MovimientoDTO> coll = new ArrayList<>();
            MovimientoDTO dto;
            
            while(rs.next()){
                
                dto = new MovimientoDTO();
                
                dto.setAreaDestino(rs.getString("areadestino"));
                dto.setAreaOrigen(rs.getString("areaorigen"));
                dto.setIdTramite(rs.getInt("idtramite"));
                dto.setMotivo(rs.getString("motivo"));
                dto.setFecha(UDate.dateToString(rs.getDate("fecha")));
                
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
                
            } catch (SQLException e) {
                
            }
            
        }
    
    } //fin obtenerTodos

    @Override
    public boolean borrarPorFecha(String fecha) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "DELETE FROM movimiento WHERE fecha = '" +
                    UDate.stringToDate(fecha).toString() + "'";
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo != obtenerPorFecha(fecha).size()){
                con.rollback();
                throw new SQLException("Error al intentar borrar los movimientos");
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
    
    } // fin borrarPorFecha

    @Override
    public ArrayList<MovimientoDTO> obtenerPorIdTramite(int idTramite) {
        
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM movimiento WHERE idtramite = " +
                    idTramite;
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<MovimientoDTO> coll = new ArrayList<>();
            MovimientoDTO dto;
            
            while(rs.next()){
                
                dto = new MovimientoDTO();
                
                dto.setAreaDestino(rs.getString("areadestino"));
                dto.setAreaOrigen(rs.getString("areaorigen"));
                dto.setIdTramite(rs.getInt("idtramite"));
                dto.setMotivo(rs.getString("motivo"));
                dto.setFecha(UDate.dateToString(rs.getDate("fecha")));
                
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
                
            } catch (SQLException e) {
                
            }
        
        } 
    
    }// fin obtenerPorIdTramite
}
