package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fonteditor.model.ControlPanelModel;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.ControlPanelView;
import spbstu.cg.fonteditor.view.MainFontEditorView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Dima Naumenko on 29.03.2015.
 */
public class ControlPanelController extends Controller {
    private MainFontEditorView mainView;
    private ControlPanelModel controlPanelModel;
    private ControlPanelView controlPanelView;
    private ControlPanelListener controlPanelListener;


    public ControlPanelController(MainFontEditorView view, ControlPanelModel model) {
        this.mainView = view;
        controlPanelModel = model;
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
    }

    public void setControlPanelListener(ControlPanelListener controlPanelListener) {
        this.controlPanelListener = controlPanelListener;
    }
}
