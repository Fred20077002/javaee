package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  Módulo de conexãp *. */
	// Parâmetros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true6serverTimeZone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "1234";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// Método de conexão 
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 *  CRUD CREAT *.
	 *
	 * @param contato the contato
	 */
	public void inserirContato(JavaBeans contato) {
		String update = "insert into contatos(nome, fone, email) values (?,?,?)";
		try {
			// abrir conexão PASSOS 7 e 8 do diagrama
			Connection con = conectar();
			// Preparar a query para execução na base de dados
			PreparedStatement pst = con.prepareStatement(update);
			// Substituir os parametros ? pelo conteúdo das variáveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// Executar a query PASSO 9 do diagrama
			pst.executeUpdate();
			// Encerrar a conexão à base de dados
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 *  CRUD READ *.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> listarContatos() {
		// Criar um objeto para aceder a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome";
		try {
			// abrir conexão
			Connection con = conectar();
			// Preparar a query read para execução na base de dados
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// ciclo while executado enquanto houver contatos na tabela
			while (rs.next()) {
				// variáveis de apoio que recebemos dados da Base de Dados dbagenda (tabela
				// contatos)
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// saltar o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			// Encerrar a conexão à base de dados
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// ** CRUD UPDATE **//
	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	// selecionar o contato
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		try {
			// abrir conexão
			Connection con = conectar();
			// Preparar a query read para execução na base de dados
			PreparedStatement pst = con.prepareStatement(read2);
			// substituir ? pelo idcon do contatoselecionado
			pst.setString(1, contato.getIdcon()); // Passo 4 pst.setString--> Passo 5
			ResultSet rs = pst.executeQuery(); // pst.executeQuery() --> Passo 6
			while (rs.next()) { // Passo 7 e 8
				// setar as variáveis JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			// Encerrar a conexão à base de dados
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	// editar o contato
	public void alterarContato(JavaBeans contato) {
		String create = "update contatos set nome = ?, fone = ?, email = ? where idcon = ?";
		try {
			// abrir conexão PASSOS 16 e 17 do diagrama
			Connection con = conectar();
			// Preparar a query para execução na base de dados
			PreparedStatement pst = con.prepareStatement(create);
			// Substituir os parametros ? pelo conteúdo das variáveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			// Executar a query PASSO 18 do diagrama
			pst.executeUpdate();
			// Encerrar a conexão à base de dados
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * CRUD DELETE*.
	 *
	 * @param contato the contato
	 */
	public void deletarcontato(JavaBeans contato) {
		String delete = "delete from contatos where idcon=?";
			try {
				Connection con = conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, contato.getIdcon());
				pst.executeUpdate();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
	}

}
