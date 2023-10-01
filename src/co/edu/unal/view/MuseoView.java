
package co.edu.unal.view;

import co.edu.unal.model.Museo;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class MuseoView extends JFrame {
    
    private String rutaResources = "/co/edu/unal/resources/";
    
    /**
     * Creates new form Museo
     */
    public MuseoView() {
       
        initComponents();
                                 
    }
    
    //Borra todas la filas del jTable
    private void limpiarListadoTabla(){
        DefaultTableModel modelo;
        modelo = (DefaultTableModel) jtListado.getModel();
        for(int i=modelo.getRowCount()-1; i>=0 ; i--){
            modelo.removeRow(i);
        }
    }
    
    //Carga los datos de los museos en el jTable
    public void cargarMuseos(ArrayList<Museo> listadoMuseos){
        DefaultTableModel modelo;
        modelo = (DefaultTableModel) jtListado.getModel();        
        limpiarListadoTabla();
        for(int i= 0; i < listadoMuseos.size(); i++){
              modelo.addRow(new Object[]{
                listadoMuseos.get(i).getMu_id(),
                listadoMuseos.get(i).getMu_nombre()
              });
        }
    }
    
    public int getCodigo(){
        return Integer.parseInt(txtCodigo.getText().trim());
    }
       
    
    public String getNombre(){
        return txtNombre.getText();
    }
          
    public void addListenerBtnNuevo(ActionListener listenPrograma){
        btnNuevo.addActionListener(listenPrograma);       
    }
    
    public void addListenerBtnModificar(ActionListener listenPrograma){
        btnModificar.addActionListener(listenPrograma);        
    }
    
    public void addListenerBtnBorrar(ActionListener listenPrograma){
        btnBorrar.addActionListener(listenPrograma);        
    }
     
      
    public void addListenerBtnCerrar(ActionListener listenPrograma){
        btnCerrar.addActionListener(listenPrograma);        
    } 
    
    public void gestionMensajes(String mensaje, String titulo, int icono){
         JOptionPane.showMessageDialog(this,mensaje, titulo, icono);
    }
       
    public void activarControles(boolean estado){
        txtCodigo.setEnabled(estado);
        txtNombre.setEnabled(estado);
     
        btnBorrar.setEnabled(!estado);
        btnCerrar.setEnabled(!estado);
        jtListado.setEnabled(!estado);
    }   
    
    
     public void nuevoAction(){          
        if(btnNuevo.getText().equals("Nuevo")){  
            txtCodigo.setText("");
            txtNombre.setText("");
            activarControles(true); 
            btnNuevo.setText("Grabar");
            btnNuevo.setActionCommand("Grabar");            
            btnModificar.setText("Cancelar");
            btnModificar.setActionCommand("Cancelar");
            btnNuevo.setIcon(new ImageIcon(getClass().
                    getResource(rutaResources + "save-program.png"))); // NOI18N
            btnModificar.setIcon(new ImageIcon(getClass().
                    getResource(rutaResources + "cancel.png"))); // NOI18N
            txtCodigo.requestFocusInWindow();
        }
        else{
            activarControles(false);            
            btnNuevo.setText("Nuevo");
            btnNuevo.setActionCommand("Nuevo");
            btnModificar.setText("Modificar");
            btnModificar.setActionCommand("Modificar");
            btnNuevo.setIcon(new ImageIcon(getClass().
                    getResource(rutaResources + "new-program.png"))); // NOI18N
            btnModificar.setIcon(new ImageIcon(getClass().
                    getResource(rutaResources + "update-program.png"))); // NOI18N
            btnNuevo.requestFocusInWindow();
        }
    }    
     
      public void modificarAction(){
        if(btnModificar.getText().equals("Modificar")){
            if(jtListado.getSelectedRow() == -1){
               if(jtListado.getRowCount() == 0){
                   JOptionPane.showMessageDialog(this,"No hay registros");
               }
               else{
                   JOptionPane.showMessageDialog(this,"Seleccione una fila");
               }
            }else{ 
                activarControles(true); 
                txtCodigo.setEnabled(false);
                btnNuevo.setText("Actualizar");
                btnNuevo.setActionCommand("Actualizar");            
                btnModificar.setText("Cancelar");
                btnModificar.setActionCommand("Cancelar");
                btnNuevo.setIcon(new ImageIcon(getClass().
                    getResource(rutaResources + "save-program.png"))); // NOI18N
                btnModificar.setIcon(new ImageIcon(getClass().
                    getResource(rutaResources + "cancel.png"))); // NOI18N
                txtNombre.requestFocusInWindow();
            }
        }else{
            activarControles(false);            
            btnNuevo.setText("Nuevo");
            btnNuevo.setActionCommand("Nuevo");
            btnModificar.setText("Modificar");
            btnModificar.setActionCommand("Modificar");
            btnNuevo.setIcon(new ImageIcon(getClass().
                    getResource(rutaResources + "new-program.png"))); // NOI18N
            btnModificar.setIcon(new ImageIcon(getClass().
                    getResource(rutaResources + "update-program.png"))); // NOI18N
            btnNuevo.requestFocusInWindow();
        }
    }
      
    public void cerrarAction(){
        System.exit(0);
    }
                      
    private void initComponents() {

        setTitle("Gestion de Museos");
        
        setEnabled(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        txtCodigo = new JTextField();
        jLabel3 = new JLabel();
        txtNombre = new JTextField();
        jLabel2 = new JLabel();        
        jPanel2 = new JPanel();
        btnNuevo = new JButton();
        btnBorrar = new JButton();
        btnModificar = new JButton();
        btnCerrar = new JButton();
        jScrollPane1 = new JScrollPane();
        jtListado = new JTable();
        
        

        jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Datos Museo", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new java.awt.Font("Arial", 1, 12))); // NOI18N

        jLabel1.setText("Codigo : ");

        txtCodigo.setEnabled(false);

        jLabel3.setText("Nombre : ");

        txtNombre.setEnabled(false);
        
        

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(
                        GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombre, GroupLayout.DEFAULT_SIZE, 201, 
                            Short.MAX_VALUE)
                    .addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, 111, 
                            GroupLayout.PREFERRED_SIZE))                    
                .addContainerGap(66, Short.MAX_VALUE))
        );
        
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(
                        GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, 
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, 
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(
                        GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2))                    
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(BorderFactory.createTitledBorder(null, "Controles", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new java.awt.Font("Arial", 1, 12))); // NOI18N

        btnNuevo.setIcon(new ImageIcon(getClass().getResource(
                rutaResources + "new-program.png"))); // NOI18N
        btnNuevo.setText("Nuevo");

        btnBorrar.setIcon(new 
        ImageIcon(getClass().getResource(
                rutaResources + "delete-program.png"))); // NOI18N
        btnBorrar.setText("Borrar");

        btnModificar.setIcon(new ImageIcon(getClass().getResource(
                rutaResources + "update-program.png"))); // NOI18N
        btnModificar.setText("Modificar");
        
        btnCerrar.setIcon(new ImageIcon(getClass().getResource(
                rutaResources + "close.png"))); // NOI18N
        btnCerrar.setText("Salir");

       
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(
                        GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(
                                GroupLayout.Alignment.LEADING)
                            .addComponent(btnCerrar, GroupLayout.DEFAULT_SIZE, 
                                    101, Short.MAX_VALUE)
                            .addComponent(btnBorrar, GroupLayout.DEFAULT_SIZE, 
                                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificar, GroupLayout.DEFAULT_SIZE, 
                                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevo, GroupLayout.DEFAULT_SIZE, 
                                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        ))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnNuevo)
                .addGap(4, 4, 4)
                .addComponent(btnModificar)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrar)
                .addGap(2, 2, 2)                
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar))
        );

        jtListado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        jtListado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtListadoMouseClicked(evt);
            }
        });
        
        jScrollPane1.setViewportView(jtListado);
        
        if (jtListado.getColumnModel().getColumnCount() > 0) {
            jtListado.getColumnModel().getColumn(0).setResizable(false);            
        }

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(
                        GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 301, 
                        Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>                        

    private void jtListadoMouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
        DefaultTableModel modelo;
        modelo = (DefaultTableModel) jtListado.getModel();
                    
        if(jtListado.getSelectedRow()==-1){
            if(jtListado.getRowCount()==0){
                JOptionPane.showMessageDialog(this,"No hay registros");
            }
            else{
                JOptionPane.showMessageDialog(this,"Seleccione una fila");
            }
        }else {                     
            txtCodigo.setText(modelo.getValueAt(
                    jtListado.getSelectedRow(), 0).toString());            
            txtNombre.setText(modelo.getValueAt(
                    jtListado.getSelectedRow(), 1).toString());                                    
        }
    }                                      
                                      

    // Variables declaration                     
    private JButton btnBorrar;
    private JButton btnCerrar;
    private JButton btnModificar;
    private JButton btnNuevo;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JTable jtListado;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    // End of variables declaration                   
}
