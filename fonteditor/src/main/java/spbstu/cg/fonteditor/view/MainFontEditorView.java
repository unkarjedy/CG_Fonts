package spbstu.cg.fonteditor.view;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class MainFontEditorView extends JFrame {
    private ProjectPanelView projectsPanel;
    private LetterEditorView letterEditor;
    private ControlPanelView controlPanel;

    private JPanel statusBar;
    private JLabel statusBarText;

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
        setSize(900, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    public void setStatusBarMessage(String s) {
        statusBarText.setText(s);
    }

    private Component buildFontProjectPanel() {
        //ImageIcon icon = new ImageIcon(getClass().getResource("simulator.png"));
        projectsPanel = new ProjectPanelView();

        JPanel wrapper = new JPanel(new BorderLayout());
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setForeground(Color.gray);
        wrapper.add(sep, BorderLayout.EAST);
        wrapper.add(projectsPanel);

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
        JMenuItem newFontMi = new JMenuItem(new MenuItemAction("New Font Project", iconNew, KeyEvent.VK_P));
        JMenuItem newLetterMi = new JMenuItem(new MenuItemAction("New Letter", iconNew, KeyEvent.VK_L));
        newMenu.add(newFontMi);
        newMenu.add(newLetterMi);

        JMenuItem openMi = new JMenuItem(new MenuItemAction("Open Font", iconOpen,
                KeyEvent.VK_O));

        JMenuItem saveMi = new JMenuItem(new MenuItemAction("Save Font", iconSave,
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

    private void createToolBars() {

        JToolBar toolbar = new JToolBar();

        ImageIcon iconNew = new ImageIcon(this.getClass().getResource("/newFile.png").getFile());
        ImageIcon iconOpen = new ImageIcon(this.getClass().getResource("/openFile.png").getFile());
        ImageIcon iconSave = new ImageIcon(this.getClass().getResource("/saveFile.png").getFile());

        JButton buttonNew = new JButton(iconNew);
        JButton buttonOpen = new JButton(iconOpen);
        JButton buttonSave = new JButton(iconSave);

        toolbar.add(buttonNew);
        toolbar.add(buttonOpen);
        toolbar.add(buttonSave);


//        exitb.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                System.exit(0);
//            }
//        });

        toolbar.setBorderPainted(false);
        toolbar.setFloatable(false);
        add(toolbar, BorderLayout.NORTH);
    }

    private class MenuItemAction extends AbstractAction {
        public MenuItemAction(String text, ImageIcon icon, Integer mnemonic) {
            super(text);

            putValue(SMALL_ICON, icon);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }

    /*
     * Get methods for each panel
     */
    public ProjectPanelView getProejctPanel() { return projectsPanel; }

    public LetterEditorView getLetterEditor() {
        return letterEditor;
    }

    public ControlPanelView getControlPanel() {
        return controlPanel;
    }
}