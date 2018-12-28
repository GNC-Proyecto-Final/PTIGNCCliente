package Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Controlador.EnfermedadTerneraBeanRemote;
import entidades.EnfermedadTernera;
import excepciones.TerneraEnfermaException;

public class GNCInformeDeTernerasEnfermas extends JInternalFrame {

	 
		private static final long serialVersionUID = 1L;
		private static JTable table;
		private EnfermedadTerneraBeanRemote controladorTerneraEnfermedad;
		private static GNCBuscarTerneraEditar formBuscarTerneraEditar;
		private static GNCEditarTerneraEnferma editarTerneraEnferma;
		
		public GNCInformeDeTernerasEnfermas() throws TerneraEnfermaException {
			setBounds(100, 100, 514, 300);
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
			JScrollPane scrollPane = new JScrollPane(table,
				    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(10, 36, 483, 224);
			
			JLabel lblListadoDeEnfermedades = new JLabel("Informe de Terneras Enfermas");
			lblListadoDeEnfermedades.setBounds(10, 11, 483, 14);
			lblListadoDeEnfermedades.setHorizontalAlignment(SwingConstants.CENTER);
			lblListadoDeEnfermedades.setFont(new Font("Calibri", Font.BOLD, 16));
			getContentPane().setLayout(null);
			getContentPane().setLayout(null);
			getContentPane().add(scrollPane);
			getContentPane().add(lblListadoDeEnfermedades);

		}
		
		public void recargarPanel() throws TerneraEnfermaException{
			//Cargo tabla de nuevo (para que se tomen los cambios de la edici�n)
			cargarTabla();
			this.revalidate();
			this.repaint();
		}
		
		
		public void cargarTabla() throws TerneraEnfermaException{
			//Nombre de las columnas de la tabla
	        String[] columnas = new String[] { "IdTer","Enfermedad","FechaNac", "FechaIniEnf", "FechaFinEnf","Enfermo a d�as"};
	            
	      //Se obtienen las Enfermedades para llenar la tabla
	        List<EnfermedadTernera> ternerasEnfermas= controladorTerneraEnfermedad.obtenerInformeTodasEnfermedadesTerneras();
	      
	       //Collections.sort(ternerasEnfermas,EnfermedadTernera.comparar);

	        /*Los datos de una tabla se pueden ver como una matriz o un doble array de objetos 
	         * (ya que los campos son o numero o caraceres se especifica que el tipo de datos es un objeto gen�rico)*/
	        Object[][] datosTabla = new Object[ternerasEnfermas.size()][6];
	        int fila = 0;
	        
	        //Formato para convertir fechas
	        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	          
	        for(EnfermedadTernera t : ternerasEnfermas){
	        	datosTabla[fila][0] = t.getTernera().getIdTernera();
	        	datosTabla[fila][1] = t.getEnfermedad().getNombre().getNombre();
	        	datosTabla[fila][2] = formato.format(t.getTernera().getFechaNacimiento());
	        	datosTabla[fila][3] = formato.format(t.getId().getFechaDesde());
	        	if(t.getFechaHasta()==null){
	        		datosTabla[fila][4] = t.getFechaHasta();
	        	}else{
	        		datosTabla[fila][4] = formato.format(t.getFechaHasta());
	        	}
	        	datosTabla[fila][5] = t.getDiasVida();
	        	
				fila++;
	        }
	        
			//Se crea un modelo para setearle a la tabla, de esta forma se indica los datos y las columnas
			DefaultTableModel model = new DefaultTableModel(datosTabla, columnas) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

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
					return int.class;
				}
			
			};
			
			table.setModel(model);
			

		}
		public void conectar() throws NamingException{
			controladorTerneraEnfermedad = (EnfermedadTerneraBeanRemote)
					InitialContext.doLookup("PTIGNCJPA/EnfermedadTerneraBean!Controlador.EnfermedadTerneraBeanRemote");	
		}

}
