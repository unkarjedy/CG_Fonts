package spbstu.cg.fonteditor.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dima Naumenko on 28.02.2015.
 */
public class ProjectPanelView extends JPanel {
    private String projectName = "";
    private JLabel projectNameLabel;
    private JList list;
    private DefaultListModel<String> listModel;


    public ProjectPanelView(){
        super(new BorderLayout());

        JPanel verticalBoxPanel = new JPanel();
        verticalBoxPanel.setLayout(new BoxLayout(verticalBoxPanel, BoxLayout.Y_AXIS));

        projectNameLabel = new JLabel("");
        projectNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        verticalBoxPanel.add(projectNameLabel);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
        verticalBoxPanel.add(separator);
        add(verticalBoxPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(150, 400));

        list.setEnabled(false);

        verticalBoxPanel.add(listScroller, BorderLayout.NORTH);


        //setPreferredSize(new Dimension(150, 200));
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
        projectNameLabel.setText(projectName);
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public JList getList() {
        return list;
    }


}
