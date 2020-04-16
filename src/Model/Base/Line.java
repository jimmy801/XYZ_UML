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
	protected Port src;
	protected Port dst;
	public static final int ARROW_LEN = 8;

	public Line() {
	}

	public Line(Port src, Port dst) {
		this.src = src;
		this.dst = dst;
	}

	public Port getSrc() {
		return src;
	}

	public Port getDst() {
		return dst;
	}
}
