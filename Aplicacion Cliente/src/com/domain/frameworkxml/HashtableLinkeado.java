/*
 * Clase que encapsula una hastable que recibe como elementos listas enlazadas
 * de un objeto genérico en T
 */
package com.domain.frameworkxml;

import java.util.Hashtable;
import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Edu
 * @param <T>
 */
public class HashtableLinkeado<T> {
    
    private Hashtable<String,LinkedList<T>> tabla;
    private ArrayList<String> claves;
    
    /**
     * Crea un hashtable que almacena listas enlazadas, de modo que se puedan
     * almacenar elementos que tengan una misma key. Gestiona internamente las
     * keys almacenándolas en una colección.
     */
    public HashtableLinkeado(){
        
        tabla = new Hashtable<>();
        claves = new ArrayList<>();
        
    }
    
    
    public void put(String key, T elm){ // agrega listas a la tabla o las crea
        LinkedList<T> lst = tabla.get(key);
        // En el caso de que la lista no exista dentro de la tabla, la crea
        if(lst == null){
            lst = new LinkedList<>();
            tabla.put(key, lst);
            claves.add(key); // añado la clave a la colección de claves
        }
        lst.add(elm);
    } // fin put()
    
    /**
     * Retorna una LinkedList que contiene todos los elementos que coincidan con
     * la key especificada.
     * @param key
     * @return 
     */
    public LinkedList<T> get(String key){ 
        return tabla.get(key);
    }
    
    /**
     * Retorna una colección con todas las keys que tiene el Hastable
     * @return 
     */
    public Collection<String> key(){
        return claves;
    }
    
}
