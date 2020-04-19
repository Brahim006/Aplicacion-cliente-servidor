/*
 * Interfaz de usuario que interacciona con los clientes de cada área según los
 * permisos a los que el usuario tenga acceso.
 */
package com.domain.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.border.Border;
import com.domain.dto.*;
import com.domain.frameworkxml.XMLFactory;
import com.domain.utils.ServicesLocator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Brahim
 */
public class InterfazCliente extends JFrame{
    
    private JTabbedPane pestanias;
    private JTable tabla, tablaFinalizar;
    private JTextArea taMotivo, taObservacionTramite,
            taPermisos;
    private JButton bConsultarInteresado, bConsultarMovimientos, bRecibir,
            bEnviar, bIneteresadoResetear, bTramiteResetear, bCrearTramite,
            bFinalizar, bPorDefecto, bAplicarCambios, bDeslogear;
    private JTextField tfInteresadoNombre, tfInteresadoDni, tfTramiteAsunto,
            tfTramiteId, tfFechaIngreso, tfOcupacionInteresado,
            tfFechaNacInter;
    private JRadioButton radHombre, radMujer;
    private ButtonGroup bg;
    private Border border;
    private JComboBox<String> jcTramites, jcArea, jcAreaDestino,
            jcTamanioVentana, jcIdioma, jcEstiloVentana;
    private DefaultTableModel modelTabla, modelTablaFinalizar;
    private UserDTO usr;
    private ActionListener listener;
    private ActualizadorDeComponentes actualizador;
    
    // Constantes estáticas
    public static final String[] NOMBRE_COLUMNAS =
                {"Identificador","Asunto","Interesado","Estado","Area Creadora",
                "Area de Destino", "Area actual", "Fecha de creacion"};
    
    public InterfazCliente(UserDTO usr){
        super("Gestor de trámites - Cliente");
        this.usr = usr;
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        border = BorderFactory.createEtchedBorder(Color.lightGray,
                Color.DARK_GRAY);
        listener = new BotonListener();
        initComponents(usr);
        pack();
        setLocationRelativeTo(null);
        actualizador =  new ActualizadorDeComponentes();
    }
    
    // Método de uso privado para inicializar componentes
    private void initComponents(UserDTO usr){
        
        pestanias = new JTabbedPane();
        pestanias.add("Bandeja de entrada", crearBandejaEntrada(usr.getArea()));
        pestanias.add("Enviar", crearPestaniaEnviar());
        if(usr.isCreador())
            pestanias.add("Crear Tramite", crearPestaniaCrear());
        if(usr.isFinalizador())
            pestanias.add("Finalizar Trámites", crearPestaniaFinalizar());
        pestanias.add("Configuración", crearPestaniaConfig(usr));
        this.add(pestanias);

    }
    
    // Métodos de uso interno para crear las pestañas de la ventana
    
    private JPanel crearBandejaEntrada(String nombreArea){
        
        JPanel bandeja = new JPanel(new BorderLayout());
        
        // Zona norte de la pestaña
        JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pNorte.add(new JLabel(nombreArea));
        TitledBorder bordeN = new TitledBorder(border, "Area");
        pNorte.setBorder(bordeN);
        bandeja.add(pNorte, BorderLayout.NORTH);
        
        // Zona centro de la pestaña        
        modelTabla = new DefaultTableModel(new String[30][8],
                NOMBRE_COLUMNAS);
        tabla = new JTable(modelTabla);
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);
        JScrollPane scroll = new JScrollPane(tabla);
        TitledBorder bordeC = new TitledBorder(border, "Info de trámites");
        scroll.setBorder(bordeC);
        bandeja.add(scroll, BorderLayout.CENTER);
        
        // Zona sur de la pestaña
        JPanel pSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bRecibir = new JButton("Recibir");
        bRecibir.addActionListener(listener);
        pSur.add(bRecibir);
        bConsultarInteresado = new JButton("Consultar interesado");
        bConsultarInteresado.addActionListener(listener);
        pSur.add(bConsultarInteresado);
        bConsultarMovimientos = new JButton("Consultar Movimientos");
        bConsultarMovimientos.addActionListener(listener);
        pSur.add(bConsultarMovimientos);
        TitledBorder bordeS = new TitledBorder(border, "Acciones");
        pSur.setBorder(bordeS);
        bandeja.add(pSur, BorderLayout.SOUTH);
        
