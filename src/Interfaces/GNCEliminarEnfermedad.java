package Interfaces;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import Controlador.EnfermedadBeanRemote;
import Controlador.EnfermedadTerneraBeanRemote;
import excepciones.GNCException;
import entidades.Enfermedad;


public class GNCEliminarEnfermedad extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Launch the application.
	 */
	JLabel lblNombre;
	JButton btnAceptar,btnCancelar; 
	JLabel lblMensajes ;
	EnfermedadBeanRemote controladorEnfermedad;
	EnfermedadTerneraBeanRemote controladorTernerasEnfermas;
	private JLabel lblId;
	private JLabel lblIdEnfermedad;
	private long idEnfermedad = 0;
	Enfermedad enferm;
	private static GNCEnfermedades formEnfermedades;
    JLabel lblnombreGradoGravedad ,lblNombreEnfermedad;
	/**
	 * Create the frame.
	 */
	public GNCEliminarEnfermedad(long enfermedadId) {
		
		idEnfermedad = enfermedadId;
		getContentPane().setFont(new Font("Tahoma", Font.BOLD, 12));
		setTitle("Eliminar Enfermedad");
		
		setIconifiable(true);
		
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(12, 102, 165, 20);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblNombre);
		
		JLabel lblGravedad = new JLabel("Grado de gravedad");
		lblGravedad.setBounds(12, 134, 165, 20);
		lblGravedad.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblGravedad);
		
		 btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(120, 180, 89, 23);
		getContentPane().add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(219, 180, 89, 23);
		getContentPane().add(btnCancelar);
		
		JLabel lblRegistroDeNueva = new JLabel("Eliminar Enfermedad");
		lblRegistroDeNueva.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRegistroDeNueva.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDeNueva.setBounds(21, 17, 384, 14);
		getContentPane().add(lblRegistroDeNueva);
		
        lblMensajes = new JLabel("");
        lblMensajes.setBounds(10, 220, 395, 14);
        getContentPane().add(lblMensajes);
        
        lblId = new JLabel("Id Enfermedad");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblId.setBounds(12, 64, 165, 20);
        getContentPane().add(lblId);
        
        lblIdEnfermedad = new JLabel();
        lblIdEnfermedad.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblIdEnfermedad.setBounds(172, 64, 233, 20);
        getContentPane().add(lblIdEnfermedad);
        
        lblNombreEnfermedad = new JLabel("");
        lblNombreEnfermedad.setHorizontalAlignment(SwingConstants.LEFT);
        lblNombreEnfermedad.setBounds(172, 106, 200, 14);
        getContentPane().add(lblNombreEnfermedad);

         lblnombreGradoGravedad = new JLabel("");
        lblnombreGradoGravedad.setHorizontalAlignment(SwingConstants.LEFT);
        lblnombreGradoGravedad.setBounds(172, 138, 200, 14);
        getContentPane().add(lblnombreGradoGravedad);

        try {
			conectar();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cargarDatosDeLaBD(enfermedadId);
		
		btnAceptar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {   
	        	accionEliminar();       	
	        }
	    });
		
		btnCancelar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {   
	        	accionCancelar();
	        }
	    });
	}
	//Carga los datos de la base de datos al formulario
	public void cargarDatosDeLaBD(Long idEnfermedad){
		//Obtengo la materia de id el que me pasan por parámetro en el constructor
		enferm = controladorEnfermedad.obtenerEnfermedadPorId(idEnfermedad);
		lblIdEnfermedad.setText(String.valueOf(enferm.getIdEnfermedad()));
		lblNombreEnfermedad.setText(enferm.getNombre().getNombre());
		lblnombreGradoGravedad.setText(String.valueOf(enferm.getGradoGravedad()));
	}
	//Carga los controladores remotos de 
	public void conectar() throws NamingException{
		controladorEnfermedad = (EnfermedadBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/EnfermedadBean!Controlador.EnfermedadBeanRemote");
		controladorTernerasEnfermas= (EnfermedadTerneraBeanRemote)
		InitialContext.doLookup("PTIGNCJPA/EnfermedadTerneraBean!Controlador.EnfermedadTerneraBeanRemote");
	}
	
	private void accionCancelar() {
		// si se cancela, se eliminar la ventana
		ventana();
		this.dispose();
	}
	private void accionEliminar() {
		// Si es ingresar se validan datos!
			boolean existe = controladorTernerasEnfermas.existeEnfermedadEnTernaraEnfermedad(enferm.getIdEnfermedad());
			if(existe){
				
					JOptionPane.showInternalMessageDialog(this, "La Enfermedad tiene registros Asociados, \n imposible Eliminar",
						"Error al Eliminar!", JOptionPane.WARNING_MESSAGE);
					ventana();
					this.dispose();
			}else{
				boolean eliminado = false;
				try {
					eliminado = controladorEnfermedad.eliminarEnfermedad(enferm);
				} catch (GNCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (eliminado) {
					JOptionPane.showInternalMessageDialog(this, "la  Enfermedad se ha sido Eliminado con éxito.",
							"Enfermedad Elimada!", JOptionPane.INFORMATION_MESSAGE);
					
					// cerramos la ventanta
					
					ventana();
					this.dispose();
				}
				else{
					JOptionPane.showInternalMessageDialog(this, "Hubo un error al almacenar. Intente nuevamente más tarde",
							"Error al registrar!", JOptionPane.ERROR_MESSAGE);
				}
			}
	}
	public void ventana(){
    	if(formEnfermedades == null || formEnfermedades.isClosed()){
    		try {
				formEnfermedades = new GNCEnfermedades();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		formEnfermedades.setVisible(true);
        	GNCPrincipal.escritorio.add(formEnfermedades);
        	GNCPrincipal.centrarVentana(formEnfermedades);
        	formEnfermedades.toFront();
    	}
    	else{
    		 JOptionPane.showInternalMessageDialog(GNCPrincipal.escritorio, "¡Formulario ya esta abierto!", "Aviso: Enfermedades", JOptionPane.INFORMATION_MESSAGE);
    	}  
	}
}