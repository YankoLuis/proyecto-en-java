/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import controlador.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yanko
 */
public class ProductoDAO {
    ConexionBD conexion = new ConexionBD();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
        String sql = "select * from productos";
        List<Producto> lista = new ArrayList<>();
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt(1));
                producto.setNombre(rs.getString(2));
                producto.setPrecio(rs.getDouble(3));
                producto.setInventario(rs.getInt(4));
                lista.add(producto);
            }
        } catch (Exception e){
            System.out.println("Error al listar");
        }
            return lista;
    }
    
    //Metodo agregar
    public void agregar(Producto producto){
        String sql = "insert into productos(nombre, precio, inventario) values(?, ?, ?)";
        
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3,producto.getInventario());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error en agregar: " + e);
        }
    }//afin del metodo agregar
    
    //Metodo Actualizar
    public void actualizar(Producto producto){
        String sql = "update productos set nombre=?, precio=?, inventario=? where codigo=?";
            try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3,producto.getInventario());
            ps.setInt(4, producto.getCodigo());
            ps.executeUpdate();
            
        } catch (Exception e) {
                System.out.println("Error en actualizar. DAO" + e);
        }
            
    }//fin del metodo actualizar
    
    public void borrar(int id){
         String sql = "delete from productos where codigo=" + id;
         try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            
        } catch (SQLException e) {
                System.out.println("Error al eliminar. DAO" + e);
        }
    }//fin del metodo borrar
    
            
           
    
}
