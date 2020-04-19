/*
 * JDialog usado para mostrar la información de los movimientos ó interesados
 */
package com.domain.gui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import com.domain.dto.MovimientoDTO;
import com.domain.dto.InteresadoDTO;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class JDialogInfo extends JDialog{
    
    private JTextArea tInfo;
    
    public JDialogInfo(ArrayList<MovimientoDTO> coll){
        setVisible(true);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Muestro el texto
        String aux = "";
        
        for(MovimientoDTO dto : coll){
            
            aux += "Motivo del movimiento: " + dto.getMotivo();
            aux += "Fecha del movimiento: " + dto.getFecha() + "\n";
            aux += "Area de origen: " + dto.getAreaOrigen() + "\n";
            aux += "Area de destino: " + dto.getAreaDestino() + "\n\n";
            
        }
        
        tInfo.setText(aux);
        tInfo.setEditable(false);
    }
    
    public JDialogInfo(InteresadoDTO dto){
        
        setVisible(true);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        tInfo.setText( "Datos del interesado:\n" +
                       "Nombre: " + dto.getNombre() + "\n" +
                       "DNI nro: " + dto.getDni() + "\n" +
                       "Sexo: " + 
                       ((dto.getSexo() == 'M') ? "masculino" : "femenino") + 
                       "\nOcupación: " + dto.getOcupacion() + "\n" +
                       "Fecha de nacimiento: " + dto.getFechaNacimiento());
        tInfo.setEditable(false);
    }
    
    private void initComponents(){
        
        JPanel panel = new JPanel();
        tInfo = new JTextArea(40, 40);
        panel.add(tInfo);
        this.add(panel);
        
    }
    
}
