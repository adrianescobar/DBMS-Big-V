package basededato;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.*;


public class ConexionBD extends JFrame implements ActionListener{
	
	protected static Connection conexion = null;
	private JTextField userField = null,passField = null;
	private JLabel userLabel = null,passLabel = null;
	private JButton bAceptar = null,bCancelar = null;
	

	String driver = "com.mysql.jdbc.Driver";
	String baseDeDato = "jdbc:mysql://localhost:3306/";
	String usuario,contrasena; 
	public ConexionBD(){
		
	}
	public void getConexion(String usuario,String contrasena){
		
		try{
			 
			 Class.forName(driver);
			 conexion = DriverManager.getConnection(baseDeDato,this.usuario,this.contrasena);
			 JOptionPane.showMessageDialog(null, "Conexion Exitosa!!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			 dispose();
		 }
		 catch(Exception e){
			 JOptionPane.showMessageDialog(null,"Hubo un problema con la conexion.\n"+"Revice su Usuario y Contrasena.","Error",JOptionPane.ERROR_MESSAGE);
		 }
	}
	public void closeConexion(){
		try{
			conexion.close();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"Hubo problemas al cerrar la conexion.\n"+"Vuelva a intentarlo.","Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	public void ventanaLogin(){
		Container contenedor = getContentPane();
		contenedor.setLayout(new FlowLayout());
		agregarLookAndFeel();
		// Creacion etiqueta y el cuadro de texto del usuario....
		userField = new JTextField(15);
		userLabel = new JLabel("Usuario: ");
		userField.setToolTipText("Escriba el usuario de su base de datos");
		add(Box.createVerticalStrut(50));
		add(userLabel);
		add(userField);
		
		// Creacion de etiqueta y el cuadro de texto de usuario....
		passField = new JPasswordField(15);
		passLabel = new JLabel("Password: ");
		passField.setToolTipText("Escriba la contrasena de su base de datos");
		add(passLabel);
		add(passField);
		
		// crear y agregar los botones....
		bAceptar = new JButton("Aceptar");
		// establecemos el boton aceptar por defecto....
		getRootPane().setDefaultButton(bAceptar);
		// Agregamos el action listener del boton aceptar
		bAceptar.addActionListener(this);
		bCancelar = new JButton("Cancelar");
		bCancelar.addActionListener(this);
		add(bAceptar);
		add(bCancelar);
		setBounds(450,330,300,189);
		
		setVisible(true);
	}
	public void actionPerformed(ActionEvent evento){
		 
		if(evento.getSource() == bAceptar){
			if(userField.getText().length() > 0 && passField.getText().length() > 0){
				usuario = userField.getText();
				contrasena = passField.getText();
				getConexion(usuario,contrasena);
				
			}
		
			else{
				JOptionPane.showMessageDialog(null, "Escriba su usuario y password.\n "+"No puede dejar vacio uno de estos campos.");
				userField.setText("");
				passField.setText("");
			}
		}
		else{
			System.exit(0);			
		}
			
	}
	private void agregarLookAndFeel(){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch(Exception e){
			System.err.println("No se ha podido usar  el sistema " + "look and feel: " + e);
		}
		
	}
	public String getUsuario(){
		usuario = userField.getText();
		return usuario;
	}
	public String getContrasena(){
		contrasena = passField.getText();
		return contrasena;
	}
	
}	

