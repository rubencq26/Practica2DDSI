/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ActividadDAO;
import Utilidades.GestionTablas;
import Vista.ActividadesPanel;
import Vista.MonitorPanel;
import Vista.VistaMensajes;
import Vista.VistaPrincipal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;



/**
 *
 * @author rubco
 */
public class ControladorActividad {
    private final ActividadesPanel vActividad;
    private final SessionFactory sessionFactory;
    private final VistaPrincipal vPrincipal;
    
    public ControladorActividad(SessionFactory sessionFactory, VistaPrincipal vPrincipal){
        this.sessionFactory = sessionFactory;
        vActividad = new ActividadesPanel();
        this.vPrincipal = vPrincipal;
        
        vPrincipal.add(vActividad);
        vActividad.setVisible(false);
        
        GestionTablas.inicializarTablaActividad(vActividad);
        dibujaRellenaTablaActividad();
        
    }
    
    private void dibujaRellenaTablaActividad() {
        try {
            GestionTablas.dibujarTablaActividad(vActividad);
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            try {
                List<Object[]> lActividad = ActividadDAO.listarActividades(session);
                GestionTablas.vaciarTablaActividad();
                GestionTablas.rellenarTablaActividad(lActividad);
                tr.commit();
                
            } catch (Exception e) {
                tr.rollback();
                VistaMensajes.error(e.getMessage(), vPrincipal);
            }finally{
                if(session != null && session.isOpen()){
                    session.close();
                }
            }
            
        } catch (Exception e) {
            VistaMensajes.error(e.getMessage(), vPrincipal);
        }
    }
    
    public void mostrarPanel(boolean mostrar){
        vActividad.setVisible(mostrar);
    }
    
}
