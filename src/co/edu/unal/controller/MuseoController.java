
package co.edu.unal.controller;

import co.edu.unal.view.MuseoView;
import co.edu.unal.model.Museo;
import co.edu.unal.access.MuseoDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author Admin
 */
public class MuseoController {
    
    private MuseoView vista;
    private MuseoDAO modelo;
    
     /**
     * @param vista Instancia de la vista de Museo
     * @param modelo Instancia del modelo de Museo
     */
    public MuseoController(MuseoView vista, MuseoDAO modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        this.vista.addListenerBtnNuevo(new MuseoListener());
        this.vista.addListenerBtnModificar(new MuseoListener());        
        this.vista.addListenerBtnBorrar(new MuseoListener());
        this.vista.addListenerBtnCerrar(new MuseoListener());               
        
        ArrayList<Museo> listadoMuseos;
        
        // ------------------MySQL Local------------------------
        //listadoMuseos = this.modelo.getListMuseos(0);
        
        // ------------------MySQL Remote------------------------
        listadoMuseos = this.modelo.getListMuseums();
        
        
        this.vista.cargarMuseos(listadoMuseos);
                
    }
               
    class MuseoListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(e.getActionCommand().equalsIgnoreCase("nuevo")){
                vista.nuevoAction();
            }else if(e.getActionCommand().equalsIgnoreCase("cancelar")){
                vista.nuevoAction();
            }else if(e.getActionCommand().equalsIgnoreCase("modificar")){
                vista.modificarAction();
            }else if(e.getActionCommand().equalsIgnoreCase("salir")){
                vista.cerrarAction();
            }else if(e.getActionCommand().equalsIgnoreCase("grabar")){     
                registrar();
            }else if(e.getActionCommand().equalsIgnoreCase("borrar")){
                borrar();
            }else if(e.getActionCommand().equalsIgnoreCase("actualizar")){
                actualizar();
            }
        }
               

        private void registrar(){
           if(vista.getCodigo() == 0){
                vista.gestionMensajes("Ingrese el código",
                        "Error de Entrada", JOptionPane.ERROR_MESSAGE);                                  
            }else{
                
               Museo museo = new Museo();
                museo.setMu_id(vista.getCodigo());
                museo.setMu_nombre(vista.getNombre());                                

                int tamaño;
                tamaño = modelo.getListMuseos(museo.getMu_id()).size();

                if(tamaño == 0){
                    int resultado = 0;
                    // ------------------MySQL Local------------------------
                    //resultado = modelo.saveMuseo(museo);
                    // ------------------MySQL Local------------------------
                    resultado = modelo.saveMuseum(museo);
                    if(resultado == 1){
                        vista.gestionMensajes("Registro Grabado con éxito", 
                                "Confirmación",JOptionPane.INFORMATION_MESSAGE); 
                        
                        ArrayList<Museo> listadoMuseos;
                        // ------------------MySQL Local------------------------
                        //listadoMuseos = modelo.getListMuseos(0);

                        // ------------------MySQL Remote------------------------
                        listadoMuseos = modelo.getListMuseums();
                        vista.cargarMuseos(listadoMuseos);

                        vista.activarControles(false); 
                        vista.nuevoAction();
                    }
                    else{
                        vista.gestionMensajes("Error al grabar",
                                "Confirmación",JOptionPane.ERROR_MESSAGE);                                                 
                    }
                }else{
                    vista.gestionMensajes("Codigo ya está registrado",
                            "Confirmación",
                            JOptionPane.ERROR_MESSAGE);                                      
                }
            }
        }
        
        private void borrar(){
            int codigo = 0;
            codigo  = vista.getCodigo();
            
            if(codigo == 0){
                 vista.gestionMensajes(
                         "Por favor seleccione un programa de la tabla",
                         "Mensaje de Advertencia ", 
                    JOptionPane.ERROR_MESSAGE);
            }else{
                int respuesta = JOptionPane.showConfirmDialog(null,
                        "¿Desea Eliminar la museo de codigo : " +
                        codigo + " ?", 
                        "Confirmación de Acción", JOptionPane.YES_NO_OPTION);
                
                if(respuesta == JOptionPane.YES_OPTION){                    
                    // ------------------MySQL Local------------------------
                    //if(modelo.deleteMuseo(codigo) ==  1){
                    // ------------------MySQL Remote------------------------
                    if(modelo.deleteMuseum(codigo) ==  1){
                        JOptionPane.showMessageDialog(null, 
                                "Registro Borrado con éxtio", 
                                "Confirmación de acción", 
                                JOptionPane.INFORMATION_MESSAGE);                    
                       
                        ArrayList<Museo> listadoMuseos; 
                        // ------------------MySQL Local------------------------
                        //listadoMuseos = modelo.getListMuseos(0);

                        // ------------------MySQL Remote------------------------
                        listadoMuseos = modelo.getListMuseums();
                        vista.cargarMuseos(listadoMuseos);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, 
                                "Error al borrar",
                                "Confirmación de acción", 
                                JOptionPane.ERROR_MESSAGE);                    
                    }
                }
            }
        } 
        
        private void actualizar(){
            //Se crea el objeto museo 
           Museo museo = new Museo();
             
            //Se configura los datos en el objeto museo de la clase
            //Museo
            museo.setMu_id(vista.getCodigo());
            museo.setMu_nombre(vista.getNombre());                                          
    
            // ------------------MySQL Local------------------------
            //if(modelo.updateMuseo(museo) == 1){
            // ------------------MySQL Remote------------------------
            if(modelo.updateMuseum(museo) == 1){
                vista.gestionMensajes(
                        "Actualización exitosa",
                        "Confirmación ", 
                        JOptionPane.INFORMATION_MESSAGE);
                                        
                vista.activarControles(false); 
                vista.nuevoAction();
                
                ArrayList<Museo> listadoMuseos; 
                // ------------------MySQL Local------------------------
                //listadoMuseos = modelo.getListMuseos(0);

                // ------------------MySQL Remote------------------------
                listadoMuseos = modelo.getListMuseums();
                vista.cargarMuseos(listadoMuseos);           
            } else {
                vista.gestionMensajes(
                        "Actualización Falida",
                        "Confirmación ", 
                        JOptionPane.ERROR_MESSAGE);                 
            }       
        } 
    }
}
