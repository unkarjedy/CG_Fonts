package spbstu.cg.texteditor.view;

import spbstu.cg.texteditor.model.TextEditorModel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by JDima on 26/04/15.
 */
public class MainTextEditorView extends JFrame {

    public static final String AVAILABLE_LETTERS = "Available letters: ";
    public static final String LAST_CHANGE = "Last change: ";
    public static final String[] SIZES = {"5", "10", "20", "30"};

    private JPanel mainPanel;
    private JComboBox fontComboBox;
    private JComboBox sizeComboBox;
    private JLabel avalaibleLettersLabel;

    private JMenuItem menuNew;
    private JMenuItem menuSave;
    private JMenuItem menuLoad;
    private JMenuItem menuLoadText;

    private TextEditorView textEditor;

    private JPanel statusBar;
    private JLabel statusBarText;


    public MainTextEditorView(TextEditorModel textEditorModel) {
        //TODO NOT SIZE IN THE BEGINNINGr
        textEditorModel.setSize(Integer.valueOf(SIZES[0]));
        initUI(textEditorModel);

        setTitle("Text Editor");
        pack();
        setSize(900, 600);
        setResizable(true);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initUI(TextEditorModel textEditorModel) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        createMenuBar();
        createLetterPropertiesPanel();
        createStatusBar();

        createTextEditorPanel(textEditorModel);
        add(mainPanel);
    }

    private void createTextEditorPanel(TextEditorModel textEditorModel) {
        textEditor = new TextEditorView(textEditorModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textEditor);
        mainPanel.add(panel);
    }

    private void createLetterPropertiesPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fontComboBox = createComboBox(new String[]{"Times New Roman", "Our"});
        panel.add(fontComboBox);

        sizeComboBox = createComboBox(SIZES);
        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(sizeComboBox);

        avalaibleLettersLabel = new JLabel(AVAILABLE_LETTERS);
        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(avalaibleLettersLabel);
        mainPanel.add(panel);

    }

    private JComboBox createComboBox(String[] elements) {
        JComboBox comboBox = new JComboBox(elements);
        comboBox.setMaximumSize( comboBox.getPreferredSize() );
        add(comboBox);
        return comboBox;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuNew = new MenuItemAction("New", KeyEvent.VK_N);
        menuSave = new MenuItemAction("Save text",  KeyEvent.VK_S);
        menuLoad = new MenuItemAction("Load font", KeyEvent.VK_L);
        menuLoadText = new MenuItemAction("Load text", KeyEvent.VK_T);
        JMenuItem newExit = new MenuItemAction("Exit", KeyEvent.VK_E);

        newExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        fileMenu.add(menuNew);
        fileMenu.add(menuSave);
        fileMenu.add(menuLoadText);
        fileMenu.add(menuLoad);
        fileMenu.add(newExit);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    public TextEditorView getTextEditor() {
        return textEditor;
    }

    public JComboBox getFontComboBox() {
        return fontComboBox;
    }

    public JComboBox getSizeComboBox() {
        return sizeComboBox;
    }

    public JLabel getAvalaibleLettersLabel() {
        return avalaibleLettersLabel;
    }

    public void setStatusBarText(String text) {
        this.statusBarText.setText(text);
    }

    public JMenuItem getMenuNew() {
        return menuNew;
    }

    public JMenuItem getMenuLoadText() {
        return menuLoadText;
    }

    public JMenuItem getMenuSave() {
        return menuSave;
    }

    public JMenuItem getMenuLoad() {
        return menuLoad;
    }

    private class MenuItemAction extends JMenuItem {
        public MenuItemAction(String title, int keyEvent) {
            this.setText(title);
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
