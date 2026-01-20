/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import Modelo.SocioDAO;
import Utilidades.GestionTablas;
import Vista.ActualizarSocioDialog;
import Vista.InsertarSocioDialog;
import Vista.SocioPanel;
import Vista.VistaMensajes;
import Vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Controlador encargado de gestionar la lógica de negocio de la vista de
 * Socios. Permite realizar operaciones CRUD sobre la entidad Socio, gestionando
 * la comunicación entre el modelo (SocioDAO) y la interfaz gráfica (SocioPanel
 * y diálogos).
 *
 * * @author rubco
 */
public class ControladorSocio implements ActionListener {

    private final SocioPanel vSocio;
    private final SessionFactory sessionFactory;
    private final VistaPrincipal vPrincipal;
    private List<Socio> lSocios;
    private InsertarSocioDialog dialog;
    private ActualizarSocioDialog acDialog;

    /**
     * Constructor del controlador de socios. Inicializa el panel de socios,
     * añade la vista a la ventana principal, configura las tablas y registra
     * los listeners de los botones de gestión.
     *
     * * @param sessionFactory Factoría de sesiones de Hibernate.
     * @param vPrincipal Ventana principal del sistema.
     */
    public ControladorSocio(SessionFactory sessionFactory, VistaPrincipal vPrincipal) {
        vSocio = new SocioPanel();
        lSocios = new ArrayList<>();
        this.sessionFactory = sessionFactory;
        this.vPrincipal = vPrincipal;

        vPrincipal.add(vSocio);
        vSocio.setVisible(false);

        GestionTablas.inicializarTablaSocio(vSocio);
        dibujaRellenaTablaSocios();

        /**
         * Listener para el botón de nuevo socio. Genera automáticamente el
         * siguiente código de socio y abre el diálogo de inserción.
         */
        vSocio.nuevoSocio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cod = lSocios.getLast().getNumeroSocio();
                String num = cod.substring(1);
                int n = Integer.parseInt(num);
                n++;
                if (n < 10) {
                    cod = "S00" + n;
                } else if (n < 100) {
                    cod = "S0" + n;
                } else {
                    cod = "S" + n;
                }

                dialog = new InsertarSocioDialog(vPrincipal, true, cod);
                dialog.insertarButton.addActionListener(ControladorSocio.this);
                dialog.cancelarButton.addActionListener(e2 -> dialog.dispose());
                dialog.setLocationRelativeTo(vPrincipal);
                dialog.setVisible(true);
            }

        });

        /**
         * Listener para el botón de baja de socio. Valida la selección en la
         * tabla y procede a eliminar al socio tras confirmación.
         */
        vSocio.bajaSocio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vSocio.tablaSocio.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(dialog, "Error: Seleccione una fila de la tabla para proceder con la baja del socio", null, JOptionPane.ERROR_MESSAGE);
                } else {
                    String cod = (String) vSocio.tablaSocio.getValueAt(vSocio.tablaSocio.getSelectedRow(), 0);
                    Socio soc = new Socio();
                    for (Socio s : lSocios) {
                        if (s.getNumeroSocio().equals(cod)) {
                            soc = s;
                        }
                    }

                    int confirmar = JOptionPane.showConfirmDialog(vPrincipal, "¿Deseas eliminar al socio " + cod + "?", "Baja Socio", JOptionPane.YES_NO_OPTION);
                    if (confirmar == JOptionPane.YES_OPTION) {
                        Session session = sessionFactory.openSession();
                        Transaction tr = session.beginTransaction();
                        try {
                            boolean ok = SocioDAO.eliminarSocio(session, soc);
                            if (ok) {
                                tr.commit();
                                JOptionPane.showMessageDialog(vPrincipal, "Socio Eliminado.");
                                dibujaRellenaTablaSocios();
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

        /**
         * Listener para el botón de actualización de socio. Recupera los datos
         * del socio seleccionado y abre el diálogo de edición.
         */
        vSocio.actualizacionSocio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vSocio.tablaSocio.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(vPrincipal, "Error: Seleccione una fila de la tabla para proceder con la actualizacion del socio", null, JOptionPane.ERROR_MESSAGE);
                } else {
                    String cod = (String) vSocio.tablaSocio.getValueAt(vSocio.tablaSocio.getSelectedRow(), 0);
                    Socio soc = new Socio();
                    for (Socio s : lSocios) {
                        if (s.getNumeroSocio().equals(cod)) {
                            soc = s;
                        }
                    }

                    try {
                        acDialog = new ActualizarSocioDialog(vPrincipal, true, soc);
                        acDialog.ActualizarButton.addActionListener(ControladorSocio.this);
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

    /**
     * Refresca el contenido de la tabla de socios obteniendo la lista
     * actualizada desde el DAO.
     */
    private void dibujaRellenaTablaSocios() {
        try {
            GestionTablas.dibujarTablaSocio(vSocio);
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            try {
                lSocios = SocioDAO.listarSocios(session);
                GestionTablas.vaciarTablaSocio();
                GestionTablas.rellenarTablaSocio(lSocios);
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

    /**
     * Cambia la visibilidad del panel de socios.
     *
     * * @param mostrar true para mostrar el panel, false para ocultarlo.
     */
    public void mostrarPanel(boolean mostrar) {
        vSocio.setVisible(mostrar);
    }

    /**
     * Sobrescribe el método actionPerformed para procesar las peticiones de los
     * diálogos (Insertar y Actualizar).
     *
     * * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "InsertarSocio" -> {
                String codigo = dialog.codigoTextField.getText();
                String nombre = dialog.nombreTextField.getText();
                String dni = dialog.dniTextField.getText();
                String telefono = dialog.telefonoTextField.getText();
                String correo = dialog.correoTextField.getText();
                Date fNac = dialog.fNac.getDate();
                SimpleDateFormat sdfn = new SimpleDateFormat("dd/MM/yyyy");
                String fechaNacFormateada = (fNac != null) ? sdfn.format(fNac) : "";
                String categoria = (String) dialog.categoria.getSelectedItem();
                Date fAlt = dialog.fAlt.getDate();
                SimpleDateFormat sdfa = new SimpleDateFormat("dd/MM/yyyy");
                String fechaAltFormateada = (fAlt != null) ? sdfa.format(fNac) : "";
                boolean ok = false;
                if (nombre.isEmpty() || dni.isEmpty() || nombre.isEmpty() || fechaAltFormateada.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Error: Campos obligatorios de texto vacios", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                telefono = (!telefono.isEmpty()) ? telefono : "";
                correo = (!correo.isEmpty()) ? correo : "";
                fechaNacFormateada = (!fechaNacFormateada.isEmpty()) ? fechaNacFormateada : "";

                if (!dni.matches("^[0-9]{8}[A-Z]$")) {
                    JOptionPane.showMessageDialog(dialog, "Error: Formato del dni incorrecto (8 Numeros y una Mayúscula)", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                if (!correo.equals("") && !correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                    JOptionPane.showMessageDialog(dialog, "Error: Formato del correo incorrecto (xx@xx)", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                if (!telefono.equals("") && !telefono.matches("^[0-9]{9}$")) {
                    JOptionPane.showMessageDialog(dialog, "Error: Formato del telefono incorrecto (9 digitos)", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                Date fechaActual = new Date(); // Captura la fecha y hora actual

                if (fechaAltFormateada != null) {
                    if (fAlt.after(fechaActual)) {
                        JOptionPane.showMessageDialog(dialog, "Error: La fecha de alta es posterior a la fecha actual", null, JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                }

                if (fNac != null) {
                    // 1. Obtener la fecha actual
                    Calendar hoy = Calendar.getInstance();

                    // 2. Obtener la fecha de nacimiento
                    Calendar fechaNacimiento = Calendar.getInstance();
                    fechaNacimiento.setTime(fNac);

                    // 3. Calcular la edad inicial (diferencia de años)
                    int edad = hoy.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);

                    // 4. Ajustar si aún no ha cumplido años en el año actual
                    if (hoy.get(Calendar.DAY_OF_YEAR) < fechaNacimiento.get(Calendar.DAY_OF_YEAR)) {
                        edad--;
                    }

                    // 5. Validaciones
                    if (fNac.after(new Date())) {
                        JOptionPane.showMessageDialog(dialog, "Error: La fecha de nacimiento no puede ser futura.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    } else if (edad < 18) {
                        JOptionPane.showMessageDialog(dialog, "Error: El socio debe ser mayor de edad (18 años o más). Edad actual: " + edad, "Restricción de Edad", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }
                 
               
                Socio soc = new Socio(codigo, nombre, dni, fechaNacFormateada, telefono, correo, fechaAltFormateada, categoria.charAt(0));
                Session session = sessionFactory.openSession();
                Transaction tr = session.beginTransaction();
                try {
                    ok = SocioDAO.insertarSocio(session, soc);
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
                    JOptionPane.showMessageDialog(dialog, "Socio insertado correctamente", null, JOptionPane.INFORMATION_MESSAGE);
                }

                dibujaRellenaTablaSocios();
                dialog.dispose();
                break;
            }
            case "ActualizarSocio" -> {
                String codigo = acDialog.codigoTextField.getText();
                String nombre = acDialog.nombreTextField.getText();
                String dni = acDialog.dniTextField.getText();
                String telefono = acDialog.telefonoTextField.getText();
                String correo = acDialog.correoTextField.getText();
                Date fNac = acDialog.fNac.getDate();
                SimpleDateFormat sdfn = new SimpleDateFormat("dd/MM/yyyy");
                String fechaNacFormateada = (fNac != null) ? sdfn.format(fNac) : "";
                Date fAlt = acDialog.fAlt.getDate();
                SimpleDateFormat sdfa = new SimpleDateFormat("dd/MM/yyyy");
                String fechaAltFormateada = (fAlt != null) ? sdfa.format(fAlt) : "";
                String categoria = (String) acDialog.categoria.getSelectedItem();
                boolean ok = false;
                 if (nombre.isEmpty() || dni.isEmpty() || nombre.isEmpty() || fechaAltFormateada.isEmpty()) {
                    JOptionPane.showMessageDialog(acDialog, "Error: Campos obligatorios de texto vacios", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                telefono = (!telefono.isEmpty()) ? telefono : "";
                correo = (!correo.isEmpty()) ? correo : "";
                fechaNacFormateada = (!fechaNacFormateada.isEmpty()) ? fechaNacFormateada : "0/0/0";

                if (!dni.matches("^[0-9]{8}[A-Z]$")) {
                    JOptionPane.showMessageDialog(acDialog, "Error: Formato del dni incorrecto (8 Numeros y una Mayúscula)", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                if (!correo.equals("") && !correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                    JOptionPane.showMessageDialog(acDialog, "Error: Formato del correo incorrecto (xx@xx)", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                if (!telefono.equals("") && !telefono.matches("^[0-9]{9}$")) {
                    JOptionPane.showMessageDialog(acDialog, "Error: Formato del telefono incorrecto (9 digitos)", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }

                Date fechaActual = new Date(); // Captura la fecha y hora actual

                if (fechaAltFormateada != null) {
                    if (fAlt.after(fechaActual)) {
                        JOptionPane.showMessageDialog(acDialog, "Error: La fecha de alta es posterior a la fecha actual", null, JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                }

                if (fNac != null) {
                    // 1. Obtener la fecha actual
                    Calendar hoy = Calendar.getInstance();

                    // 2. Obtener la fecha de nacimiento
                    Calendar fechaNacimiento = Calendar.getInstance();
                    fechaNacimiento.setTime(fNac);

                    // 3. Calcular la edad inicial (diferencia de años)
                    int edad = hoy.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);

                    // 4. Ajustar si aún no ha cumplido años en el año actual
                    if (hoy.get(Calendar.DAY_OF_YEAR) < fechaNacimiento.get(Calendar.DAY_OF_YEAR)) {
                        edad--;
                    }

                    // 5. Validaciones
                    if (fNac.after(new Date())) {
                        JOptionPane.showMessageDialog(acDialog, "Error: La fecha de nacimiento no puede ser futura.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    } else if (edad < 18) {
                        JOptionPane.showMessageDialog(acDialog, "Error: El socio debe ser mayor de edad (18 años o más). Edad actual: " + edad, "Restricción de Edad", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }
                 
               
                Socio soc = new Socio(codigo, nombre, dni, fechaNacFormateada, telefono, correo, fechaAltFormateada, categoria.charAt(0));
                Session session = sessionFactory.openSession();
                Transaction tr = session.beginTransaction();
                try {
                    ok = SocioDAO.actualizarSocio(session, soc);
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
                    JOptionPane.showMessageDialog(acDialog, "Socio actualizado correctamente", null, JOptionPane.INFORMATION_MESSAGE);
                }

                dibujaRellenaTablaSocios();
                acDialog.dispose();

                break;
            }

        }
    }
}
