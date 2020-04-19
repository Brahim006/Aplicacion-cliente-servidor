/*
 * Esta clase da inicio al programa y se encarga de cargar el archivo XML que 
 * determina todos los parámetros del cliente
 */
package com.domain.ejecutable;

import com.domain.frameworkxml.XMLFactory;
import com.domain.gui.Login;

/**
 *
 * @author User
 */
public class Ejecutable {
    
    public static void main(String[] args) {
        
        XMLFactory.load("configuracion.xml");
        Login.getInstancia();
        
    }
    
}
