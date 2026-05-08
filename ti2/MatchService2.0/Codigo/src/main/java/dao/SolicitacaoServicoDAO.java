package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import model.SolicitacaoServico;

public class SolicitacaoServicoDAO extends DAO {

	public SolicitacaoServicoDAO() {
		super();
	}
	
	public Long insertSolicitacao(SolicitacaoServico ss) throws SQLException {
		String INSERT_SQL = "INSERT INTO solicitacoes_servicos (descricao, rua, bairro, status, tipo_solicitacao, "
				+ "usuarios_id, categorias_id, lat, long, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Long idGerado = (long) 0;
		
		try(PreparedStatement pstmt = db.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)){
			pstmt.setObject(1, ss.getDescricao());
			pstmt.setObject(2, ss.getRua());
			pstmt.setObject(3, ss.getBairro());
			pstmt.setObject(4, ss.getStatus());
			pstmt.setObject(5, ss.getTipoSolicitacao());
			pstmt.setObject(6, ss.getUsuariosId());
			pstmt.setObject(7, ss.getCategoriaId());
			pstmt.setObject(8, ss.getLat());
			pstmt.setObject(9, ss.getLng());
			
			String dataString = ss.getCreatedAt();
			LocalDateTime ldt = LocalDateTime.parse(dataString);
			pstmt.setTimestamp(10, Timestamp.valueOf(ldt));
			
			int rowsUpdated = pstmt.executeUpdate();

			if (rowsUpdated > 0) {
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						idGerado = generatedKeys.getLong(1);
						ss.setId(idGerado);
					
					}
				}
			}
		}
		
		return idGerado;
	}
	
	public void vincularImagens(Long idSolicitacao, List<String> nomes) throws SQLException {
		String INSERT_SQL = "INSERT INTO solicitacoes_imagens (url, solicitacoes_servicos_id) VALUES (? , ?)";

		try (PreparedStatement pstmt = db.prepareStatement(INSERT_SQL)) {
			for (String nome : nomes) {
				pstmt.setObject(1, nome);
				pstmt.setObject(2, idSolicitacao);
				pstmt.addBatch();
			}

			pstmt.executeBatch();
		} 
	}
}
