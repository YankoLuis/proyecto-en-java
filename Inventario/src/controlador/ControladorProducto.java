/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.ProductoDAO;
import vista.Interfaz;


/**
 *
 * @author yanko
 */
public class ControladorProducto implements ActionListener{
    
    //Instancias
    Producto producto = new Producto();
    ProductoDAO productodao = new ProductoDAO();
    Interfaz vista = new Interfaz();
    DefaultTableModel modeloTabla = new DefaultTableModel();
    
    //variables globales
    private int codigo = 0;
    private String nombre;
    private double precio;
    private int inventario;
    
    public ControladorProducto(Interfaz vista){
        this.vista = vista;
        vista.setVisible(true);
        agregarEventos();
        listarTabla();
    }
    
    private void agregarEventos() {
    vista.getBtnAgregar().addActionListener(this);
    vista.getBtnActualizar().addActionListener(this);
    vista.getBtnBorrar().addActionListener(this);
    vista.getBtnLimpiar().addActionListener(this);
    vista.getTblTabla().addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
            llenarCampos(e);
        }
    });
}
    private void listarTabla(){
        String [] titulos = new String[]{"Codigo", "Nombre", "Precio", "Inventario"};
        modeloTabla = new DefaultTableModel(titulos, 0);
        List<Producto> listaProductos = productodao.listar();
        for (Producto producto:listaProductos){
            modeloTabla.addRow(new Object[]{producto.getCodigo(),producto.getNombre(),producto.getPrecio(),producto.getInventario()});
            
        }
        vista.getTblTabla().setModel(modeloTabla);
        vista.getTblTabla().setPreferredSize(new Dimension(350, modeloTabla.getRowCount() * 16));
    }
    
    private void llenarCampos(MouseEvent e){
        JTable target = (JTable) e.getSource();
        codigo = (int) vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 0);
        vista.getTxtNombre().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(),1).toString());
        vista.getTxtPrecio().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(),2).toString());
        vista.getTxtInventario().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(),3).toString());
       
    }
    //-----------------------------validar formulario-----------------
    private boolean validarDatos(){
        if ("".equals(vista.getTxtNombre().getText())|| "".equals(vista.getTxtPrecio().getText())|| "".equals(vista.getTxtInventario().getText())){
            JOptionPane.showMessageDialog(null, "Los campos no puede ser vacios", "Error" , JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    
    //Metodo 3 en 1
    private boolean cargarDatos(){
        try {
            nombre = vista.getTxtNombre().getText();
            precio = Double.parseDouble(vista.getTxtPrecio().getText());
            inventario = Integer.parseInt(vista.getTxtInventario().getText());
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error al cargar datos:" + e);
             JOptionPane.showMessageDialog(null, "Los campos precio e inventario deben ser numericos", "Error" , JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private void limpiarCampos(){
        vista.getTxtNombre().setText("");
        vista.getTxtPrecio().setText("");
        vista.getTxtInventario().setText("");
        
        nombre = "";
        precio = 0;
        inventario = 0;
    }
    //---------------------------------------------------
    private void agregarProducto(){
        try {
            if(validarDatos()){
                if(cargarDatos()){
                    Producto producto = new Producto(nombre, precio, inventario);
                    productodao.agregar(producto);
                    JOptionPane.showMessageDialog(null, "Registro Exitoso");
                    limpiarCampos();
                }
                
            }
        } catch (Exception e) {
            System.out.println("Error en agregar C datos: " + e);
        } finally {
                listarTabla();
        }
    }
    private void actualizarProducto(){
        try {
            if(validarDatos()){
                if(cargarDatos()){
                    Producto producto = new Producto(codigo, nombre, precio, inventario);
                    productodao.actualizar(producto);
                    JOptionPane.showMessageDialog(null, "Registro actualizado");
                    limpiarCampos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error en actualizar C" + e);
        } finally {
            listarTabla();
        }
    }
    
    private void borrarProducto(){
        try {
            if(codigo !=0){
                productodao.borrar(codigo);
                JOptionPane.showMessageDialog(null, "Registro borrado");
                limpiarCampos();
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un productoo de la tabla", "Error" , JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en borrar C" + e);
        } finally {
            listarTabla();
        }
    }
    //Dar acciones a los botones
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== vista.getBtnAgregar()) {
            agregarProducto();
        }
        if (e.getSource() == vista.getBtnActualizar()){
            actualizarProducto();
        }
        if(e.getSource() == vista.getBtnBorrar()){
            borrarProducto();
        }
        if(e.getSource() == vista.getBtnLimpiar()){
            limpiarCampos();
        }
    }
    
}
