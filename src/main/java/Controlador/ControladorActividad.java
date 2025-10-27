/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import org.hibernate.SessionFactory;

/**
 *
 * @author rubco
 */
public class ControladorActividad {
    private final SessionFactory sessionFactory;
    private ActividadDAO actividadDAO = null;
    
    public ControladorActividad(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        actividadDAO = new ActividadDAO();
    }
    
}
