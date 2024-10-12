/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Persona;
import modelo.PersonaDAO;
import vista.Vista;

/**
 *
 * @author Miguel
 */
public class Controlador implements ActionListener {
    private PersonaDAO dao = new PersonaDAO();
    private Vista vista;
    private DefaultTableModel modelo;

public Controlador(Vista v) {
    this.vista = v;
    this.modelo = (DefaultTableModel) vista.getTabla().getModel(); // Acceso a la tabla

    // Registro de los listeners de los botones usando getters
    vista.getBtnListar().addActionListener(this);
    vista.getBtnGuardar().addActionListener(this);
    vista.getBtnEditar().addActionListener(this);
    vista.getBtnActualizar().addActionListener(this);
    vista.getBtnEliminar().addActionListener(this);
    
    listar(); // Listar al inicializar el controlador
}

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == vista.getBtnListar()) { // Usa el getter
        listar();
    } else if (e.getSource() == vista.getBtnGuardar()) { // Usa el getter
        agregar(); 
    } else if (e.getSource() == vista.getBtnEditar()) { // Usa el getter
        editar();
    } else if (e.getSource() == vista.getBtnActualizar()) { // Usa el getter
        actualizar();
    } else if (e.getSource() == vista.getBtnEliminar()) { // Usa el getter
        eliminar();
    }
}

    public void guardar(String id, String nombres, String correo, String telefono) {
    // Validar campos (opcional, puedes mover la validación a otro método)
    if (nombres.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
        showMessage("Todos los campos son obligatorios.");
        return;
    }

    // Crear una nueva Persona
    Persona persona = new Persona(nombres, correo, telefono);

    // Llamar al DAO para agregar la persona
    if (dao.agregar(persona) == 1) {
        showMessage("Persona guardada con éxito.");
    } else {
        showMessage("Error al guardar persona.");
    }

    // Listar nuevamente para actualizar la tabla
    listar();
}

    
public void editar() {
    int fila = vista.getTabla().getSelectedRow(); // Obtener la fila seleccionada
    if (fila == -1) {
        showMessage("Debe seleccionar una fila.");
    } else {
        int id = (int) vista.getTabla().getValueAt(fila, 0);
        String nombres = (String) vista.getTabla().getValueAt(fila, 1);
        String correo = (String) vista.getTabla().getValueAt(fila, 2);
        String telefono = (String) vista.getTabla().getValueAt(fila, 3);
        
        // Llenar los campos de texto de la vista usando setters
        vista.setId(String.valueOf(id));
        vista.setNombres(nombres);
        vista.setCorreo(correo);
        vista.setTelefono(telefono);
    }
}

public void eliminar() {
    int fila = vista.getTabla().getSelectedRow(); // Obtener la fila seleccionada
    if (fila == -1) {
        showMessage("Debe seleccionar un usuario.");
    } else {
        int id = (int) vista.getTabla().getValueAt(fila, 0); // Obtener el ID de la tabla
        if (dao.delete(id) > 0) { // Si se eliminó correctamente
            showMessage("Usuario eliminado.");
            listar(); // Actualiza la tabla después de eliminar
        } else {
            showMessage("Error al eliminar usuario.");
        }
    }
}

public void actualizar() {
    if (validarCampos()) {
        try {
            int id = Integer.parseInt(vista.getId()); // Obtener ID usando el método getter
            String nom = vista.getNombres(); // Usar el método getter
            String correo = vista.getCorreo(); // Usar el método getter
            String tel = vista.getTelefono(); // Usar el método getter
            
            Persona p = new Persona(id, nom, correo, tel);
            if (dao.actualizar(p) == 1) {
                showMessage("Usuario actualizado con éxito.");
                vista.limpiarCampos(); // Limpia los campos después de actualizar
            } else {
                showMessage("Error al actualizar usuario."); 
            }
            listar(); // Actualizar la tabla después de la operación
        } catch (NumberFormatException ex) {
            showMessage("ID debe ser un número.");
        }
    }
}

public void agregar() {
    if (validarCampos()) {
        String nom = vista.getNombres(); // Obtener nombres desde la vista
        String correo = vista.getCorreo(); // Obtener correo desde la vista
        String tel = vista.getTelefono(); // Obtener teléfono desde la vista
        Persona p = new Persona(nom, correo, tel); // Crear una nueva instancia de Persona
        
        // Inserta en la base de datos y muestra un mensaje
        if (dao.agregar(p) == 1) {
            showMessage("Usuario agregado con éxito.");
            vista.limpiarCampos(); // Limpia los campos después de agregar
        } else {
            showMessage("Error al agregar usuario."); 
        }
    }
}

    public void listar() {
        limpiarTabla(); // Limpia la tabla antes de mostrar nuevos datos
        List<Persona> lista = dao.listar(); // Obtiene la lista de personas del DAO

        // Llenar la tabla con los datos obtenidos
        for (Persona p : lista) {
            modelo.addRow(new Object[]{p.getId(), p.getNombres(), p.getCorreo(), p.getTelefono()});
        }
    }


    private void limpiarTabla() {
        modelo.setRowCount(0); // Limpiar todas las filas
    }

private boolean validarCampos() {
    String nom = vista.getNombres(); // Usar el getter
    String correo = vista.getCorreo(); // Usar el getter
    String tel = vista.getTelefono(); // Usar el getter

    if (nom.isEmpty() || correo.isEmpty() || tel.isEmpty()) {
        showMessage("Todos los campos son obligatorios.");
        return false;
    }
    if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        showMessage("Formato de correo no válido.");
        return false;
    }
    return true;
}

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(vista, message);
    }
}
