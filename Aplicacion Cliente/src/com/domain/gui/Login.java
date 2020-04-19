/*
 * Ventana que representa al login del usuario
 */
package com.domain.gui;

import com.domain.dto.UserDTO;
import com.domain.utils.ServicesLocator;
import com.domain.gui.InterfazCliente;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Login extends JFrame{
    
    private JTextField tfUsuario, tfContrasenia;
    private JButton bIngresar;

    public static Login instancia = null;
    
    public Login(){
        
        super("Login - Cliente");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setSize(200,300);
        setResizable(false);
        setLocationRelativeTo(null);
        
    }
    
    /**
     * Método estático para acceder a esta instancia única de Login
     * @return 
     */
    public static Login getInstancia(){
        
        if(instancia == null)
            return new Login();
        else return instancia;
        
    } // fin getInstancia
    
    private void initComponents(){
        
        this.setLayout(new BorderLayout());
        
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        panel1.add(new JLabel("Login"));
        panel2.add(new JLabel("Usuario:"));
        tfUsuario = new JTextField(20);
        panel2.add(tfUsuario);
        panel2.add(new JLabel("Contraseña:"));
        tfContrasenia = new JTextField(20);;
        panel2.add(tfContrasenia);
        bIngresar = new JButton("Ingresar");
        bIngresar.addActionListener(new IngresarListener());
        panel3.add(bIngresar);
        
        this.add(panel1,BorderLayout.NORTH);
        this.add(panel2,BorderLayout.CENTER);
        this.add(panel3,BorderLayout.SOUTH);
        
    } // fin initComponents
    
    /**
     * Listener para el evento de ingresar
     */
    class IngresarListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        
            // levanto la info de los textfields
            String nickname = tfUsuario.getText();
            String contrasenia = tfContrasenia.getText();
        
            // busco el usuario en el servidor
            UserDTO usr = ServicesLocator.obtenerUsuarioPorNickname(nickname);
            
            if(usr != null){
                
                if(!contrasenia.equals(usr.getPassword()))
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                else{
                    // Invisibilizo la ventana y doy inicio a la interfaz cliente
                    InterfazCliente gui = new InterfazCliente(usr);
                    Login.getInstancia().dispose();
                    
                }
                
            } else
                JOptionPane.showMessageDialog(null,
                "Usuario incorrecto ó problemas para conectarse al servidor.");
            
        }
        
    } // fin IngresarListener
    
}
