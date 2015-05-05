package spbstu.cg.fonteditor.view;

import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fontcommons.point.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Dima Naumenko on 28.02.2015.
 */
public class ControlPanelView extends JPanel {
    private static final int SLIVER_DIV = Point.WEIGHT_MAX;
    private JPanel verticalBoxPanel; // main vertical container
    private JPanel pointTypeBox;
    private Map<PointType, JRadioButton> pointTypeButtonMap;
    private JLabel pointWeightLabel;
    private JSlider pointWeightSlider;
    private JCheckBox drawLetterCheckbox;
    private JCheckBox splineTypeCheckbox;

    public JCheckBox getSplineTypeCheckbox() {
        return splineTypeCheckbox;
    }

    public JSlider getPointWeightSlider() {
        return pointWeightSlider;
    }

    public ControlPanelView() {
        super(new BorderLayout());

        createVerticalBoxPanel();
        createPointTypeBox();
        createPointWeightSlider();
        createSplineTypeCheckbox();
        createDrawLetterCheckbox();

        setPreferredSize(new Dimension(220, 200));
    }

    public JCheckBox getDrawLetterCheckbox() {
        return drawLetterCheckbox;
    }

    private void createDrawLetterCheckbox() {
        drawLetterCheckbox = new JCheckBox("Draw letter");
        drawLetterCheckbox.setSelected(false);
        verticalBoxPanel.add(drawLetterCheckbox);
    }

    private void createSplineTypeCheckbox() {
        splineTypeCheckbox = new JCheckBox("Spline is external");
        splineTypeCheckbox.setEnabled(false);
        verticalBoxPanel.add(splineTypeCheckbox);
    }

    private void createVerticalBoxPanel() {
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
        pointTypeButtonMap = new LinkedHashMap<>(5);

        ButtonGroup pointTypeBtnGroup = new ButtonGroup();

        pointTypeBox = new JPanel(new GridLayout(0, 1));
        pointTypeBox.setBorder(BorderFactory.createTitledBorder("Point type"));
        for (PointType type : PointType.values()) {
            if (!type.isControlPointType())
                continue;

            JRadioButton btn = new JRadioButton(type.getName());
            pointTypeButtonMap.put(type, btn);
            pointTypeBtnGroup.add(btn);
            pointTypeBox.add(btn);
        }

        pointTypeBox.setAlignmentX(LEFT_ALIGNMENT);
        setPointTypeBoxVisibility(true);
        enablePointTypesBox(false);

        verticalBoxPanel.add(pointTypeBox);
    }


    /*
     * Note. JSlider does not use floats or doubles.
     * It uses hashtable  for values...
     */
    private void createPointWeightSlider() {
        // create lable
        pointWeightLabel = new JLabel("Point weight");
        pointWeightLabel.setAlignmentX(LEFT_ALIGNMENT);
        verticalBoxPanel.add(pointWeightLabel);

        // create slider
        pointWeightSlider = new JSlider(JSlider.HORIZONTAL);
        pointWeightSlider.setMinimum(SLIVER_DIV * Point.WEIGHT_MIN);
        pointWeightSlider.setMaximum(SLIVER_DIV * Point.WEIGHT_MAX);
        // fill with values
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

        labelTable.put(0, new JLabel(Float.toString(Point.WEIGHT_MIN_PRACTICE)));
        for(int i = 1; i <= SLIVER_DIV; i++){
            Integer labelValue = i * Point.WEIGHT_MAX / SLIVER_DIV;
            labelTable.put(i * Point.WEIGHT_MAX, new JLabel(labelValue.toString()));
        }

        pointWeightSlider.setLabelTable(labelTable);
        pointWeightSlider.setPaintTicks(true);
        pointWeightSlider.setPaintLabels(true);
        pointWeightSlider.setMajorTickSpacing(20);

        pointWeightSlider.setEnabled(false);

        pointWeightSlider.setAlignmentX(LEFT_ALIGNMENT);
        verticalBoxPanel.add(pointWeightSlider);
    }

    public void setSliderWeight(float weight) {
        pointWeightSlider.setValue((int) (weight * SLIVER_DIV));
    }

    public float getSliderWeight(){
        float ret = pointWeightSlider.getValue() / (float)SLIVER_DIV;
        if(ret == 0.0)
            return Point.WEIGHT_MIN_PRACTICE;
        return ret;
    }

    public void enablePointTypesBox(boolean val) {
        pointTypeBox.setEnabled(val);

        for (JRadioButton button : pointTypeButtonMap.values()) {
            button.setEnabled(val);
        }
    }

    public void setPointTypeBoxVisibility(boolean val) {
        pointTypeBox.setVisible(val);
    }

    public void setPointType(PointType type) {
        pointTypeButtonMap.get(type).doClick();
    }

    public Map<PointType, JRadioButton> getPointTypeButtonMap() {
        return pointTypeButtonMap;
    }

    public void enableWeightSlider(boolean b) {
        pointWeightSlider.setEnabled(b);
    }

    public void enableCurrentSplineType(boolean b) {
        splineTypeCheckbox.setEnabled(b);
    }

    public void setSplineType(boolean splineType) {
        splineTypeCheckbox.setSelected(splineType);
    }
}
