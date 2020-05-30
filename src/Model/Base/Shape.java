package Model.Base;

import javax.swing.JComponent;

public class Shape extends JComponent {

	/**
	 * Check component is selected by user
	 */
	protected boolean selected;

	/**
	 * Set component is selected by user
	 * 
	 * @param selected - user is selected this component or not
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Get this component is selected by user or not
	 * 
	 * @return this component is selected by user or not
	 */
	public boolean isSelected() {
		return selected;
	}
}
