package Interfaces;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Controlador.UsuariosBeanRemote;
import excepciones.UsuarioException;

public class GNCLogin extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5824971867614860481L;
	private JTextField txtUser;
	private JPasswordField txtPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GNCLogin frame = new GNCLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GNCLogin() {
		setTitle("Login");
		setIconifiable(true);
		//setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 446, 166);
		getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(32, 49, 53, 14);
		getContentPane().add(lblUsuario);
		
		
		JLabel lblContrasenia = new JLabel("Contrase\u00F1a:");
		lblContrasenia.setBounds(32, 74, 70, 14);
		getContentPane().add(lblContrasenia);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(106, 102, 89, 23);
		getContentPane().add(btnIngresar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(236, 102, 89, 23);
		getContentPane().add(btnCancelar);
		
		txtUser = new JTextField();
		txtUser.setBounds(172, 46, 198, 20);
		getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(172, 71, 198, 20);
		getContentPane().add(txtPass);
		
		JLabel lblLoginUsuario = new JLabel("Login Usuario");
		lblLoginUsuario.setFont(new Font("Verdana", Font.BOLD, 13));
		lblLoginUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginUsuario.setBounds(32, 11, 385, 27);
		getContentPane().add(lblLoginUsuario);

		
		
		btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	
            	try {
					accionIngresar();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UsuarioException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

			
        });
		btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {   
            	
            	accionCancelar();
            }

			
        });
	}
	private void accionCancelar() {
		this.dispose();
				
	}
	private void accionIngresar() throws NamingException, UsuarioException {
		UsuariosBeanRemote controladorUsuario= (UsuariosBeanRemote)
				InitialContext.doLookup("PTIGNCJPA/UsuariosBean!Controlador.UsuariosBeanRemote");
		String user =  txtUser.getText();
		String password = txtPass.getText();
		if (user.equals("") || password.equals("") ) {
			JOptionPane.showInternalMessageDialog(this, "Por favor complete todos los campos", "Datos incompletos!",
					JOptionPane.WARNING_MESSAGE);
				return;
		}else{
			boolean existe = controladorUsuario.validarUsuario(user, password);
			if (existe) {
				
				int usuario = 1; 
				GNCPrincipal.setUsuario(usuario);
				GNCPrincipal.recargar();
				this.dispose();
			}else{
				JOptionPane.showInternalMessageDialog(this, "El Usuario o Contrase�a no validos",
						"Error Usuario!", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		
	}
}