        return bandeja;
        
    } // fin crearBandejaEntrada
    
    private JPanel crearPestaniaEnviar(){
        
        JPanel enviar = new JPanel(new BorderLayout());
        
        // panel norte
        JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pNorte.add(new JLabel("Trámite"));
        jcTramites = new JComboBox<>();
        jcTramites.addItem("Asunto");
        pNorte.add(jcTramites);
        enviar.add(pNorte, BorderLayout.NORTH);
        
        // panel central
        JPanel pCentro = new JPanel();
        taMotivo = new JTextArea(10, 25);
        JScrollPane scroll = new JScrollPane(taMotivo);
        pCentro.add(scroll);
        TitledBorder bordeCentro =  new TitledBorder(border, "Motivo");
        pCentro.setBorder(bordeCentro);
        enviar.add(pCentro);
        
        // panel sur
        JPanel pSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jcArea = new JComboBox<>();
        jcArea.addItem("Area");
        pSur.add(jcArea);
        bEnviar = new JButton("Enviar");
        bEnviar.addActionListener(listener);
        pSur.add(bEnviar);
        TitledBorder bordeSur = new TitledBorder(border, "Enviar");
        pSur.setBorder(bordeSur);
        enviar.add(pSur, BorderLayout.SOUTH);
        
        return enviar;
        
    } // fin crearPestaniaEnviar
    
    private JPanel crearPestaniaCrear(){
        
        JPanel crear = new JPanel(new BorderLayout());
        
        // Panel norte
        JPanel pNorte = new JPanel(new FlowLayout());
        
        // subpanel izquierdo
        JPanel subPanelIzq = new JPanel();
        subPanelIzq.setLayout(new BoxLayout(subPanelIzq, BoxLayout.Y_AXIS));
        subPanelIzq.add(new JLabel("Nombre y apellido:"));
        tfInteresadoNombre = new JTextField(null, 20);
        subPanelIzq.add(tfInteresadoNombre);
        subPanelIzq.add(new JLabel("DNI N°:"));
        tfInteresadoDni = new JTextField(null, 8);
        subPanelIzq.add(tfInteresadoDni);
        subPanelIzq.add(new JLabel("Sexo:"));
        
        bg = new ButtonGroup(); // botones radiales para el sexo
        radHombre = new JRadioButton("Masculino");
        bg.add(radHombre);
        radMujer = new JRadioButton("Femenino");
        bg.add(radMujer);
        subPanelIzq.add(radHombre);
        subPanelIzq.add(radMujer);
        
        subPanelIzq.add(new JLabel("Fecha de nacimiento(dd/mm/aaaa):"));
        tfFechaNacInter = new JTextField(null, 8);
        subPanelIzq.add(tfFechaNacInter);
        subPanelIzq.add(new JLabel("Ocupación:"));
        tfOcupacionInteresado = new JTextField("", 8);
        subPanelIzq.add(tfOcupacionInteresado);
        bIneteresadoResetear = new JButton("Resetear");
        bIneteresadoResetear.addActionListener(listener);
        subPanelIzq.add(bIneteresadoResetear);
        TitledBorder bordeSPIzq = new TitledBorder(border,
                                  "Datos del Interesado");
        subPanelIzq.setBorder(bordeSPIzq);
        pNorte.add(subPanelIzq);
        
        // Creación del subpanel derecho
        JPanel subPanelDer = new JPanel();
        subPanelDer.setLayout(new BoxLayout(subPanelDer, BoxLayout.Y_AXIS));
        subPanelDer.add(new JLabel("Asunto:"));
        tfTramiteAsunto = new JTextField(20);
        subPanelDer.add(tfTramiteAsunto);
        subPanelDer.add(new JLabel("ID del trámite:"));
        tfTramiteId = new JTextField(20);
        subPanelDer.add(tfTramiteId);
        subPanelDer.add(new JLabel("Fecha de ingreso(dd/mm/aaaa):"));
        tfFechaIngreso = new JTextField(20);
        subPanelDer.add(tfFechaIngreso);
        subPanelDer.add(new JLabel("Área de destino:"));
        jcAreaDestino = new JComboBox<>();
        jcAreaDestino.addItem("Area");
        subPanelDer.add(jcAreaDestino);
        bTramiteResetear = new JButton("Resetear");
        bTramiteResetear.addActionListener(listener);
        subPanelDer.add(bTramiteResetear);
        TitledBorder bordeSPDer = new TitledBorder(border, "Datos del trámite");
        subPanelDer.setBorder(bordeSPDer);
        pNorte.add(subPanelDer);
        
        crear.add(pNorte, BorderLayout.NORTH);
        
        // creación del panel sur
        
        JPanel pSur = new JPanel();
        pSur.setLayout(new BoxLayout(pSur, BoxLayout.Y_AXIS));
        taObservacionTramite = new JTextArea(10, 20);
        JScrollPane scroll2 = new JScrollPane(taObservacionTramite);
        pSur.add(scroll2);
        bCrearTramite = new JButton("Crear");
        bCrearTramite.addActionListener(listener);
        pSur.add(bCrearTramite);
        TitledBorder bordeSur = new TitledBorder(border, "Observaciones");
        pSur.setBorder(bordeSur);
        crear.add(pSur, BorderLayout.SOUTH);
        
        return crear;
        
    } // fin crearPestaniaCrear
    
    private JPanel crearPestaniaFinalizar(){
        
        JPanel finalizar = new JPanel();
        finalizar.setLayout(new BoxLayout(finalizar, BoxLayout.Y_AXIS));
        
        
        modelTablaFinalizar = new DefaultTableModel(new String[30][8],
                NOMBRE_COLUMNAS);
        tablaFinalizar = new JTable(modelTablaFinalizar);
        tablaFinalizar.setRowSelectionAllowed(true);
        tablaFinalizar.setColumnSelectionAllowed(false);
        JScrollPane scroll = new JScrollPane(tablaFinalizar);
        finalizar.add(scroll);
        
        bFinalizar = new JButton("Finalizar");
        bFinalizar.addActionListener(listener);
        finalizar.add(bFinalizar);
        
        TitledBorder borde = new TitledBorder(border, "Finalizar Trámites");
        finalizar.setBorder(borde);
        
        return finalizar;
        
    } // fin crearPestaniaFinalizar
    
    private JPanel crearPestaniaConfig(UserDTO usr){
        
        JPanel configuracion = new JPanel(new FlowLayout());
        
        // Panel izquierdo
        JPanel pIzquierdo = new JPanel();
        pIzquierdo.setLayout(new BoxLayout(pIzquierdo, BoxLayout.Y_AXIS));
        pIzquierdo.add(new JLabel("Tamaño de la ventana"));
        
        jcTamanioVentana = new JComboBox<>();
        jcTamanioVentana.addItem("Defecto");
        jcTamanioVentana.addItem("Pantalla Completa");
        pIzquierdo.add(jcTamanioVentana);
        
        pIzquierdo.add(new JLabel("Idioma"));
        jcIdioma = new JComboBox<>();
        jcIdioma.addItem("Español");
        jcIdioma.addItem("Inglés");
        jcIdioma.addItem("Portugués");
        pIzquierdo.add(jcIdioma);
        
        pIzquierdo.add(new JLabel("Estilo de la ventana"));
        jcEstiloVentana = new JComboBox<>();
        jcEstiloVentana.addItem("Nimbus");
        jcEstiloVentana.addItem("Metal");
        pIzquierdo.add(jcEstiloVentana);
        
        bPorDefecto = new JButton("Valores por Defecto");
        pIzquierdo.add(bPorDefecto);
        
        bAplicarCambios = new JButton("Aplicar");
        pIzquierdo.add(bAplicarCambios);
        
        TitledBorder bordeIzq = new TitledBorder(border, "Configuración");
        pIzquierdo.setBorder(bordeIzq);
        configuracion.add(pIzquierdo);
        
        // Panel derecho
        JPanel pDerecho = new JPanel();
        pDerecho.setLayout(new BoxLayout(pDerecho, BoxLayout.Y_AXIS));
        pDerecho.add(new JLabel("Nombre del Usuario: " + usr.getNombre()));
        pDerecho.add(new JLabel("DNI: " + usr.getDni()));
        pDerecho.add(new JLabel("Area: " + usr.getArea()));
        pDerecho.add(new JLabel("Permisos"));
        taPermisos = new JTextArea(20, 20);
        
        // descripción de los permisos de usuario
        String aux = "";
        
        if(!usr.isCreador() && !usr.isFinalizador()){
            aux = "Este usuario no tiene permisos especiales.";
        } else{
            if(usr.isCreador())
                aux += "Este usuario tiene permisos para crear trámites.\n";
            if(usr.isFinalizador())
                aux += "Este usuario tiene permisos para finalizar trámites.";
        }
        
        taPermisos.setText(aux);
        taPermisos.setEditable(false);
        pDerecho.add(taPermisos);
        
        bDeslogear = new JButton("Cerrar sesión");
        bDeslogear.addActionListener(listener);
        pDerecho.add(bDeslogear);
        
        TitledBorder bordeDer = new TitledBorder(border, "Datos de Usuario");
        pDerecho.setBorder(bordeDer);
        configuracion.add(pDerecho);
        
        return configuracion;
        
    } // fin crearPestaniaConfig
    
    /**
     * Clase interna que se encarga de tener toda la información de los 
     * componentes actualizada y correspondiente a la base de datos del server
     */
    class ActualizadorDeComponentes extends Thread{
        
        public ActualizadorDeComponentes(){
            this.start();
        }
        
        @Override
        public void run(){
            
            int s = Integer.parseInt(XMLFactory
                    .getByPath("/client-config/actualization-rate")
                    .getAtts().get("seconds"));
            
            int cont = 0;
            
            try {
                
                while(true){
                    
                    actualizarTablas();
                    actualizarComboBoxTramites();
                    
                    // controla que las áreas se actualizen cada s minutos
                    if(cont == 0) actualizarComboBoxAreas();
                    else if (cont == 60) cont = 0;
                    
                    ++cont;
                    sleep(s * 1000);
                    
                }
                
            } catch (InterruptedException e) {
            }
            
        }
        
        private void actualizarTablas(){
            
            ArrayList<TramiteDTO> coll = ServicesLocator
                    .obtenerTramitePorAreaActual(usr.getArea());
            
            if(coll != null){
                
                //vacío ambas tablas
                for (int i = 29; i > 0; i--) {   
                    for (int j = 7; j > 0; j--) {
                        modelTabla.setValueAt("", i, j);
                        modelTablaFinalizar.setValueAt("", i, j);
                    }
                }
                
                // inserto los datos en ambas tablas
                int cont = 0;
                for(TramiteDTO dto : coll){
                    // tabla de la bandeja de entrada
                    modelTabla.setValueAt(Integer
                            .toString(dto.getId()), cont, 0);
                    modelTabla.setValueAt(dto.getAsunto(), cont, 1);
                    modelTabla.setValueAt(Integer
                            .toString(dto.getDniInteresado()), cont, 2);
                    modelTabla.setValueAt(dto.getEstado(), cont, 3);
                    modelTabla.setValueAt(dto.getAreaCreadora(), cont, 4);
                    modelTabla.setValueAt(dto.getAreaDestino(), cont, 5);
                    modelTabla.setValueAt(dto.getAreaAcual(), cont, 6);
                    modelTabla.setValueAt(dto.getFechaCreacion(), cont, 7);
                    
                    // tabla de la pestaña finalizar
                    modelTablaFinalizar.setValueAt(Integer
                            .toString(dto.getId()), cont, 0);
                    modelTablaFinalizar.setValueAt(dto.getAsunto(), cont, 1);
                    modelTablaFinalizar.setValueAt(Integer
                            .toString(dto.getDniInteresado()), cont, 2);
                    modelTablaFinalizar.setValueAt(dto.getEstado(), cont, 3);
                    modelTablaFinalizar.setValueAt(dto
                            .getAreaCreadora(), cont, 4);
                    modelTablaFinalizar.setValueAt(dto
                            .getAreaDestino(), cont, 5);
                    modelTablaFinalizar.setValueAt(dto
                            .getAreaAcual(), cont, 6);
                    modelTablaFinalizar.setValueAt(dto
                            .getFechaCreacion(), cont, 7);
                    ++cont;
                }
                
            }
            
        } // fin actualizarTablas
        
        private void actualizarComboBoxAreas(){
            
            ArrayList<AreaDTO> coll = ServicesLocator.obtenerTodasLasAreas();
            
            if(coll != null){
                
                jcArea.removeAllItems();
                jcAreaDestino.removeAllItems();
            
                for(AreaDTO dto : coll){
                    if(!dto.getNombre().equals(usr.getArea())){
                        jcArea.addItem(dto.getNombre());
                        jcAreaDestino.addItem(dto.getNombre());
                    }
                }
                
            }
            
        } // fin actualizarComboBoxAreas
        
        private void actualizarComboBoxTramites(){
            
            ArrayList<TramiteDTO> coll = ServicesLocator
                    .obtenerTramitePorAreaActual(usr.getArea());
            
            if(coll != null){
                
                jcTramites.removeAllItems();
                for(TramiteDTO dto : coll){
                    // Se aseguro de que sólo se muestren trámites válidos
                    if(dto.getEstado().equals("Recibido") ||
                            dto.getEstado().equals("Creado"))
                        jcTramites.addItem(dto.getId() + ":" + dto.getAsunto());
                    
                }
                
            }
            
        } // fin actualizarComboBoxTramites
        
    } // fin clase ActualizadorDeComponentes
    
    /*
     * Listener para todos los botones de esta ventana
     */
    class BotonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        
            if(e.getSource() == bRecibir)
                recibirTramite();
            
            if(e.getSource() == bConsultarInteresado)
                consultarInteresado();
            
            if(e.getSource() == bConsultarMovimientos)
                consultarMovimientos();
            
            if(e.getSource() == bEnviar)
                enviarTramite();
            
            if(e.getSource() == bIneteresadoResetear)
                resetearDatosInteresado();
            
            if(e.getSource() == bTramiteResetear)
                resetearDatosTramite();
            
            if(e.getSource() == bCrearTramite)
                crearTramite();
            
            if(e.getSource() == bFinalizar)
                finalizarTramite();
            
            if(e.getSource() == bDeslogear)
                cerrarSesion();
            
        } // fin actionPerformed
 
        /* Recibe el trámite seleccionado de la tabla siemrpe y cuando este 
         * tenga el estado "en curso" 
         */
        private void recibirTramite(){
            
            int n = tabla.getSelectedRow();
            
            if(n != -1){
                
                switch((String)tabla.getValueAt(n, 3)){
                    case "En curso":
                        // modifico el estado del trámite
                        int id = Integer.parseInt((String)tabla.getValueAt(n, 0));
                        ServicesLocator.modificarEstadoTramite(id,"Recibido");
                        JOptionPane.showMessageDialog(null,
                        "El trámite se ha Recibido correctamente."
                        , "Listo", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "Finalizado":
                        JOptionPane.showMessageDialog(null,
                        "El trámite ya se ha finalizado."
                        , "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Recibido":
                        JOptionPane.showMessageDialog(null,
                        "El trámite ya se ha Recibido."
                        , "Error", JOptionPane.ERROR_MESSAGE);
                    case "Creado":
                        JOptionPane.showMessageDialog(null,
                        "El trámite aún no se ha movido."
                        , "Error", JOptionPane.ERROR_MESSAGE);
                }
               
            } else
                JOptionPane.showMessageDialog(null,
                        "No se ha seleccionado una fila", "Error",
                        JOptionPane.ERROR_MESSAGE);
            
        } // fin recibirTramite
        
        /*
         * Muestra la información del interesado del trámite seleccionado
         */
        private void consultarInteresado(){
            
            int n = tabla.getSelectedRow();
            
            if(n != -1){
                
                // obtengo los datos del interesado
                int dni = Integer.parseInt((String)tabla.getValueAt(n, 2));
                InteresadoDTO dto = ServicesLocator.obtenerInteresadoPorDni(dni);
                JDialogInfo jd = new JDialogInfo(dto);
            } else
                JOptionPane.showMessageDialog(null,
                        "No se ha seleccionado una fila", "Error",
                        JOptionPane.ERROR_MESSAGE);
            
        } // fin consultarInteresado
        
        private void consultarMovimientos(){
            
            int n = tabla.getSelectedRow();
            
            if(n != -1){
                int id = Integer.parseInt((String)tabla.getValueAt(n, 0));
                ArrayList<MovimientoDTO> coll = ServicesLocator
                        .obtenerMovimientosPorIdTramite(id);
                
                if(coll != null){
                    
                    JDialogInfo jd = new JDialogInfo(coll);
                    
                } else
                    JOptionPane.showMessageDialog(null,
                        "Este trámite no tiene movimientos aún", "!!!",
                        JOptionPane.INFORMATION_MESSAGE);
                
            } else
                JOptionPane.showMessageDialog(null,
                        "No se ha seleccionado una fila", "Error",
                        JOptionPane.ERROR_MESSAGE);
            
        } // fin consultarMovimientos
        
        private void enviarTramite(){
            
            try {
                
                // creo el movimiento correspondiente
                String tramiteAux = (String)jcTramites.getSelectedItem();
                StringTokenizer st = new StringTokenizer(tramiteAux, ":");
                int idTramite = Integer.parseInt(st.nextToken());
                String motivo = taMotivo.getText();
                String areaDestino = (String)jcArea.getSelectedItem();
                // Obtengo la fecha actual
                Calendar c = Calendar.getInstance();
                int dia = c.get(Calendar.DATE);
                int mes = c.get(Calendar.MONTH);
                int anio = c.get(Calendar.YEAR);
                String fecha = dia + "/" + mes + "/" + anio;
                
                ServicesLocator.crearMovimiento(usr.getArea(), areaDestino,
                        idTramite, fecha, motivo);
                
                ServicesLocator.modificarAreaActual(idTramite, areaDestino);
                ServicesLocator.modificarEstadoTramite(idTramite, "En curso");
                
                taMotivo.setText("");
                jcArea.removeItem(tramiteAux);
                
                JOptionPane.showMessageDialog(null,
                        "Trámite enviado correctamente", "Listo",
                        JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Error al intentar enviar trámite", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            
        } // fin enviarTramite
        
        private void resetearDatosInteresado(){
            
            tfInteresadoNombre.setText("");
            tfInteresadoDni.setText("");
            radHombre.setSelected(true);
            tfFechaNacInter.setText("");
            tfOcupacionInteresado.setText("");
            
        } // fin resetearDatosInteresado
        
        private void resetearDatosTramite(){
            
            tfTramiteAsunto.setText("");
            tfTramiteId.setText("");
            tfFechaIngreso.setText("");
            taObservacionTramite.setText("");
            
        } // fin resetearDatosTramite
        
        private void crearTramite(){
            
            try {
                
                String nombreInteresado = tfInteresadoNombre.getText();
                int interesadoDni = Integer.parseInt(tfInteresadoDni.getText());
                char sexo;
                if(radMujer.isSelected()) sexo = 'F';
                else sexo = 'M';
                String fechaInteresado = tfFechaNacInter.getText();
                String ocupacion = tfOcupacionInteresado.getText();
                
                if(!ServicesLocator.crearInteresado(nombreInteresado,
                        interesadoDni, sexo, ocupacion, fechaInteresado))
                    throw new NumberFormatException("Error de formato");
                
                String asuntoTramite = tfTramiteAsunto.getText();
                int idTramite = Integer.parseInt(tfTramiteId.getText());
                String fechaTramite = tfFechaIngreso.getText();
                String areaDestino = (String)jcAreaDestino.getSelectedItem();
                String motivoTramite = taObservacionTramite.getText();
                
                if(!ServicesLocator.crearTramite(asuntoTramite, idTramite,
                        areaDestino, usr.getArea(), interesadoDni,
                        motivoTramite, fechaTramite, "Creado", usr.getArea()))
                    throw new NumberFormatException("Error de formato");
                else {
                    JOptionPane.showMessageDialog(null,
                            "Trámite creado exitosamente.", "Tramite creado",
                            JOptionPane.INFORMATION_MESSAGE);
                    resetearDatosInteresado();
                    resetearDatosTramite();
                }
                
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,
                    "Verifique que todos los datos están ingresados correctamente"
                    , "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Verifique que todos los datos están ingresados correctamente"
                    , "Error al intentar crear trámite",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } // fin crearTramite
        
        private void finalizarTramite(){
            
            int n = tabla.getSelectedRow();
            
            if(n != -1){
                
                switch((String)tabla.getValueAt(n, 3)){
                    case "En curso":
                        JOptionPane.showMessageDialog(null,
                        "El trámite debe recibirse antes de finalizar"
                        , "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Finalizado":
                        JOptionPane.showMessageDialog(null,
                        "El trámite ya se ha finalizado."
                        , "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Recibido":
                        // modifico el estado del trámite
                        int id = (Integer)tabla.getValueAt(n, 3);
                        ServicesLocator.modificarEstadoTramite(id,"Finalizado");
                        JOptionPane.showMessageDialog(null,
                        "El trámite se ha finalizado correctamente."
                        , "Listo", JOptionPane.INFORMATION_MESSAGE);
                    case "Creado":
                        JOptionPane.showMessageDialog(null,
                        "No se pueden finalizar trámites sin movimientos"
                        , "Error", JOptionPane.ERROR_MESSAGE);
                }
               
            } else
                JOptionPane.showMessageDialog(null,
                        "No se ha seleccionado una fila", "Error",
                        JOptionPane.ERROR_MESSAGE);
            
        } // fin crearTramite
        
        public void cerrarSesion(){
            
            // TODO: programar esto
            
        } // fin cerrarSesion
        
    } // fin clase BotonListener
    
}
