/*
 * Clase que se encarga de ejecutar el programa servidor
 */
package com.domain.ejecucion;

import com.domain.frameworkxml.XMLFactory;
import com.domain.server.Server;
import com.domain.gui.InterfazServer;

/**
 *
 * @author User
 */
public class Ejecutable {
    
    public static void main(String[] args) {
        XMLFactory.load("configuracion.xml");
        InterfazServer gui = new InterfazServer();
        Server.initServer();
    }
    
}
