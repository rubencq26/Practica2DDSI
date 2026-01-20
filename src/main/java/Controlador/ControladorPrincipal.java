/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.InicioPanel;
import Vista.VistaPrincipal;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.hibernate.SessionFactory;

/**
 * Controlador central de la aplicación que gestiona la navegación entre los diferentes módulos.
 * Implementa el patrón Mediador para coordinar el intercambio de paneles (Monitores, Socios, 
 * Actividades e Inscripciones) dentro de la ventana principal.
 * * @author rubco
 */
public class ControladorPrincipal implements ActionListener {

    private final SessionFactory sessionFactory;
    private final VistaPrincipal vPrincipal;
    private final InicioPanel vInicio;
    private final ControladorSocio cSocio;
    private final ControladorActividad cActividad;
    private final ControladorMonitor cMonitor;
    private final ControladorInscripciones cInscripciones;

    /**
     * Inicializa el controlador principal y todos los subcontroladores de la aplicación.
     * Configura el diseño de la ventana principal mediante un CardLayout y establece 
     * el panel de inicio por defecto.
     * * @param sessionFactory Factoría de sesiones de Hibernate compartida por todos los controladores.
     */
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
        cInscripciones = new ControladorInscripciones(sessionFactory, vPrincipal);

        vInicio.setVisible(true);

    }

    /**
     * Registra los eventos de los elementos del menú de la vista principal.
     */
    private void addListeners() {
        vPrincipal.inicioMenu.addActionListener(this);
        vPrincipal.monitorMenu.addActionListener(this);
        vPrincipal.socioMenu.addActionListener(this);
        vPrincipal.actividadMenu.addActionListener(this);
        vPrincipal.salirMenu.addActionListener(this);
        vPrincipal.inscripcionesMenu.addActionListener(this);
    }

    /**
     * Procesa las acciones del menú principal para alternar entre los diferentes paneles de gestión.
     * * @param e El evento de acción capturado del menú.
     */
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
            case "Inscripciones":
                muestraPanel("Inscripciones");
                break;
            case "SalirMenu":
                System.exit(0);
            default:
                throw new AssertionError();
        }
    }

    /**
     * Gestiona la visibilidad de los paneles, asegurando que solo uno sea visible a la vez.
     * * @param panel Nombre del panel o módulo que se desea mostrar.
     */
    private void muestraPanel(String panel) {
        vInicio.setVisible(false);
        cMonitor.mostrarPanel(false);
        cSocio.mostrarPanel(false);
        cActividad.mostrarPanel(false);
        cInscripciones.muestraPanel(false);

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
            case "Inscripciones":
                cInscripciones.muestraPanel(true);
                
        }
    }

}