package spbstu.cg.fonteditor.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 28.02.2015.
 */
public class LetterEditorPanel extends JPanel {
    public LetterEditorPanel(){
        super(new BorderLayout());

        JPanel verticalBoxPanel = new JPanel();
        verticalBoxPanel.setLayout(new BoxLayout(verticalBoxPanel, BoxLayout.Y_AXIS));

        JLabel projectNameLabel = new JLabel("Spline Editor");
        projectNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        verticalBoxPanel.add(projectNameLabel);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
        verticalBoxPanel.add(separator);

        add(verticalBoxPanel, BorderLayout.NORTH);

        //setPreferredSize(new Dimension(150, 200));
    }
}