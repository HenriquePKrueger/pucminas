package service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Part;

import dao.SolicitacaoServicoDAO;
import model.SolicitacaoServico;
import spark.Request;
import spark.Response;

public class SolicitacaoServicoService {
	private SolicitacaoServicoDAO solicitacaoServicoDAO;	
	
	public SolicitacaoServicoService() {
		this.solicitacaoServicoDAO = new SolicitacaoServicoDAO();
	}

	public boolean criarNovaSolicitacao(Request req, Response res) {
		Long userId = (Long) req.session().attribute("user_id");
		int SOLICITACAO_INDIRETA = 1;
		int STATUS_ANDAMENTO = 0;
		try {
			SolicitacaoServico ss = new SolicitacaoServico();

			String latStr = getParamSafe(req, "lat");
			String lngStr = getParamSafe(req, "long");
			String catStr = getParamSafe(req, "categoria");

			if (isBlank(latStr) || isBlank(lngStr) || isBlank(catStr)) {
				return false;
			}

			ss.setDescricao(getParamSafe(req, "descricao"));
			ss.setRua(getParamSafe(req, "rua address-search"));
			ss.setBairro(getParamSafe(req, "bairro"));
			ss.setStatus(STATUS_ANDAMENTO);
			ss.setTipoSolicitacao(SOLICITACAO_INDIRETA);
			ss.setUsuariosId(userId);
			ss.setCep(getParamSafe(req, "cep"));
			ss.setLat(Double.parseDouble(latStr));
			ss.setLng(Double.parseDouble(lngStr));
			ss.setCategoriaId(Integer.parseInt(catStr));
			
			ss.setCreatedAt(LocalDateTime.now().toString());
			
			List<String> nomes = salvarArquivo(req);

			Long idSolicitacao = solicitacaoServicoDAO.insertSolicitacao(ss);
			solicitacaoServicoDAO.vincularImagens(idSolicitacao, nomes);

			return true;

		} catch (SQLException e) {
			System.err.println("Erro na consulta: " + e.getMessage());
		} catch (ServletException e) {
			System.err.println("Servlet: Erro ao salvar arquivo: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Erro ao salvar arquivo: " + e.getMessage());
		}
		return false;
	}

	private List<String> salvarArquivo(Request req) throws IOException, ServletException {
		List<String> nomesArquivos = new ArrayList<>();
		Collection<Part> parts = req.raw().getParts();
		Path diretorio = Paths.get("storage");
		
		for(Part part : parts) {
			if (part.getName().equals("img") && part.getSize() > 0) {
	            
	            String nomeUnico = "foto_" + System.nanoTime() + ".jpg"; 
	            Path caminhoDestino = diretorio.resolve(nomeUnico);

	            try (InputStream is = part.getInputStream()) {
	                Files.copy(is, caminhoDestino, StandardCopyOption.REPLACE_EXISTING);
	                nomesArquivos.add(nomeUnico);
	            }
	        }
			
		}
		
		return nomesArquivos;
	}

	private String getParamSafe(Request req, String param) {
		String value = req.queryParams(param);

		return (value == null) ? "" : value.strip();
	}

	private boolean isBlank(String str) {
		return str == null || str.trim().isEmpty();
	}
}
