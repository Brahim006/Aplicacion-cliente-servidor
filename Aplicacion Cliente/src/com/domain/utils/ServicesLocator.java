/*
 * Clase que encapsula la conexión al servidor y solicita servicios según su 
 * código
 */
package com.domain.utils;

import com.domain.frameworkxml.XMLFactory;
import com.domain.dto.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class ServicesLocator {
    
    // constanstes estáticas para la conexión al server
    public static final String SERVER_IP = XMLFactory
            .getByPath("/client-config/server-connection/ip-server")
            .getAtts().get("value");
    public static final int SERVER_PORT = Integer.parseInt(XMLFactory
            .getByPath("/client-config/server-connection/port-server")
            .getAtts().get("value"));
    
    /**
     * Se conecta al servidor y busca al usuario
     * @param nickName El NickName del usuario que se pretende Buscar
     * @return Un UserDTO representando al usuario o null si este no existe
     */
    public static UserDTO obtenerUsuarioPorNickname(String nickName){
        
        Socket s = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            
            // solicito el servicio de código 1
            dos.writeInt(1);
            // envío el nickname del usuario que busco
            dos.writeUTF(nickName);
            
            // obtengo el string enviado por el server
            String respuesta = dis.readUTF();
            
            if(respuesta.equals("null")) // en el caso de que el nickname no exista
                return null;
            else // en caso positivo, retorno el DTO correspondiente
                return UDto.stringToUserDTO(respuesta);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
    } // fin obtenerUsuarioPorNickname
    
    public static ArrayList<TramiteDTO> obtenerTramitePorAreaActual(String area){
        
        Socket s = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dis =  new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // solicito al server el servicio nro 2
            dos.writeInt(2);
            // envío el nombre del área
            dos.writeUTF(area);
            // recibo la cantidad de Strings que voy a recibir
            int n = dis.readInt();
            
            if(n != 0){ // en caso de que reciba elementos...
                
                ArrayList<TramiteDTO> coll = new ArrayList<>();
                
                for (int i = 0; i < n; i++) {
                    coll.add(UDto.stringToTramiteDTO(dis.readUTF()));
                }
                
                return coll;
                
            } else // caso omiso
                return null;
            
        } catch (IOException e) {
            return null;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
        
    } // fin obtenerTramitePorAreaActual
    
    public static ArrayList<MovimientoDTO> 
                                obtenerMovimientosPorIdTramite(int idTramite){
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // solicito al server el servicio de código 3
            dos.writeInt(3);
            // envío el id del trámite al que quiero consultarle los movimientos
            dos.writeInt(idTramite);
            
            // recibo la cantidad de elementos
            int n = dis.readInt();
            
            if(n != 0){
                
                ArrayList<MovimientoDTO> coll = new ArrayList<>();
                
                for (int i = 0; i < n; i++) {
                    coll.add(UDto.stringToMovimientoDTO(dis.readUTF()));
                }
                return coll;
            } else
                return null;
            
        } catch (IOException e) {
            return null;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
    } // fin obtenerMovimientosPorIdTramite
    
    public static InteresadoDTO obtenerInteresadoPorDni(int dniInteresado){
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // solicito al server el servicio de código 4
            dos.writeInt(4);
            // envio el dni del interesado
            dos.writeInt(dniInteresado);
            //leo la respuesta del servidor
            String respuesta = dis.readUTF();
            
            if(respuesta.equals("null"))
                return null;
            else
                return UDto.stringToInteresadoDTO(respuesta);
            
        } catch (IOException e) {
            return null;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
    } // fin obtenerInteresadoPorDni
                         
    public static ArrayList<AreaDTO> obtenerTodasLasAreas(){
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // solicito al server el servicio de código 5
            dos.writeInt(5);
            
            // obtengo la cantidad de áreas
            int n = dis.readInt();
            
            if(n != 0){
                
                ArrayList<AreaDTO> coll = new ArrayList<>();
                
                for (int i = 0; i < n; i++) {
                    coll.add(UDto.stringToAreaDTO(dis.readUTF()));
                }
                
                return coll;
                
            } else
                return null;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
    } // fin obtenerTodasLasAreas
    
    public static boolean modificarEstadoTramite(int idTramite, String estado){
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // solicito el servicio de código 6
            dos.writeInt(6);
            // envio el id del trámite
            dos.writeInt(idTramite);
            // envío el nuevo estado del trámite
            dos.writeUTF(estado);
            
            // retorno la confirmación de éxito ó falla de la operación
            return Boolean.parseBoolean(dis.readUTF());
            
        } catch (IOException e) {
            return false;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
    } // fin modificarEstadoTramite
    
    public static boolean modificarAreaActual(int idTramite, String area){
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // solicito el servicio de código 7
            dos.writeInt(7);
            // envio el id del trámite
            dos.writeInt(idTramite);
            // envío la nueva area del trámite
            dos.writeUTF(area);
            
            // retorno la confirmación de éxito ó falla de la operación
            return Boolean.parseBoolean(dis.readUTF());
            
        } catch (IOException e) {
            return false;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
    } // fin modificarAreaActual
    
    public static boolean crearInteresado(String nombre, int dni, char sexo,
                                    String ocupacion, String fechanacimiento){
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // solicito el servicio de código 8
            dos.writeInt(8);
            
            // envío el nombre del interesado
            dos.writeUTF(nombre);
            // envio el dni del interesado
            dos.writeInt(dni);
            // envío el sexo del interesado
            dos.writeUTF(Character.toString(sexo));
            // envío la ocupación del interesado
            dos.writeUTF(ocupacion);
            // envío la fecha de nacimiento
            dos.writeUTF(fechanacimiento);
            
            // retorno el aviso de éxito o fallo de la operación
            return Boolean.parseBoolean(dis.readUTF());
            
        } catch (IOException e) {
            return false;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
    } // fin crearInteresado
    
    public static boolean crearTramite(String asunto, int id, String areaDestino,
                        String areaCreadora, int dniInteresado, String motivo,
                        String fechaCreacion, String estado, String areaActual){
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // solicito el servicio de código 9
            dos.writeInt(9);
            
            // envio el asunto
            dos.writeUTF(asunto);
            // envio el id del trámite
            dos.writeInt(id);
            // envio el area de destino
            dos.writeUTF(areaDestino);
            // envio el area creadora
            dos.writeUTF(areaCreadora);
            // envio el dni del interesado
            dos.writeInt(dniInteresado);
            // envio el motivo
            dos.writeUTF(motivo);
            // envio la fecha de creacion
            dos.writeUTF(fechaCreacion);
            // envio el estado
            dos.writeUTF(estado);
            // envio el area actual
            dos.writeUTF(areaActual);
            
            // retorno el booleano informando éxito o fallo
            return Boolean.parseBoolean(dis.readUTF());
            
        } catch (IOException e) {
            return false;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
    } // fin crearTramite
    
    public static boolean crearMovimiento(String areaOrigen, String areaDestino,
                                    int idTramite, String fecha, String motivo){
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            
            s = new Socket(SERVER_IP, SERVER_PORT);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // solicito el servicio de código 10
            dos.writeInt(10);
            
            // envio el area de origen
            dos.writeUTF(areaOrigen);
            // envio el area de destino
            dos.writeUTF(areaDestino);
            // envío el id del trámite
            dos.writeInt(idTramite);
            // envio la fecha
            dos.writeUTF(fecha);
            // envío el motivo
            dos.writeUTF(motivo);
            
            // leo el resultado de la operación y lo retorno
            return Boolean.parseBoolean(dis.readUTF());
            
        } catch (IOException e) {
            return false;
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(s != null) s.close();
                
            } catch (IOException e) {
            }
            
        }
        
    } // fin crearMovimiento
    
}
