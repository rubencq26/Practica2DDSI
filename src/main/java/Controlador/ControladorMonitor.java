/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import org.hibernate.SessionFactory;

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
    
}
