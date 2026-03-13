package exercicio02;

import java.sql.*;

public class DAO {
	private Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "exercicio02";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "exercicio02";
		String password = "exercicio02";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirPessoa(Pessoa pessoa) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			//st.executeUpdate("INSERT INTO usuario (codigo, login)" + "VALUES ("+usuario.getCodigo()+ ", '" + usuario.getLogin() + "');");
			st.executeUpdate("INSERT INTO pessoa (nome) VALUES ('" + pessoa.getNome() + "')");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarPessoa(String nomeAntigo, Pessoa pessoaAtualizada) {
		boolean status;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE pessoa SET nome = '" + pessoaAtualizada.getNome() + "' WHERE nome = '" + nomeAntigo + "'";
			
			int linhasAlteradas = st.executeUpdate(sql);
			
			if(linhasAlteradas == 0) {
				
				status = false;
				
			}
			else {
				
				status = true;
				
			}
			
			st.close();
			
		} catch (SQLException u) { 
			
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean excluirPessoa(String nome) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM pessoa WHERE nome = '" + nome + "'");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Pessoa[] getPessoas() {
		Pessoa[] pessoas = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM pessoa");		
	         if(rs.next()){
	             rs.last();
	             pessoas = new Pessoa[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	            	pessoas[i] = new Pessoa(rs.getString("nome"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pessoas;
	}
	
}