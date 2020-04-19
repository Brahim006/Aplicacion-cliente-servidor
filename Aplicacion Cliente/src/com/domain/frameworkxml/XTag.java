/*
 * Clase que representa un tag genérico de un archivo XML parseado por la clase
 * XMLFactory
 */
package com.domain.frameworkxml;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *
 * @author User
 */
public class XTag {
    
    private String name;
    private Hashtable<String,String> atts;
    private HashtableLinkeado<XTag> subtags;
    
    /**
     * Crea un nuevo XTag, objeto que representa a un tag genérico de un archivo
     * XML, recibiendo como argumentos su nombre y un Hashtable conteniendo sus
     * atributos.
     * @param qname
     * @param atts 
     */
    public XTag(String qname, Hashtable<String,String> atts){
        
        this.name = qname;
        this.atts = atts;
        subtags = new HashtableLinkeado<>();
        
    }
    
    /**
     * Añade el subtag a la colección de subtags
     * @param t 
     */
    public void addSubtag(XTag t){
        
        subtags.put(t.getName(), t);
        
    }
    
    // Getters y Setters

    /**
     * Retorna el nombre del tag/subtag
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Cambia el nombre del tag/subtag
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Retorna el subtag de la ruta especificada entre barras "/" que recibe
     * como argumento. Este método sólo devuelve la primera ocurrencia, por lo
     * que si se requieren obtener subtags con múltiples ocurrencias dentro del
     * XML debe usarse getSubtags.
     * @param name
     * @return 
     */
    public XTag getSubtag(String name){
        
        String auxName;
        // Si el path es obsoluto, se lo transforma a relativo
        if(name.startsWith("/" + this.name + "/"))
            auxName = name.substring(this.name.length() + 2);
        else auxName = name;
        
        StringTokenizer st = new StringTokenizer(auxName, "/");
        XTag ret = this;
        String tok;
        // Recorre todos los subtags hasta llegar al especificado
        while(st.hasMoreTokens()){
            
            tok = st.nextToken();
            ret = ret.subtags.get(tok).getFirst();
            
        }
        
        return ret;
    } // fin getSubtag
    
    /**
     * Retorna un arreglo conteniendo todos los tags/subtags con múltiples 
     * ocurrencias según la ruta especificada.
     * @param name
     * @return 
     */
    public XTag[] getSubtags(String name){
        
        int idx = name.lastIndexOf("/");
        LinkedList<XTag> hijos;
        // Se analiza si la ruta se refiere a un subtag o al tag mismo
        if(idx > 0){ // En caso de lo que se requiera es un subtag
            
            String auxName = name.substring(0,idx);
            String auxName2 = name.substring(idx + 1);
            
            XTag tag = getSubtag(auxName); // tag padre
            hijos = tag.subtags.get(auxName2);
            
        } else { // En caso de que se refiera a un sólo tag
            
            hijos = subtags.get(name);
            
        }
        
        XTag[] ret = new XTag[hijos.size()];
        int i = 0;
        
        for (Iterator<XTag> it = hijos.iterator(); it.hasNext();) {
            ret[i++] = it.next();
        }
        
        return ret;
    } // fin getSubtags
    
    /**
     * Retorna el tag ubicado en la ruta path cuyo atributo attName tiene el
     * valor value. En caso de no existir, retorna un valor null.
     * @param path
     * @param attName
     * @param value
     * @return 
     */
    public XTag getSubtagByAttibute(String path, String attName, 
                                    String value){
        
        XTag[] tags = getSubtags(path);
        
        for (int i = 0; i < tags.length; i++) {
            if(tags[i].atts.get(attName).equals(value)) return tags[i];
        }
        
        return null;
    } // fin getSubtagByAttribute

    public Hashtable<String, String> getAtts() {
        return atts;
    }
    
    // Métodos heredados de Object
    
    /**
     * Retorna una cadena con el nombre del tag seguido por sus atributos entre
     * paréntesis, todos separados por comas.
     * @return 
     */
    @Override
    public String toString(){
        
        String aux, ret = name + " (";
        int i = 0;
        
        for (Enumeration<String> e = atts.keys(); e.hasMoreElements();) {
            
            aux = e.nextElement();
            ret += aux + " = " + atts.get(aux) +
                    (i++ < atts.size() - 1 ? ", " : ")");
            
        }
        
        return ret;
    } // fin toString
    
    /**
     * Determina la igualdad entre dos tags, tomando como criterio de igualdad
     * sus nombres.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj){
        
        return name.equals(((XTag)obj).getName());
        
    } // fin equals
    
}
