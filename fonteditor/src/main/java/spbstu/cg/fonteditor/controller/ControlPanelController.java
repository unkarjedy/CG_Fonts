package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fontcommons.utils.Logger;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.ControlPanelView;
import spbstu.cg.fonteditor.view.MainFontEditorView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dima Naumenko on 29.03.2015.
 */
public class ControlPanelController extends Controller {
    private MainFontEditorView mainView;
    private ControlPanelView controlPanelView;
    private ControlPanelListener controlPanelListener;


    public ControlPanelController(MainFontEditorView view, Logger logger) {
        this.mainView = view;
    }

    public void control() {
        controlPanelView = mainView.getControlPanel();

        for (JRadioButton button : controlPanelView.getPointTypeButtonMap().values()) {
            button.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controlPanelListener.changePointType(PointType.toType(e.getActionCommand()));
                    mainView.repaint();
                }
            });
        }

        controlPanelView.getPointWeightSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

                if (!source.getValueIsAdjusting()) {
                    float weight = controlPanelView.getSliderWeight();
                    controlPanelListener.changePointWeight(weight);
                    mainView.repaint();
                }
            }
        });

        controlPanelView.getSplineTypeCheckbox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox source = (JCheckBox) e.getSource();
                controlPanelListener.changeSplineType(source.isSelected());
                mainView.repaint();
            }
        });

        controlPanelView.getDrawLetterCheckbox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox source = (JCheckBox) e.getSource();
                controlPanelListener.changeDrawLetterMode(source.isSelected());
                mainView.repaint();
            }
        });
    }

    public void setControlPanelListener(ControlPanelListener controlPanelListener) {
        this.controlPanelListener = controlPanelListener;
    }
}
