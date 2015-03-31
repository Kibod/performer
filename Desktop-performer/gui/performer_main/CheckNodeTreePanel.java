/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_main;

/**
 *
 * @author Dragan
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.EventObject;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import gui.performer_event.StudentResultListener;
import gui.performer_event.StudentResultSource;



/**
 * @version 1.1 01/15/99
 */
public class CheckNodeTreePanel extends JPanel {
  
    public CheckNode students = new CheckNode("Students");
    public CheckNode tests = new CheckNode("Tests");
    
    private JTree tree;
    
  public CheckNodeTreePanel() {
    this.setLayout(new GridLayout());  

    
    CheckNode root = new CheckNode("");
    root.add(students);
    root.add(tests);

    tree = new JTree( root );
    tree.setRootVisible(false);
    tree.setCellRenderer(new CheckRenderer());
    tree.getSelectionModel().setSelectionMode(
      TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION
    );
    tree.putClientProperty("JTree.lineStyle", "Angled");
    tree.addMouseListener(new NodeSelectionListener(tree));
    JScrollPane sp = new JScrollPane(tree);
    sp.setBorder(new EmptyBorder(0, 0, 0, 0));
    this.add(sp);

  }
  
    public CheckNodeTreePanel(CheckNode s, CheckNode t) {
    this.setLayout(new GridLayout());                                   
    
    CheckNode root = new CheckNode("");
    root.add(s);
    
    // change this
    root.add(tests);
    tests.add(t);

    tree = new JTree( root );
    tree.setRootVisible(false);
    tree.setCellRenderer(new CheckRenderer());
    tree.getSelectionModel().setSelectionMode(
      TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION
    );
    tree.putClientProperty("JTree.lineStyle", "Angled");
    tree.addMouseListener(new NodeSelectionListener(tree));
    JScrollPane sp = new JScrollPane(tree);
    sp.setBorder(new EmptyBorder(0, 0, 0, 0));
    this.add(sp);

  }
    
    public static void refreshPanel (JPanel panel)
    {
        panel.removeAll();
        panel.setLayout(new GridLayout(1,0));
        panel.add(new CheckNodeTreePanel(StudentRepresentationModel.getStudentsRoot(),StudentRepresentationModel.getTestRoot()));
    }
  
  

  class NodeSelectionListener extends MouseAdapter {
    JTree tree;
    
    NodeSelectionListener(JTree tree) {
      this.tree = tree;
    }
    
    public void mouseClicked(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      int row = tree.getRowForLocation(x, y);
      TreePath  path = tree.getPathForRow(row);
      //TreePath  path = tree.getSelectionPath();
      if (path != null) {
        CheckNode node = (CheckNode)path.getLastPathComponent();
        boolean isSelected = ! (node.isSelected());
        node.setSelected(isSelected);
        if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION) {
          if ( isSelected) {
            tree.expandPath(path);
          } else {
            tree.collapsePath(path);
          }
        }
        ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
        // I need revalidate if node is root.  but why?
        if (row == 0) {
          tree.revalidate();
          tree.repaint();
        }
      }
    }
  }
}
  
class CheckRenderer extends JPanel implements TreeCellRenderer {
  protected JCheckBox check;

  protected TreeLabel label;

  public CheckRenderer() {
    setLayout(null);
    add(check = new JCheckBox());
    add(label = new TreeLabel());
    check.setBackground(UIManager.getColor("Tree.textBackground"));
    label.setForeground(UIManager.getColor("Tree.textForeground"));
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value,
      boolean isSelected, boolean expanded, boolean leaf, int row,
      boolean hasFocus) {
    String stringValue = tree.convertValueToText(value, isSelected,
        expanded, leaf, row, hasFocus);
    setEnabled(tree.isEnabled());
    check.setSelected(((CheckNode) value).isSelected());
    //check.setForeground(((CheckNode)value).getColor());
    label.setFont(tree.getFont());
    label.setText(stringValue);
    label.setSelected(isSelected);
    label.setFocus(hasFocus);
    label.setForeground(((CheckNode)value).getColor());
    if (leaf) {
      label.setIcon(UIManager.getIcon("Tree.leafIcon"));
    } else if (expanded) {
      label.setIcon(UIManager.getIcon("Tree.openIcon"));
    } else {
      label.setIcon(UIManager.getIcon("Tree.closedIcon"));
    }
    return this;
  }

