package Utils;

/**
 * enum of modes
 * 
 * @author Jimmy801
 *
 */
public enum MODE {
	SELECT(0, "Select"), ASSOCIATION(1, "Association Line"), GENERALIZATION(2, "Generalization Line"),
	COMPOSITION(3, "Composition Line"), CLASS(4, "Class"), USECASE(5, "Use Case");

	/**
	 * mode id
	 */
	private int modeID;
	/**
	 * mode title
	 */
	private String title;

	/**
	 * Create new MODE by its mode id and title
	 * 
	 * @param modeID - mode id
	 * @param title  - mode title
	 */
	MODE(int modeID, String title) {
		this.modeID = modeID;
		this.title = title;
	}

	/**
	 * Get mode id
	 * 
	 * @return mode id
	 */
	public int getModeID() {
		return modeID;
	}

	/**
	 * Get mode title
	 * 
	 * @return mode title
	 */
	public String getTitle() {
		return title;
	}
}
