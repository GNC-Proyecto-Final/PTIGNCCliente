package Interfaces;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import Controlador.EnfermedadTerneraBeanRemote;
import entidades.Enfermedad;
import entidades.EnfermedadTernera;
import enumerados.NombreEnfermedad;
import excepciones.TerneraEnfermaException;
import excepciones.TerneraException;

import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GNCTernerasEnfermas extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private JButton btnLimpiar;
	private static JTable table;
	private EnfermedadTerneraBeanRemote controladorTerneraEnfermedad;
	public static JTextField txtTernera;
	public static  JTextField textCaravana;
	private static GNCBuscarTerneraEditar formBuscarTerneraEditar;
	static JLabel lblMensaje ;
	private static GNCEditarTerneraEnferma editarTerneraEnferma;
	/**
	 * Create the frame.
	 * @throws TerneraEnfermaException 
	 */
	public GNCTernerasEnfermas() throws TerneraEnfermaException {
		setBounds(100, 100, 519, 300);
		setTitle("Enfermedades");
		
		setIconifiable(true);
		//setMaximizable(true);
		
		setClosable(true);
		
		table = new JTable();
		
		table.setBackground(Color.WHITE);
		try {
			conectar();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cargarTabla();
		//Se debe colocar la tabla dentro de un jscrollpane ya que sino no se ven los nombres de las columnas
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 106, 483, 154);
		
		
		JLabel lblListadoDeEnfermedades = new JLabel("Listado de Terneras Enfermas");
		lblListadoDeEnfermedades.setBounds(20, 11, 396, 14);
		lblListadoDeEnfermedades.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDeEnfermedades.setFont(new Font("Calibri", Font.BOLD, 16));
		
		JLabel lblEnfermedad = new JLabel("Id Ternera");
		lblEnfermedad.setBounds(10, 50, 67, 14);
		
		JLabel lblNroCaravana = new JLabel("Id Caravana");
		lblNroCaravana.setBounds(172, 50, 81, 14);
		getContentPane().setLayout(null);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(412, 72, 81, 23);
		getContentPane().setLayout(null);
		getContentPane().add(scrollPane);
		getContentPane().add(lblEnfermedad);
		getContentPane().add(lblNroCaravana);
		getContentPane().add(btnLimpiar);
		getContentPane().add(lblListadoDeEnfermedades);
		
		txtTernera = new JTextField();
		txtTernera.setEditable(false);
		txtTernera.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTernera.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrarTernera(txtTernera.getText()) ;
			}
		});
		txtTernera.setBounds(87, 47, 75, 20);
		getContentPane().add(txtTernera);
		txtTernera.setColumns(10);
		
		textCaravana = new JTextField();
		textCaravana.setEditable(false);
		textCaravana.setHorizontalAlignment(SwingConstants.RIGHT);
		textCaravana.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				filtrarCaravana(textCaravana.getText()) ;
			}
		});
		textCaravana.setColumns(10);
		textCaravana.setBounds(263, 47, 96, 20);
		getContentPane().add(textCaravana);
		
		JButton btnBuscarTernera = new JButton("Buscar Ternera");
		btnBuscarTernera.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnBuscarTernera.setHorizontalAlignment(SwingConstants.LEADING);
		btnBuscarTernera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(formBuscarTerneraEditar == null || formBuscarTerneraEditar.isClosed()){
					try {
						formBuscarTerneraEditar = new GNCBuscarTerneraEditar();
					} catch (TerneraException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					formBuscarTerneraEditar.setVisible(true);
		    		GNCPrincipal.escritorio.add(formBuscarTerneraEditar);
		    		GNCPrincipal.centrarVentana(formBuscarTerneraEditar);
		    		formBuscarTerneraEditar.toFront();
		    	}
		    	else{
		    		 JOptionPane.showInternalMessageDialog(GNCPrincipal.escritorio, "�Formulario ya esta abierto!", "Aviso: Buscar Enfermedades", JOptionPane.INFORMATION_MESSAGE);
		    	}
			}
		});
		btnBuscarTernera.setBounds(390, 41, 103, 23);
		getContentPane().add(btnBuscarTernera);
		
		lblMensaje = new JLabel("");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMensaje.setBounds(20, 75, 353, 23);
		getContentPane().add(lblMensaje);

	
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//JTable table =(JTable) arg0.getSource();
		        Point p = arg0.getPoint();
		        int row = table.rowAtPoint(p);
		        long idTernera = Long.parseLong( table.getModel().getValueAt(row, 0).toString() );
		        long idEnfermedad = Long.parseLong( table.getModel().getValueAt(row, 2).toString() );
		        String date =  table.getModel().getValueAt(row, 4).toString() ;
		        if (arg0.getClickCount() == 2) {
		        	
		        	if(editarTerneraEnferma == null || editarTerneraEnferma.isClosed()){
		        		try {
							editarTerneraEnferma = new GNCEditarTerneraEnferma(idTernera,idEnfermedad,date);
						} catch (NamingException e) {

							e.printStackTrace();
						} catch (TerneraEnfermaException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		editarTerneraEnferma.setVisible(true);
		        		GNCPrincipal.escritorio.add(editarTerneraEnferma);
		        		GNCPrincipal.centrarVentana(editarTerneraEnferma);
		        		editarTerneraEnferma.toFront();
		        		editarTerneraEnferma.setVisible(true);
		            	dispose();
	            	}
	            	else{
	            		 JOptionPane.showInternalMessageDialog(GNCPrincipal.escritorio, "�Formulario ya esta abierto!", "Aviso: Terneras Enfermas", JOptionPane.INFORMATION_MESSAGE);
	            	}        
		        }
			}
		});
		
		btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {       	
            	accionLimpiarFiltro();
            }
        });
	}
	
	public void recargarPanel() throws TerneraEnfermaException{
		//Cargo tabla de nuevo (para que se tomen los cambios de la edici�n)
		cargarTabla();
		this.revalidate();
		this.repaint();
	}
	
	public void cargarTabla() throws TerneraEnfermaException{
		//Nombre de las columnas de la tabla
        String[] columnas = new String[] { "IdTernera","Nro Caravana" ,"IdEnfermedad","Enfermedad", "Fecha Inicio", "Fecha Fin","Notas"};
            
      //Se obtienen las Enfermedades para llenar la tabla
        List<EnfermedadTernera> ternerasEnfermas= controladorTerneraEnfermedad.obtenerTodasEnfermedadesTerneras();

        /*Los datos de una tabla se pueden ver como una matriz o un doble array de objetos 
         * (ya que los campos son o numero o caraceres se especifica que el tipo de datos es un objeto gen�rico)*/
        Object[][] datosTabla = new Object[ternerasEnfermas.size()][7];
        int fila = 0;
        
        //Formato para convertir fechas
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
          
        for(EnfermedadTernera t : ternerasEnfermas){
        	datosTabla[fila][0] = t.getTernera().getIdTernera();
        	datosTabla[fila][1] = t.getTernera().getNroCaravana();
        	datosTabla[fila][2] = t.getEnfermedad().getIdEnfermedad();
        	datosTabla[fila][3] = t.getEnfermedad().getNombre().getNombre();
        	datosTabla[fila][4] = formato.format(t.getId().getFechaDesde());
        	if(t.getFechaHasta()==null){
        		datosTabla[fila][5] = t.getFechaHasta();
        	}else{
        		datosTabla[fila][5] = formato.format(t.getFechaHasta());
        	}
        	
        	datosTabla[fila][6] = t.getObservacion();
			fila++;
        }
        
		//Se crea un modelo para setearle a la tabla, de esta forma se indica los datos y las columnas
		DefaultTableModel model = new DefaultTableModel(datosTabla, columnas) {
			/*
			 * Este codigo indica que las celdas no son editables y que son todas
			 * del tipos String
			 */
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		
		table.setModel(model);

	}
	public void conectar() throws NamingException{
		controladorTerneraEnfermedad = (EnfermedadTerneraBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/EnfermedadTerneraBean!Controlador.EnfermedadTerneraBeanRemote");	
	}
	
	public static void filtrarTernera(String ternera) {
		
		/* Para Filtrar la ternera por numero de ternera*/

			List<RowFilter<Object,Object>> filters = new ArrayList<>(2);
			filters.add(RowFilter.regexFilter(ternera, 0));
			
			TableRowSorter<TableModel> filtro = new TableRowSorter<>(table.getModel());
			filtro.setRowFilter(RowFilter.andFilter(filters));
			table.setRowSorter(filtro);
			//jTable1.getValueAt(fila,columna)==null
			if(table.getRowCount() == 0){
				lblMensaje.setText("La ternera ingresada no tiene registros de enfermedad!");		
			}
	}
	
	
	public void filtrarCaravana(String caravana) {
		
		/* Para Filtrar la ternera por numero de caravana*/

			List<RowFilter<Object,Object>> filters = new ArrayList<>(2);
			filters.add(RowFilter.regexFilter(caravana, 1));
			
			TableRowSorter<TableModel> filtro = new TableRowSorter<>(this.table.getModel());
			filtro.setRowFilter(RowFilter.andFilter(filters));
			this.table.setRowSorter(filtro);
	}

	private void accionLimpiarFiltro() {	
		this.table.setRowSorter(null);
		txtTernera.setText("");
		textCaravana.setText("");
		lblMensaje.setText("");
	}
}
