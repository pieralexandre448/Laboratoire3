import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;



public class Application {
	private static Connection conn = null;

	public static void main(String[] args) {
		try{
			conn = GetConnection("localhost","gestionclient","root","pieralexandre");
			String affichageMenu = "Faites un choix :\n";
			affichageMenu += "a) Lister les clients\n";
			affichageMenu += "b) Insérer un client\n";
			affichageMenu += "c) Modifier un client\n";
			affichageMenu += "d) supprimer un client\n";
			affichageMenu += "e) sortir du programme\n";
			char choix;
			do{
				choix = JOptionPane.showInputDialog(affichageMenu).charAt(0);
				switch(choix){
				case 'a' : listerClients();
						   break;
				case 'b' : insererClient();
						   break;
				case 'c' : modifierClient();
						   break;
				case 'd' : supprimerClient();
						   break;
				case 'e' : JOptionPane.showMessageDialog(null, "Fin du programme");
						   break;
				default : JOptionPane.showMessageDialog(null, "Choix invalide");
				}
			}while(choix != 'e');
			
			
			
		}catch(ClassNotFoundException ex){
			System.out.println(ex.getMessage());
		}catch(SQLException ex){
			System.out.println(ex.getMessage());
		}

	}
	
	public static Connection GetConnection(String serveur,String nomBd,String user,String password) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + serveur + "/" + nomBd + "?user=" + user + "&password=" + password);
		return conn;
	}
	
	public static Statement getStatement() throws SQLException{
		return conn.createStatement();
	}
	
	public static void listerClients() throws SQLException{
		Statement stm = getStatement();
		String requete = "select * from client";
		ResultSet resultat = stm.executeQuery(requete);
		String listeNoms = "";
		
		while(resultat.next()){
			int id = resultat.getInt("id");
			String nom = resultat.getString("nom");
			String prenom = resultat.getString("prenom");
			String telephone = resultat.getString("telephone");
			listeNoms += id + " " + nom + " " + prenom + " " + telephone + "\n";
		}
		
		JOptionPane.showMessageDialog(null, listeNoms);
		
	}
	
	public static void insererClient() throws SQLException{
		Statement stm = getStatement();
		
		//int id = Integer.parseInt(JOptionPane.showInputDialog("Entrer l'ID du client à modifier"));
		
		String nom = JOptionPane.showInputDialog("Entrer le nom du client");
		String prenom = JOptionPane.showInputDialog("Entrer le prenom du client");
		String telephone = JOptionPane.showInputDialog("Entrer le telephone du client");

		String requete = "insert into client values(null, '" + nom + "', '" + prenom + "', '" + telephone + "')";
		stm.executeUpdate(requete);
		stm.close();
		
		JOptionPane.showMessageDialog(null, "Un client vient d'être enregistré.");

	}
	
	public static void modifierClient() throws SQLException{
		Statement stm = getStatement();
		int id = Integer.parseInt(JOptionPane.showInputDialog("Entrer l'ID du client à modifier"));
		String nom = JOptionPane.showInputDialog("Entrer le nouveau nom du client");
		
		String requete = "update client set nom='" + nom + "' where id=" + id;
		stm.executeUpdate(requete);
		stm.close();
		JOptionPane.showMessageDialog(null, "Le client d'id " + id + " vient d'être modifié.");
	}
	
	public static void supprimerClient() throws SQLException{
		Statement stm = getStatement();
		int id = Integer.parseInt(JOptionPane.showInputDialog("Entrer l'ID du client à modifier"));
		String requete = "delete from client where id=" + id;
		stm.executeUpdate(requete);
		stm.close();
		JOptionPane.showMessageDialog(null, "Le client d'id " + id + " vient d'être supprimé.");
		
	}

}
