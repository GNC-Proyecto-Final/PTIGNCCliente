package Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Controlador.EnfermedadBeanRemote;
import entidades.Enfermedad;
import enumerados.NombreEnfermedad;
import excepciones.TerneraEnfermaException;



public class GNCBuscarEnfermedad extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static GNCEliminarEnfermedad GNCEditarEnfermedad;
	/**
	 * 
	 */
	private JButton btnBuscar ,btnLimpiar;
	private JTable table;
	private EnfermedadBeanRemote controladorEnfermedad;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbEnfermedad,cmbGravedad;
	private String enfermedadSeleccionado = "Todos";
	private String gradoSeleccionado = "Todos";

	/**
	 * Create the frame.
	 * @throws TerneraEnfermaException 
	 */
	public GNCBuscarEnfermedad() throws TerneraEnfermaException {
		setBounds(100, 100, 450, 300);
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
		scrollPane.setBounds(10, 106, 406, 154);
		
		
		JLabel lblListadoDeEnfermedades = new JLabel("Listado de Enfermedades");
		lblListadoDeEnfermedades.setBounds(20, 11, 396, 14);
		lblListadoDeEnfermedades.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDeEnfermedades.setFont(new Font("Calibri", Font.BOLD, 16));
		
		JLabel lblEnfermedad = new JLabel("Enfermedad");
		lblEnfermedad.setBounds(27, 45, 58, 14);
		
		JLabel lblGradoGravedad = new JLabel("Grado Gravedad");
		lblGradoGravedad.setBounds(20, 76, 79, 14);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(240, 43, 81, 23);
	
		cmbEnfermedad = new JComboBox();
		cmbEnfermedad.setBounds(103, 49, 119, 20);
		
		cmbGravedad = new JComboBox();
		cmbGravedad.setBounds(103, 75, 119, 20);
		getContentPane().setLayout(null);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(240, 72, 81, 23);
		getContentPane().setLayout(null);
		getContentPane().add(scrollPane);
		getContentPane().add(lblEnfermedad);
		getContentPane().add(lblGradoGravedad);
		getContentPane().add(cmbGravedad);
		getContentPane().add(cmbEnfermedad);
		getContentPane().add(btnLimpiar);
		getContentPane().add(btnBuscar);
		getContentPane().add(lblListadoDeEnfermedades);
		cargarComboEnfermedades();
		cargarComboGravedad();
		
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//JTable table =(JTable) arg0.getSource();
		        Point p = arg0.getPoint();
		        int row = table.rowAtPoint(p);
		        long idTernera = Long.parseLong( table.getModel().getValueAt(row, 0).toString() );
		        if (arg0.getClickCount() == 2) {
		            //System.out.println("Doble click"); 
		        	
		        	 GNCNuevoTerneraEnferma.txtEnfermedad.setText(String.valueOf(idTernera));
			        	dispose();
		           
		        }
			}
		});
		
		
		btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	
            	accionFiltrar();
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
        String[] columnas = new String[] { "Id", "Nombre", "Grado Gravedad"};
            
      //Se obtienen las Enfermedades para llenar la tabla
        List<Enfermedad> enfermedades= controladorEnfermedad.obtenerTodasEnfermedades();

        /*Los datos de una tabla se pueden ver como una matriz o un doble array de objetos 
         * (ya que los campos son o numero o caraceres se especifica que el tipo de datos es un objeto gen�rico)*/
        Object[][] datosTabla = new Object[enfermedades.size()][3];
        int fila = 0;
        for(Enfermedad e : enfermedades){
        	datosTabla[fila][0] = e.getIdEnfermedad();
        	datosTabla[fila][1] = e.getNombre().getNombre();
        	datosTabla[fila][2] = e.getGradoGravedad();
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
		controladorEnfermedad = (EnfermedadBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/EnfermedadBean!Controlador.EnfermedadBeanRemote");
		
	}
	@SuppressWarnings("unchecked")
	public void cargarComboEnfermedades(){
		
		
		cmbEnfermedad.addItem("Todos");
        for(NombreEnfermedad e: NombreEnfermedad.values()){
        	cmbEnfermedad.addItem(e.getNombre().toString());
        }
        
	}
	
	@SuppressWarnings("unchecked")
	public void cargarComboGravedad(){
		
		String[] gravedad={"1","2","3"};
		cmbGravedad.addItem("Todos");
        for(String e: gravedad){
        	cmbGravedad.addItem(e);
        }
        
	}

	
	public void filtrar() {
		
		/* Para filtrar, debemos considerar el campo de texto y ademas el valor seleccionado en el tipo. 
		 * Si el tipo es todos, debemos filtrar solo por e texto, 
		 * si el tipo es distinto de todos debemos filtar por tipo y por el texto.
		 */

		if (!this.enfermedadSeleccionado.equals("Todos")) {

			List<RowFilter<Object,Object>> filters = new ArrayList<>(2);
			filters.add(RowFilter.regexFilter(this.enfermedadSeleccionado, 1));
			filters.add(RowFilter.regexFilter(this.gradoSeleccionado, 2));

			TableRowSorter<TableModel> filtro = new TableRowSorter<>(this.table.getModel());
			filtro.setRowFilter(RowFilter.andFilter(filters));
			this.table.setRowSorter(filtro);

		}
		else{
			
			TableRowSorter<TableModel> filtro = new TableRowSorter<>(this.table.getModel());
			filtro.setRowFilter(RowFilter.regexFilter(this.gradoSeleccionado, 0));
			
			this.table.setRowSorter(filtro);
		}
	}

	public void itemSeleccionado() {
		
		/* Cuando cambia el valor, almacenamos el valor actual, para poder acceder desde el filtro */
		
			this.enfermedadSeleccionado =(String) this.cmbEnfermedad.getSelectedItem();
			this.filtrar();
		
			this.gradoSeleccionado = (String)this.cmbGravedad.getSelectedItem();
			this.filtrar();


	}

	private void accionFiltrar() {
		itemSeleccionado();
		
		if (!this.enfermedadSeleccionado.equals("Todos")&& !this.gradoSeleccionado.equals("Todos")) {

			List<RowFilter<Object,Object>> filters = new ArrayList<>(2);
			filters.add(RowFilter.regexFilter(this.enfermedadSeleccionado, 1));
			filters.add(RowFilter.regexFilter(this.gradoSeleccionado, 2));

			TableRowSorter<TableModel> filtro = new TableRowSorter<>(this.table.getModel());
			filtro.setRowFilter(RowFilter.andFilter(filters));
			this.table.setRowSorter(filtro);

		}
		else if (this.enfermedadSeleccionado.equals("Todos")&& !this.gradoSeleccionado.equals("Todos")) {

			List<RowFilter<Object,Object>> filters = new ArrayList<>(2);
			filters.add(RowFilter.regexFilter(this.gradoSeleccionado, 2));

			TableRowSorter<TableModel> filtro = new TableRowSorter<>(this.table.getModel());

			this.table.setRowSorter(filtro);

		}
		else if (!this.enfermedadSeleccionado.equals("Todos")&& this.gradoSeleccionado.equals("Todos")) {

			List<RowFilter<Object,Object>> filters = new ArrayList<>(2);
			filters.add(RowFilter.regexFilter(this.enfermedadSeleccionado, 1));

			TableRowSorter<TableModel> filtro = new TableRowSorter<>(this.table.getModel());
			filtro.setRowFilter(RowFilter.andFilter(filters));
			this.table.setRowSorter(filtro);

		}
		else{
			
			TableRowSorter<TableModel> filtro = new TableRowSorter<>(this.table.getModel());	
			this.table.setRowSorter(filtro);
		}
	}
	private void accionLimpiarFiltro() {

		this.table.setRowSorter(null);
		this.cmbEnfermedad.setSelectedItem("Todos");
		this.cmbGravedad.setSelectedItem("Todos");
	}
	
}
