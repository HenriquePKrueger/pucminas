package model;

public class Oferta {
	private int id;
	private String descricao;
	private double valor;
	private int status;
	private String justicativaRecusa;
	private String createdAt;
	private int solicitacoesServicosId;
	private int prestadoresId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getJusticativaRecusa() {
		return justicativaRecusa;
	}
	public void setJusticativaRecusa(String justicativaRecusa) {
		this.justicativaRecusa = justicativaRecusa;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public int getSolicitacoesServicosId() {
		return solicitacoesServicosId;
	}
	public void setSolicitacoesServicosId(int solicitacoesServicosId) {
		this.solicitacoesServicosId = solicitacoesServicosId;
	}
	public int getPrestadoresId() {
		return prestadoresId;
	}
	public void setPrestadoresId(int prestadoresId) {
		this.prestadoresId = prestadoresId;
	}
}
