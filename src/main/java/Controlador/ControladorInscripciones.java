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
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Controlador para la gestión de inscripciones de socios en actividades.
 * Administra la lógica de alta y baja de socios en actividades, así como la 
 * actualización de las tablas de actividades inscritas y disponibles.
 * * @author rubco
 */
public class ControladorInscripciones {

    private InscripcionesPanel vPanel;
    private List<Socio> lSocios;
    private List<Actividad> lActividad;
    private SessionFactory sessionFactory;
    private VistaPrincipal vPrincipal;

    /**
     * Constructor del controlador de inscripciones.
     * Configura la vista inicial, inicializa las tablas, carga la lista de socios
     * y registra todos los manejadores de eventos necesarios.
     * * @param sessionFactory Factoría de sesiones de Hibernate para el acceso a datos.
     * @param vPrincipal Referencia a la ventana principal de la aplicación.
     */
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

        /**
         * Listener para el cambio de selección en el combo de socios.
         * Refresca las tablas de inscripciones según el socio seleccionado.
         */
        vPanel.sociosCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicializaPanel();
            }

        });

        /**
         * Listener de selección para la tabla de actividades inscritas.
         * Habilita o deshabilita el botón de baja según haya una fila seleccionada.
         */
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

        /**
         * Listener de selección para la tabla de actividades disponibles.
         * Habilita o deshabilita el botón de alta según haya una fila seleccionada.
         */
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

        /**
         * Listener para el botón de baja. 
         * Elimina la relación entre el socio seleccionado y la actividad de la tabla 1.
         */
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
                
                if(vPanel.tabla1.getRowCount() == 0){
                    vPanel.bajaBoton.setEnabled(false);
                }

            }

        });

        /**
         * Listener para el botón de alta.
         * Crea la relación entre el socio seleccionado y la actividad de la tabla 2.
         */
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
                
                if(vPanel.tabla2.getRowCount() == 0){
                    vPanel.altaBoton.setEnabled(false);
                }
            }
        });

    }

    /**
     * Controla la visibilidad del panel de inscripciones en la interfaz principal.
     * @param mostrar true para visualizar el panel, false para ocultarlo.
     */
    public void muestraPanel(boolean mostrar) {
        vPanel.setVisible(mostrar);
    }

    /**
     * Refresca el contenido de las tablas del panel. 
     * Clasifica las actividades totales en dos listas (inscritas y disponibles) 
     * basándose en la relación N:M del socio seleccionado en el combo.
     */
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