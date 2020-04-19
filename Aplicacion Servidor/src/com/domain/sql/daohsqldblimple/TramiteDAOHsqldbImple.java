/*
 * DAO que se encarga de resolver los métodos exigidos por la interface 
 * TramiteDAO implementando la sintaxis para una base de datos de tipo HSQLDB
 */
package com.domain.sql.daohsqldblimple;

import com.domain.sql.dto.TramiteDTO;
import com.domain.sql.interfacesdao.TramiteDAO;
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
public class TramiteDAOHsqldbImple implements TramiteDAO{

    @Override
    public boolean crearTramite(String asunto, int id, String areaDestino,
                        String areaCreadora, int dniInteresado, String motivo,
                        String fechaCreacion, String estado, String areaActual) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "INSERT INTO tramite (asunto, id, areadestino, " +
                    "areacreadora, dniinteresado, motivo, fechacreacion, " +
                    "estado, areaactual) VALUES ('" + asunto + "', " + id + 
                    ", '" + areaDestino + "', '" + areaCreadora + "', " +
                    dniInteresado + ", '" + motivo + "', '" +
                    UDate.stringToDate(fechaCreacion).toString() + "', '" +
                    estado + "', '" + areaActual + "')";
            
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1){
                con.rollback();
                throw new SQLException("Problema al crear trámite");
            } else con.commit();
            
            return true;
            
        } catch (SQLException e) {
            return false;
        } finally {
            
            try {
                
                ConnectionPool.getPool().releaseConnection(con);
                if(pstm != null) pstm.close();
                
            } catch (SQLException e) {
            }
            
        }
    
    } // fin crearTramite

    @Override
    public boolean borrarTramite(int id) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "DELETE FROM tramite WHERE id = " + id;
            
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1){
                con.rollback();
                throw new SQLException("Problema al crear trámite");
            } else con.commit();
            
            return true;
            
        } catch (SQLException e) {
            return false;
        } finally {
            
            try {
                
                ConnectionPool.getPool().releaseConnection(con);
                if(pstm != null) pstm.close();
                
            } catch (SQLException e) {
            }
            
        }
    
    } // fin borrarTramite

    @Override
    public TramiteDTO obtenerTramitePorId(int id) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM tramite WHERE id = " + id;
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            TramiteDTO dto = new TramiteDTO();
            
            rs.next();
            dto.setAsunto(rs.getString("asunto"));
            dto.setAreaCreadora(rs.getString("arecreadora"));
            dto.setAreaDestino(rs.getString("areadestino"));
            dto.setId(rs.getInt("id"));
            dto.setDniInteresado(rs.getInt("dniinteresado"));
            dto.setEstado(rs.getString("estado"));
            dto.setMotivo(rs.getString("motivo"));
            dto.setAreaAcual(rs.getString("areaactual"));
            dto.setFechaCreacion
                            (UDate.dateToString(rs.getDate("fechacreacion")));
            
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
    
    } // fin obtenerTramitePorId

    @Override
    public TramiteDTO obtenerTramitePorInteresado(int dni) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM tramite WHERE dni = " + dni;
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            TramiteDTO dto = new TramiteDTO();
            
            rs.next();
            dto.setAsunto(rs.getString("asunto"));
            dto.setAreaCreadora(rs.getString("arecreadora"));
            dto.setAreaDestino(rs.getString("areadestino"));
            dto.setId(rs.getInt("id"));
            dto.setDniInteresado(rs.getInt("dniinteresado"));
            dto.setEstado(rs.getString("estado"));
            dto.setMotivo(rs.getString("motivo"));
            dto.setAreaAcual(rs.getString("areaactual"));
            dto.setFechaCreacion
                            (UDate.dateToString(rs.getDate("fechacreacion")));
            
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
    
    } // fin obtenerTramitePorInteresado

    @Override
    public ArrayList<TramiteDTO> obtenerTramitesPorAreaDestino(String area) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM tramite WHERE areadestino = '" +
                    area + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<TramiteDTO> coll = new ArrayList<>();
            TramiteDTO dto;
            
            while(rs.next()){
                
                dto = new TramiteDTO();
                dto.setAsunto(rs.getString("asunto"));
                dto.setAreaCreadora(rs.getString("arecreadora"));
                dto.setAreaDestino(rs.getString("areadestino"));
                dto.setId(rs.getInt("id"));
                dto.setDniInteresado(rs.getInt("dniinteresado"));
                dto.setEstado(rs.getString("estado"));
                dto.setMotivo(rs.getString("motivo"));
                dto.setAreaAcual(rs.getString("areaactual"));
                dto.setFechaCreacion
                            (UDate.dateToString(rs.getDate("fechacreacion")));
                
                coll.add(dto);
                
            }
            
            return coll;
            
        } catch (SQLException e) {
            return null;
        } finally {
            
            try {
                
                ConnectionPool.getPool().releaseConnection(con);
                if(pstm != null) pstm.close();
                if (rs != null) rs.close();
                
            } catch (SQLException e) {
            }
            
        }
    
    } // fin ObtenerTramitesPorAreaDestino

    @Override
    public ArrayList<TramiteDTO> obtenerTramitesPorAreaCreadora(String area) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM tramite WHERE areacreadora = '" +
                    area + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<TramiteDTO> coll = new ArrayList<>();
            TramiteDTO dto;
            
            while(rs.next()){
                
                dto = new TramiteDTO();
                dto.setAsunto(rs.getString("asunto"));
                dto.setAreaCreadora(rs.getString("arecreadora"));
                dto.setAreaDestino(rs.getString("areadestino"));
                dto.setId(rs.getInt("id"));
                dto.setDniInteresado(rs.getInt("dniinteresado"));
                dto.setEstado(rs.getString("estado"));
                dto.setMotivo(rs.getString("motivo"));
                dto.setAreaAcual(rs.getString("areaactual"));
                dto.setFechaCreacion
                            (UDate.dateToString(rs.getDate("fechacreacion")));
                
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
    
    } // fin obtenerTramitesPorAreaCreadora

    @Override
    public ArrayList<TramiteDTO> obtenerTramitesPorFecha(String fecha) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM tramite WHERE fechacreacion = '" +
                    UDate.stringToDate(fecha).toString() + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<TramiteDTO> coll = new ArrayList<>();
            TramiteDTO dto;
            
            while(rs.next()){
                
                dto = new TramiteDTO();
                dto.setAsunto(rs.getString("asunto"));
                dto.setAreaCreadora(rs.getString("arecreadora"));
                dto.setAreaDestino(rs.getString("areadestino"));
                dto.setId(rs.getInt("id"));
                dto.setDniInteresado(rs.getInt("dniinteresado"));
                dto.setEstado(rs.getString("estado"));
                dto.setMotivo(rs.getString("motivo"));
                dto.setAreaAcual(rs.getString("areaactual"));
                dto.setFechaCreacion
                            (UDate.dateToString(rs.getDate("fechacreacion")));
                
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
    
    } // fin obtenerTramitesPorFecha

    @Override
    public boolean modificarEstado(int id, String estado) {
    
        Connection con =  ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "UPDATE tramite SET estado = '" + estado +
                    "' WHERE id = " + id;
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1){
                con.rollback();
                throw new SQLException("Problema al modificar estado");
            } else con.commit();
            
            return true;
            
        } catch (SQLException e) {
            return false;
        } finally {
            
            try {
                
                ConnectionPool.getPool().releaseConnection(con);
                if(pstm != null) pstm.close();
                
            } catch (SQLException e) {
            }
            
        }
    
    } // fin modificarEstado

    @Override
    public ArrayList<TramiteDTO> obtenerTramitesPorAreaActual(String area) {
    
        Connection con = ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "SELECT * FROM tramite WHERE areaactual = '" +
                    area + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            con.commit();
            
            ArrayList<TramiteDTO> coll = new ArrayList<>();
            TramiteDTO dto;
            
            while(rs.next()){
                
                dto = new TramiteDTO();
                dto.setAsunto(rs.getString("asunto"));
                dto.setAreaCreadora(rs.getString("areacreadora"));
                dto.setAreaDestino(rs.getString("areadestino"));
                dto.setId(rs.getInt("id"));
                dto.setDniInteresado(rs.getInt("dniinteresado"));
                dto.setEstado(rs.getString("estado"));
                dto.setMotivo(rs.getString("motivo"));
                dto.setAreaAcual(rs.getString("areaactual"));
                dto.setFechaCreacion
                            (UDate.dateToString(rs.getDate("fechacreacion")));
                
                coll.add(dto);
                
            }
            
            return coll;
            
        } catch (SQLException e) {
            return null;
        } catch (Exception ex){
            return null;
        }
        finally {
            
            try {
                
                ConnectionPool.getPool().releaseConnection(con);
                if(pstm != null) pstm.close();
                if(rs != null) rs.close();
                
            } catch (SQLException e) {
            }
            
        }
    
    } // fin obtenerTramitesPorAreaActual

    @Override
    public boolean modificarAreaActual(int id, String area){
        
        Connection con =  ConnectionPool.getPool().getConnection();
        PreparedStatement pstm = null;
        
        try {
            
            String sql = "UPDATE tramite SET areaactual = '" + area +
                    "' WHERE id = " + id;
            pstm = con.prepareStatement(sql);
            int rtdo = pstm.executeUpdate();
            
            if(rtdo > 1){
                con.rollback();
                throw new SQLException("Problema al modificar area actual");
            } else con.commit();
            
            return true;
            
        } catch (SQLException e) {
            return false;
        } finally {
            
            try {
                
                ConnectionPool.getPool().releaseConnection(con);
                if(pstm != null) pstm.close();
                
            } catch (SQLException e) {
            }
            
        }
        
    } // fin modificarAreaActual
    
}
