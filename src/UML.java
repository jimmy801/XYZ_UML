import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import View.ButtonsPanel;
import View.Canvas;
import View.MenuBar;

/**
 * Main Frame
 * 
 * @author Jimmy801
 * 
 * @see {@link JFrame}
 */
public class UML extends JFrame {
	/**
	 * Mode buttons panel
	 */
	private ButtonsPanel btnPanel;
	/**
	 * Canvas container
	 */
	private Canvas canvasPanel;
	/**
	 * MenuBar of specified operation
	 */
	private MenuBar menuBar;

	public UML() {
		initComponents();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500, 640));
		initCanvas();
		initMenuBar();
		initButtons();
	}

	/**
	 * Initialize style of UML frame
	 */
	private void initComponents() {
		this.getContentPane().setLayout(new BorderLayout());
		this.setTitle("UML editor");
		this.setSize(960, 720);
	}

	/**
	 * Initialize style of canvas container
	 */
	private void initCanvas() {
		canvasPanel = Canvas.getInstance();
		this.getContentPane().add(BorderLayout.CENTER, canvasPanel);
	}

	/**
	 * Initialize style of menu bar
	 */
	private void initMenuBar() {
		menuBar = MenuBar.getInstance();
		this.getContentPane().add(BorderLayout.NORTH, menuBar);
	}

	/**
	 * Initialize style of mode button panel
	 */
	private void initButtons() {
		btnPanel = new ButtonsPanel();
		this.getContentPane().add(BorderLayout.WEST, btnPanel);
	}

}
