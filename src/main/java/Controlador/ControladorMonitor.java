/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.Socio;
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
    
    public ControladorMonitor(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        monitorDAO = new MonitorDAO();
    }
    
     public void actividadesMonitorResponsableDni(String dni){
        Session sesion = sessionFactory.openSession();
        Transaction tr = null;
        
        try{
            tr = sesion.beginTransaction();
            try {
                Query consulta = sesion.createNativeQuery("SELECT m.codMonitor FROM MONITOR m WHERE m.dni = :dniP", Object.class);
                consulta.setParameter("dniP", dni);
                String codMonitor = (String) consulta.getSingleResult();
                
                consulta = sesion.createNativeQuery("SELECT * FROM ACTIVIDAD a WHERE a.monitorResponsable = :codMon", Actividad.class);
                consulta.setParameter("codMon", codMonitor);
                List<Actividad> actividades = consulta.getResultList();
                
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
    
}
