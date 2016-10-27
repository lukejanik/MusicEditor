package cs3500.music.view;

import cs3500.music.model.IMusicEditorModel;
import oracle.jvm.hotspot.jfr.JFR;

import java.awt.*;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events

import javax.swing.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IView {

  private JFrame frame;
  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    frame = new JFrame("GUI FRAME");
    frame.setVisible(true);
    frame.setSize(600, 600);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // displayPanel.setBackground(Color.BLACK);

    this.displayPanel = new ConcreteGuiViewPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    frame.add(displayPanel);
    this.pack();
  }

  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(100, 100);
  }


  @Override
  public void render(IMusicEditorModel model) {

  }
}

