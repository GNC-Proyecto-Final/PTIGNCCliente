package Interfaces;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Controlador.EnfermedadBeanRemote;
import enumerados.NombreEnfermedad;
import excepciones.GNCException;
import entidades.Enfermedad;

import javax.swing.JComboBox;
import javax.swing.JSeparator;
import java.awt.Color;

public class GNCNuevaEnfermedad extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNombre;
	private JLabel lblGravedad;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JComboBox<String> cmbEnfermedad,cmbGravedad;
	
	EnfermedadBeanRemote controlador;
	private JLabel lblMensajes;

	/**
	 * Launch the application.
	 */

	public GNCNuevaEnfermedad() {
		
		
		getContentPane().setFont(new Font("Tahoma", Font.BOLD, 12));
		setTitle("Nueva Enfermedad");
		
		setIconifiable(true);
	//	setMaximizable(true);
		
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(21, 67, 165, 20);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNombre);
		
		lblGravedad = new JLabel("Grado de gravedad");
		lblGravedad.setBounds(21, 124, 165, 20);
		lblGravedad.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblGravedad);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(120, 180, 89, 23);
		getContentPane().add(btnAceptar);
		
		btnCancelar=new JButton("Cancelar");
		btnCancelar.setBounds(219, 180, 89, 23);
		getContentPane().add(btnCancelar);
		
		JLabel lblRegistroDeNueva = new JLabel("Registro de Nueva Enfermedad");
		lblRegistroDeNueva.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRegistroDeNueva.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDeNueva.setBounds(21, 17, 384, 14);
		getContentPane().add(lblRegistroDeNueva);
		
        lblMensajes = new JLabel("");
        lblMensajes.setBounds(10, 220, 395, 14);
        getContentPane().add(lblMensajes);
        try {
			conectar();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cargarComboEnfermedades();
		cargarComboGravedad();
		
		btnAceptar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {   
	        	accionGuardar();
	        	
	        }
	    });
		
		btnCancelar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {   
	        	accionCancelar();
	        }
	    });
	}
	
	public void cargarComboEnfermedades(){
		
		cmbEnfermedad = new JComboBox<String>();
		cmbEnfermedad.setBounds(210, 68, 195, 20);
		
		cmbEnfermedad.addItem("");
        for(NombreEnfermedad e: NombreEnfermedad.values()){
        	cmbEnfermedad.addItem(e.getNombre().toString());
        }
        getContentPane().add(cmbEnfermedad);
	}
	
	public void cargarComboGravedad(){
		
		cmbGravedad = new JComboBox<String>();
		cmbGravedad.setBounds(210, 113, 195, 20);
		
		String[] gravedad={"1","2","3"};
		cmbGravedad.addItem("");
        for(String e: gravedad){
        	cmbGravedad.addItem(e);
        }
        getContentPane().add(cmbGravedad);
	}
		
	public void conectar() throws NamingException{
		controlador = (EnfermedadBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/EnfermedadBean!Controlador.EnfermedadBeanRemote");
		
	}
	
	private void accionCancelar() {
		// si se cancela, se eliminar la ventana
		this.dispose();
	}
	private void accionGuardar() {
		// Si es ingresar se validan datos!
		String gGravedad = (String) this.cmbGravedad.getSelectedItem();
		String enfermedad = (String) this.cmbEnfermedad.getSelectedItem();
		
		
		NombreEnfermedad emumEnfermedad = null;
		for (NombreEnfermedad es : NombreEnfermedad.values()) {
            if (es.getNombre().equals(enfermedad)) {
            	emumEnfermedad = es;
            }
        }
		
		// Si alguno es vacío, mostramos una ventana de mensaje
		if (gGravedad.equals("") || enfermedad.equals("") ) {
			JOptionPane.showInternalMessageDialog(this, "Debe completar todos los datos solicitados.", "Datos incompletos!",
					JOptionPane.WARNING_MESSAGE);
				return;
		}
		else{
			int gradoGavedad = Integer.parseInt(gGravedad);
			
			Enfermedad enferm = new Enfermedad (gradoGavedad,emumEnfermedad);
			boolean existe = controlador.existeEnfermedad(enferm);
			if (existe) {
				JOptionPane.showInternalMessageDialog(this, "La Enfermedad ya se encuentra registrada.",
						"La enfermedad ya Existe!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			boolean almacenado = false;
			try {
				almacenado = controlador.ingresarNuevaEnfermedad(enferm);
			} catch (GNCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (almacenado) {
				JOptionPane.showInternalMessageDialog(this, "la  Enfermedad se ha sido registrado con éxito.",
						"Enfermedad Registrada!", JOptionPane.INFORMATION_MESSAGE);
				
				// cerramos la ventanta
				this.dispose();
			}
			else{
				JOptionPane.showInternalMessageDialog(this, "Hubo un error al almacenar. Intente nuevamente más tarde",
						"Error al registrar!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
