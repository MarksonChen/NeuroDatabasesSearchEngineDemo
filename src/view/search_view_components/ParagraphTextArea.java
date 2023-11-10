package view.search_view_components;

import view_model.SearchViewModel;

import javax.swing.*;

public class ParagraphTextArea extends JTextArea {
    public ParagraphTextArea(String text){
        super(text);
        setWrapStyleWord(true);
        setLineWrap(true);
//        setRows(rows);
        setEditable(false);
        setBackground(SearchViewModel.BACKGROUND_COLOR);
    }
}
