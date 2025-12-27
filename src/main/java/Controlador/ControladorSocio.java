/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import Modelo.SocioDAO;
import Utilidades.GestionTablas;
import Vista.SocioPanel;
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
public class ControladorSocio {

    private final SocioPanel vSocio;
    private final SessionFactory sessionFactory;
    private final VistaPrincipal vPrincipal;

    public ControladorSocio(SessionFactory sessionFactory, VistaPrincipal vPrincipal) {
        vSocio = new SocioPanel();
        this.sessionFactory = sessionFactory;
        this.vPrincipal = vPrincipal;
        
        vPrincipal.add(vSocio);
        vSocio.setVisible(false);
        
        GestionTablas.inicializarTablaSocio(vSocio);
        dibujaRellenaTablaSocios();
    }
    
    
    private void dibujaRellenaTablaSocios() {
        try {
            GestionTablas.dibujarTablaSocio(vSocio);
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            try {
                List<Socio> lSocios = SocioDAO.listarSocios(session);
                GestionTablas.vaciarTablaSocio();
                GestionTablas.rellenarTablaSocio(lSocios);
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
        vSocio.setVisible(mostrar);
    }
}
