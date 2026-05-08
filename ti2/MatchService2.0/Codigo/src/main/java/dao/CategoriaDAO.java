package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;

public class CategoriaDAO extends DAO{
	public CategoriaDAO() {
		super();
	}
	public List<Categoria> getCategorias(){
		String SELECT_SQL = "SELECT * FROM categorias";
		List<Categoria> cats = new ArrayList<>();
		try (Statement st = db.createStatement(); ResultSet rs = st.executeQuery(SELECT_SQL)) {

			while (rs.next()) {

				Byte id= rs.getByte("id");
				String nome = rs.getString("nome");

				Categoria c = new Categoria();
				c.setId(id);
				c.setNome(nome);
				cats.add(c);
			}

		} catch (SQLException e) {
			System.err.println("Erro na consulta: " + e.getMessage());
		}
		
		return cats;
	}
	
	public List<Integer> buscarIdsPorUsuario(long idPrestador) {
	    String SELECT_SQL = "SELECT categorias_id FROM prestadores_categorias WHERE prestadores_id = ?";
	    List<Integer> cats = new ArrayList<>();
	    
	    try (PreparedStatement pstmt = db.prepareStatement(SELECT_SQL)) {
	        pstmt.setLong(1, idPrestador);
	        
	        try (ResultSet rs = pstmt.executeQuery()) { 
	            while(rs.next()) {
	                cats.add(rs.getInt("categorias_id"));
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro na consulta de categorias: " + e.getMessage());
	    }
	    return cats;
	}
}
