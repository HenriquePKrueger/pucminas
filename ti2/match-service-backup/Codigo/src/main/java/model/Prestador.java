package model;

public class Prestador {
	private long id;
	private String descricao;
	private long usuariosId;
	private String nomeUsuario;
	private String nomeCategoria;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public long getUsuariosId() {
		return usuariosId;
	}
	public void setUsuariosId(long usuariosId) {
		this.usuariosId = usuariosId;
	}
	public String getNomeUsuario() {
		return this.nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getNomeCategoria(){
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria){
		this.nomeCategoria = nomeCategoria;
	}
	
}
