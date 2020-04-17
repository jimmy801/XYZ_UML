package Model.Base;

import javax.swing.JComponent;

/**
 * Base of all line object components
 * 
 * @author Jimmy801
 *
 * @see {@link JComponent}
 */
public class Line extends JComponent {
	/**
	 * Source of line
	 */
	protected Port src;
	/**
	 * Destination of line
	 */
	protected Port dst;
	/**
	 * Length of line arrow
	 */
	public static final int ARROW_LEN = 8;

	public Line() {
	}

	/**
	 * Initialize by source port and destination port
	 * 
	 * @param src - source port
	 * @param dst - destination port
	 */
	public Line(Port src, Port dst) {
		this.src = src;
		this.dst = dst;
	}

	/**
	 * Get source Port
	 * 
	 * @return source port
	 */
	public Port getSrc() {
		return src;
	}

	/**
	 * Get destination port
	 * 
	 * @return destination port
	 */
	public Port getDst() {
		return dst;
	}
}
