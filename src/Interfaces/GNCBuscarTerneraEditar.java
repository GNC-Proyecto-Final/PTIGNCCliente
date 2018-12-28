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
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import Controlador.TernerasBeanRemote;
import entidades.Ternera;
import excepciones.TerneraException;

import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GNCBuscarTerneraEditar extends JInternalFrame  {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JButton btnLimpiar;
	private JTable table;
	private TernerasBeanRemote controladorTernera;
	private JTextField txtTernera;
	private JTextField txtCaravana;
	private static GNCTernerasEnfermas  formTerneraEnferma;
	/**
	 * Create the frame.
	 * @throws TerneraException 
	 */
	public GNCBuscarTerneraEditar() throws TerneraException {
		setBounds(100, 100, 450, 300);
		setTitle("Terneras");
		
		setIconifiable(true);
		
		setClosable(true);
		
		table = new JTable();
		
		table.setBackground(Color.WHITE);
		try {
			conectar();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		cargarTabla();
		//Se debe colocar la tabla dentro de un jscrollpane ya que sino no se ven los nombres de las columnas
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 118, 414, 141);
		
		
		JLabel lblListadoDeEnfermedades = new JLabel("Listado de Terneras");
		lblListadoDeEnfermedades.setBounds(20, 11, 396, 14);
		lblListadoDeEnfermedades.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDeEnfermedades.setFont(new Font("Calibri", Font.BOLD, 16));
		
		JLabel lblTernera = new JLabel("Id Ternera");
		lblTernera.setBounds(27, 48, 51, 14);
		
		JLabel lblCaravana = new JLabel("Nro Caravana");
		lblCaravana.setBounds(20, 85, 67, 14);
		getContentPane().setLayout(null);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(298, 84, 90, 23);
		
		txtTernera = new JTextField();
		txtTernera.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				filtrarTernera(txtTernera.getText()) ;
			}
		});
		txtTernera.setBounds(115, 45, 140, 20);
		txtTernera.setColumns(10);
		
		txtCaravana = new JTextField();
		txtCaravana.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				filtrarCaravana(txtCaravana.getText()) ;
			}
		});
		txtCaravana.setBounds(115, 82, 140, 20);
		txtCaravana.setColumns(10);
		getContentPane().setLayout(null);
		getContentPane().add(lblTernera);
		getContentPane().add(lblCaravana);
		getContentPane().add(txtCaravana);
		getContentPane().add(txtTernera);
		getContentPane().add(btnLimpiar);
		getContentPane().add(lblListadoDeEnfermedades);
		getContentPane().add(scrollPane);

		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

		        Point p = arg0.getPoint();
		        int row = table.rowAtPoint(p);
		        long idTernera= Long.parseLong( table.getModel().getValueAt(row, 0).toString() );
		        long nroCaravana = Long.parseLong( table.getModel().getValueAt(row, 1).toString() );
		        if (arg0.getClickCount() == 2) {
		        			        	
		        	GNCTernerasEnfermas.txtTernera.setText(String.valueOf(idTernera));
		        	GNCTernerasEnfermas.textCaravana.setText(String.valueOf(nroCaravana));
		        	GNCTernerasEnfermas.filtrarTernera(String.valueOf(idTernera));
		        	dispose();
		           
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
	
	public void recargarPanel() throws TerneraException{
		//Cargo tabla de nuevo (para que se tomen los cambios de la edici�n)
		cargarTabla();
		this.revalidate();
		this.repaint();
	}
	
	
	public void cargarTabla() throws TerneraException{
		//Nombre de las columnas de la tabla
        String[] columnas = new String[] { "Id", "Nro Caravana", "Fecha Alta"};
            
      //Se obtienen las Enfermedades para llenar la tabla
        List<Ternera> terneras= controladorTernera.obtenerTodasTerneras();

        /*Los datos de una tabla se pueden ver como una matriz o un doble array de objetos 
         * (ya que los campos son o numero o caraceres se especifica que el tipo de datos es un objeto gen�rico)*/
        Object[][] datosTabla = new Object[terneras.size()][3];
        int fila = 0;
        for(Ternera t : terneras){
        	datosTabla[fila][0] = t.getIdTernera();
        	datosTabla[fila][1] = t.getNroCaravana();
        	datosTabla[fila][2] = t.getFechaNacimiento();
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
		controladorTernera = (TernerasBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/TernerasBean!Controlador.TernerasBeanRemote");		
	}
	private void accionLimpiarFiltro() {

		this.table.setRowSorter(null);
		this.txtTernera.setText("");
		this.txtCaravana.setText("");
	}
	
	public void filtrarTernera(String ternera) {
		
		/* Para Filtrar la ternera por numero de ternera*/

			List<RowFilter<Object,Object>> filters = new ArrayList<>(2);
			filters.add(RowFilter.regexFilter(ternera, 0));
			
			TableRowSorter<TableModel> filtro = new TableRowSorter<>(this.table.getModel());
			filtro.setRowFilter(RowFilter.andFilter(filters));
			this.table.setRowSorter(filtro);

	}
	public void filtrarCaravana(String caravana) {
		
		/* Para Filtrar la ternera por numero de caravana*/

			List<RowFilter<Object,Object>> filters = new ArrayList<>(2);
			filters.add(RowFilter.regexFilter(caravana, 1));	

			TableRowSorter<TableModel> filtro = new TableRowSorter<>(this.table.getModel());
			filtro.setRowFilter(RowFilter.andFilter(filters));
			this.table.setRowSorter(filtro);
	}
	   
}

