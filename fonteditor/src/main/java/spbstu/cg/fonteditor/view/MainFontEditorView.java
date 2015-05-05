package spbstu.cg.fonteditor.view;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class MainFontEditorView extends JFrame {
    private ProjectPanelView projectPanel;
    private LetterEditorView letterEditor;
    private ControlPanelView controlPanel;
    private JMenuItem saveMi;
    private JButton buttonSave;

    private JLabel statusBarText;

    public JMenuItem getOpenMi() {
        return openMi;
    }

    private JMenuItem openMi;

    public JMenuItem getNewFontMi() {
        return newFontMi;
    }

    private JMenuItem newFontMi;

    public JMenuItem getNewLetterMi() {
        return newLetterMi;
    }

    private JMenuItem newLetterMi;

    public JButton getButtonLoad() {
        return buttonLoad;
    }

    private JButton buttonLoad;

    public MainFontEditorView() {
        initUI();
    }

    private void initUI() {
        createMenuBar();
        createToolBars();
        createStatusBar();

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);

        // Create and place 3 panels
        add(buildFontProjectPanel(), BorderLayout.WEST);
        add(buildCurveEditorPanel(), BorderLayout.CENTER);
        add(buildControlPanel(),     BorderLayout.EAST);

        setTitle("Font Editor");
        pack();
        setSize(1000, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(statusBar, BorderLayout.SOUTH);
        statusBar.setPreferredSize(new Dimension(this.getWidth(), 16));
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
        statusBarText = new JLabel("...");
        statusBarText.setHorizontalAlignment(SwingConstants.LEFT);
        statusBar.add(statusBarText);
    }

    public void setStatusBarMessage(String s) {
        statusBarText.setText(s);
    }

    private Component buildFontProjectPanel() {
        //ImageIcon icon = new ImageIcon(getClass().getResource("simulator.png"));
        projectPanel = new ProjectPanelView();

        JPanel wrapper = new JPanel(new BorderLayout());
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setForeground(Color.gray);
        wrapper.add(sep, BorderLayout.EAST);
        wrapper.add(projectPanel);

        return wrapper;
    }

    private Component buildCurveEditorPanel() {
        letterEditor = new LetterEditorView();
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(letterEditor);
        return wrapper;
    }

    private Component buildControlPanel() {
        controlPanel = new ControlPanelView();

        JPanel wrapper = new JPanel(new BorderLayout());
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setForeground(Color.gray);
        wrapper.add(sep, BorderLayout.WEST);
        wrapper.add(controlPanel);

        return wrapper;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        ImageIcon iconNew = new ImageIcon("res/newFile.png");
        ImageIcon iconOpen = new ImageIcon("res/openFile.png");
        ImageIcon iconSave = new ImageIcon("res/saveFile.png");
        ImageIcon iconExit = new ImageIcon("res/exit.png");

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu newMenu = new JMenu(new MenuItemAction("New", iconNew, KeyEvent.VK_N));
        newFontMi = new JMenuItem(new MenuItemAction("New Font Project", iconNew, KeyEvent.VK_P));
        newFontMi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));

        newLetterMi = new JMenuItem(new MenuItemAction("New Letter", iconNew, KeyEvent.VK_L));
        newLetterMi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));

        newMenu.add(newFontMi);
        newMenu.add(newLetterMi);

        openMi = new JMenuItem(new MenuItemAction("Open Font", iconOpen,
                KeyEvent.VK_O));

        saveMi = new JMenuItem(new MenuItemAction("Save Font", iconSave,
                KeyEvent.VK_S));

        JMenuItem exitMi = new JMenuItem("Exit", iconExit);
        exitMi.setMnemonic(KeyEvent.VK_E);
        exitMi.setToolTipText("Exit application");
        exitMi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));

        exitMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        fileMenu.add(newMenu);
        fileMenu.add(openMi);
        fileMenu.add(saveMi);
        fileMenu.addSeparator();
        fileMenu.add(exitMi);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    JButton newLetterButton;
    JButton newFontButton;

    public JButton getNewLetterButton() {
        return newLetterButton;
    }

    public JButton getNewFontButton() {
        return newFontButton;
    }

    private void createToolBars() {

        JToolBar toolbar = new JToolBar();

        ImageIcon iconNewFont = new ImageIcon(this.getClass().getResource("/new_font.png").getFile());
        ImageIcon iconNewLetter = new ImageIcon(this.getClass().getResource("/new_letter.png").getFile());
        ImageIcon iconOpen = new ImageIcon(this.getClass().getResource("/font_load.png").getFile());
        ImageIcon iconSave = new ImageIcon(this.getClass().getResource("/font_save.png").getFile());

        newFontButton = new JButton(iconNewFont);
        newLetterButton = new JButton(iconNewLetter);
        buttonLoad = new JButton(iconOpen);
        buttonSave = new JButton(iconSave);

        toolbar.add(newFontButton);
        toolbar.add(newLetterButton);
        toolbar.add(buttonLoad);
        toolbar.add(buttonSave);

        toolbar.setBorderPainted(false);
        toolbar.setFloatable(false);
        add(toolbar, BorderLayout.NORTH);
    }

    private static class MenuItemAction extends AbstractAction {
        public MenuItemAction(String text, ImageIcon icon, Integer mnemonic) {
            super(text);

            putValue(SMALL_ICON, icon);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    /*
     * Get methods for each panel
     */
    public ProjectPanelView getProjectPanel() { return projectPanel; }

    public LetterEditorView getLetterEditor() {
        return letterEditor;
    }

    public ControlPanelView getControlPanel() {
        return controlPanel;
    }

    public JMenuItem getSaveMi() {
        return saveMi;
    }

    public JButton getSaveButton() {
        return buttonSave;
    }
}