

package co.edu.unal.access;

import co.edu.unal.services.Fachada;
import co.edu.unal.model.Museo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Admin
 */

public class MuseoDAO {
    HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    String uri = "https://6538-35-247-86-151.ngrok-free.app/";
    ObjectMapper mapper = new ObjectMapper();

    public MuseoDAO() {
    }
    
    //-------------------------------MySQL Local-----------------------------------------------
    /**
     * 
     * @param museo de la clase museo a grabar     
     * @return resultado resultado de la operación grbar
     */
    public int saveMuseo(Museo museo){      
        Connection con = null;
        PreparedStatement pstm;
        pstm = null;
        int resultado;
        resultado = 0;
        try{           
            con = Fachada.getConnection();            
            String sql = "INSERT INTO museo values (?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, museo.getMu_id());
            pstm.setString(2, museo.getMu_nombre());
            
            resultado = pstm.executeUpdate();  
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Código : " + 
                        ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "\nError :" + ex.getMessage());
        }
        return resultado;
    }
    
     /**
     * 
     * @param museo Objeto de la clase museona a grabar
     * @return resultado resultado de la operación modificar
     */
    public int updateMuseo(Museo museo){      
        Connection con = null;
        PreparedStatement pstm;
        pstm = null;
        int resultado;
        resultado = 0;
        try{
            con = Fachada.getConnection();
            String sql = "UPDATE museo " +
                        "SET mu_nombre = ? WHERE mu_id = ?";
            pstm = con.prepareStatement(sql);            
            pstm.setInt(2, museo.getMu_id());
            pstm.setString(1,museo.getMu_nombre());            
            resultado = pstm.executeUpdate();  
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Código : " + 
                        ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "\nError :" + ex.getMessage());
        }
        return resultado;
    }
            
    /**
     * 
     * @param mu_id código de lamuseona a borrar
     * @return resultado resultado de la operación borrar
     */
    public int deleteMuseo(int mu_id){      
        Connection con = null;
        PreparedStatement pstm = null;
        int resultado;
        resultado = 0;
        try{
            con = Fachada.getConnection();
            String sql = "DELETE FROM museo WHERE mu_id = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, mu_id);
            resultado = pstm.executeUpdate(); 
            return resultado;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Código : " + 
                        ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "\nError :" + ex.getMessage());
        }
        return resultado;
    }
    /**
     * 
     * @param mu_id codigo de museona a listar, 0 se listaran todas
     * @return ArrayList, lista de objetos Museo
     */
    public ArrayList<Museo> getListMuseos(int mu_id){      
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Museo> listadoMuseos = new ArrayList<>();
        try{
            con = Fachada.getConnection();
            String sql="";
            if(mu_id == 0){
                sql = "SELECT * FROM museo  ORDER BY mu_id";            
            }else{
                sql = "SELECT * FROM museo WHERE mu_id = ? "
                    + "ORDER BY mu_id";      
            }                        
            pstm = con.prepareStatement(sql);
            
            if(mu_id != 0){
                pstm.setInt(1, mu_id);
            }
            
            rs = pstm.executeQuery();
                        
            Museo museo = null;
            while(rs.next()){
                museo = new Museo();
                museo.setMu_id(rs.getInt("mu_id"));
                museo.setMu_nombre(rs.getString("mu_nombre"));                
                listadoMuseos.add(museo);
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Código : " + 
                        ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "\nError :" + ex.getMessage());
        }
        finally{
            try{
                if(rs!=null) rs.close();
                if(pstm!=null) pstm.close();                
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null,"Código : " + 
                        ex.getErrorCode() + "\nError :" + ex.getMessage());
            }
        }
        return listadoMuseos;
    }
    
    //-------------------------------MySQL Remote-----------------------------------------------
    
    private <T> T getData(String json, TypeReference<T> reference)
    {
        try {
            return mapper.readValue(json, reference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } 
     /**
     * 
     * @return ArrayList, lista de objetos Museo
     */
    public ArrayList<Museo> getListMuseums(){
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(uri + "museos")).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ArrayList<Museo> museoList = getData(response.body(), new TypeReference<ArrayList<Museo>>(){

            });
            return museoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };
     /**
     * 
     * @param mu_id código de lamuseona a borrar
     * @return resultado resultado de la operación borrar
     */
     public int deleteMuseum(int mu_id){
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(uri + "museo/" + mu_id)).build();

        try {
         HttpResponse response =  client.send(request, HttpResponse.BodyHandlers.ofString());
         
         return 1;
       } catch (Exception e) {
           e.printStackTrace();
       }
        return 0;
     }
     /**
     * 
     * @param museo de la clase museo a grabar     
     * @return resultado resultado de la operación grbar
     */
     public int saveMuseum (Museo museo){
     
        Map<String, Object> museoDTO = new HashMap<String, Object>(){
         {
          put("mu_id" , museo.getMu_id());
          put("mu_nombre" , museo.getMu_nombre());
         }
         };
         try {
             String museoMapper = mapper.writeValueAsString(museoDTO);
             HttpRequest request = HttpRequest.newBuilder()
                     .POST(HttpRequest.BodyPublishers.ofString(museoMapper))
                     .header("Content-Type", "application/json")
                     .uri(URI.create(uri + "museo"))
                     .build();
             
             HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
             
             return 1;
         } catch (Exception e) {
         }
        return 0;
     }
     /**
     * 
     * @param museo Objeto de la clase museona a grabar
     * @return resultado resultado de la operación modificar
     */ 
    public int updateMuseum(Museo museo) {
        Map<String, Object> museoDTO = new HashMap<String, Object>() {
            {
                put("mu_nombre", museo.getMu_nombre());
            }
        };

        try {
            String museoMapper = mapper.writeValueAsString(museoDTO);
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(museoMapper))
                    .header("Content-Type", "application/json")
                    .uri(URI.create(uri + "museo/" + museo.getMu_id()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return 1; // Puedes decidir retornar un valor diferente basado en la respuesta si es necesario.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    } 
    
}
