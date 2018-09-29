package Interfaces;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.naming.NamingException;
import javax.swing.GroupLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GNCPrincipal extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	//private JPanel PanelContenido;
	public static JDesktopPane escritorio = new JDesktopPane()  ;
	private static GNCNuevaEnfermedad nuevaEnfermedad;
	private static GNCLogin formLogin;
	private static GNCNuevoTerneraEnferma formNuevoTerneraEnferma;
	private static GNCTernerasEnfermas formTernerasEnfermas;
	private static GNCEnfermedades formEnfermedades;
	private static GNCInformeDeTernerasEnfermas formInformeTerneras;
	private static int usuario =  0;
	static GNCPrincipal window;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new GNCPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GNCPrincipal() {
		 
		
		iniciarGNCPrincipal();
		IniciarMenuBar(frame);
				
	}
	
	public void iniciarGNCPrincipal(){
				
		frame = new JFrame();
		frame.setTitle("GNC: Gestion de Nacimientos de Crias");
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 600, 450);
		

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(escritorio, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
		);
		escritorio.setBackground(SystemColor.activeCaption);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(escritorio, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
		);
		frame.getContentPane().setLayout(groupLayout);
        pack();

		crearEscritorio(frame);
	}
	
	public void crearEscritorio(JFrame frame){
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		setJMenuBar(menuBar);
		
	}
	public static void recargar(){
		IniciarMenuBar(frame);
	}
	public static void centrarVentana(JInternalFrame InternalFrame){
		int x =(escritorio.getWidth()/2) - InternalFrame.getWidth()/2;
		int y =(escritorio.getHeight()/2) - InternalFrame.getHeight()/2;
		if(InternalFrame.isShowing()){
			InternalFrame.setLocation(x,y);
		}else{
			escritorio.add(InternalFrame);
			InternalFrame.setLocation(x, y);
			InternalFrame.show();
		}
	}
	private static  void IniciarMenuBar(JFrame frame) {
		JMenuBar menuBar = new JMenuBar();
		if(usuario==0){
			crearMenuLogin(menuBar, frame);
		}else{
			crearMenuLogin(menuBar, frame);
			crearMenuTambo(menuBar, frame);
			crearMenuUsuario(menuBar, frame);
			
		}
		frame.setJMenuBar(menuBar);
	}
	private static void crearMenuLogin(JMenuBar menuBar, final JFrame frame) {
		
		JMenu inicio = new JMenu("Iniciar");	
		JMenuItem login= new JMenuItem("Login");
		
		login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	if(formLogin == null || formLogin.isClosed()){
            		formLogin = new GNCLogin();
            		formLogin.setVisible(true);
	            	escritorio.add(formLogin);
	            	centrarVentana(formLogin);
            	}
            	else{
            		 JOptionPane.showInternalMessageDialog(escritorio, "¡Formulario ya Abierto!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            	} 
            }
        });
		
		JMenuItem logout= new JMenuItem("Logout");
		logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {  
            	cerrarSession();
            }

			
        });
		
		if(usuario==0){
			inicio.add(login);
		}else{
			inicio.add(logout);
		}
		
		menuBar.add(inicio);
		
	}
	private static void crearMenuTambo(JMenuBar menuBar, final JFrame frame) {
		
		JMenu tambo = new JMenu("Tambo");
		JMenu subAlimentos = new JMenu("Alimentos");
		JMenu subMedicamentos = new JMenu("Medicamentos");
		JMenu subEnfermedades = new JMenu("Enfermedades");
		JMenu subInformes = new JMenu("Informes");
		JMenuItem nuevaTernero= new JMenuItem("Nueva Ternera");
		
		nuevaTernero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	//new FrameNuevaPersona(frame);
            }
        });
		
		JMenuItem listadoTerneras = new JMenuItem("Terneras");
		listadoTerneras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            //	new FrameListarPersonas(frame);
            }
        });
		JMenuItem nuevoAlimento= new JMenuItem("Nuevo Alimento");
		
		nuevoAlimento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	//new FrameNuevaPersona(frame);
            }
        });		
		JMenuItem alimentos = new JMenuItem("Alimentos");
		alimentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            //	new FrameListarPersonas(frame);
            }
        });
		JMenuItem ConsumoAlimento= new JMenuItem("Consumo Alimento");
		
		ConsumoAlimento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	//new FrameNuevaPersona(frame);
            }
        });
		JMenuItem gananciaPeso= new JMenuItem("Registro Peso");
		
		gananciaPeso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	//new FrameNuevaPersona(frame);
            }
        });
		
		
		
		
		JMenuItem nuevoMedicamento= new JMenuItem("Nuevo Medicamento");
		
		nuevoMedicamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	//new FrameNuevaPersona(frame);
            }
        });		
		JMenuItem medicamentos = new JMenuItem("Medicamentos");
		medicamentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            //	new FrameListarPersonas(frame);
            }
        });
		JMenuItem consumoMedicamento= new JMenuItem("Consumo Medicamentos");
		
		consumoMedicamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	//new FrameNuevaPersona(frame);
            }
        });
		
		
		JMenuItem nuevoEnfermedad= new JMenuItem("Nuevo Enfermedad");
		
		nuevoEnfermedad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	/**Abrir ventana interna**/
            	if(nuevaEnfermedad == null || nuevaEnfermedad.isClosed()){
            		nuevaEnfermedad = new GNCNuevaEnfermedad();
            		nuevaEnfermedad.setVisible(true);
	            	escritorio.add(nuevaEnfermedad);
	            	centrarVentana(nuevaEnfermedad);
	            	nuevaEnfermedad.toFront();
            	}
            	else{
            		 JOptionPane.showInternalMessageDialog(escritorio, "¡Formulario ya esta abierto!", "Aviso: Nueva Enfermedad", JOptionPane.INFORMATION_MESSAGE);
            	}            
            }
        });		
		JMenuItem enfermedades = new JMenuItem("Enfermedades");
		enfermedades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            //	new FrameListarPersonas(frame);
            	/**Abrir ventana interna**/
            	if(formEnfermedades == null || formEnfermedades.isClosed()){
            		try {
						formEnfermedades = new GNCEnfermedades();
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		formEnfermedades.setVisible(true);
	            	escritorio.add(formEnfermedades);
	            	centrarVentana(formEnfermedades);
	            	formEnfermedades.toFront();
            	}
            	else{
            		 JOptionPane.showInternalMessageDialog(escritorio, "¡Formulario ya esta abierto!", "Aviso: Enfermedades", JOptionPane.INFORMATION_MESSAGE);
            	}  
            }
        });
		JMenuItem registroEnfermedad= new JMenuItem("Registro Enfermedad");
		
		registroEnfermedad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	/**Abrir ventana interna**/
            	if(formNuevoTerneraEnferma == null || formNuevoTerneraEnferma.isClosed()){
            		
            		
            		formNuevoTerneraEnferma = new GNCNuevoTerneraEnferma();
            		formNuevoTerneraEnferma.setVisible(true);
	            	escritorio.add(formNuevoTerneraEnferma);
	            	centrarVentana(formNuevoTerneraEnferma);
	            	formNuevoTerneraEnferma.toFront();
	            	
            	}
            	else{
            		 JOptionPane.showInternalMessageDialog(escritorio, "¡Formulario ya esta abierto!", "Aviso: Registro Terneras Enfermas", JOptionPane.INFORMATION_MESSAGE);
            	}     
            }
        });
		JMenuItem ternerasEnfermas= new JMenuItem("Terneras Enfermas");
 

		ternerasEnfermas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	/**Abrir ventana interna**/
            	if(formTernerasEnfermas == null || formTernerasEnfermas.isClosed()){
            		
            		formTernerasEnfermas = new GNCTernerasEnfermas();
            		formTernerasEnfermas.setVisible(true);
	            	escritorio.add(formTernerasEnfermas);
	            	centrarVentana(formTernerasEnfermas);
	            	formTernerasEnfermas.toFront();
            	}
            	else{
            		 JOptionPane.showInternalMessageDialog(escritorio, "¡Formulario ya esta abierto!", "Aviso: Terneras Enfermas", JOptionPane.INFORMATION_MESSAGE);
            	}     
            }
        });
		
		
		JMenuItem informeTerneras= new JMenuItem("Informe de terneras enfermas");
		informeTerneras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	/**Abrir ventana interna**/
            	if(formInformeTerneras == null || formInformeTerneras.isClosed()){
            		
            		formInformeTerneras = new GNCInformeDeTernerasEnfermas();
            		formInformeTerneras.setVisible(true);
	            	escritorio.add(formInformeTerneras);
	            	centrarVentana(formInformeTerneras);
	            	formInformeTerneras.toFront();
            	}
            	else{
            		 JOptionPane.showInternalMessageDialog(escritorio, "¡Formulario ya esta abierto!", "Aviso: Informe Terneras Enfermas", JOptionPane.INFORMATION_MESSAGE);
            	}     
            }
        });
		tambo.add(nuevaTernero);
		tambo.add(listadoTerneras);
		tambo.addSeparator();
		tambo.add(subAlimentos);
		tambo.addSeparator();
		tambo.add(subMedicamentos);
		tambo.addSeparator();
		tambo.add(subEnfermedades);
		tambo.addSeparator();
		tambo.add(subInformes);	
		
		subAlimentos.add(nuevoAlimento);
		subAlimentos.add(alimentos);
		subAlimentos.add(ConsumoAlimento);
		subAlimentos.add(gananciaPeso);

		subMedicamentos.add(nuevoMedicamento);
		subMedicamentos.add(medicamentos);
		subMedicamentos.add(consumoMedicamento);
		
		subEnfermedades.add(nuevoEnfermedad);
		subEnfermedades.add(enfermedades);
		subEnfermedades.add(registroEnfermedad);
		subEnfermedades.add(ternerasEnfermas);
		subInformes.add(informeTerneras);
		menuBar.add(tambo);
		
	}
	private static void crearMenuUsuario(JMenuBar menuBar, final JFrame frame) {
		
		JMenu usuario = new JMenu("Usuario");
		
		JMenuItem modificar= new JMenuItem("Modificar Usuario");
		
		modificar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {   
	        	//new FrameNuevaPersona(frame);
	        }
	    });
		
		JMenuItem listarUsuario= new JMenuItem("Listar Usuario");
		listarUsuario.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent event) {   
	        //	new FrameListarPersonas(frame);
	        }
	    });
		usuario.add(modificar);
		usuario.add(listarUsuario);
		menuBar.add(usuario);
		
	}

	public static int getUsuario() {
		return usuario;
	}

	public static void setUsuario(int usuario) {
		GNCPrincipal.usuario = usuario;
	}
	private static void cerrarSession() {
		
    	escritorio.removeAll();
    	escritorio.repaint();
    	setUsuario(0); 
    	
    	frame.repaint();	
		recargar();
	}
	
}