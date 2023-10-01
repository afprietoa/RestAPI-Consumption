
import co.edu.unal.access.MuseoDAO;
import co.edu.unal.controller.MuseoController;
import java.util.ArrayList;
import co.edu.unal.model.Museo;
import co.edu.unal.view.MuseoView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yovany
 */
public class Principal {
    public static void main(String[] args) {
        /*MuseoDAO museoDao = new MuseoDAO();
        ArrayList<Museo> listado = new ArrayList();
        
        
        
        Museo museo1 = new Museo(305,"UNAL");
        
        //museoDao.grabarMuseo(museo1);
        //museoDao.modificarMuseo(museo1);
        //museoDao.borrarMuseo(museo1.getMuId());
        
        listado = museoDao.getListMuseos(304);
        
        for (Museo museo : listado) {
            System.out.println(museo.getMuId() + " | " + museo.getMuNombre());
        }*/
        
        MuseoView vista = new MuseoView();
        MuseoDAO modelo = new MuseoDAO();
        
        MuseoController controladorMuseo = new MuseoController(vista, modelo);
    }
}
