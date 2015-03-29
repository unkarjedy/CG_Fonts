package spbstu.cg.fonteditor.controller;

import javafx.scene.control.RadioButton;
import org.omg.CORBA.UNSUPPORTED_POLICY;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fontcommons.point.SymmetricControlPoint;
import spbstu.cg.fonteditor.model.ControlPanelModel;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.ControlPanelView;
import spbstu.cg.fonteditor.view.LetterEditorView;
import spbstu.cg.fonteditor.view.MainFontEditorView;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
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
                }
            });
        }

        controlPanelView.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        controlPanelView.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public void setControlPanelListener(ControlPanelListener controlPanelListener) {
        this.controlPanelListener = controlPanelListener;
    }
}
