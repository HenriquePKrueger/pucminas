package model;

public class SolicitacoesImagens {
	private int id;
	private String url;
	private int solicitacoesServicosId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getSolicitacoesServicosId() {
		return solicitacoesServicosId;
	}
	public void setSolicitacoesServicosId(int solicitacoesServicosId) {
		this.solicitacoesServicosId = solicitacoesServicosId;
	}
}
