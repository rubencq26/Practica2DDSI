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
import Vista.ActualizarActividadDialog;
import Vista.ActualizarMonitorDialog;
import Vista.InsertarActividadDialog;
import Vista.InsertarSocioDialog;
import Vista.VistaMensajes;
import Vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author rubco
 */
public class ControladorActividad implements ActionListener {

    private final ActividadesPanel vActividad;
    private final SessionFactory sessionFactory;
    private final VistaPrincipal vPrincipal;
    private List<Object[]> lActividad;
    private InsertarActividadDialog dialog;
    private List<Monitor> lMonitor;
    private ActualizarActividadDialog acDialog;

    public ControladorActividad(SessionFactory sessionFactory, VistaPrincipal vPrincipal) {
        this.sessionFactory = sessionFactory;
        vActividad = new ActividadesPanel();
        this.vPrincipal = vPrincipal;

        vPrincipal.add(vActividad);
        vActividad.setVisible(false);

        GestionTablas.inicializarTablaActividad(vActividad);
        dibujaRellenaTablaActividad();

        vActividad.nuevaActividad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cod;
                    if (lActividad == null || lActividad.isEmpty()) {
                        cod = "AC01";
                    } else {
                        
                        String ultimoCod = (String) lActividad.getLast()[0];

                        
                        String num = ultimoCod.substring(2);
                        int n = Integer.parseInt(num);
                        n++;

                       
                        cod = String.format("AC%02d", n);
                    }

                
                    dialog = new InsertarActividadDialog(vPrincipal, true, cod);

                
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                    Session session = sessionFactory.openSession();
                    Transaction tr = session.beginTransaction();
                    try {
                        lMonitor = MonitorDAO.listarMonitores(session);

                    } catch (Exception ex) {
                        tr.rollback();
                        VistaMensajes.error(ex.getMessage(), vPrincipal);
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    if (lActividad != null) {
                        for (Monitor m : lMonitor) {
                            model.addElement(m.getNombre()); // Asumo que el nombre del monitor está en la posición 6
                        }
                    }
                    dialog.monitor.setModel(model);

                    // Listeners del diálogo
                    dialog.insertarButton.addActionListener(ControladorActividad.this);
                    dialog.cancelarButton.addActionListener(e2 -> dialog.dispose());

                    dialog.setLocationRelativeTo(vPrincipal);
                    dialog.setVisible(true);

                } catch (Exception ex) {
                    // Esto te dirá en la consola de NetBeans exactamente qué línea falla
                    System.err.println("Error al abrir el diálogo: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        vActividad.bajaActividad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vActividad.tablaActividad.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(dialog, "Error: Seleccione una fila de la tabla para proceder con la baja de la actividad", null, JOptionPane.ERROR_MESSAGE);
                } else {
                    String cod = (String) vActividad.tablaActividad.getValueAt(vActividad.tablaActividad.getSelectedRow(), 0);
                    Object[] obj = new Object[7];
                    for (Object[] o : lActividad) {
                        if (o[0].equals(cod)) {
                            obj = o;
                        }
                    }

                    Actividad act = new Actividad((String) obj[0], (String) obj[1], (String) obj[2], (int) obj[3], (String) obj[4], (int) obj[5]);

                    int confirmar = JOptionPane.showConfirmDialog(vPrincipal, "¿Deseas eliminar la  Actividad " + cod + "?", "Baja Actividad", JOptionPane.YES_NO_OPTION);
                    if (confirmar == JOptionPane.YES_OPTION) {
                        Session session = sessionFactory.openSession();
                        Transaction tr = session.beginTransaction();
                        try {
                            boolean ok = ActividadDAO.eliminarActividad(session, act);
                            if (ok) {
                                tr.commit();
                                JOptionPane.showMessageDialog(vPrincipal, "Actividad Eliminada.");
                                dibujaRellenaTablaActividad();
                            }
                        } catch (Exception ex) {
                            tr.rollback();
                            VistaMensajes.error(ex.getMessage(), vPrincipal);
                        } finally {
                            if (session != null && session.isOpen()) {
                                session.close();
                            }
                        }
                    }

                }
            }

        });

        vActividad.actualizacionActividad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vActividad.tablaActividad.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(vPrincipal, "Error: Seleccione una fila de la tabla para proceder con la actualizacion de la actividad", null, JOptionPane.ERROR_MESSAGE);
                } else {
                    String cod = (String) vActividad.tablaActividad.getValueAt(vActividad.tablaActividad.getSelectedRow(), 0);
                    Actividad act = new Actividad();
                    Session session = sessionFactory.openSession();
                    Transaction tr = session.beginTransaction();
                    List<Actividad> lActi = new ArrayList<>();
                    try {
                        lActi = ActividadDAO.listarActividadesA(session);

                    } catch (Exception ex) {
                        tr.rollback();
                        VistaMensajes.error(ex.getMessage(), vPrincipal);
                    } finally {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    }

                    for (Actividad a : lActi) {
                        if (a.getIdActividad().equals(cod)) {
                            act = a;
                        }
                    }

                    try {
                        acDialog = new ActualizarActividadDialog(vPrincipal, true, act);
                        
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                        session = sessionFactory.openSession();
                        tr = session.beginTransaction();
                        try {
                            lMonitor = MonitorDAO.listarMonitores(session);

                        } catch (Exception ex) {
                            tr.rollback();
                            VistaMensajes.error(ex.getMessage(), vPrincipal);
                        } finally {
                            if (session != null && session.isOpen()) {
                                session.close();
                            }
                        }

                        if (lActividad != null) {
                            for (Monitor m : lMonitor) {
                                model.addElement(m.getNombre()); 
                            }
                        }
                        acDialog.monitor.setModel(model);
                        
                        int indice =  0;
                        int i = 0;
                        for (Monitor m: lMonitor) {
                            if(m.getCodMonitor().equals(act.getMonitorResponsable().getCodMonitor())){
                                indice = i;
                            }
                            i++;
                        }
                        
                        acDialog.monitor.setSelectedIndex(indice);

                        acDialog.actualizarButton.addActionListener(ControladorActividad.this);
                        acDialog.cancelarButton.addActionListener(e2 -> acDialog.dispose());
                        acDialog.setLocationRelativeTo(vPrincipal);
                        acDialog.setVisible(true);

                    } catch (Exception ex) {
                        VistaMensajes.error("Error de creacion", vPrincipal);
                    }
                }
            }

        });

    }

    private void dibujaRellenaTablaActividad() {
        try {
            GestionTablas.dibujarTablaActividad(vActividad);
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            try {
                lActividad = ActividadDAO.listarActividades(session);
                GestionTablas.vaciarTablaActividad();
                GestionTablas.rellenarTablaActividad(lActividad);
                tr.commit();

            } catch (Exception e) {
                tr.rollback();
                VistaMensajes.error(e.getMessage(), vPrincipal);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }

        } catch (Exception e) {
            VistaMensajes.error(e.getMessage(), vPrincipal);
        }
    }

    public void mostrarPanel(boolean mostrar) {
        vActividad.setVisible(mostrar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "InsertarActividad" -> {
                try {
                    // 1. Recuperar datos con tipos correctos
                    String codigo = dialog.codigoTextField.getText();
                    String nombre = dialog.nombreTextField.getText();
                    String dia = (String) dialog.diaCombo.getSelectedItem();
                    String desc = dialog.descripcion.getText();

                    // JSpinner devuelve Integer, no String
                    int h = (int) dialog.hora.getValue();
                    int p = (int) dialog.precio.getValue();

                    int monitIndex = dialog.monitor.getSelectedIndex();

                    // 2. Validación lógica corregida
                    if (nombre.isEmpty() || desc.isEmpty() || h < 0 || h > 24 || p < 0) {
                        JOptionPane.showMessageDialog(dialog, "Error: Datos inválidos o campos vacíos", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    Session session = sessionFactory.openSession();
                    Transaction tr = session.beginTransaction();

                    try {
                        // Obtenemos la lista real de monitores para sacar el objeto
                        List<Monitor> lMonitores = MonitorDAO.listarMonitores(session);
                        Actividad actividad;

                        if (lMonitores.isEmpty() || monitIndex == -1) {
                            actividad = new Actividad(codigo, nombre, dia, h, desc, p);
                        } else {
                            Monitor monitorElegido = lMonitores.get(monitIndex);
                            actividad = new Actividad(codigo, nombre, dia, h, desc, p, monitorElegido);
                        }

                        boolean ok = ActividadDAO.insertarActividad(session, actividad);

                        if (ok) {
                            tr.commit();
                            JOptionPane.showMessageDialog(dialog, "Actividad insertada correctamente");
                            dibujaRellenaTablaActividad();
                            dialog.dispose();
                        } else {
                            tr.rollback();
                        }

                    } catch (Exception exc) {
                        if (tr != null) {
                            tr.rollback();
                        }
                        VistaMensajes.error("Error DB: " + exc.getMessage(), vPrincipal);
                    } finally {
                        if (session != null) {
                            session.close();
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error de formato: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            case "ActualizarActividad" -> {
               try {
                    // 1. Recuperar datos con tipos correctos
                    String codigo = acDialog.codigoTextField.getText();
                    String nombre = acDialog.nombreTextField.getText();
                    String dia = (String) acDialog.diaCombo.getSelectedItem();
                    String desc = acDialog.descripcion.getText();

                    // JSpinner devuelve Integer, no String
                    int h = (int) acDialog.hora.getValue();
                    int p = (int) acDialog.precio.getValue();

                    int monitIndex = acDialog.monitor.getSelectedIndex();

                    // 2. Validación lógica corregida
                    if (nombre.isEmpty() || desc.isEmpty() || h < 0 || h > 24 || p < 0) {
                        JOptionPane.showMessageDialog(acDialog, "Error: Datos inválidos o campos vacíos", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    
                    
                   
                    Session session = sessionFactory.openSession();
                    Transaction tr = session.beginTransaction();

                    try {
                        // Obtenemos la lista real de monitores para sacar el objeto
                        List<Monitor> lMonitores = MonitorDAO.listarMonitores(session);
                        Actividad actividad = new Actividad();
                        List<Actividad> lActi = ActividadDAO.listarActividadesA(session);
                        for(Actividad act : lActi){
                            if(act.getIdActividad().equals(codigo)){
                                actividad = act;
                            }
                        }
                        
                        actividad.setNombre(nombre);
                        actividad.setDia(dia);
                        actividad.setHora(h);
                        actividad.setDescripcion(desc);
                        actividad.setPrecioBaseMes(p);
                        if (lMonitores.isEmpty() || monitIndex == -1) {
                            
                        } else {
                            Monitor monitorElegido = lMonitores.get(monitIndex);
                            actividad.setMonitorResponsable(monitorElegido);
                        }

                        boolean ok = ActividadDAO.insertarActividad(session, actividad);

                        if (ok) {
                            tr.commit();
                            JOptionPane.showMessageDialog(acDialog, "Actividad actualizada correctamente");
                            dibujaRellenaTablaActividad();
                            acDialog.dispose();
                        } else {
                            tr.rollback();
                        }

                    } catch (Exception exc) {
                        if (tr != null) {
                            tr.rollback();
                        }
                        VistaMensajes.error("Error DB: " + exc.getMessage(), vPrincipal);
                    } finally {
                        if (session != null) {
                            session.close();
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(acDialog, "Error de formato: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

        }
    }

}
