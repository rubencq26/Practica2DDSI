/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Modelo.Monitor;
import Modelo.MonitorDAO;
import Modelo.Socio;
import Modelo.SocioDAO;
import Utilidades.GestionTablas;
import Vista.ActividadesPanel;
import Vista.InicioPanel;
import Vista.MonitorPanel;
import Vista.SocioPanel;
import Vista.VistaMensajes;
import Vista.VistaPrincipal;
import config.HibernateUtil;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author rubco
 */
public class ControladorPrincipal implements ActionListener {

    private final SessionFactory sessionFactory;
    private final VistaPrincipal vPrincipal;
    private final InicioPanel vInicio;
    private final SocioPanel vSocio;
    private final ActividadesPanel vActividad;
    private final MonitorPanel vMonitor;

    public ControladorPrincipal(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        vPrincipal = new VistaPrincipal();
        vInicio = new InicioPanel();
        vSocio = new SocioPanel();
        vActividad = new ActividadesPanel();
        vMonitor = new MonitorPanel();
        addListeners();

        vPrincipal.getContentPane().setLayout(new CardLayout());
        vPrincipal.add(vInicio);
        vPrincipal.add(vSocio);
        vPrincipal.add(vActividad);
        vPrincipal.add(vMonitor);

        vInicio.setVisible(true);
        vSocio.setVisible(false);
        vActividad.setVisible(false);
        vMonitor.setVisible(false);
        
        GestionTablas.inicializarTablaMonitor(vMonitor);
        dibujaRellenaTablaMonitores();
        
        GestionTablas.inicializarTablaSocio(vSocio);
        dibujaRellenaTablaSocios();
        
        GestionTablas.inicializarTablaActividad(vActividad);
        dibujaRellenaTablaActividad();

    }

    private void addListeners() {
        vPrincipal.inicioMenu.addActionListener(this);
        vPrincipal.monitorMenu.addActionListener(this);
        vPrincipal.socioMenu.addActionListener(this);
        vPrincipal.actividadMenu.addActionListener(this);
        vPrincipal.salirMenu.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "InicioMenu":
                muestraPanel("Inicio");
                break;
            case "GestionMonitores":
                muestraPanel("Monitor");
                break;
            case "GestionSocios":
                muestraPanel("Socio");
                break;
            case "GestionActividades":
                muestraPanel("Actividad");
                break;
            case "SalirMenu":
                System.exit(0);
            default:
                throw new AssertionError();
        }
    }

    private void muestraPanel(String panel) {
        vInicio.setVisible(false);
        vMonitor.setVisible(false);
        vSocio.setVisible(false);
        vActividad.setVisible(false);

        switch (panel) {
            case "Inicio":
                vInicio.setVisible(true);
                break;
            case "Monitor":
                vMonitor.setVisible(true);
                break;
            case "Socio":
                vSocio.setVisible(true);
                break;
            case "Actividad":
                vActividad.setVisible(true);
                break;
        }
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

}
