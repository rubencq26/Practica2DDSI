/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.ActividadesPanel;
import Vista.InicioPanel;
import Vista.MonitorPanel;
import Vista.SocioPanel;
import Vista.VistaPrincipal;
import config.HibernateUtil;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author rubco
 */
public class ControladorPrincipal implements ActionListener{

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
        
        
    }
    
    private void addListeners(){
        vPrincipal.inicioMenu.addActionListener(this);
        vPrincipal.monitorMenu.addActionListener(this);
        vPrincipal.socioMenu.addActionListener(this);
        vPrincipal.actividadMenu.addActionListener(this);
        vPrincipal.salirMenu.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
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
    
    
    private void muestraPanel(String panel){
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
    
    

    
}
