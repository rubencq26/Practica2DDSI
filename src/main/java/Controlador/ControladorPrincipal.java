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
    private final ControladorSocio cSocio;
    private final ControladorActividad cActividad;
    private final ControladorMonitor cMonitor;

    public ControladorPrincipal(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        vPrincipal = new VistaPrincipal();
        vInicio = new InicioPanel();

        addListeners();

        vPrincipal.getContentPane().setLayout(new CardLayout());

        cMonitor = new ControladorMonitor(sessionFactory, vPrincipal);
        cSocio = new ControladorSocio(sessionFactory, vPrincipal);
        vPrincipal.add(vInicio);
        cActividad = new ControladorActividad(sessionFactory, vPrincipal);

        vInicio.setVisible(true);

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
        cMonitor.mostrarPanel(false);
        cSocio.mostrarPanel(false);
        cActividad.mostrarPanel(false);

        switch (panel) {
            case "Inicio":
                vInicio.setVisible(true);
                break;
            case "Monitor":
                cMonitor.mostrarPanel(true);
                break;
            case "Socio":
                cSocio.mostrarPanel(true);
                break;
            case "Actividad":
                cActividad.mostrarPanel(true);
                break;
        }
    }

}
