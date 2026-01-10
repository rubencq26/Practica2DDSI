/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import Utilidades.GestionTablas;
import Vista.ActualizarMonitorDialog;
import Vista.InsertarMonitorDialog;
import Vista.MonitorPanel;
import Vista.VistaMensajes;
import Vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author rubco
 */
public class ControladorMonitor implements ActionListener {

    private final MonitorPanel vMonitor;
    private final SessionFactory sessionFactory;
    private final VistaPrincipal vPrincipal;
    private List<Monitor> lMonitores;
    private InsertarMonitorDialog dialog;
    private ActualizarMonitorDialog acDialog;

    public ControladorMonitor(SessionFactory sessionFactory, VistaPrincipal vPrincipal) {
        vMonitor = new MonitorPanel();
        this.sessionFactory = sessionFactory;
        this.vPrincipal = vPrincipal;
        lMonitores = new ArrayList<>();
        vPrincipal.add(vMonitor);
        vMonitor.setVisible(false);

        GestionTablas.inicializarTablaMonitor(vMonitor);
        dibujaRellenaTablaMonitores();

        vMonitor.nuevoMonitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cod = lMonitores.getLast().getCodMonitor();
                String num = cod.substring(1);
                int n = Integer.parseInt(num);
                n++;
                if (n < 10) {
                    cod = "M00" + n;
                } else if (n < 100) {
                    cod = "M0" + n;
                } else {
                    cod = "M" + n;
                }

                dialog = new InsertarMonitorDialog(vPrincipal, true, cod);
                dialog.insertarButton.addActionListener(ControladorMonitor.this);
                dialog.cancelarButton.addActionListener(e2 -> dialog.dispose());
                dialog.setLocationRelativeTo(vPrincipal);
                dialog.setVisible(true);
            }

        });

        vMonitor.bajaMonitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vMonitor.tablaMonitor.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(dialog, "Error: Seleccione una fila de la tabla para proceder con la baja del monitor", null, JOptionPane.ERROR_MESSAGE);
                } else {
                    String cod = (String) vMonitor.tablaMonitor.getValueAt(vMonitor.tablaMonitor.getSelectedRow(), 0);
                    Monitor mon = new Monitor();
                    for (Monitor m : lMonitores) {
                        if (m.getCodMonitor().equals(cod)) {
                            mon = m;
                        }
                    }

                    int confirmar = JOptionPane.showConfirmDialog(vPrincipal, "¿Deseas eliminar al monitor " + cod + "?", "Baja Monitor", JOptionPane.YES_NO_OPTION);
                    if (confirmar == JOptionPane.YES_OPTION) {
                        Session session = sessionFactory.openSession();
                        Transaction tr = session.beginTransaction();
                        try {
                            boolean ok = MonitorDAO.eliminarMonitor(session, mon);
                            if (ok) {
                                tr.commit();
                                JOptionPane.showMessageDialog(vPrincipal, "Monitor eliminado.");
                                dibujaRellenaTablaMonitores();
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

        vMonitor.actualizacionMonitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vMonitor.tablaMonitor.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(vPrincipal, "Error: Seleccione una fila de la tabla para proceder con la actualizacion del monitor", null, JOptionPane.ERROR_MESSAGE);
                } else {
                    String cod = (String) vMonitor.tablaMonitor.getValueAt(vMonitor.tablaMonitor.getSelectedRow(), 0);
                    Monitor mon = new Monitor();
                    for (Monitor m : lMonitores) {
                        if (m.getCodMonitor().equals(cod)) {
                            mon = m;
                        }
                    }

                    try {
                        acDialog = new ActualizarMonitorDialog(vPrincipal, true, mon);
                        acDialog.actualizarButton.addActionListener(ControladorMonitor.this);
                        acDialog.cancelarButton.addActionListener(e2 -> acDialog.dispose());
                        acDialog.setLocationRelativeTo(vPrincipal);
                        acDialog.setVisible(true);

                    } catch (ParseException ex) {
                        VistaMensajes.error("Error de la fecha", vPrincipal);
                    }
                }
            }

        });

    }

    private void dibujaRellenaTablaMonitores() {
        try {
            GestionTablas.dibujarTablaMonitores(vMonitor);
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            try {
                lMonitores = MonitorDAO.listarMonitores(session);
                GestionTablas.vaciarTablaMonitores();
                GestionTablas.rellenarTablaMonitores(lMonitores);
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
        vMonitor.setVisible(mostrar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "InsertarMonitor" -> {
                String codigo = dialog.codigoTextField.getText();
                String nombre = dialog.nombreTextField.getText();
                String dni = dialog.dniTextField.getText();
                String telefono = dialog.telefonoTextField.getText();
                String correo = dialog.correoTextField.getText();
                Date fecha = dialog.calendario.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String fechaFormateada = (fecha != null) ? sdf.format(fecha) : "";
                String nick = dialog.nickTextFIeld.getText();
                boolean ok = false;
                if (nombre.isEmpty() || dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || fechaFormateada.isEmpty() || nick.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Error: Campos de texto vacios", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                Monitor mon = new Monitor(codigo, nombre, dni, telefono, correo, fechaFormateada, nick);
                Session session = sessionFactory.openSession();
                Transaction tr = session.beginTransaction();
                try {
                    ok = MonitorDAO.insertarMonitor(session, mon);
                    if (ok) {
                        tr.commit();
                    } else {
                        tr.rollback();
                    }
                } catch (Exception exc) {
                    tr.rollback();
                    VistaMensajes.error(exc.getMessage(), vPrincipal);
                    ok = false;

                } finally {
                    if (session != null && session.isOpen()) {
                        session.close();
                    }
                }
                if (ok) {
                    JOptionPane.showMessageDialog(dialog, "Monitor insertado correctamente", null, JOptionPane.INFORMATION_MESSAGE);
                }

                dibujaRellenaTablaMonitores();
                dialog.dispose();
                break;
            }
            case "ActualizarMonitor" -> {
                String codigo = acDialog.codigoTextField.getText();
                String nombre = acDialog.nombreTextField.getText();
                String dni = acDialog.dniTextField.getText();
                String telefono = acDialog.telefonoTextField.getText();
                String correo = acDialog.correoTextField.getText();
                Date fecha = acDialog.calendario.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String fechaFormateada = (fecha != null) ? sdf.format(fecha) : "";
                String nick = acDialog.nickTextFIeld.getText();
                boolean ok = false;
                if (nombre.isEmpty() || dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || fechaFormateada.isEmpty() || nick.isEmpty()) {
                    JOptionPane.showMessageDialog(acDialog, "Error: Campos de texto vacios", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                Monitor mon = new Monitor(codigo, nombre, dni, telefono, correo, fechaFormateada, nick);
                Session session = sessionFactory.openSession();
                Transaction tr = session.beginTransaction();
                try {
                    ok = MonitorDAO.actualizarMonitor(session, mon);
                    if (ok) {
                        tr.commit();
                    } else {
                        tr.rollback();
                    }
                } catch (Exception exc) {
                    tr.rollback();
                    VistaMensajes.error(exc.getMessage(), vPrincipal);
                    ok = false;

                } finally {
                    if (session != null && session.isOpen()) {
                        session.close();
                    }
                }
                if (ok) {
                    JOptionPane.showMessageDialog(acDialog, "Monitor actualizado correctamente", null, JOptionPane.INFORMATION_MESSAGE);
                }

                dibujaRellenaTablaMonitores();
                acDialog.dispose();
                
                break;
            }

        }
    }

}
