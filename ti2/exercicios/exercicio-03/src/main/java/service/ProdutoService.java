package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.ProdutoDAO;
import model.Produto;
import spark.Request;
import spark.Response;

public class ProdutoService {

	private ProdutoDAO produtoDAO = new ProdutoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRICAO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	public ProdutoService() {
		makeForm();
	}

	public void makeForm() {
		makeForm(FORM_INSERT, new Produto(), FORM_ORDERBY_DESCRICAO);
	}

	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Produto(), orderBy);
	}

	public void makeForm(int tipo, Produto produto, int orderBy) {
		String nomeArquivo = "src/main/resources/form.html";
		form = "";
		try {
			Scanner entrada = new Scanner(new File(nomeArquivo));
			while(entrada.hasNext()){
				form += (entrada.nextLine() + "\n");
			}
			entrada.close();
		} catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umProduto = "";
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/produto/";
			String title, buttonLabel, descricaoValue;
			
			if (tipo == FORM_INSERT){
				action += "insert";
				title = "Cadastrar Novo Produto";
				descricaoValue = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + produto.getID();
				title = "Atualizar Produto (ID " + produto.getID() + ")";
				descricaoValue = produto.getDescricao();
				buttonLabel = "Atualizar";
			}

			umProduto += "\t<div class=\"card-header bg-primary text-white\">";
			umProduto += "\t\t<h5 class=\"mb-0\"><i class=\"bi bi-plus-circle\"></i> " + title + "</h5>";
			umProduto += "\t</div>";
			umProduto += "\t<div class=\"card-body\">";
			umProduto += "\t\t<form class=\"row g-3\" action=\"" + action + "\" method=\"POST\">";
			umProduto += "\t\t\t<div class=\"col-md-6\">";
			umProduto += "\t\t\t\t<label class=\"form-label\">Descrição do Produto</label>";
			umProduto += "\t\t\t\t<input type=\"text\" class=\"form-control\" name=\"descricao\" value=\"" + descricaoValue + "\" required>";
			umProduto += "\t\t\t</div>";
			umProduto += "\t\t\t<div class=\"col-md-3\">";
			umProduto += "\t\t\t\t<label class=\"form-label\">Preço</label>";
			umProduto += "\t\t\t\t<input type=\"number\" class=\"form-control\" step=\"0.01\" name=\"preco\" value=\"" + produto.getPreco() + "\" required>";
			umProduto += "\t\t\t</div>";
			umProduto += "\t\t\t<div class=\"col-md-3\">";
			umProduto += "\t\t\t\t<label class=\"form-label\">Quantidade</label>";
			umProduto += "\t\t\t\t<input type=\"number\" class=\"form-control\" name=\"quantidade\" value=\"" + produto.getQuantidade() + "\" required>";
			umProduto += "\t\t\t</div>";
			umProduto += "\t\t\t<div class=\"col-md-6\">";
			umProduto += "\t\t\t\t<label class=\"form-label\">Data de Fabricação</label>";
			umProduto += "\t\t\t\t<input type=\"date\" class=\"form-control\" name=\"dataFabricacao\" value=\"" + produto.getDataFabricacao().toLocalDate().toString() + "\" required>";
			umProduto += "\t\t\t</div>";
			umProduto += "\t\t\t<div class=\"col-md-6\">";
			umProduto += "\t\t\t\t<label class=\"form-label\">Data de Validade</label>";
			umProduto += "\t\t\t\t<input type=\"date\" class=\"form-control\" name=\"dataValidade\" value=\"" + produto.getDataValidade().toString() + "\" required>";
			umProduto += "\t\t\t</div>";
			umProduto += "\t\t\t<div class=\"col-12 text-end mt-4\">";
			umProduto += "\t\t\t\t<button type=\"submit\" class=\"btn btn-success px-4\"><i class=\"bi bi-check-lg\"></i> " + buttonLabel + "</button>";
			umProduto += "\t\t\t</div>";
			umProduto += "\t\t</form>";
			umProduto += "\t</div>";		
		} else if (tipo == FORM_DETAIL) {
			umProduto += "\t<section class=\"card shadow-sm mb-4\">";
			umProduto += "\t\t<div class=\"card-header bg-primary text-white\">";
			umProduto += "\t\t\t<h5 class=\"mb-0\"><i class=\"bi bi-search\"></i> Detalhar Produto (ID " + produto.getID() + ")</h5>";
			umProduto += "\t\t</div>";
			umProduto += "\t\t<div class=\"card-body\">";
			umProduto += "\t\t\t<div class=\"row g-3\">";
			umProduto += "\t\t\t\t<div class=\"col-md-6\"><p><strong>Descrição:</strong> " + produto.getDescricao() + "</p></div>";
			umProduto += "\t\t\t\t<div class=\"col-md-3\"><p><strong>Preço:</strong> BRL " + produto.getPreco() + "</p></div>";
			umProduto += "\t\t\t\t<div class=\"col-md-3\"><p><strong>Quantidade:</strong> " + produto.getQuantidade() + "</p></div>";
			umProduto += "\t\t\t\t<div class=\"col-md-6\"><p><strong>Fabricação:</strong> " + produto.getDataFabricacao().toString() + "</p></div>";
			umProduto += "\t\t\t\t<div class=\"col-md-6\"><p><strong>Validade:</strong> " + produto.getDataValidade().toString() + "</p></div>";
			umProduto += "\t\t\t\t<div class=\"col-12 text-end\"><a href=\"/produto/list/1\" class=\"btn btn-secondary btn-sm\">Voltar</a></div>";
			umProduto += "\t\t\t</div>";
			umProduto += "\t\t</div>";
			umProduto += "\t</section>";	
		}

		form = form.replaceFirst("<UM-PRODUTO>", java.util.regex.Matcher.quoteReplacement(umProduto));
		
		String list = "<div class=\"table-responsive\"><table class=\"table table-hover align-middle\">";
		list += "<thead class=\"table-dark\"><tr>";
		list += "<th><a href=\"/produto/list/" + FORM_ORDERBY_ID + "\" class=\"text-white\">ID</a></th>";
		list += "<th><a href=\"/produto/list/" + FORM_ORDERBY_DESCRICAO + "\" class=\"text-white\">Descrição</a></th>";
		list += "<th><a href=\"/produto/list/" + FORM_ORDERBY_PRECO + "\" class=\"text-white\">Preço</a></th>";
		list += "<th class=\"text-center\">Ações</th></tr></thead><tbody>";
		
		List<Produto> produtos;
		if (orderBy == FORM_ORDERBY_ID) produtos = produtoDAO.getOrderByID();
		else if (orderBy == FORM_ORDERBY_DESCRICAO) produtos = produtoDAO.getOrderByDescricao();
		else if (orderBy == FORM_ORDERBY_PRECO) produtos = produtoDAO.getOrderByPreco();
		else produtos = produtoDAO.get();

		for (Produto p : produtos) {
			list += "<tr>";
			list += "<td>" + p.getID() + "</td>";
			list += "<td>" + p.getDescricao() + "</td>";
			list += "<td>" + p.getPreco() + "</td>";
			list += "<td class=\"text-center\">";
			list += "<a href=\"/produto/" + p.getID() + "\" class=\"btn btn-sm btn-info text-white me-1\"><i class=\"bi bi-eye\"></i></a>";
			list += "<a href=\"/produto/update/" + p.getID() + "\" class=\"btn btn-sm btn-warning me-1\"><i class=\"bi bi-pencil\"></i></a>";
			list += "<a href=\"/produto/delete/" + p.getID() + "\" class=\"btn btn-sm btn-danger\"><i class=\"bi bi-trash\"></i></a>";
			list += "</td></tr>";
		}
		list += "</tbody></table></div>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", java.util.regex.Matcher.quoteReplacement(list));				
	}

	public Object insert(Request request, Response response) {
		String descricao = request.queryParams("descricao");
		float preco = Float.parseFloat(request.queryParams("preco"));
		int quantidade = Integer.parseInt(request.queryParams("quantidade"));
		LocalDateTime dataFabricacao = LocalDate.parse(request.queryParams("dataFabricacao")).atStartOfDay();
		LocalDate dataValidade = LocalDate.parse(request.queryParams("dataValidade"));
		
		String resp = "";
		Produto produto = new Produto(-1, descricao, preco, quantidade, dataFabricacao, dataValidade);
		
		if(produtoDAO.insert(produto)) {
			resp = "Produto (" + descricao + ") inserido!";
			response.status(201);
		} else {
			resp = "Erro ao inserir produto!";
			response.status(404);
		}
			
		response.redirect("/produto/list/1");
		return null;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Produto produto = produtoDAO.get(id);
		if (produto != null) {
			response.status(200);
			makeForm(FORM_DETAIL, produto, FORM_ORDERBY_DESCRICAO);
		} else {
			response.status(404);
			makeForm();
		}
		return form;
	}

	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Produto produto = produtoDAO.get(id);
		if (produto != null) {
			response.status(200);
			makeForm(FORM_UPDATE, produto, FORM_ORDERBY_DESCRICAO);
		} else {
			response.status(404);
			makeForm();
		}
		return form;
	}
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
		response.header("Content-Type", "text/html");
		response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Produto produto = produtoDAO.get(id);
		String resp = "";       

		if (produto != null) {
			produto.setDescricao(request.queryParams("descricao"));
			produto.setPreco(Float.parseFloat(request.queryParams("preco")));
			produto.setQuantidade(Integer.parseInt(request.queryParams("quantidade")));
			produto.setDataFabricacao(LocalDate.parse(request.queryParams("dataFabricacao")).atStartOfDay());
			produto.setDataValidade(LocalDate.parse(request.queryParams("dataValidade")));
			produtoDAO.update(produto);
			resp = "Produto atualizado!";
		} else {
			resp = "Produto não encontrado!";
		}
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	public Object delete(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		if (produtoDAO.delete(id)) response.status(200);
		else response.status(404);
		makeForm();
		return form;
	}
}