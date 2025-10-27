/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author rubco
 */
public class ControladorPrincipal {
    
    private final SessionFactory sessionFactory;
    
    public ControladorPrincipal(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
    
    public void menu(int opc){
        switch (opc) {
            case 1:
                new ControladorSocio(sessionFactory);
                break;
            case 2:
                new ControladorMonitor(sessionFactory);
                break;
            case 3:
                new ControladorMonitor(sessionFactory);
                break;
        }
    }
    
}
