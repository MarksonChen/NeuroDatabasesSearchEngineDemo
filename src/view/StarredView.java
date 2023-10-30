package view;

import interface_adapter.star.StarController;
import interface_adapter.view_model.StarredViewModel;
import use_case.star.StarDataAccessInterface;

import javax.swing.*;
import java.awt.*;

public class StarredView extends JFrame {
    public StarredView(StarredViewModel starredViewModel, StarDataAccessInterface starDataAccessObject, StarController starController) {
        add(new JLabel("Starred Results"));
        setPreferredSize(new Dimension(890, 650));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
}
