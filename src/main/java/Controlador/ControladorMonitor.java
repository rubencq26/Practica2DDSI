/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MonitorDAO;
import Modelo.Actividad;
import Modelo.Socio;
import Vista.VistaMensajes;
import Vista.VistaMenu;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author rubco
 */
public class ControladorMonitor {
    public final SessionFactory sessionFactory;
    public MonitorDAO monitorDAO = null;
    public final Vista.VistaMensajes vMensaje;
    
    public ControladorMonitor(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        monitorDAO = new MonitorDAO();
        vMensaje = new VistaMensajes();
        menuMonitor();
    }
    
     public void actividadesMonitorResponsableDni(String dni){
        Session sesion = sessionFactory.openSession();
        Transaction tr = null;
        
        try{
            tr = sesion.beginTransaction();
            try {
                
                String codMonitor = MonitorDAO.codMonitorDni(sesion, dni);
                
                
                List<Actividad> actividades = MonitorDAO.getActividadesMonitor(sesion, codMonitor);
                
                vMensaje.mensajeConsola("Las actividades responsables del monitor " + dni + " son: ");
                for(Actividad a : actividades){
                    System.out.println(a.toString());
                }
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                
            }
            tr.commit();
        }catch(Exception e){
            if(tr != null){
                tr.rollback();
            }
            
        }finally{
            if(sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
        
        
    }
    
    public void actividadesMonitorResponsableDni(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el dni del monitor");
        String dni = sc.next();
        actividadesMonitorResponsableDni(dni);

        
    }
    
    public void menuMonitor(){
        VistaMenu.menuMonitor();
        int opc;
        Scanner sc = new Scanner(System.in);
        VistaMensajes.pedirDato("Introduzca una opcion: ");
        opc = sc.nextInt();
        if(opc == 1){
            actividadesMonitorResponsableDni();
        }
       
    }
    
}
