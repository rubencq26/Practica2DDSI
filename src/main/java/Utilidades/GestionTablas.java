/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import Modelo.Actividad;
import Modelo.Monitor;
import Modelo.Socio;
import Vista.ActividadesPanel;
import Vista.MonitorPanel;
import Vista.SocioPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author rubco
 */
public class GestionTablas {

    private static DefaultTableModel modeloTablaMonitores;
    private static DefaultTableModel modeloTablaSocios;
    private static DefaultTableModel modeloTablaActividad;

    public static void inicializarTablaMonitor(Vista.MonitorPanel vMonitor) {
        modeloTablaMonitores = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        vMonitor.tablaMonitor.setModel(modeloTablaMonitores);

    }

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

    public static void vaciarTablaMonitores() {
        modeloTablaMonitores.setRowCount(0);
        System.out.println("Tabla vaciada");
    }

    public static void inicializarTablaSocio(Vista.SocioPanel vSocio) {
        modeloTablaSocios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        vSocio.tablaSocio.setModel(modeloTablaSocios);

    }

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

    public static void vaciarTablaSocio() {
        modeloTablaSocios.setRowCount(0);
        System.out.println("Tabla vaciada");
    }

    public static void inicializarTablaActividad(Vista.ActividadesPanel vActividad) {
        modeloTablaActividad = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        vActividad.tablaActividad.setModel(modeloTablaActividad);

    }

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

    public static void vaciarTablaActividad() {
        modeloTablaActividad.setRowCount(0);
        System.out.println("Tabla vaciada");
    }

}
