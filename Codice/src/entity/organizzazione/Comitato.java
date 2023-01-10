package entity.organizzazione;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
class Comitato {
	private Ente nominatoDa;
	private Organizzatore responsabile;
	private LinkedList<Organizzatore> membri;

	public Comitato(){
		membri = new LinkedList<Organizzatore>();
	}
	public Comitato(Organizzatore responsabile){
		membri = new LinkedList<Organizzatore>();
		this.responsabile=responsabile;
	}
	public void setResponsabile(Organizzatore responsabile){
		this.responsabile = responsabile;
	}
	public Organizzatore getResponsabile(){
		return responsabile;
	}

	public void setNominatoDa(Ente nominatoDa) {
		this.nominatoDa = nominatoDa;
		nominatoDa.nomina(this);
	}

	public Ente getNominatoDa() {
		return nominatoDa;
	}

	public void addMembro(Organizzatore organizzatore){
		if(!membri.contains(organizzatore))
			membri.add(organizzatore);
	}

}
