package Utils;

public enum MODE{
	SELECT(0, "Select"),
	ASSOCIATION(1, "Association Line"),
	GENERALIZATION(2, "Generalization Line"),
	COMPOSITION(3, "Composition Line"),
	CLASS(4, "Class"),
	USECASE(5, "Use case");
	
	private int modeID;
	private String title;
	
	MODE(int modeID, String title) {
		this.modeID = modeID;
		this.title = title;
	}
	
	public int getModeID() { return modeID; }
	public String getTitle() { return title; }
}
