package Interfaces;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Controlador.EnfermedadBeanRemote;
import Controlador.EnfermedadTerneraBeanRemote;
import Controlador.TernerasBeanRemote;
import entidades.Enfermedad;
import entidades.EnfermedadTernera;
import entidades.EnfermedadTerneraPK;
import entidades.Ternera;
import excepciones.GNCException;

//import org.eclipse.wb.swing.FocusTraversalOnArray;


import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class GNCEditarTerneraEnferma extends JInternalFrame {
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
	//Cargo el controlador
	EnfermedadTerneraBeanRemote controladorTerneraEnfermedad;
	TernerasBeanRemote controladorTernera; 
	EnfermedadBeanRemote controladorEnfermedad;  
	//JTextFields
	public static JTextField txtTernera;
	public static JTextField txtEnfermedad;
	JTextField txtAspectosSanitarios;
	JTextArea aspectSanitarios;
	private JDatePickerImpl datePickerFin;
	//JButtons
	JButton btnAceptar,btnCancelar;
	private JTextField txtFechaInicio;
	private Date dateInicio,dateFin ;
	private long idter, idEnf;
	
	Ternera ternera ;
	Enfermedad enfermedad;
	private static GNCTernerasEnfermas formTernerasEnfermas;

	/**
	 * Create the frame.
	 * @throws NamingException 
	 */
	public GNCEditarTerneraEnferma(long idTernera,long idEnfermedad,String date) throws NamingException {
				
		idter = idTernera;
		idEnf = idEnfermedad;
		
		SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dateInicio = dt1.parse(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		setTitle("Editar Ternera Enferma");
		setIconifiable(true);
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
		txtTernera.setEditable(false);
		txtTernera.setBounds(145, 79, 125, 22);
		
		txtEnfermedad=new JTextField();
		txtEnfermedad.setEditable(false);
		txtEnfermedad.setBounds(145, 48, 125, 22);
		
				
		//seteando los JButtons
		btnAceptar=new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formatoFecha();	
				accionGuardar();
			}
		});
		btnAceptar.setBounds(334, 211, 90, 20);
		
		btnCancelar=new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				accionCancelar();
			}
		});
		btnCancelar.setBounds(334, 249, 90, 20);
		
		this.datePickerFin = this.creareDatePickerFin();
		datePickerFin.setBounds(145, 141, 125, 26);
		
		
		//seteando JScrollPane
		aspectSanitarios=new JTextArea();
		aspectSanitarios.setWrapStyleWord(true);
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
		getContentPane().add(datePickerFin);
		getContentPane().add(aspectSanitarios);
		getContentPane().add(lblRegistroDeTernera);
		
		txtFechaInicio = new JTextField();
		txtFechaInicio.setEditable(false);
		txtFechaInicio.setBounds(145, 111, 125, 22);
		getContentPane().add(txtFechaInicio);

		cargarDatosDeLaBD();
	
	}

	//Genera el JDatePicker para elegir la fecha fin
	private JDatePickerImpl creareDatePickerFin() throws NamingException {
		controladorTernera = (TernerasBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/TernerasBean!Controlador.TernerasBeanRemote");
		ternera = controladorTernera.obtenerTerneraPorId(idter);
		//Se carga el controlador remoto de enfernedad
		controladorEnfermedad = (EnfermedadBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/EnfermedadBean!Controlador.EnfermedadBeanRemote");
		
		enfermedad= controladorEnfermedad.obtenerEnfermedadPorId(idEnf);
		
		EnfermedadTerneraPK pk = new EnfermedadTerneraPK();
		pk.setIdEnfermedad(idEnf);
		pk.setIdTernera(idter);
		pk.setFechaDesde(dateInicio);
		EnfermedadTernera ternerita = new EnfermedadTernera (pk,ternera,enfermedad);
		
		//Se carga el controlador de remoto de ternera enferma
		controladorTerneraEnfermedad = (EnfermedadTerneraBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/EnfermedadTerneraBean!Controlador.EnfermedadTerneraBeanRemote");
		EnfermedadTernera enferm = controladorTerneraEnfermedad.obtenerTerneraEnfermaFecha(ternerita);
		UtilDateModel model = new UtilDateModel();
		Date d=enferm.getFechaHasta();
		if(!(enferm.getFechaHasta()==null)){
		model.setValue(d);
		model.setSelected(true);}
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePickerFin = new JDatePickerImpl(datePanel);
		
		return datePickerFin;
	}
	
	
	private void accionCancelar() {
		// si se cancela, se eliminar la ventana
		this.dispose();
	}
	public void cargarDatosDeLaBD() throws NamingException{
		//Obtengo la materia de id el que me pasan por parámetro en el constructor
		//Se carga el controlador remoto de ternera
		controladorTernera = (TernerasBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/TernerasBean!Controlador.TernerasBeanRemote");
		ternera = controladorTernera.obtenerTerneraPorId(idter);
		//Se carga el controlador remoto de enfernedad
		controladorEnfermedad = (EnfermedadBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/EnfermedadBean!Controlador.EnfermedadBeanRemote");
		
		enfermedad= controladorEnfermedad.obtenerEnfermedadPorId(idEnf);
		
		EnfermedadTerneraPK pk = new EnfermedadTerneraPK();
		pk.setIdEnfermedad(idEnf);
		pk.setIdTernera(idter);
		pk.setFechaDesde(dateInicio);
		EnfermedadTernera ternerita = new EnfermedadTernera (pk,ternera,enfermedad);
		
		//Se carga el controlador de remoto de ternera enferma
		controladorTerneraEnfermedad = (EnfermedadTerneraBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/EnfermedadTerneraBean!Controlador.EnfermedadTerneraBeanRemote");
		EnfermedadTernera enferm = controladorTerneraEnfermedad.obtenerTerneraEnfermaFecha(ternerita);

		
		txtEnfermedad.setText(String.valueOf(enferm.getEnfermedad().getIdEnfermedad()));
		txtTernera.setText(String.valueOf(enferm.getTernera().getIdTernera()));
		//Se asigna el formato de la fecha a mostrar en el formulario
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		 
		
		 
		txtFechaInicio.setText(String.valueOf(formato.format(enferm.getId().getFechaDesde())));
		aspectSanitarios.setText(enferm.getObservacion());
		/*UtilDateModel modelo = new UtilDateModel();
		Date d=enferm.getFechaHasta();
		int dia=d.getDay();
		int mes=d.getSeconds();
		int anio=d.getYear();
		modelo.setDate(anio, mes, dia);
		modelo.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(modelo);
		datePickerFin = new JDatePickerImpl(datePanel);*/
		
	}
	//obtiene la fecha del DatePicker y la convierte a Date
	public void  formatoFecha(){
		
		Date fechaFin = (Date) this.datePickerFin.getModel().getValue();
		SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			if(!(fechaFin==null)){

				String dtf = dt1.format(fechaFin);
				dateFin = dt1.parse(dtf);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public boolean validarFecha(){
		
		boolean fechaValida= false;
	
		Date fechaFin = (Date) this.datePickerFin.getModel().getValue();
		Date fecha = new Date();
		if(!(fechaFin==null)){
			if(dateFin.compareTo(fecha)<=0 && dateInicio.compareTo(dateFin)<=0){
				fechaValida= true;
			}
		}else if(fechaFin==null){
			fechaValida=true;
		}
		return fechaValida;
		
	}
	private void accionGuardar(){
		formatoFecha();
		String aspectSan = aspectSanitarios.getText();
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
		
		EnfermedadTerneraPK pk = new EnfermedadTerneraPK();
		pk.setIdEnfermedad(idEnf);
		pk.setIdTernera(idter);
		pk.setFechaDesde(dateInicio);
		EnfermedadTernera terneraEnferma = new EnfermedadTernera (pk,ternera,enfermedad,dateFin,aspectSan);
		
	
		boolean almacenado = false;
		try {
			almacenado = controladorTerneraEnfermedad.editarTerneraEnferma(terneraEnferma);
		} catch (GNCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (almacenado) {
			JOptionPane.showInternalMessageDialog(this, "la  Enfermedad por Ternera se ha sido registrado con éxito.",
					"Enfermedad Editada!", JOptionPane.INFORMATION_MESSAGE);
			
			// cerramos la ventanta
			if(formTernerasEnfermas == null || formTernerasEnfermas.isClosed()){
        		
        		formTernerasEnfermas = new GNCTernerasEnfermas();
        		formTernerasEnfermas.setVisible(true);
        		GNCPrincipal.escritorio.add(formTernerasEnfermas);
        		GNCPrincipal.centrarVentana(formTernerasEnfermas);
            	formTernerasEnfermas.toFront();
        	}
        	else{
        		 JOptionPane.showInternalMessageDialog(GNCPrincipal.escritorio, "¡Formulario ya esta abierto!", "Aviso: Terneras Enfermas", JOptionPane.INFORMATION_MESSAGE);
        	}     
			
			this.dispose();
		}
		else{
			JOptionPane.showInternalMessageDialog(this, "Hubo un error al almacenar. Intente nuevamente más tarde",
					"Error al editar!", JOptionPane.ERROR_MESSAGE);
		}
	}
}