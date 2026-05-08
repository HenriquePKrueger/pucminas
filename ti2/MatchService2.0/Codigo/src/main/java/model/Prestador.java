package model;

public class Prestador {
	private long id;
	private String descricao;
	private long usuariosId;
	
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
	
}
