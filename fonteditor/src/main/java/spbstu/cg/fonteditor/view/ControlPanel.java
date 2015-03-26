package spbstu.cg.fonteditor.view;

import spbstu.cg.fontcommons.point.CurveControlPoint;
import spbstu.cg.fontcommons.point.Point;
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
public class ControlPanel extends JPanel implements ChangeListener {
    private ButtonGroup pointTypeBtnGroup;
    private Map<Class<? extends Point>, JRadioButton> pointTypeButtonMap;
    private JPanel pointTypeBox;
    public ControlPanel(){
        super(new BorderLayout());

        JPanel verticalBoxPanel = new JPanel();
        verticalBoxPanel.setLayout(new BoxLayout(verticalBoxPanel, BoxLayout.Y_AXIS));

        JLabel panelName = new JLabel("Control panel");
        panelName.setAlignmentX(LEFT_ALIGNMENT);
        //panelName.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        verticalBoxPanel.add(panelName);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
        verticalBoxPanel.add(separator);

        // radio button group box for point type
        pointTypeButtonMap = new LinkedHashMap<Class<? extends Point>, JRadioButton>(5);
        pointTypeBtnGroup = new ButtonGroup();
        pointTypeBox = new JPanel(new GridLayout(0, 1));
        pointTypeBox.setBorder(BorderFactory.createTitledBorder("Point type"));
        for (Class<? extends Point> cl : Consts.pointTypeMap.keySet()) {
            JRadioButton btn = new JRadioButton(Consts.pointTypeMap.get(cl));
            pointTypeButtonMap.put(cl, btn);
            pointTypeBtnGroup.add(btn);
            pointTypeBox.add(btn);
            btn.addChangeListener(this);
            btn.setEnabled(false);
        }
        pointTypeButtonMap.get(CurveControlPoint.class).doClick();
        pointTypeBox.setAlignmentX(LEFT_ALIGNMENT);
        //pointTypeBox.setVisible(true);
        verticalBoxPanel.add(pointTypeBox);
        //pointTypeBox.setVisible(false);
        JLabel ll = new JLabel("DSADSA");
        ll.setAlignmentX(LEFT_ALIGNMENT);
        verticalBoxPanel.add(ll);

        add(verticalBoxPanel, BorderLayout.NORTH);
        setPreferredSize(new Dimension(220, 200));
    }

    public void enablePointTypesBox() {
        pointTypeBox.setEnabled(true);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        e.getSource();
    }
}
