package app;

import static spark.Spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.MultipartConfigElement;

import com.google.gson.Gson;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import model.Usuario;
import service.CategoriaService;
import service.SolicitacaoServicoService;
import service.UsuarioService;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Aplicacao {
	private static Configuration freeMarkerConfig;
	private static FreeMarkerEngine engine;
	private static final Gson gson = new Gson();
	
	public static void setupFreeMarker() {
		freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_26);
        freeMarkerConfig.setClassForTemplateLoading(Aplicacao.class, "/templates");
		freeMarkerConfig.setDefaultEncoding("UTF-8");
		freeMarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		freeMarkerConfig.setLogTemplateExceptions(false);
		freeMarkerConfig.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
		engine = new FreeMarkerEngine(freeMarkerConfig);
    }
	
	public static void main(String[] args) {
		setupFreeMarker();
		
		port(8080);
		staticFiles.location("/public");

		CategoriaService categoriaService = new CategoriaService();
		UsuarioService usuarioService = new UsuarioService();
		SolicitacaoServicoService solicitacaoServicoService = new SolicitacaoServicoService();
		
		before("*", (req, res) -> {
		    if (req.session().attribute("user_id") != null) {
		        req.attribute("user_login", req.session().attribute("user_login"));
		        req.attribute("user_id", req.session().attribute("user_id"));
		        req.attribute("tipo_usuario", req.session().attribute("tipo_usuario"));
		    }
		});
		get("/", (req, res) -> {
			Map<String, Object> model = getModel(req);
			
			return render(model, "index.ftl");
		});

		get("/login", (req, res)-> {
			Map<String, Object> model = getModel(req);
			
			model.put("erro", req.session().attribute("erro"));
			req.session().removeAttribute("erro");
			
			return render(model, "form/login.ftl");
		});
		
		post("/login", (req, res)-> {
			usuarioService.autenticarUsuario(req, res);
			return null;
		});
		
		get("/perfil", (req, res)-> {
			Map<String, Object> model = getModel(req);
			Usuario u = usuarioService.carregarDadosPerfil(req, res);
			model.put("usuario", u);
			model.put("categorias", req.attribute("categorias"));
		    model.put("categoriasDoUsuario", req.attribute("categoriasDoUsuario"));
		    
			model.put("erro", req.session().attribute("erro"));
			model.put("sucesso", req.session().attribute("sucesso"));
			
			req.session().removeAttribute("erro");
			req.session().removeAttribute("sucesso");
			
			return render(model, "form/perfil.ftl");
		});
		
		post("/editar-perfil", (req, res) -> {
			usuarioService.editarPerfil(req, res);
			
			return null;
		});
		
		
		get("/cadastro", (req, res) -> {
			Map<String, Object> model = getModel(req);
			
			model.put("categorias", categoriaService.obterCategorias());
			model.put("erro", req.session().attribute("erro"));

			model.put("temp_nome", req.session().attribute("temp_nome"));
			model.put("temp_email", req.session().attribute("temp_email"));
			model.put("temp_telefone", req.session().attribute("temp_telefone"));
			
			model.put("temp_rua", req.session().attribute("temp_rua"));
			model.put("temp_bairro", req.session().attribute("temp_bairro"));
			model.put("temp_cidade", req.session().attribute("temp_cidade"));
			model.put("temp_login", req.session().attribute("temp_login"));
			
			req.session().removeAttribute("erro");
			req.session().removeAttribute("temp_nome");
			req.session().removeAttribute("temp_email");
			req.session().removeAttribute("temp_telefone");
			
			req.session().removeAttribute("temp_rua");
			req.session().removeAttribute("temp_bairro");
			req.session().removeAttribute("temp_cidade");
			
			req.session().removeAttribute("temp_login");
			
			return render(model, "form/cadastro.ftl");
		});

		post("/cadastro", (req, res) -> {
		    usuarioService.criarUsuario(req, res);
		    return null;
		});
		
		get("/logout", (req, res) -> {
			req.session().invalidate();
		    res.redirect("/");
		    return null;
		});
		
		//Chama o ftl criado para a pesquisa
		get("/pesquisa", (req, res) -> {
			
			Map<String, Object> model = getModel(req);
			
			model.put("categorias", categoriaService.obterCategorias());
			model.put("erro", req.session().attribute("erro"));
			req.session().removeAttribute("erro");
			
			return render(model, "pesquisa/index.ftl");
        });
		
		get("/about", (req, res) -> {
			Map<String, Object> model = getModel(req);
			
			return render(model, "about/index.ftl");
		});
		
		before("/criar-solicitacao", (request, response) -> {
			if(request.session().attribute("user_id") == null) {
				response.redirect("/login"); 
			}
		});
		
		get("/criar-solicitacao", (req, res) -> {
			Map<String, Object> model = getModel(req);
			model.put("categorias", categoriaService.obterCategorias());
			
			return render(model, "solicitacao_de_servico/index.ftl");
		});
		
		post("/criar-solicitacao", (req, res) -> {
			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
	
			boolean isSuccess = solicitacaoServicoService.criarNovaSolicitacao(req, res);
			
			Map<String, String> stringMap = new HashMap<>();
			String mensagem = isSuccess 
			        ? "Solicitação aberta com sucesso! Aguarde para receber ofertas." 
			        : "Erro ao processar solicitação! Tente novamente mais tarde";
			
			res.status(isSuccess ? 201 : 400);
			
			stringMap.put("mensagem", mensagem);
			
			return gson.toJson(stringMap);
		});
		
		get("/obter-endereco", (req, res) -> {
			Map<String, String> stringMap = new HashMap<>();
			usuarioService.carregarEnderecoDoUsuario(req, res);
			stringMap.put("rua", req.attribute("rua"));
			stringMap.put("cep", req.attribute("cep"));
			stringMap.put("bairro", req.attribute("bairro"));
			stringMap.put("cidade", req.attribute("cidade"));
			
			return gson.toJson(stringMap);
		});
		
		before("/minhas-solicitacoes", (request, response) -> {
			if(request.session().attribute("user_id") == null) {
				response.redirect("/login"); 
			}
		});
		
		get("/minhas-solicitacoes", (req, res) -> {
			Map<String, Object> model = getModel(req);
			return render(model, "solicitacao_de_servico/minhas_solicitacoes.ftl");
		});
		
		
		System.out.println("Servidor rodando em http://localhost:8080");
	}
	
	public static String render(Map<String, Object> model, String templatePath) {
		return engine.render(new ModelAndView(model, templatePath));
	}
	
	private static Map<String, Object> getModel(spark.Request req) {
	    Map<String, Object> model = new HashMap<>();
	  
	    model.put("user_login", req.attribute("user_login"));
	    model.put("user_id", req.attribute("user_id"));
	    model.put("tipo_usuario", req.attribute("tipo_usuario"));
	    return model;
	}
}