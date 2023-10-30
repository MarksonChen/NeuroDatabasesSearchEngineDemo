package interface_adapter.switch_view;

import interface_adapter.view_model.CellularDBSearchViewModel;
import interface_adapter.view_model.DatasetDBSearchViewModel;
import interface_adapter.view_model.FrontPageViewModel;
import interface_adapter.view_model.ViewManagerModel;
import use_case.switch_view.SwitchViewOutputBoundary;

public class SwitchViewPresenter implements SwitchViewOutputBoundary {
    private final ViewManagerModel viewManagerModel;

    public SwitchViewPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void switchToView(String viewName) {
        viewManagerModel.setActiveView(viewName);
        viewManagerModel.firePropertyChanged();
    }
}
