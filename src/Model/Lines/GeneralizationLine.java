package Model.Lines;

import java.awt.Graphics;

import Model.Base.Line;
import Model.Base.Port;
import Utils.PaintMethods;

/**
 * Generalization line component
 * 
 * @author Jimmy801
 *
 * @see {@link Model.Base.Line}
 */
public class GeneralizationLine extends Line {
	/**
	 * Initialize by source port and destination port
	 * 
	 * @param src - source port
	 * @param dst - destination port
	 * 
	 * @see {@link Line}
	 */
	public GeneralizationLine(Port src, Port dst) {
		super(src, dst);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		PaintMethods.paintGeneralizationLine(g, src.getLocation(), dst.getLocation(), CROSS_LEN, NORMAL_LEN);
	}
}
