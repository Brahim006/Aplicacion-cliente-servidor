/*
 * Clase utilitaria que se encarga de transformar objetos sql.Date en cadenas de
 * texto con formato dd/mm/aaaa y viceversa
 */
package com.domain.utils;

import java.sql.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Edu
 */
public class UDate {
    
    /**
     * Toma un objto sql.Date y lo transforma en un string con el formato 
     * dd/mm/aaaa
     * @param date
     * @return 
     */
    public static String dateToString(Date date){
        
        String data = date.toString();
        String[] aux = new String[3];
        int cont = 0;
        StringTokenizer st = new StringTokenizer(data, "-");
        
        while(st.hasMoreTokens()){
            aux[cont] = st.nextToken();
            ++cont;
        }
        
        return aux[2] + "/" + aux[1] + "/" + aux[0];
        
    } // fin dateToString
    
    /**
     * Recibe un string con una fecha en formato dd/mm/aaaa y lo retorna como
     * un objeto sql.Date
     * @param date
     * @return 
     */
    public static Date stringToDate(String date){
        
        StringTokenizer st = new StringTokenizer(date, "/");
        
        int dia = Integer.parseInt(st.nextToken());
        int mes = Integer.parseInt(st.nextToken()) - 1;
        int anio = Integer.parseInt(st.nextToken()) - 1900;
        
        return new Date(anio, mes, dia);
        
    } // fin stringToDate
    
}
