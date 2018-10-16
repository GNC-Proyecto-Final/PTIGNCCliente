package Interfaces;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import excepciones.GNCException;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.SwingConstants;
import Controlador.EnfermedadBeanRemote;
import Controlador.EnfermedadTerneraBeanRemote;
import Controlador.TernerasBeanRemote;
import entidades.Enfermedad;
import entidades.EnfermedadTernera;
import entidades.EnfermedadTerneraPK;
import entidades.Ternera;


public class GNCNuevoTerneraEnferma extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//JLAbels
	JLabel lblIDTernera;
	JLabel lblIDEnfermedad;
	JLabel lblFechaInicio;
	JLabel lblFechaFin;
	JLabel lblAspectosSanitarios;
	
	//JTextFields
	public static JTextField txtTernera;
	public static JTextField txtEnfermedad;
	JTextField txtAspectosSanitarios;
	JTextArea aspectSanitarios;
	private static GNCBuscarTernera formBuscarTernera;
	private static GNCBuscarEnfermedad formBuscarEnfermedad;
	//DataPickers
	
	private JDatePickerImpl datePickerInicio,datePickerFin;
	//JButtons
	JButton btnAceptar,btnCancelar,btnTernera,btnEnfermedad;

	private Date dateInicio,dateFin;
	
	/**
	 * Create the frame.
	 */
	public GNCNuevoTerneraEnferma() {
		setTitle("Registro Ternera Enferma");
		setIconifiable(true);
		//setMaximizable(true);
		setClosable(true);
		setBounds(60, 30, 450, 330);
		//Seteando los JLabel
		lblIDTernera=new JLabel("Id Enfermedad");
		lblIDTernera.setBounds(30, 48, 115, 20);
		lblIDTernera.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lblIDEnfermedad=new JLabel("Id Ternera");
		lblIDEnfermedad.setBounds(30, 79, 80, 20);
		lblIDEnfermedad.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lblFechaInicio=new JLabel("Fecha de inicio");
		lblFechaInicio.setBounds(30, 110, 125, 20);
		lblFechaInicio.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		
		lblFechaFin=new JLabel("Fecha de Fin");
		lblFechaFin.setBounds(30, 141, 125, 20);
		lblFechaFin.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lblAspectosSanitarios=new JLabel("Otros aspectos sanitarios");
		lblAspectosSanitarios.setBounds(30, 172, 163, 22);
		lblAspectosSanitarios.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		//Seteando los JTextField
		txtTernera=new JTextField();
		txtTernera.setBounds(145, 79, 125, 22);
		
		txtEnfermedad=new JTextField();
		txtEnfermedad.setBounds(145, 48, 125, 22);
		
				
		//seteando los JButtons
		btnAceptar=new JButton("Aceptar");
		btnAceptar.setBounds(334, 211, 90, 20);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formatoFecha();		
				try {
					accionGuardar();
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnCancelar=new JButton("Cancelar");
		btnCancelar.setBounds(334, 249, 90, 20);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				accionCancelar();
			}
		});
		
		
		
		this.datePickerInicio = this.creareDatePickerInicio();
		
		this.datePickerFin = this.creareDatePickerFin();
		
		btnTernera = new JButton("Ternera");
		btnTernera.setBounds(300, 82, 89, 23);
		btnTernera.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnTernera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarFormBuscarTernera();
			}
		});
		
		btnEnfermedad = new JButton("Enfermedad");
		btnEnfermedad.setBounds(300, 48, 89, 23);
		btnEnfermedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarFormBuscarEnfermedad();
			}
		});
		btnEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		
		//seteando JScrollPane
		aspectSanitarios=new JTextArea();
		
		aspectSanitarios.setBounds(30, 201, 286, 77);
		aspectSanitarios.setLineWrap(true);
		
		JLabel lblRegistroDeTernera = new JLabel("Registro de Ternera Enferma");
		lblRegistroDeTernera.setBounds(20, 11, 404, 14);
		lblRegistroDeTernera.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDeTernera.setFont(new Font("Tahoma", Font.BOLD, 16));
		getContentPane().setLayout(null);
		getContentPane().add(lblIDTernera);
		getContentPane().add(lblIDEnfermedad);
		getContentPane().add(lblFechaInicio);
		getContentPane().add(lblFechaFin);
		getContentPane().add(lblAspectosSanitarios);
		getContentPane().add(txtTernera);
		getContentPane().add(txtEnfermedad);
		getContentPane().add(btnAceptar);
		getContentPane().add(btnCancelar);
		getContentPane().add(datePickerInicio);
		getContentPane().add(datePickerFin);
		getContentPane().add(btnTernera);
		getContentPane().add(btnEnfermedad);
		getContentPane().add(aspectSanitarios);
		getContentPane().add(lblRegistroDeTernera);

	}

	private JDatePickerImpl creareDatePickerInicio() {

		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePickerInicio = new JDatePickerImpl(datePanel);
		datePickerInicio.setBounds(145, 110, 125, 20);
		return datePickerInicio;
	}
	private JDatePickerImpl creareDatePickerFin() {

		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePickerFin = new JDatePickerImpl(datePanel);
		datePickerFin.setBounds(145, 141, 125, 20);
		return datePickerFin;
	}
	
	
	public void cargarFormBuscarEnfermedad(){
    	if(formBuscarEnfermedad == null || formBuscarEnfermedad.isClosed()){
    		formBuscarEnfermedad = new GNCBuscarEnfermedad();
    		formBuscarEnfermedad.setVisible(true);
    		GNCPrincipal.escritorio.add(formBuscarEnfermedad);
    		GNCPrincipal.centrarVentana(formBuscarEnfermedad);
        	formBuscarEnfermedad.toFront();
    	}
    	else{
    		 JOptionPane.showInternalMessageDialog(GNCPrincipal.escritorio, "¡Formulario ya esta abierto!", "Aviso: Buscar Enfermedades", JOptionPane.INFORMATION_MESSAGE);
    	}
	}
	public void cargarFormBuscarTernera(){
    	if(formBuscarTernera == null || formBuscarTernera.isClosed()){
    		formBuscarTernera = new GNCBuscarTernera();
    		formBuscarTernera.setVisible(true);
        	GNCPrincipal.escritorio.add(formBuscarTernera);
        	GNCPrincipal.centrarVentana(formBuscarTernera);
        	formBuscarTernera.toFront();
    	}
    	else{
    		 JOptionPane.showInternalMessageDialog(GNCPrincipal.escritorio, "¡Formulario ya esta abierto!", "Aviso: Buscar Terneras", JOptionPane.INFORMATION_MESSAGE);
    	}
	}

	private void accionCancelar() {
		// si se cancela, se eliminar la ventana
		this.dispose();
	}
	
	public void  formatoFecha(){
		
		Date fechaInicio = (Date) this.datePickerInicio.getModel().getValue();
		Date fechaFin = (Date) this.datePickerFin.getModel().getValue();
		
		SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			if(!(fechaInicio==null)&& !(fechaFin==null)){
				String dti = dt1.format(fechaInicio);
				String dtf = dt1.format(fechaFin);
				dateInicio = dt1.parse(dti);
				dateFin = dt1.parse(dtf);
			}else if(!(fechaInicio==null)&& (fechaFin==null)){
				String dti = dt1.format(fechaInicio);
				dateInicio = dt1.parse(dti);
			}
			else if((fechaInicio==null)&& (fechaFin==null)){
				
			}
			else{
				String dtf = dt1.format(fechaFin);
				dateFin = dt1.parse(dtf);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean validarFecha(){
		
		boolean fechaValida= false;
		Date fechaInicio = (Date) this.datePickerInicio.getModel().getValue();
		Date fechaFin = (Date) this.datePickerFin.getModel().getValue();
		
		Date fecha = new Date();
		
			if(!(fechaInicio==null)&& !(fechaFin==null)){
				if(dateInicio.compareTo(dateFin)<=0 && dateInicio.compareTo(fecha)<=0  && dateFin.compareTo(fecha)<=0){
					fechaValida= true;
				}

			}else if(!(fechaInicio==null)&& (fechaFin==null)){
				if(dateInicio.compareTo(fecha)<=0 ){
					fechaValida= true;
				}
			}
			else if((fechaInicio==null)&& (fechaFin==null)){

					fechaValida= false;
			}
			else{
				if( dateFin.compareTo(fecha)<=0 ){
					fechaValida= true;
				}
			}
			return fechaValida;
		
	}
	private void accionGuardar() throws NamingException{
		// Si es ingresar se validan datos!
				String terneraString =  GNCNuevoTerneraEnferma.txtTernera.getText();
				String enfermedadString = GNCNuevoTerneraEnferma.txtEnfermedad.getText();
				String aspectSan = aspectSanitarios.getText();
				
				Long idTernera=Long.parseLong(terneraString);
				TernerasBeanRemote controladorTernera = (TernerasBeanRemote)
						InitialContext.doLookup("PTIGNCJPA/TernerasBean!Controlador.TernerasBeanRemote");
				Ternera ternera = controladorTernera.obtenerTerneraPorId(idTernera);
				
				if(!(ternera.getFechaMuerte()==null)||!(ternera.getFechaBaja()==null)){
					JOptionPane.showInternalMessageDialog(this, "Verifique que la ternera este habilitada, ternera muerta o dada de baja", "Ternera no habilitada",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
								
				if( !validarFecha()){
					JOptionPane.showInternalMessageDialog(this, "Verifique las fechas: \n Fecha Inicio o Fin mayor a fecha actual", "Fechas Incorrectas!",
							JOptionPane.WARNING_MESSAGE);
						return;
				}		
				
				if (aspectSan.length()>=250) {
					JOptionPane.showInternalMessageDialog(this, "Otros aspectos Sanitarios \n Excede de los 250 caracteres ", "Otros aspectos sanitarios!",
							JOptionPane.WARNING_MESSAGE);
						return;
				}
				
				// Si alguno es vacio, mostramos una ventana de mensaje
				if (terneraString.equals("") || enfermedadString.equals("")||  dateInicio == null ) {
					JOptionPane.showInternalMessageDialog(this, "Debe completar los campos \n(Ternera, Enfermedad, Fecha Inicio.", "Datos incompletos!",
							JOptionPane.WARNING_MESSAGE);
						return;
				}
				else{
					long  idEnfermedad = Long.parseLong(enfermedadString);
					
			
					EnfermedadBeanRemote controladorEnfermedad = (EnfermedadBeanRemote)
							InitialContext.doLookup("PTIGNCJPA/EnfermedadBean!Controlador.EnfermedadBeanRemote");

					Enfermedad enfermedad = controladorEnfermedad.obtenerEnfermedadPorId(idEnfermedad);
									
					EnfermedadTerneraBeanRemote controladorTerneraEnfermedad = (EnfermedadTerneraBeanRemote)
								InitialContext.doLookup("PTIGNCJPA/EnfermedadTerneraBean!Controlador.EnfermedadTerneraBeanRemote");
					
					if(dateFin == null){
						EnfermedadTerneraPK pk = new EnfermedadTerneraPK();
						pk.setIdEnfermedad(idEnfermedad);
						pk.setIdTernera(idTernera);
						pk.setFechaDesde(dateInicio);
						EnfermedadTernera terneraEnferma = new EnfermedadTernera (pk,ternera,enfermedad,aspectSan);
						
										
						boolean existe = controladorTerneraEnfermedad.obtenerTerneraEnfermaFechaExiste(terneraEnferma);
						if (existe) {
							JOptionPane.showInternalMessageDialog(this, "La Enfermedad por Ternera ya se encuentra registrada.",
									"La enfermedad ya Existe!", JOptionPane.WARNING_MESSAGE);
							return;
						}
							
						boolean almacenado = false;
						try {
							
							almacenado = controladorTerneraEnfermedad.crearTerneraEnferma(terneraEnferma);
						
						} catch (GNCException e) {
							e.printStackTrace();
						}
						
						if (almacenado) {
							JOptionPane.showInternalMessageDialog(this, "la  Enfermedad por Ternera se ha sido registrado con Exito.",
									"Enfermedad Registrada!", JOptionPane.INFORMATION_MESSAGE);
							
							// cerramos la ventanta
							this.dispose();
						}
						else{
							JOptionPane.showInternalMessageDialog(this, "Hubo un error al almacenar. Intente nuevamente mas tarde",
									"Error al registrar!", JOptionPane.ERROR_MESSAGE);
						}
					}
					
					else{
								
						EnfermedadTerneraPK pk = new EnfermedadTerneraPK();
						pk.setIdEnfermedad(idEnfermedad);
						pk.setIdTernera(idTernera);
						pk.setFechaDesde(dateInicio);
						EnfermedadTernera terneraEnferma = new EnfermedadTernera (pk,ternera,enfermedad,dateFin,aspectSan);
						
						boolean existe = controladorTerneraEnfermedad.obtenerTerneraEnfermaFechaExiste(terneraEnferma);
						if (existe) {
							JOptionPane.showInternalMessageDialog(this, "La Enfermedad por Ternera ya se encuentra registrada.",
									"La enfermedad ya Existe!", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						boolean almacenado = false;
						try {
							almacenado = controladorTerneraEnfermedad.crearTerneraEnferma(terneraEnferma);
						} catch (GNCException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if (almacenado) {
							JOptionPane.showInternalMessageDialog(this, "la  Enfermedad por Ternera se ha sido registrado con Exito.",
									"Enfermedad Registrada!", JOptionPane.INFORMATION_MESSAGE);				
							// cerramos la ventanta
							this.dispose();
						}
						else{
							JOptionPane.showInternalMessageDialog(this, "Hubo un error al almacenar. Intente nuevamente mas tarde",
									"Error al registrar!", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
	}
}