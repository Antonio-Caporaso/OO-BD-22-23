package Organizzazione;
import java.util.Set;
class Comitato {
	private Organizzatore responsabile;
	private Set<Organizzatore> membri;

	public Organizzatore getResponsabile(){
		return responsabile;
	}
	public void setResponsabile(Organizzatore responsabile){
		this.responsabile=responsabile;
	}
}
