import java.awt.BorderLayout;

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
	 * mode buttons panel
	 */
	private ButtonsPanel btnPanel;
	/**
	 * canvas container
	 */
	private Canvas canvasPanel;
	/**
	 * MenuBar of specified operation
	 */
	private MenuBar menuBar;

	public UML() {
		initComponents();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	 * 
	 */
	private void initCanvas() {
		canvasPanel = Canvas.getInstance();
		this.getContentPane().add(BorderLayout.CENTER, canvasPanel);
	}

	private void initMenuBar() {
		menuBar = MenuBar.getInstance();
		this.getContentPane().add(BorderLayout.NORTH, menuBar);
	}

	private void initButtons() {
		btnPanel = new ButtonsPanel();
		this.getContentPane().add(BorderLayout.WEST, btnPanel);
	}

}
