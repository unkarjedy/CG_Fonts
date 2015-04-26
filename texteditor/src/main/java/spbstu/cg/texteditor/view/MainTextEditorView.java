package spbstu.cg.texteditor.view;

import javafx.scene.control.ComboBox;
import sun.jvm.hotspot.ui.action.MemoryAction;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by JDima on 26/04/15.
 */
public class MainTextEditorView extends JFrame {

    private JPanel mainPanel;
    private JLabel display;
    private ComboBox fontComboBox;
    private ComboBox sizeComboBox;
    private JLabel avalaibleLettersLabel;
    private TextEditorView textEditor;

    private JPanel statusBar;
    private JLabel statusBarText;


    //TODO ACTIONS!
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    };

    public MainTextEditorView() {
        initUI();

        setTitle("Text Editor");
        pack();
        setSize(900, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        createMenuBar();
        createLetterPropertiesPanel();
        createStatusBar();

        createTextEditorPanel();
        add(mainPanel);
    }

    private void createTextEditorPanel() {
        textEditor = new TextEditorView();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textEditor);
        mainPanel.add(panel);
    }

    private void createLetterPropertiesPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(createComboBox(new String[]{"Times New Roman", "Our"}, actionListener));

        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(createComboBox(new String[]{"5", "10", "20", "30"}, actionListener));

        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(new JLabel("Available letters:"));
        mainPanel.add(panel);

    }

    private JComboBox createComboBox(String[] elements, ActionListener actionListener) {
        JComboBox comboBox = new JComboBox(elements);
        comboBox.setMaximumSize( comboBox.getPreferredSize() );
        comboBox.addActionListener(actionListener);
        add(comboBox);
        return comboBox;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenu = new MenuItemAction("New", actionListener, KeyEvent.VK_N);
        JMenuItem newSave = new MenuItemAction("Save", actionListener, KeyEvent.VK_S);
        JMenuItem newLoad = new MenuItemAction("Load", actionListener, KeyEvent.VK_L);
        JMenuItem newExit = new MenuItemAction("Exit", actionListener, KeyEvent.VK_E);

        fileMenu.add(newMenu);
        fileMenu.add(newSave);
        fileMenu.add(newLoad);
        fileMenu.add(newExit);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }



    private class MenuItemAction extends JMenuItem {
        public MenuItemAction(String title, ActionListener actionListener, int keyEvent) {
            this.setText(title);
            this.addActionListener(actionListener);
            KeyStroke keyStrokeToOpen
                    = KeyStroke.getKeyStroke(keyEvent, KeyEvent.CTRL_DOWN_MASK);
            this.setAccelerator(keyStrokeToOpen);
        }

    }

    private void createStatusBar() {
        statusBar = new JPanel();
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(statusBar, BorderLayout.SOUTH);
        statusBar.setPreferredSize(new Dimension(this.getWidth(), 16));
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
        statusBarText = new JLabel("...");
        statusBarText.setHorizontalAlignment(SwingConstants.LEFT);
        statusBar.add(statusBarText);
    }

}
