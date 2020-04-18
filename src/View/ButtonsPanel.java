package View;

import java.awt.Color;
import java.awt.Font;
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
		Color initColor = new Color(153, 153, 204);
		Color focusColor = new Color(157, 183, 183);
		Font btnFont = new Font("Arial", Font.BOLD, 20);
		btns = new Vector<JButton>();
		for (MODE m : MODE.values()) {
			JButton btn = new JButton(m.getTitle());
			btn.setFont(btnFont);
			btn.setForeground(Color.DARK_GRAY);
			btn.setBackground(initColor);
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// change previous style of button
					btns.get(canvas.getMode()).setForeground(Color.DARK_GRAY);
					btns.get(canvas.getMode()).setBackground(initColor);
					btn.setForeground(Color.RED);
					btn.setBackground(focusColor);
					
					menuBar.setMenuItemEnable();
					canvas.setMode(m.getModeID());
				}
			});
			this.add(btn);
			btns.add(btn);
		}
		btns.get(0).doClick();
	}
}
