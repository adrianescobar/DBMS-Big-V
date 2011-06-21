package eventos;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import basededato.ConexionBD;
import graficos.GraphicInterface;

public class ClaseEvento extends ConexionBD{
	
	GraphicInterface g; 
	public Statement sentencia;
	

	
	public ResultSet getQueries(String query){
		
		ResultSet queryResult = null;
		
		try{
			sentencia = conexion.createStatement();
			queryResult = sentencia.executeQuery(query);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error al ejecutar el Query.\n"+"Revise la sintaxis.","Error",JOptionPane.ERROR_MESSAGE);
			
		}
		return queryResult;
	}
	public int getCountAfected(String query){
		int afectado = 0;
		try{
			sentencia = conexion.createStatement();
			afectado = sentencia.executeUpdate(query);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error al ejecutar el Query.\n"+"Revise la sintaxis.","Error",JOptionPane.ERROR_MESSAGE);
		}
		return afectado;
	}
	
}
