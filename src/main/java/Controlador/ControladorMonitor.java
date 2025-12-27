/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import Utilidades.GestionTablas;
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
public class ControladorMonitor {
    private final MonitorPanel vMonitor;
    private final SessionFactory sessionFactory;
    private final VistaPrincipal vPrincipal;
    
    public ControladorMonitor(SessionFactory sessionFactory, VistaPrincipal vPrincipal){
        vMonitor = new MonitorPanel();
        this.sessionFactory = sessionFactory;
        this.vPrincipal = vPrincipal;
        
        vPrincipal.add(vMonitor);
        vMonitor.setVisible(false);
        
        GestionTablas.inicializarTablaMonitor(vMonitor);
        dibujaRellenaTablaMonitores();
    } 
    
    private void dibujaRellenaTablaMonitores() {
        try {
            GestionTablas.dibujarTablaMonitores(vMonitor);
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            try {
                List<Monitor> lMonitores = MonitorDAO.listarMonitores(session);
                GestionTablas.vaciarTablaMonitores();
                GestionTablas.rellenarTablaMonitores(lMonitores);
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
        vMonitor.setVisible(mostrar);
    }
}


