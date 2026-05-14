package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import model.Prestador;
import model.Categoria;
import model.SolicitacaoServico;

public class PesquisaDAO extends DAO {

	public PesquisaDAO() {
		super();
	}
	
	public List<Prestador> filtrarPrestadores(int idCategoria, String cidade, String genero) {
	    List<Prestador> lista = new java.util.ArrayList<>();
	    
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT p.id, p.descricao, p.usuarios_id, u.nome AS nome_usuario, c.nome AS nome_categoria ");
	    sql.append("FROM prestadores p ");
	    sql.append("JOIN usuarios u ON p.usuarios_id = u.id ");
	    sql.append("LEFT JOIN prestadores_categorias pc ON p.id = pc.prestadores_id ");
	    sql.append("LEFT JOIN categorias c ON pc.categorias_id = c.id ");
	    sql.append("WHERE 1=1 ");

	    if (idCategoria > 0) sql.append("AND pc.categorias_id = ? ");
	    if (cidade != null && !cidade.isEmpty()) sql.append("AND u.cidade ILIKE ? ");
	    if (genero != null && !genero.isEmpty()) sql.append("AND u.genero = ? ");

	    try (PreparedStatement pstmt = db.prepareStatement(sql.toString())) {
	        int i = 1;
	        if (idCategoria > 0) pstmt.setInt(i++, idCategoria);
	        if (cidade != null && !cidade.isEmpty()) pstmt.setString(i++, "%" + cidade + "%");
	        if (genero != null && !genero.isEmpty()) pstmt.setString(i++, genero);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Prestador p = new Prestador();
	                p.setId(rs.getLong("id"));
	                p.setDescricao(rs.getString("descricao"));
	                p.setUsuariosId(rs.getLong("usuarios_id"));
	                p.setNomeUsuario(rs.getString("nome_usuario"));
	                p.setNomeCategoria(rs.getString("nome_categoria"));
	                
	                lista.add(p);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao filtrar prestadores: " + e.getMessage());
	    }
	    return lista;
	}
	
	public List<model.Categoria> listarCategorias() {
	    List<model.Categoria> lista = new java.util.ArrayList<>();
	    String sql = "SELECT * FROM categorias ORDER BY nome";
	    
	    try (PreparedStatement pstmt = db.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        while (rs.next()) {
	            model.Categoria c = new model.Categoria();
	            c.setId(rs.getInt("id"));
	            c.setNome(rs.getString("nome"));
	            lista.add(c);
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao listar categorias: " + e.getMessage());
	    }
	    return lista;
	}
	
}