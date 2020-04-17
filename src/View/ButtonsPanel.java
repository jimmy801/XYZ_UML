package View;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import Utils.MODE;

/**
 * Panel of mode buttons
 * 
 * @author Jimmy801
 *
 * @see {@link JPanel}
 */
public class ButtonsPanel extends JPanel {
	/**
	 * Mode buttons of this panel
	 */
	Vector<JButton> btns;
	/**
	 * UML canvas
	 */
	private Canvas canvas = Canvas.getInstance();
	/**
	 * UML menu bar
	 */
	private MenuBar menuBar = MenuBar.getInstance();

	public ButtonsPanel() {
		setLayout(new GridLayout(6, 0));
		btns = new Vector<JButton>();
		for (MODE m : MODE.values()) {
			JButton btn = new JButton(m.getTitle());
			btn.setForeground(Color.DARK_GRAY);
			btn.setBackground(Color.WHITE);
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// change previous style of button
					btns.get(canvas.getMode()).setForeground(Color.DARK_GRAY);
					btns.get(m.getModeID()).setForeground(Color.RED);
					
					menuBar.setMenuItemEnable();
					canvas.setMode(m.getModeID());
				}
			});
			this.add(btn);
			btns.add(btn);
		}
	}
}
