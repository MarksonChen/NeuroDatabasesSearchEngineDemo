package view;

import interface_adapter.query.QueryController;
import interface_adapter.star.StarController;
import interface_adapter.switch_view.SwitchViewController;
import interface_adapter.view_model.DatasetDBSearchViewModel;

import javax.swing.*;

public class DatasetDBSearchView extends SearchView{
    public DatasetDBSearchView(DatasetDBSearchViewModel datasetDBSearchViewModel, SwitchViewController switchViewController, QueryController queryController, StarController starController) {
        this.add(new JLabel("DatasetDBSearchViewModel"));
    }
}
