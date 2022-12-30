package organizzazione;
import java.util.HashSet;
import java.util.Set;
class Comitato {
	private Organizzatore responsabile;
	private Set<Organizzatore> membri;

	public Comitato(){
		membri = new HashSet<>();
	}
	public Comitato(Organizzatore responsabile){
		membri = new HashSet<>();
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
