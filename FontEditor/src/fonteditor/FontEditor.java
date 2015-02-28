package fonteditor;

/**
 * Created by user on 27.02.2015.
 */
import fonteditor.panels.ControlPanel;
import fonteditor.panels.CurveEditorPanel;
import fonteditor.panels.FontProjectsPanel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class FontEditor extends JFrame {
    private JPopupMenu pmenu;

    FontProjectsPanel projectsPanel;
    CurveEditorPanel curveEditorPanel;
    ControlPanel controlPanel;

    public FontEditor() {
        initUI();
    }

    private void initUI() {
        createMenuBar();
        createToolBars();

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);

        add(buildFontProjectPanel(), BorderLayout.WEST);
        add(buildCurveEditorPanel(), BorderLayout.CENTER);
        add(buildControlPanel(),     BorderLayout.EAST);


        setTitle("Font Editor");
        /* pack(); */
        setSize(1200, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    private Component buildFontProjectPanel() {
        //ImageIcon icon = new ImageIcon(getClass().getResource("simulator.png"));
        projectsPanel = new FontProjectsPanel();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.EAST);
        wrapper.add(projectsPanel);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));

        return wrapper;
    }

    private Component buildCurveEditorPanel() {
        curveEditorPanel = new CurveEditorPanel();

        JPanel wrapper = new JPanel(new BorderLayout());
        //wrapper.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.WEST);
        wrapper.add(curveEditorPanel);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));

        return wrapper;
    }

    private Component buildControlPanel() {
        controlPanel = new ControlPanel();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.WEST);
        wrapper.add(controlPanel);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));

        return wrapper;
    }

    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        ImageIcon iconNew = new ImageIcon("new.png");
        ImageIcon iconOpen = new ImageIcon("open.png");
        ImageIcon iconSave = new ImageIcon("save.png");
        ImageIcon iconExit = new ImageIcon("exit.png");

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu newMenu = new JMenu(new MenuItemAction("New", iconNew, KeyEvent.VK_N));
        JMenuItem newFontMi = new JMenuItem(new MenuItemAction("New Font Project", iconNew, KeyEvent.VK_F));
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
        exitMi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

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

        menubar.add(fileMenu);

        setJMenuBar(menubar);
    }

    private void createToolBars() {

        JToolBar toolbar = new JToolBar();

        ImageIcon newi = new ImageIcon("res/item.png");
        Image image = newi.getImage(); // transform it
        Image newimg = image.getScaledInstance(15, 15,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        newi = new ImageIcon(newimg);
//        ImageIcon open = new ImageIcon("res/item.png");
//        ImageIcon save = new ImageIcon("res/item.png");

        JButton newb = new JButton(newi);
        JButton openb = new JButton(newi);
        JButton saveb = new JButton(newi);

        toolbar.add(newb);
        toolbar.add(openb);
        toolbar.add(saveb);


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

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FontEditor ex = new FontEditor();
                ex.setVisible(true);
            }
        });
    }
}