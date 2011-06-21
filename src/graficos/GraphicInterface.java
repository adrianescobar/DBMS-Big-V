package graficos;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import eventos.ClaseEvento;
import basededato.ConexionBD;

public class GraphicInterface extends JFrame implements ActionListener{
	
	public JButton botonEjecutar = null;
	
	private JScrollPane scroll1 = null, scroll2 = null;
	
	private JToolBar barraTool = null;
	public JTextArea areaQuery = null,areaMensaje = null;
	public ConexionBD conexion = new ConexionBD();
	private ClaseEvento event = new ClaseEvento();
	
	DefaultTableModel modelo = null;
	public JTable tablaR = null;
	public JScrollPane scroll3 = null;
	
	
	public GraphicInterface(){
		super("DBMS Big V");
		
		menu();
		conexion.ventanaLogin();
	}
	
	public void menu(){
		
		// Creacion  de la barra toolbar
		barraTool = new JToolBar();
		add(barraTool,BorderLayout.NORTH);
		getContentPane();
		
		// Creacion de los botones del toolbar....
		
	
		botonEjecutar = new JButton("Ejecutar");
		botonEjecutar.setToolTipText("Ejecuta Queries");
		botonEjecutar.addActionListener(this);
		barraTool.add(botonEjecutar);
		
		
		// Agragamos a nuestro frame  el objeto separador.
		setBounds(100,34,1100,720);
		separadores();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int opcion = JOptionPane.showConfirmDialog(null, "Esta seguro que desea salir de la aplicacion?");
				if(opcion==0){
					conexion.closeConexion();
					System.exit(0);
				}
			}
		});
		setVisible(true);
	}
	//area de desarrollo de queries
	
	public JScrollPane areaQ1(){
		areaQuery = new JTextArea();
		scroll1 = new JScrollPane(areaQuery);
		//separador = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		return scroll1;
	}
	public JScrollPane areaQ2(){
		areaMensaje = new JTextArea();
		areaMensaje.setEditable(false);
		scroll2 = new JScrollPane(areaMensaje);
		return scroll2;
	}
	public DefaultTableModel modeloTabla(){
		modelo = new DefaultTableModel();
		return modelo;
	}
	public JTable tabla(){
		tablaR = new JTable(modeloTabla());
		return tablaR;
	}
	public JScrollPane scrollTabla(){
		scroll3 = new JScrollPane();
		scroll3.setViewportView(tabla());
		return scroll3;
	}
	
	public void separadores(){
		JSplitPane separador1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,areaQ1(),areaQ2());
		JSplitPane separador2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,separador1,scrollTabla());
	   
		separador1.setDividerLocation(getHeight()/2+350);
		separador2.setDividerLocation(getHeight()/2-50);
		add(separador2);
	}
	public void actionPerformed(ActionEvent evento){
		if(evento.getSource() == botonEjecutar){
			String query = areaQuery.getText();
			if(query.trim().toLowerCase().startsWith("select")){
				areaMensaje.setText("");
				try {
					
					ResultSet resultado = event.getQueries(query);
					ResultSetMetaData metaData = resultado.getMetaData();
					int columnCount = metaData.getColumnCount();
					String columnNames[] = new String[columnCount];
					for(int ind = 1; ind <= columnCount; ind++){
						columnNames[ind-1] = metaData.getColumnName(ind);
					}
					modelo.setColumnIdentifiers(new String[]{});
					modelo.setColumnIdentifiers(columnNames); 
					String rowData[] = new String[columnCount];
					modelo.setRowCount(0);
					while(resultado.next()){
						for(int ind = 1; ind <= columnCount; ind++){
							rowData[ind-1] = resultado.getString(ind);
						}
						modelo.addRow(rowData);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(query.trim().toLowerCase().startsWith("insert") || query.trim().toLowerCase().startsWith("delete")
					||query.trim().toLowerCase().startsWith("update")||query.trim().toLowerCase().startsWith("create")
					||query.trim().toLowerCase().startsWith("use") ||query.trim().toLowerCase().startsWith("alter")){
				areaMensaje.setText("");
				int resultado = event.getCountAfected(query);
				String mensaje = "(" +resultado+")"+"filas fueron afectadas";
				areaMensaje.setText(mensaje);
			}
			else{
				JOptionPane.showMessageDialog(null,"Error al ejecutar el Query.\n"+"Revise la sintaxis.","Error",JOptionPane.ERROR_MESSAGE);

			}
		}
	}
	
	public static void main(String[]args){
		GraphicInterface gi = new GraphicInterface();
		
	}
}

