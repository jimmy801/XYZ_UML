import java.awt.BorderLayout;

import javax.swing.JFrame;

import View.ButtonsPanel;
import View.Canvas;
import View.MenuBar;

public class UML extends JFrame {
    private ButtonsPanel btnPanel;
    private Canvas canvasPanel;
    private MenuBar menuBar;

    public UML(){
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initCanvas();
        initMenuBar();
        initButtons();
    }

    private void initComponents() {
        this.getContentPane().setLayout(new BorderLayout());
        this.setTitle("UML editor");
        this.setSize(960,720);
        //this.setResizable(false);
    }

    private void initCanvas(){
        canvasPanel = Canvas.getInstance(); // In order to paint in same Panel
        canvasPanel.setDoubleBuffered(true);
        this.getContentPane().add(BorderLayout.CENTER, canvasPanel);
    }

    private void initMenuBar(){
    	menuBar = MenuBar.getInstance();
        this.getContentPane().add(BorderLayout.NORTH, menuBar);
    }

    private void initButtons(){
        btnPanel = new ButtonsPanel();
        this.getContentPane().add(BorderLayout.WEST, btnPanel);
    }

}