  public Dimension getPreferredSize() {
    Dimension d_check = check.getPreferredSize();
    Dimension d_label = label.getPreferredSize();
    return new Dimension(d_check.width + d_label.width,
        (d_check.height < d_label.height ? d_label.height
            : d_check.height));
  }

  public void doLayout() {
    Dimension d_check = check.getPreferredSize();
    Dimension d_label = label.getPreferredSize();
    int y_check = 0;
    int y_label = 0;
    if (d_check.height < d_label.height) {
      y_check = (d_label.height - d_check.height) / 2;
    } else {
      y_label = (d_check.height - d_label.height) / 2;
    }
    check.setLocation(0, y_check);
    check.setBounds(0, y_check, d_check.width, d_check.height);
    label.setLocation(d_check.width, y_label);
    label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
  }

  public void setBackground(Color color) {
    if (color instanceof ColorUIResource)
      color = null;
    super.setBackground(color);
  }

  public class TreeLabel extends JLabel {
    boolean isSelected;

    boolean hasFocus;

    public TreeLabel() {
    }

    public void setBackground(Color color) {
      if (color instanceof ColorUIResource)
        color = null;
      super.setBackground(color);
    }

    public void paint(Graphics g) {
      String str;
      if ((str = getText()) != null) {
        if (0 < str.length()) {
          if (isSelected) {
            g.setColor(UIManager
                .getColor("Tree.selectionBackground"));
          } else {
            g.setColor(UIManager.getColor("Tree.textBackground"));
          }
          Dimension d = getPreferredSize();
          int imageOffset = 0;
          Icon currentI = getIcon();
          if (currentI != null) {
            imageOffset = currentI.getIconWidth()
                + Math.max(0, getIconTextGap() - 1);
          }
          g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
              d.height);
          if (hasFocus) {
            g.setColor(UIManager
                .getColor("Tree.selectionBorderColor"));
            g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
                d.height - 1);
          }
        }
      }
      super.paint(g);
    }

    public Dimension getPreferredSize() {
      Dimension retDimension = super.getPreferredSize();
      if (retDimension != null) {
        retDimension = new Dimension(retDimension.width + 3,
            retDimension.height);
      }
      return retDimension;
    }

    public void setSelected(boolean isSelected) {
      this.isSelected = isSelected;
    }

    public void setFocus(boolean hasFocus) {
      this.hasFocus = hasFocus;
    }
  }
}

class CheckNode extends DefaultMutableTreeNode implements StudentResultListener {

  public final static int SINGLE_SELECTION = 0;

  public final static int DIG_IN_SELECTION = 4;

  protected int selectionMode;

  protected boolean isSelected;
  
  protected Color color = Color.BLACK;

  public CheckNode() {
    this(null);
  }

  public CheckNode(Object userObject) {
    this(userObject, true, false);
  }

  public CheckNode(Object userObject, boolean allowsChildren,
      boolean isSelected) {
    super(userObject, allowsChildren);
    this.isSelected = isSelected;
    setSelectionMode(DIG_IN_SELECTION);
  }

  public void setSelectionMode(int mode) {
    selectionMode = mode;
  }

  public int getSelectionMode() {
    return selectionMode;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;

    if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
      Enumeration e = children.elements();
      while (e.hasMoreElements()) {
        CheckNode node = (CheckNode) e.nextElement();
        node.setSelected(isSelected);
      }
    }
  }

  public boolean isSelected() {
    return isSelected;
  }
  
  public Color getColor ()
  {
      return color;
  }
  
  /*
   * only work for Assignment type
   */
  public boolean childExsits(String childName)
  {

      return false;
  }
          

  // If you want to change "isSelected" by CellEditor,
  /*
   public void setUserObject(Object obj) { if (obj instanceof Boolean) {
   * setSelected(((Boolean)obj).booleanValue()); } else {
   * super.setUserObject(obj); } }
   */

    @Override
    public void handleStudentResultEvent(EventObject e) {
        Student s = ((StudentResultSource)e.getSource()).getStudent();
        CheckNode n = StudentRepresentationModel.findNodeByStudent(s);
        if ( n != null)
        {
            //n.color = s.getResultColorInterpretation();
            n.color = s.studentColor();
            for (int i=0; i<n.getChildCount(); i++)
            {
                ((CheckNode)(n.getChildAt(i))).color = s.getStudentAssignments().get(i).getResultColorInterpretation();
            }
        }
        System.out.println("nodeAck");
    }

}
