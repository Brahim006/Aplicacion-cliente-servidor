/*
 * Facade local que simplifica la interacción entre la gui del cliente local con
 * la base de datos
 */
package com.domain.facade;

import com.domain.sql.interfacesdao.*;
import com.domain.frameworkxml.XMLFactory;

/**
 *
 * @author User
 */
public class FacadeLocal {
    
    private AreaDAO areaDAO;
    private UserDAO userDAO;
    private TramiteDAO tramiteDAO;
    private InteresadoDAO interesadoDAO;
    private MovimientoDAO movimientoDAO;
    
    public FacadeLocal(){
        
        
        try {
            // Inicializo los DAOs según la implementación especificada por el XML
            String className = XMLFactory.getByPath
            ("/server-conig/dao-implementation/areadao")
                .getAtts().get("classpath");
            areaDAO = (AreaDAO) Class.forName(className).newInstance();
            
            className = XMLFactory.getByPath
            ("/server-conig/dao-implementation/userdao")
                .getAtts().get("classpath");
            userDAO = (UserDAO)Class.forName(className).newInstance();
            
            className = XMLFactory.getByPath
            ("/server-conig/dao-implementation/tramitedao")
                .getAtts().get("classpath");
            tramiteDAO = (TramiteDAO)Class.forName(className).newInstance();
            
            className = XMLFactory.getByPath
            ("/server-conig/dao-implementation/interesadodao")
                .getAtts().get("classpath");
            interesadoDAO = (InteresadoDAO)Class.forName(className).newInstance();
            
            className = XMLFactory.getByPath
            ("/server-conig/dao-implementation/movimientodao")
                .getAtts().get("classpath");
            movimientoDAO = (MovimientoDAO)Class
                    .forName(className).newInstance();
            
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
        }
        
    } // fin constructor
    
    
    
}
