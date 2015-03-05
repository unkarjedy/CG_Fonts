package fonteditor.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 28.02.2015.
 */
public class FontProjectsPanel extends JPanel {
    private String projectName = "MyFont";

    private Font currentFont;

    public FontProjectsPanel(){
        super(new BorderLayout());

        JPanel verticalBoxPanel = new JPanel();
        verticalBoxPanel.setLayout(new BoxLayout(verticalBoxPanel, BoxLayout.Y_AXIS));

        JLabel projectNameLabel = new JLabel("Font project");
        projectNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        verticalBoxPanel.add(projectNameLabel);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
        verticalBoxPanel.add(separator);

        add(verticalBoxPanel, BorderLayout.NORTH);

        setPreferredSize(new Dimension(150, 200));
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
