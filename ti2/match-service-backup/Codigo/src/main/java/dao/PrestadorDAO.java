package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import model.Prestador;
import model.Usuario;

public class PrestadorDAO extends DAO {
	public PrestadorDAO() {
		super();
	}

	public void inserirPrestador(Prestador p) {
		String INSERT_SQL = "INSERT INTO prestadores (descricao, usuarios_id) VALUES (?, ?)";

		try (PreparedStatement pstmt = db.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setObject(1, p.getDescricao());
			pstmt.setObject(2, p.getUsuariosId());

			int rowsUpdated = pstmt.executeUpdate();
			System.out.println(rowsUpdated + " linha(s) afetadas");

			if (rowsUpdated > 0) {
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						long idGerado = generatedKeys.getLong(1);
						p.setId(idGerado);

					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro na inserção: " + e.getMessage());
		}
	}

	public Prestador selectPrestador(String coluna, Object valor) {
		List<String> colunasPermitidas = Arrays.asList("id", "descricao", "usuarios_id");

		if (!colunasPermitidas.contains(coluna.toLowerCase())) {
			throw new IllegalArgumentException("Busca por coluna não permitida: " + coluna);
		}

		String SELECT_SQL = "SELECT * FROM prestadores WHERE " + coluna + " = ?";

		try (PreparedStatement pstmt = db.prepareStatement(SELECT_SQL)) {
			if (coluna.equalsIgnoreCase("usuarios_id") && valor instanceof String) {
				try {
					valor = Long.parseLong((String) valor);
				} catch (NumberFormatException e) {
					System.err.println("ID inválido fornecido: " + valor);
					return null;
				}
			}

			pstmt.setObject(1, valor);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Prestador p = new Prestador();
					p.setDescricao(rs.getString("descricao"));
					p.setId(rs.getInt("id"));
					p.setUsuariosId(rs.getLong("usuarios_id"));
					
					return p;
				}
			} 
		}catch (SQLException e) {
			System.err.println("Erro na consulta de usuário por " + coluna + ": " + e.getMessage());
		}
		
		return null;
	}
	
	public void deleteCategoriasPrestador(Long idPrestador) throws SQLException {
		String DELETE_SQL = "DELETE FROM prestadores_categorias WHERE prestadores_id = ?";
		
		try (PreparedStatement pstmt = db.prepareStatement(DELETE_SQL)){
			pstmt.setObject(1, idPrestador);
			
			pstmt.executeUpdate();
		}
	}
	
	
}
