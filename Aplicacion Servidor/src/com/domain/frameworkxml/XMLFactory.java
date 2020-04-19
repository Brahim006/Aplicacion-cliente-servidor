/*
 * Clase que se encarga de parsear los archivos XML y de delegar su información
 * a los tags genéricos XTag
 */
package com.domain.frameworkxml;

import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import java.util.Stack;
import java.util.Hashtable;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public class XMLFactory extends DefaultHandler{
    
    private static XMLFactory instancia = null;
    private Stack<XTag> pila;
    private XTag raiz;
    
    private XMLFactory(){
        
        pila = new Stack<>();
        
    }
    
    /**
     * Parsea el contenido del archivo XML pasado como argumento a través de un
     * String, dicho archivo debe estar ubicado en el package root del proyecto.
     * Usa esta misma clase como Handler, por lo que no se requiere usar un
     * handler externo.
     * @param xmlFilename 
     */
    public static void load(String xmlFilename){
        
        try {
            
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            
            instancia = new XMLFactory();
            // Parsea el archivo y usa esta misma clase como handler
            sp.parse(xmlFilename, instancia);
            
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al intentar parsear el archivo XML");
        }
        
    } // fin load
    
    /*
     * De uso interno, se encarga de tomar los atributos de un tag y almacenarlos
     * dentro de un hastable que luego retorna.
     */
    private Hashtable<String,String> _cloneAttributes(Attributes attributes){
        
        Hashtable<String,String> atts = new Hashtable<>();
        
        for (int i = 0; i < attributes.getLength(); i++) {
            atts.put(attributes.getQName(i), attributes.getValue(i));
        }
        
        return atts;
    } // fin _cloneAttributes
    
    /**
     * Retorna un tag aclarando su ruta o "path" dentro del archivo XML
     * @param path
     * @return 
     */
    public static XTag getByPath(String path){
        
        return instancia.raiz.getSubtag(path);
        
    } // fin getByPath
    
    /**
     * Retorna el tag ubicado en la ruta path cuyo atributo attName tiene el
     * valor value. En caso de no existir, retorna un valor null.
     * @param path
     * @param attName
     * @param value
     * @return 
     */
    public static XTag getByAttribute(String path, String attName,
                                      String value){
        
        return instancia.raiz.getSubtagByAttibute(path, attName, value);
        
    } // fin getByAttribute
    
    /* Métodos usados por el parser para determinar el comportamiento al detectar
     * la apertura ó cierre de un tag en el archivo XML
     */
    
    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException{
        
        // Almaceno los atributos en una hashtable
        Hashtable<String,String> atts = _cloneAttributes(attributes);
        // Instancio el tag que inicia consus respectivos argumentos
        XTag t = new XTag(name,atts);
        // En el caso de que la pila esté vacía, éste tag será la raiz del XML
        if(pila.isEmpty()) raiz = t;
        
        pila.push(t); // Almaceno el tag en la pila
    } // fin startElement
    
    @Override
    public void endElement(String uri, String localName, String Name)
                                                throws SAXException{
        // Al cerrarse un elemento se determina su relación con el contenedor
        if(pila.size() > 1){ // Aplica a todos los tags excepto a la raiz
            
            XTag hijo = pila.pop();// El último en ingresar es el que se acaba de cerrar
            XTag padre = pila.pop();// El anteúltimo lo contiene
            padre.addSubtag(hijo);// Se establece la relación tag/subtag
            pila.push(padre); // El tag padre es reinsertado a la pila
            
        }
        // finalmente, todos los tags terminarán conteniendo a sus subtags
    } // fin endElement
    
}
