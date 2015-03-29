package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.SymmetricControlPoint;
import spbstu.cg.fonteditor.model.ControlPanelModel;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.LetterEditorView;
import spbstu.cg.fonteditor.view.MainFontEditorView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Dima Naumenko on 29.03.2015.
 */
public class ControlPanelController extends Controller {
    MainFontEditorView mainView;
    ControlPanelModel controlPanelModel;
    private ControlPanelListener controlPanelListener;


    public ControlPanelController(MainFontEditorView view, ControlPanelModel model) {
        this.mainView = view;
        controlPanelModel = model;
    }

    public void control() {
        final LetterEditorView letterEditor = mainView.getLetterEditor();

        letterEditor.addMouseListener(new MouseListener() {
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

        letterEditor.addMouseMotionListener(new MouseMotionListener() {
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
