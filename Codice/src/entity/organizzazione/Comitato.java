package entity.organizzazione;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
class Comitato {
	private Organizzatore responsabile;
	private LinkedList<Organizzatore> membri;

	public Comitato(){
		membri = new LinkedList<Organizzatore>();
	}
	public Comitato(Organizzatore responsabile){
		membri = new LinkedList<Organizzatore>();
		this.responsabile=responsabile;
	}
	public Organizzatore getResponsabile(){
		return responsabile;
	}
	public void setResponsabile(Organizzatore responsabile){
		this.responsabile=responsabile;
	}

	public void addResponsabile(){
		membri.add(this.responsabile);
	}

	public void addMembro(Organizzatore org){
		membri.add(org);
	}
}
