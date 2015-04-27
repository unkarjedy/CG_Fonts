package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fonteditor.model.action.ActionStack;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.ControlPanelView;
import spbstu.cg.fonteditor.view.MainFontEditorView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;

/**
 * Created by Dima Naumenko on 29.03.2015.
 */
public class ControlPanelController extends Controller {
    private MainFontEditorView mainView;
    private ControlPanelView controlPanelView;
    private ControlPanelListener controlPanelListener;

    private final ActionStack actionStack;


    public ControlPanelController(MainFontEditorView view, ActionStack actionStack) {

        this.mainView = view;
        this.actionStack = actionStack;
    }

    public void control() {
        controlPanelView = mainView.getControlPanel();

        for (JRadioButton button : controlPanelView.getPointTypeButtonMap().values()) {
            button.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controlPanelListener.pointTypeChanged(PointType.toType(e.getActionCommand()));
                    mainView.repaint();
                }
            });
        }

        controlPanelView.getPointWeightSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();

                if (!source.getValueIsAdjusting()) {
                    //float weight = (float)source.getValue();
                    float weight = controlPanelView.getSliderWeight();
                    System.out.println(weight);
                    controlPanelListener.pointWeightChanged(weight);
                    mainView.repaint();
                }
            }
        });

        controlPanelView.getSplineTypeCheckbox().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JCheckBox source = (JCheckBox)e.getSource();
                controlPanelListener.splineTypeChanged(source.isSelected());
                mainView.repaint();
            }
        });

        controlPanelView.getDrawLetterCheckbox().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JCheckBox source = (JCheckBox)e.getSource();
                controlPanelListener.drawLetterChanged(source.isSelected());
                mainView.repaint();
            }
        });
    }

    public void setControlPanelListener(ControlPanelListener controlPanelListener) {
        this.controlPanelListener = controlPanelListener;
    }
}
