/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domain.server;

import com.domain.frameworkxml.XMLFactory;
import com.domain.sql.dto.*;
import com.domain.sql.interfacesdao.*;
import com.domain.sql.daohsqldblimple.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Server extends Thread{
    
    private Socket socket = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    
    // Constantes que determinan el código de los servicios que provee el server
    
    public static final int OBTENER_USUARIO_POR_NICKNAME = 1;
    
    public static final int OBTENER_TRAMITE_POR_AREAACTUAL = 2;
    
    public static final int OBTENER_MOVIMIENTOS_POR_IDTRAMITE = 3;
    
    public static final int OBTENER_INTERESADO_POR_DNI = 4;
    
    public static final int OBTENER_TODAS_LAS_AREAS = 5;
    
    public static final int MODIFICAR_ESTADO_TRAMITE = 6;
    
    public static final int MODIFICAR_AREA_ACTUAL = 7;
    
    public static final int CREAR_INTERESADO = 8;
    
    public static final int CREAR_TRAMITE = 9;
    
    public static final int CREAR_MOVIMIENTO = 10;
    
    // Desarrollo del servidor
    
    public Server(Socket s){
        this.socket = s;
    }
    
    public static void initServer(){
        
        try {
            int port = Integer.parseInt
                (XMLFactory.getByPath("/server-config/server/port")
                .getAtts().get("nro"));
            ServerSocket ss = new ServerSocket(port);
            Socket s;
            
            while(true){
                
                s = ss.accept();
                new Server(s).start();
            }
            
        } catch (IOException | NumberFormatException e) {
        }
        
    } // fin initServer
    
    @Override
    public void run(){ // hilos que ejecutarán los servicios por cada cliente
        
        try {
            
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            

            int codServicio = dis.readInt();
            
            switch(codServicio){
                
                case OBTENER_USUARIO_POR_NICKNAME:
                    obtenerUsuarioPorNickname(dis, dos);
                    break;
                case OBTENER_TRAMITE_POR_AREAACTUAL:
                    obtenerTramitePorAreaActual(dis,dos);
                    break;
                case OBTENER_MOVIMIENTOS_POR_IDTRAMITE:
                    obtenerMovimientosPorIdTramite(dis,dos);
                        break;
                case OBTENER_INTERESADO_POR_DNI:
                    obtenerInteresadoPorDni(dis,dos);
                    break;
                case OBTENER_TODAS_LAS_AREAS:
                    obtenerTodasLasAreas(dis,dos);
                    break;
                case MODIFICAR_ESTADO_TRAMITE:
                    modificarEstadoTramite(dis,dos);
                    break;
                case MODIFICAR_AREA_ACTUAL:
                    modificarAreaActual(dis,dos);
                    break;
                case CREAR_INTERESADO:
                    crearInteresado(dis,dos);
                    break;
                case CREAR_TRAMITE:
                    crearTramite(dis,dos);
                    break;
                case CREAR_MOVIMIENTO:
                    crearMovimiento(dis,dos);
                    break;
                
            }
            
        } catch (IOException e) {
        } finally { // libero los recursos
            
            try {
                
                if(dos != null) dos.close();
                if(dis != null) dis.close();
                if(socket != null) socket.close();
                
            } catch (IOException e) {
            }
            
        }
        
        
    } // fin run
    
    // métodos privados de servicios que ofrece el server
    
    private void obtenerUsuarioPorNickname(DataInputStream dis,
                                           DataOutputStream dos){
        
        try {
            
            UserDAO dao = new UserDAOHsqldbImple();
            
            // leo el nickname que envia el usuario
            String nickName = dis.readUTF();
            
            // obtengo el usuario correspondiente a ese nickname
            UserDTO dto = dao.consultarUsuarioPorNickName(nickName);
            
            if(dto != null) // Si efectivamente existe ese usuario
                dos.writeUTF(dto.toString());
            else // caso contrario
                dos.writeUTF("null");
            
        } catch (IOException e) {
        } 

    } // fin obtenerUsuarioPorNickname
    
    private void obtenerTramitePorAreaActual(DataInputStream dis,
                                             DataOutputStream dos){
        
        try {
            
            TramiteDAO dao = new TramiteDAOHsqldbImple();
            
            // Recibo el nombre del area sobre la que pretendo buscar
            String area = dis.readUTF();
            
            ArrayList<TramiteDTO> coll = dao.obtenerTramitesPorAreaActual(area);
            
            // si efectivamente el area existe y tiene tramites
            if(coll != null && !coll.isEmpty()){ 
                
                // Informo al cliente la cantidad de elementos que va a recibir
                dos.writeInt(coll.size());
                
                // Envío todos los Strings
                for(TramiteDTO dto : coll)
                    dos.writeUTF(dto.toString());
                
            } else // caso contrario, informo que no recibirá datos
                dos.writeInt(0);
            
        } catch (IOException e) {
        }
        
    } // fin obtenerTramitePorAreaActual
    
    private void obtenerMovimientosPorIdTramite(DataInputStream dis,
                                                DataOutputStream dos){
        
        try {
            
            MovimientoDAO dao = new MovimientoDAOHsqlDBImple();
            
            // leo el id del tramite al que pertenecen los movimientos
            int idTramite = dis.readInt();
            
            ArrayList<MovimientoDTO> coll = dao.obtenerPorIdTramite(idTramite);
            
            if(coll != null && !coll.isEmpty()){
                // aviso al cliente la dimension de la colección a enviar
                dos.writeInt(coll.size());
                
                //envío los toString correspondientes
                for(MovimientoDTO dto : coll)
                    dos.writeUTF(dto.toString());
                
            } else
                dos.writeInt(0);
            
            
        } catch (IOException e) {
        }
        
    } // fin obtenerMovimientosPorIdTramite
    
    private void obtenerInteresadoPorDni(DataInputStream dis, 
                                         DataOutputStream dos){
        
        try {
            
            InteresadoDAO dao = new InteresadoDAOHsqlDBImple();
            
            // leo el dni que envía el cliente
            int dni = dis.readInt();
            //obtengo el interesado
            InteresadoDTO dto = dao.obtenerInteresadoPorDNI(dni);
            
            if(dto != null)
                dos.writeUTF(dto.toString());
            else
                dos.writeUTF("null");
            
        } catch (IOException e) {
        }
        
    } // fin obtenerInteresadoPorDni
    
    private void obtenerTodasLasAreas(DataInputStream dis,
                                      DataOutputStream dos){
        
        try {
            
            AreaDAO dao = new AreaDAOHsqlDBImple();
            ArrayList<AreaDTO> coll = dao.obtenerTodas();
            
            if(coll != null && !coll.isEmpty()){
                
                // informo al cliente la cantidad de cadenas a recibir
                dos.writeInt(coll.size());
                
                // envío todos los datos
                for(AreaDTO dto : coll)
                    dos.writeUTF(dto.toString());
                
            } else
                dos.writeInt(0);
            
            
        } catch (IOException e) {
        }
        
    } // fin obtenerTodasLasAreas
    
    private void modificarEstadoTramite(DataInputStream dis,
                                        DataOutputStream dos){
        
        try {
            
            TramiteDAO dao = new TramiteDAOHsqldbImple();
            
            // recibo el id del trámite
            int idTramite = dis.readInt();
            // recibo el nuevo estado del trámite
            String estado = dis.readUTF();
            
            if(dao.modificarEstado(idTramite, estado))
                dos.writeUTF("true"); // informo el éxito de la operación
            else
                dos.writeUTF("false"); // informo lo contrario
                
        } catch (IOException e) {
        }
        
    } // fin modificarEstadoTramite
    
    private void modificarAreaActual(DataInputStream dis, DataOutputStream dos){
        
        try {
            
            TramiteDAO dao = new TramiteDAOHsqldbImple();
            
            // recibo el id del trámite
            int idTramite = dis.readInt();
            // recibo la nueva area del trámite
            String area = dis.readUTF();
            
            if(dao.modificarAreaActual(idTramite, area))
                dos.writeUTF("true"); // informo el éxito de la operación
            else
                dos.writeUTF("false"); // informo lo contrario
            
        } catch (IOException e) {
        }
        
    } // fin modificarAreaActual
    
    private void crearInteresado(DataInputStream dis, DataOutputStream dos){
        
        try {
            
            InteresadoDAO dao = new InteresadoDAOHsqlDBImple();
            
            // recibo el nombre del interesado
            String nombre = dis.readUTF();
            // recibo el dni del interesado
            int dni = dis.readInt();
            // recibo el sexo del interesado
            char sexo = dis.readUTF().charAt(0);
            // recibo la ocupacion del interesado
            String ocupacion = dis.readUTF();
            // recibo la fecha de nacimiento del interesado
            String fechaNacimiento = dis.readUTF();
            
            //Creo el interesado e informo al cliente del éxito o fracaso al hacerlo
            
            if(dao.crearInteresado
                        (nombre, dni, sexo, ocupacion, fechaNacimiento))
                dos.writeUTF("true");
            else
                dos.writeUTF("false");
            
        } catch (IOException e) {
        }
        
    } // fin crearInteresado
    
    private void crearTramite(DataInputStream dis, DataOutputStream dos){
        
        try {
            
            TramiteDAO dao = new TramiteDAOHsqldbImple();
            
            // obtengo el asunto
            String asunto = dis.readUTF();
            // obtengo el id
            int id = dis.readInt();
            // obtengo el area de destino
            String areaDestino = dis.readUTF();
            // obtengo el área creadora
            String areaCreadora = dis.readUTF();
            // obtengo el dni del interesado
            int dniInteresado = dis.readInt();
            // obtengo el motivo
            String motivo = dis.readUTF();
            // obtengo la fecha de creación
            String fechaCreacion = dis.readUTF();
            // obtengo el estado
            String estado = dis.readUTF();
            // Obtengo el area actual
            String areaActual = dis.readUTF();
            
            // ejecuto el update e informo al cliente sobre el éxito o fallo
            if(dao.crearTramite(asunto, id, areaDestino, areaCreadora,
                    dniInteresado, motivo, fechaCreacion, estado, areaActual))
                dos.writeUTF("true");
            else
                dos.writeUTF("false");
            
            
        } catch (IOException e) {
        }
        
    } // fin crearTramite
    
    private void crearMovimiento(DataInputStream dis, DataOutputStream dos){
        
        try {
            
            MovimientoDAO dao = new MovimientoDAOHsqlDBImple();
        
            // leo el area de origen
            String areaOrigen = dis.readUTF();
            // leo el area de destino
            String areaDestino = dis.readUTF();
            // leo el id del trámite al que le pertenece
            int idTramite = dis.readInt();
            // leo la fecha
            String fecha = dis.readUTF();
            // leo el motivo
            String motivo = dis.readUTF();
            
            // ejecuto el update e informo al cliente sobre el éxito o fallo
            if(dao.crearMovimiento(areaOrigen, areaDestino, idTramite, fecha,
                    motivo)) dos.writeUTF("true");
            else
                dos.writeUTF("false");
            
        } catch (IOException e) {
        }
        
    } // fin crearMovimiento
    
}
