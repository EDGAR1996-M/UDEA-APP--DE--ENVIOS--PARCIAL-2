package vistas;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

public class EnvioVista extends JFrame {
    private final JComboBox<String> cmbTipo = new JComboBox<>();
    private final JTextField txtCliente = new JTextField(16);
    private final JTextField txtCodigo = new JTextField(12);
    private final JTextField txtPeso = new JTextField(8);
    private final JTextField txtDistancia = new JTextField(8);
    private final JButton btnAgregar = new JButton("Agregar envío");
    private final JButton btnRetirar = new JButton("Retirar por código");
    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[]{"Código", "Cliente", "Tipo", "Peso (kg)", "Distancia (km)", "Tarifa"},
            0
    ) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    public EnvioVista() {
        setTitle("Administración de envíos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 480);
        setMinimumSize(new java.awt.Dimension(760, 400));
        setLocationRelativeTo(null);
        txtPeso.setToolTipText("Ingrese un valor mayor que cero; puede usar punto o coma decimal.");
        txtDistancia.setToolTipText("Ingrese un valor mayor que cero; puede usar punto o coma decimal.");
        txtCodigo.setToolTipText("Este campo también se usa para retirar un envío por su código.");

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del envío"));
        agregarCampo(formulario, "Tipo:", cmbTipo, 1, 0);
        agregarCampo(formulario, "Cliente:", txtCliente, 3, 0);
        agregarCampo(formulario, "Código:", txtCodigo, 1, 1);
        agregarCampo(formulario, "Peso (kg):", txtPeso, 3, 1);
        agregarCampo(formulario, "Distancia (km):", txtDistancia, 5, 1);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        acciones.add(btnRetirar);
        acciones.add(btnAgregar);
        GridBagConstraints restriccionesAcciones = new GridBagConstraints();
        restriccionesAcciones.gridx = 0;
        restriccionesAcciones.gridy = 2;
        restriccionesAcciones.gridwidth = GridBagConstraints.REMAINDER;
        restriccionesAcciones.fill = GridBagConstraints.HORIZONTAL;
        restriccionesAcciones.insets = new Insets(5, 5, 5, 5);
        formulario.add(acciones, restriccionesAcciones);

        JTable tabla = new JTable(modeloTabla);
        tabla.setFillsViewportHeight(true);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private static void agregarCampo(JPanel panel, String etiqueta, java.awt.Component campo, int columna, int fila) {
        GridBagConstraints restriccionesEtiqueta = new GridBagConstraints();
        restriccionesEtiqueta.gridx = columna;
        restriccionesEtiqueta.gridy = fila;
        restriccionesEtiqueta.insets = new Insets(5, 8, 5, 4);
        restriccionesEtiqueta.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel(etiqueta), restriccionesEtiqueta);

        GridBagConstraints restriccionesCampo = new GridBagConstraints();
        restriccionesCampo.gridx = columna + 1;
        restriccionesCampo.gridy = fila;
        restriccionesCampo.insets = new Insets(5, 4, 5, 8);
        restriccionesCampo.anchor = GridBagConstraints.LINE_START;
        panel.add(campo, restriccionesCampo);
    }

    public void setTiposEnvio(List<String> tipos) {
        cmbTipo.removeAllItems();
        tipos.forEach(cmbTipo::addItem);
    }

    public String getTipoSeleccionado() {
        return (String) cmbTipo.getSelectedItem();
    }

    public String getCliente() {
        return txtCliente.getText();
    }

    public String getCodigo() {
        return txtCodigo.getText();
    }

    public String getPeso() {
        return txtPeso.getText().trim();
    }

    public String getDistancia() {
        return txtDistancia.getText().trim();
    }

    public void setAgregarClick(ActionListener evento) {
        btnAgregar.addActionListener(evento);
    }

    public void setRetirarClick(ActionListener evento) {
        btnRetirar.addActionListener(evento);
    }

    public void mostrarEnvios(Object[][] filas) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : filas) {
            modeloTabla.addRow(fila);
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Envíos", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarFormulario() {
        txtCliente.setText("");
        txtCodigo.setText("");
        txtPeso.setText("");
        txtDistancia.setText("");
        txtCliente.requestFocusInWindow();
    }
}
