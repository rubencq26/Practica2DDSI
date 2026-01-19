/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Modelo.Socio;
import Modelo.SocioDAO;
import Utilidades.GestionTablas;
import Vista.InscripcionesPanel;
import Vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author rubco
 */
public class ControladorInscripciones {

    private InscripcionesPanel vPanel;
    private List<Socio> lSocios;
    private List<Actividad> lActividad;
    private SessionFactory sessionFactory;
    private VistaPrincipal vPrincipal;

    public ControladorInscripciones(SessionFactory sessionFactory, VistaPrincipal vPrincipal) {
        this.vPanel = new InscripcionesPanel();
        this.sessionFactory = sessionFactory;
        this.vPrincipal = vPrincipal;

        // 1. Añadir a la vista primero
        vPrincipal.add(vPanel);
        vPanel.setVisible(false);

        // 2. Inicializar tablas
        GestionTablas.inicializarTablaRealiza(vPanel);
        GestionTablas.dibujarTablaRealiza1(vPanel);
        GestionTablas.dibujarTablaRealiza2(vPanel);

        // 3. Cargar datos básicos (SocioDAO)
        try (Session session = sessionFactory.openSession()) {
            lSocios = SocioDAO.listarSocios(session);

            if (lSocios != null && !lSocios.isEmpty()) {
                DefaultComboBoxModel<String> modeloSocios = new DefaultComboBoxModel<>();
                for (Socio s : lSocios) {
                    modeloSocios.addElement(s.getNombre());
                }
                vPanel.sociosCombo.setModel(modeloSocios);

                // SOLO si hay socios, inicializamos el panel
                inicializaPanel();
            }
        } catch (Exception e) {
            Vista.VistaMensajes.error("Error al cargar socios: " + e.getMessage(), vPrincipal);
        }

        vPanel.sociosCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicializaPanel();
            }

        });

        vPanel.tabla1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = vPanel.tabla1.getSelectedRow();
                    if (fila != -1) {
                        vPanel.bajaBoton.setEnabled(true);

                    } else {
                        vPanel.bajaBoton.setEnabled(false);
                    }
                }
            }

        });

        vPanel.tabla2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = vPanel.tabla2.getSelectedRow();
                    if (fila != -1) {
                        vPanel.altaBoton.setEnabled(true);

                    } else {
                        vPanel.altaBoton.setEnabled(false);
                    }
                }
            }

        });

        vPanel.bajaBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idActividad = (String) vPanel.tabla1.getValueAt(vPanel.tabla1.getSelectedRow(), 0);
                idActividad = idActividad.substring(0, 4);
                if (idActividad.length() >= 4) {
                    idActividad = idActividad.substring(0, 4);
                }

                String idSocio = lSocios.get(vPanel.sociosCombo.getSelectedIndex()).getNumeroSocio();

                Session session = sessionFactory.openSession();
                Transaction tr = session.beginTransaction();
                
                try {

                    ActividadDAO.darBajaSocio(session, idActividad, idSocio);
                    tr.commit();
                    JOptionPane.showMessageDialog(vPrincipal, "Socio dado de baja correctamente");
                    inicializaPanel();
                    
                } catch (Exception ex) {
                    tr.rollback();
                    Vista.VistaMensajes.error(ex.getMessage(), vPrincipal);
                } finally {
                    if (session != null && session.isOpen()) {
                        session.close();
                    }
                }

            }

        });

        vPanel.altaBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = vPanel.tabla2.getSelectedRow();
                int indiceCombo = vPanel.sociosCombo.getSelectedIndex();

                if (filaSeleccionada == -1 || indiceCombo == -1) {
                    JOptionPane.showMessageDialog(vPrincipal, "Selecciona una actividad de la lista de disponibles.");
                    return;
                }

                String idActividad = (String) vPanel.tabla2.getValueAt(filaSeleccionada, 0);
                if (idActividad.length() >= 4) {
                    idActividad = idActividad.substring(0, 4);
                }

                String idSocio = lSocios.get(indiceCombo).getNumeroSocio();

                Session session = sessionFactory.openSession();
                Transaction tr = session.beginTransaction();

                try {
                    ActividadDAO.inscribirSocio(session, idActividad, idSocio);
                    tr.commit();

                    JOptionPane.showMessageDialog(vPrincipal, "Socio inscrito correctamente");
                    inicializaPanel();

                } catch (Exception ex) {
                    if (tr != null) {
                        tr.rollback();
                    }
                    Vista.VistaMensajes.error(ex.getMessage(), vPrincipal);
                } finally {
                    if (session != null && session.isOpen()) {
                        session.close();
                    }
                }
            }
        });

    }

    public void muestraPanel(boolean mostrar) {
        vPanel.setVisible(mostrar);
    }

    public void inicializaPanel() {
        // 1. Limpiar las tablas para que no se acumulen datos
        GestionTablas.vaciarTablasInscripciones();

        int indice = vPanel.sociosCombo.getSelectedIndex();
        if (indice < 0) {
            return;
        }

        // Obtenemos el socio seleccionado del combo usando el índice de nuestra lista local
        Socio socioLista = lSocios.get(indice);

        // 2. Abrir la sesión de forma manual (estilo tradicional)
        Session session = sessionFactory.openSession();

        try {
            // 3. Volvemos a pedir el socio a la base de datos para que la sesión lo reconozca
            Socio socioConectado = session.get(Socio.class, socioLista.getNumeroSocio());

            // 4. TRUCO SENCILLO: Para evitar el error de "no session", 
            // recorremos la lista de actividades del socio ahora que la sesión está abierta.
            // Esto carga los datos en memoria de forma "manual".
            for (Actividad a : socioConectado.getActividadSet()) {
                // No hace falta hacer nada aquí, solo con tocar la lista Hibernate la carga
            }

            // Traemos todas las actividades de la base de datos
            List<Actividad> todasActividades = ActividadDAO.listarActividadesA(session);

            List<String> inscritas = new ArrayList<>();
            List<String> disponibles = new ArrayList<>();

            // 5. CLASIFICAMOS: Miramos qué actividades tiene el socio y cuáles no
            for (Actividad act : todasActividades) {
                // Si el socio tiene esta actividad en su Set...
                if (socioConectado.getActividadSet().contains(act)) {
                    inscritas.add(act.getIdActividad() + " - " + act.getNombre());
                } else {
                    disponibles.add(act.getIdActividad() + " - " + act.getNombre());
                }
            }

            // 6. RELLENAMOS LAS TABLAS
            GestionTablas.rellenarTablaRealiza1(inscritas);
            GestionTablas.rellenarTablaRealiza2(disponibles);

        } catch (Exception e) {
            Vista.VistaMensajes.error("Error: " + e.getMessage(), vPrincipal);
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
