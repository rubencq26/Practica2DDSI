/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import Modelo.Monitor;
import Modelo.Socio;
import Vista.ActividadesPanel;
import Vista.MonitorPanel;
import Vista.SocioPanel;
import java.awt.Color;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Clase de utilidades encargada de la gestión, dibujo y rellenado de las tablas (JTable) 
 * de la interfaz gráfica. Centraliza la configuración de modelos y estilos visuales.
 * * @author rubco
 */
public class GestionTablas {

    private static DefaultTableModel modeloTablaMonitores;
    private static DefaultTableModel modeloTablaSocios;
    private static DefaultTableModel modeloTablaActividad;
    private static DefaultTableModel modeloTablaRealiza1;
    private static DefaultTableModel modeloTablaRealiza2;

    /**
     * Inicializa los modelos de las tablas de inscripciones (inscritas y disponibles) 
     * configurándolas como no editables.
     * * @param vInscripcionesPanel Panel de vista que contiene las tablas de inscripciones.
     */
    public static void inicializarTablaRealiza(Vista.InscripcionesPanel vInscripcionesPanel) {
        modeloTablaRealiza1 = new DefaultTableModel(new Object[]{"Actividades Inscritas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTablaRealiza2 = new DefaultTableModel(new Object[]{"Actividades Disponibles"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        vInscripcionesPanel.tabla1.setModel(modeloTablaRealiza1);
        vInscripcionesPanel.tabla2.setModel(modeloTablaRealiza2);

    }

    /**
     * Vacía el contenido de las dos tablas del panel de inscripciones.
     */
    public static void vaciarTablasInscripciones() {
        if (modeloTablaRealiza1 != null) {
            modeloTablaRealiza1.setRowCount(0);
        }
        if (modeloTablaRealiza2 != null) {
            modeloTablaRealiza2.setRowCount(0);
        }
    }

    /**
     * Rellena la tabla de actividades inscritas con una lista de cadenas.
     * @param actividades Lista de nombres o IDs de actividades en las que el socio está inscrito.
     */
    public static void rellenarTablaRealiza1(List<String> actividades) {
        int i = 0;
        Object[] ob = new Object[1];
        for (String st : actividades) {
            ob[0] = st;
            modeloTablaRealiza1.addRow(ob);
            i++;
        }

        System.out.println("Tabla1 rellenada");
    }

    /**
     * Rellena la tabla de actividades disponibles con una lista de cadenas.
     * @param actividades Lista de nombres o IDs de actividades disponibles para el socio.
     */
    public static void rellenarTablaRealiza2(List<String> actividades) {
        int i = 0;
        Object[] ob = new Object[1];
        for (String st : actividades) {
            ob[0] = st;
            modeloTablaRealiza2.addRow(ob);
            i++;
        }

        System.out.println("Tabla1 rellenada");
    }

    /**
     * Configura el aspecto visual de la tabla de actividades inscritas.
     * @param vInscripcionesPanel Referencia a la vista para acceder al componente JTable.
     */
    public static void dibujarTablaRealiza1(Vista.InscripcionesPanel vInscripcionesPanel) {
        JTable t = vInscripcionesPanel.tabla1;
        t.setFillsViewportHeight(true);
        t.setShowGrid(true);
        t.setGridColor(Color.LIGHT_GRAY);
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        t.getTableHeader().setResizingAllowed(false);
        t.getTableHeader().setReorderingAllowed(false);
        t.setAutoCreateRowSorter(true);
    }

    /**
     * Configura el aspecto visual de la tabla de actividades disponibles.
     * @param vInscripcionesPane Referencia a la vista para acceder al componente JTable.
     */
    public static void dibujarTablaRealiza2(Vista.InscripcionesPanel vInscripcionesPane) {
        JTable t = vInscripcionesPane.tabla2;
        t.setFillsViewportHeight(true);
        t.setShowGrid(true);
        t.setGridColor(Color.LIGHT_GRAY);
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        t.getTableHeader().setResizingAllowed(false);
        t.getTableHeader().setReorderingAllowed(false);
        t.setAutoCreateRowSorter(true);
    }

    /**
     * Inicializa el modelo de la tabla de monitores como no editable.
     * @param vMonitor Panel de vista que contiene la tabla de monitores.
     */
    public static void inicializarTablaMonitor(Vista.MonitorPanel vMonitor) {
        modeloTablaMonitores = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        vMonitor.tablaMonitor.setModel(modeloTablaMonitores);

    }

    /**
     * Define las columnas y el formato visual de la tabla de monitores, 
     * incluyendo la configuración de anchuras específicas para cada columna.
     * * @param vMonitor Referencia al panel de monitores.
     */
    public static void dibujarTablaMonitores(MonitorPanel vMonitor) {
        String[] columnas = {"Codigo", "Nombre", "DNI", "Teléfono", "Correo", "Fecha Incorporación", "Nick"};
        modeloTablaMonitores.setColumnIdentifiers(columnas);

        JTable t = vMonitor.tablaMonitor;
        t.setFillsViewportHeight(true);
        t.setShowGrid(true);
        t.setGridColor(Color.LIGHT_GRAY);
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        t.getTableHeader().setResizingAllowed(false);
        t.getTableHeader().setReorderingAllowed(false);
        t.setAutoCreateRowSorter(true);
        int[] anchuras = {60, 300, 100, 100, 270, 150, 100};

        TableColumnModel modeloColumna = t.getColumnModel();

        for (int i = 0; i < anchuras.length; i++) {
            TableColumn columna = modeloColumna.getColumn(i);
            int ancho = anchuras[i];
            columna.setMinWidth(ancho);
            columna.setPreferredWidth(ancho);
        }

    }

    /**
     * Rellena la tabla de monitores con los datos de una lista de objetos Monitor.
     * @param monitores Lista de objetos Monitor provenientes de la base de datos.
     */
    public static void rellenarTablaMonitores(List<Monitor> monitores) {
        int i = 0;
        Object[] fila = new Object[7];
        for (Monitor monitor : monitores) {
            fila[0] = monitor.getCodMonitor();
            fila[1] = monitor.getNombre();
            fila[2] = monitor.getDni();
            fila[3] = monitor.getTelefono();
            fila[4] = monitor.getCorreo();
            fila[5] = monitor.getFechaEntrada();
            fila[6] = monitor.getNick();
            modeloTablaMonitores.addRow(fila);
            i++;
        }

        System.out.println("Tabla rellenada");
    }

    /**
     * Elimina todas las filas de la tabla de monitores.
     */
    public static void vaciarTablaMonitores() {
        modeloTablaMonitores.setRowCount(0);
        System.out.println("Tabla vaciada");
    }

    /**
     * Inicializa el modelo de la tabla de socios como no editable.
     * @param vSocio Panel de vista que contiene la tabla de socios.
     */
    public static void inicializarTablaSocio(Vista.SocioPanel vSocio) {
        modeloTablaSocios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        vSocio.tablaSocio.setModel(modeloTablaSocios);

    }

    /**
     * Define las columnas y el formato visual de la tabla de socios,
     * aplicando anchos de columna predefinidos.
     * * @param vSocio Referencia al panel de socios.
     */
    public static void dibujarTablaSocio(SocioPanel vSocio) {
        String[] columnas = {"Codigo", "Nombre", "DNI", "Fecha de Nacimiento", "Teléfono", "Correo", "Fecha de Alta", "Cat"};
        modeloTablaSocios.setColumnIdentifiers(columnas);

        JTable t = vSocio.tablaSocio;
        t.setFillsViewportHeight(true);
        t.setShowGrid(true);
        t.setGridColor(Color.LIGHT_GRAY);
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        t.getTableHeader().setResizingAllowed(false);
        t.getTableHeader().setReorderingAllowed(false);
        t.setAutoCreateRowSorter(true);
        int[] anchuras = {60, 300, 100, 100, 270, 150, 100};

        TableColumnModel modeloColumna = t.getColumnModel();

        for (int i = 0; i < anchuras.length; i++) {
            TableColumn columna = modeloColumna.getColumn(i);
            int ancho = anchuras[i];
            columna.setMinWidth(ancho);
            columna.setPreferredWidth(ancho);
        }
    }

    /**
     * Rellena la tabla de socios con los datos de una lista de objetos Socio.
     * @param socios Lista de objetos Socio provenientes de la base de datos.
     */
    public static void rellenarTablaSocio(List<Socio> socios) {
        Object[] fila = new Object[8];
        for (Modelo.Socio socio : socios) {
            fila[0] = socio.getNumeroSocio();
            fila[1] = socio.getNombre();
            fila[2] = socio.getDni();
            fila[3] = socio.getFechaNacimiento();
            fila[4] = socio.getTelefono();
            fila[5] = socio.getCorreo();
            fila[6] = socio.getFechaEntrada();
            fila[7] = socio.getCategoria();
            modeloTablaSocios.addRow(fila);
        }
        System.out.println("Tabla rellenada");
    }

    /**
     * Elimina todas las filas de la tabla de socios.
     */
    public static void vaciarTablaSocio() {
        modeloTablaSocios.setRowCount(0);
        System.out.println("Tabla vaciada");
    }

    /**
     * Inicializa el modelo de la tabla de actividades como no editable.
     * @param vActividad Panel de vista que contiene la tabla de actividades.
     */
    public static void inicializarTablaActividad(Vista.ActividadesPanel vActividad) {
        modeloTablaActividad = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        vActividad.tablaActividad.setModel(modeloTablaActividad);

    }

    /**
     * Define las columnas y el formato visual de la tabla de actividades,
     * estableciendo anchos mínimos y preferidos para cada campo.
     * * @param vActividad Referencia al panel de actividades.
     */
    public static void dibujarTablaActividad(ActividadesPanel vActividad) {
        String[] columnas = {"Código", "Nombre", "Día", "Hora", "Descripción", "Precio", "Monitor Responsable"};
        modeloTablaActividad.setColumnIdentifiers(columnas);

        JTable t = vActividad.tablaActividad;
        t.setFillsViewportHeight(true);
        t.setShowGrid(true);
        t.setGridColor(Color.LIGHT_GRAY);
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        t.getTableHeader().setResizingAllowed(false);
        t.getTableHeader().setReorderingAllowed(false);
        t.setAutoCreateRowSorter(true);
        int[] anchuras = {60, 250, 100, 50, 350, 50, 270};

        TableColumnModel modeloColumna = t.getColumnModel();

        for (int i = 0; i < anchuras.length; i++) {
            TableColumn columna = modeloColumna.getColumn(i);
            int ancho = anchuras[i];
            columna.setMinWidth(ancho);
            columna.setPreferredWidth(ancho);
        }
    }

    /**
     * Rellena la tabla de actividades con datos formateados. 
     * A diferencia de otras tablas, esta recibe un array de objetos debido a la unión 
     * realizada en la consulta SQL para mostrar nombres de monitores.
     * * @param actividades Lista de arrays de objetos conteniendo los datos de la actividad.
     */
    public static void rellenarTablaActividad(List<Object[]> actividades) {
        Object[] fila = new Object[7];
        for (Object[] actividad : actividades) {
            fila[0] = actividad[0];
            fila[1] = actividad[1];
            fila[2] = actividad[2];
            fila[3] = actividad[3];
            fila[4] = actividad[4];
            fila[5] = actividad[5];
            fila[6] = actividad[6];
            modeloTablaActividad.addRow(fila);
        }
        System.out.println("Tabla rellenada");
    }

    /**
     * Elimina todas las filas de la tabla de actividades.
     */
    public static void vaciarTablaActividad() {
        modeloTablaActividad.setRowCount(0);
        System.out.println("Tabla vaciada");
    }

}