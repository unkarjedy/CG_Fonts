package spbstu.cg.fonteditor.view;

import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fonteditor.Consts;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 28.02.2015.
 */
public class ControlPanelView extends JPanel implements ChangeListener {
    private JPanel verticalBoxPanel; // main vertical container
    private JPanel pointTypeBox;
    private ButtonGroup pointTypeBtnGroup;
    private Map<PointType, JRadioButton> pointTypeButtonMap;

    public ControlPanelView(){
        super(new BorderLayout());

        createVerticaloxPanel();
        createPointTypeBox();


        JLabel tmpLabel = new JLabel("ABCD");
        tmpLabel.setAlignmentX(LEFT_ALIGNMENT);
        verticalBoxPanel.add(tmpLabel);

        setPreferredSize(new Dimension(220, 200));
    }

    private void createVerticaloxPanel() {
        verticalBoxPanel = new JPanel();
        verticalBoxPanel.setLayout(new BoxLayout(verticalBoxPanel, BoxLayout.Y_AXIS));

        JLabel panelName = new JLabel("Control panel");
        panelName.setAlignmentX(LEFT_ALIGNMENT);
        //panelName.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        verticalBoxPanel.add(panelName);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
        verticalBoxPanel.add(separator);

        add(verticalBoxPanel, BorderLayout.NORTH);
    }

    private void createPointTypeBox() {
        pointTypeButtonMap = new LinkedHashMap<PointType, JRadioButton>(5);

        pointTypeBtnGroup = new ButtonGroup();

        pointTypeBox = new JPanel(new GridLayout(0, 1));
        pointTypeBox.setBorder(BorderFactory.createTitledBorder("Point type"));
        for (PointType type : Consts.pointTypeNamesMap.keySet()) {
            JRadioButton btn = new JRadioButton(Consts.pointTypeNamesMap.get(type));
            pointTypeButtonMap.put(type, btn);
            pointTypeBtnGroup.add(btn);
            pointTypeBox.add(btn);
            btn.addChangeListener(this);
            // btn.setEnabled(false);
        }
        pointTypeButtonMap.get(PointType.CORNER).doClick();
        pointTypeBox.setAlignmentX(LEFT_ALIGNMENT);
        pointTypeBox.setVisible(true);
        verticalBoxPanel.add(pointTypeBox);
        //pointTypeBox.setVisible(false);

    }


    public void enablePointTypesBox(boolean val) {
        pointTypeBox.setEnabled(val);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        e.getSource();
    }
}
