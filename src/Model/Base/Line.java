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
	protected static int ARROW_LEN = 15;
	/**
	 * Distance of line arrow vertex on line
	 */
	protected static int CROSS_LEN = 12;
	/**
	 * Distance of line and arrow tail
	 */
	protected static int NORMAL_LEN = 9;

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
		setArrowLength(15);
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

	/**
	 * Set length of arrow<br>
	 * Default ratio value of arrow_length: cross_length : normal_length is 3: 4: 5
	 * 
	 * @param arrLen - arrow length of lines
	 */
	public void setArrowLength(int arrLen) {
		ARROW_LEN = arrLen;
		// 3, 4, 5 triangle
		CROSS_LEN = Math.round(ARROW_LEN / 5 * 4);
		NORMAL_LEN = Math.round(ARROW_LEN / 5 * 3);
	}

	/**
	 * Get arrow length of lines
	 * 
	 * @return arrow length of lines
	 */
	public static int getArrowLen() {
		return ARROW_LEN;
	}

	/**
	 * Get distance of line arrow vertex on line
	 * 
	 * @return distance of line arrow vertex on line
	 */
	public static int getCrossLen() {
		return CROSS_LEN;
	}

	/**
	 * Get distance of line and arrow tail
	 * 
	 * @return distance of line and arrow tail
	 */
	public static int getNormalLen() {
		return NORMAL_LEN;
	}
}
