/*
 * Pool de conexiones a la base de datos que se encarga de gestionar las 
 * conexiones a base de datos para que estas sean thread-safe, gestiona las
 * conexiones teniendo abiertas un mínimo definido por minsize y crea hasta un
 * máximo definido por maxsize en el archivo configuracion.xml
 */
package com.domain.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import com.domain.frameworkxml.*;
import java.sql.SQLException;

/**
 *
 * @author Edu
 */
public class ConnectionPool {
        // Gestores de conexiones a base de datos
        private ArrayList<Connection> libres, usadas;
        // Datos de conexión
        private String url, driver, usr, pwd;
        // Datos del connection pool
        private int minsize, maxsize, steep;
        // pool único para toda la ejecución del programa
        private static ConnectionPool pool = null;
        
        private ConnectionPool(){
            
            libres = new ArrayList<>();
            usadas = new ArrayList<>();
            
            try {
                
                // Obtengo los parámetros de la conexión
                url = XMLFactory
                        .getByPath("/server-config/database-connection/url")
                        .getAtts().get("dir");
                driver = XMLFactory
                        .getByPath("/server-config/database-connection/driver")
                        .getAtts().get("dir");
                usr = XMLFactory
                        .getByPath("/server-config/database-connection/usr")
                        .getAtts().get("name");
                pwd = XMLFactory
                        .getByPath("/server-config/database-connection/pwd")
                        .getAtts().get("value");
                
                Class.forName(driver); // Levanta el driver
                
                // Datos del connection pool
                minsize = Integer.parseInt(XMLFactory
                        .getByPath("/server-config/connection-pool/minsize")
                        .getAtts().get("value"));
                maxsize = Integer.parseInt(XMLFactory
                        .getByPath("/server-config/connection-pool/maxsize")
                        .getAtts().get("value"));
                steep = Integer.parseInt(XMLFactory
                        .getByPath("/server-config/connection-pool/steep")
                        .getAtts().get("value"));
                
                // Instanciación de las collecciones de conexiones
                
                
                // Añado el shutdown hook
                Runtime.getRuntime().addShutdownHook(new ShutDownHook());
                
                // Instanciación de las primeras n=minsize conexiones
                instanciar(minsize);
                
            } catch (ClassNotFoundException cnfe) {
                System.out.println("No se ha encontrado la clase del driver");
            } catch (NumberFormatException nfe) {
                System.out.println("Error de formatos");
            } catch (Exception e){
                System.out.println("Error en el constructor");
            } 
            
        } // fin constructor
        
        /**
         * Retorna la instancia única de ConnectionPool que se mantiene durante
         * la ejecución del programa.
         * @return 
         */
        public synchronized static ConnectionPool getPool(){
            // Se crea en caso de que no esté instanciado
            if(pool == null) pool = new ConnectionPool();
            
            return pool;
            
        } // fin getPool
        
        /**
         * Retorna una conexión a la base de datos siempre que haya al menos una
         * disponible.
         * @return 
         */
        public synchronized Connection getConnection(){
            
            Connection con;
            if(usadas.isEmpty()){
                if(libres.isEmpty()){
                crearMasConexiones();
                }
                con = libres.remove(0);
                usadas.add(con);
            } else {
                con = usadas.remove(0);
            }
            return con;
            
        } // fin GetConnection
        
        /**
         * Retorna una conexión usada a la colección de conexiones libres
         * @param con 
         */
        public synchronized void releaseConnection(Connection con){
            
            usadas.add(con);
            
        } // fin releaseConnection
        
        /**
         * Cierra todas las conexiones levantadas por el pool
         */
        public synchronized void close(){
            
            try {
                
                // Cierro todas las conexiones libres
                for(Connection con : libres) con.close();
                // Cierro todas las conexiones usadas
                for(Connection con : usadas) con.close();
                
            } catch (SQLException sqle) {
                
            } catch (Exception e){
                
            }
            
        } // fin close
        
        /*
        * Método de uso interno que verifica que aún queden conexiones por crear
        * según lo determinado por el maxsize del pool, de ser posible, instancia
        * una cantidad menor o igual al steep del pool
        */
        private boolean crearMasConexiones(){
            
            // Claculo la cantidad de conexiones creadas
            int actuales = libres.size() + usadas.size();
            // Determino si aùn puedo crear conexiones según las reglas del pool
            int n = Math.min(maxsize - actuales, steep);
            
            if(n > 0) instanciar(n);
            
            return n > 0; // Da aviso de si se pudo instanciar màs conexiones
            
        } // fin crearMasConexiones
        
        /*
        * Método de uso interno que se encarga de instanciar las conexiones a la
        * base de datos según los parámetros de conexión definidos en el archivo
        * configuracion.xml
        */
        private void instanciar(int n){
            
            try {
                
                Connection con;
                
                for (int i = 0; i < n; i++) {
                    con = DriverManager.getConnection(url, usr, pwd);
                    con.setAutoCommit(false);
                    libres.add(con);
                }
                
            } catch (SQLException sqle) {
            } catch (Exception e) {
            }
            
        } // fin instanciar
        
        /**
         * ShutDownHook que se encargar{a de cerrar todas las conexiones en un
         * hilo ejecutado antes del cierre del programa
         */
        class ShutDownHook extends Thread{
            
            @Override
            public void run(){
                
                ConnectionPool pool = ConnectionPool.getPool();
                pool.close();
                
            }
            
        } // fin clase ShutDownHook
        
}
