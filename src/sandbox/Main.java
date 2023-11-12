//package sandbox;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import com.formdev.flatlaf.FlatDarculaLaf;
//import okhttp3.*;
//import org.json.JSONArray;
//
//public class Main {
//    private JPanel panel;
//    private JFrame frame;
//    private JTextField searchField;
//    private JButton searchButton;
//    private JTextArea resultList;
//    private JScrollPane scrollPane;
//
//    public static void main(String[] args) {
//        new Main();
//    }
//
//    public Main(){
//        initializeUI();
//    }
//
//    public void initializeUI(){
//        FlatDarculaLaf.setup(); // Modern Swing UI: See https://www.formdev.com/flatlaf/
//
//        frame = new JFrame("Neuroscience Databases Search App Demo");
//
//        searchField = new JTextField(20);
//        searchButton = new JButton("Search");
//        resultList = new JTextArea(10, 30);
//        resultList.setEditable(false);
//
//        scrollPane = new JScrollPane(resultList);
//
//        frame.setLayout(new GridBagLayout());
//        // constraints() is a helper method defined for setting the parameters for GridBagLayout
//        frame.add(searchField, constraints(0, 0, 1, 0, 1, 1,
//                GridBagConstraints.HORIZONTAL, 10, 10, 10, 10));
//        frame.add(searchButton, constraints(1, 0, 0, 0, 1, 1,
//                GridBagConstraints.NONE, 0, 0, 0, 10));
//        frame.add(scrollPane, constraints(0, 1, 1, 1, 2, 1,
//                GridBagConstraints.BOTH, 0, 10, 10, 10));
//
//        searchButton.addActionListener(e -> performSearch(searchField.getText()));
//        searchField.addActionListener(e -> performSearch(searchField.getText()));   //Triggered by pressing "Enter"
//
//        frame.setSize(1000, 650);
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
//
//    private GridBagConstraints constraints(int x, int y, double weightx, double weighty,
//                                           int gridwidth, int gridheight, int fill,
//                                           int insetTop, int insetLeft, int insetBottom, int insetRight) {
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = x;
//        gbc.gridy = y;
//        gbc.weightx = weightx;
//        gbc.weighty = weighty;
//        gbc.fill = fill;
//        gbc.gridwidth = gridwidth;
//        gbc.gridheight = gridheight;
//        gbc.insets = new Insets(insetTop,insetLeft,insetBottom,insetRight);
//        return gbc;
//    }
//
//    private void performSearch(String query) {
//        try {
//            ArrayList<String> descriptions = searchAndGetDescriptions(query);
//            // Ideally one should define a data class and return a list of data instances
//            // Here for simplicity, only the descriptions from the search results are returned
//            resultList.setText("");
//            for(String text: descriptions){
//                resultList.append(text + "\n");
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(frame, "HTTP Request failed");
//        }
//    }
//
//    private ArrayList<String> searchAndGetDescriptions(String query) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        Request request = new Request.Builder()
//                .url(String.format("https://neuroml-db.org/api/search?q=%s", query))
//                .build();
//
//        Response response = client.newCall(request).execute();
//        JSONArray responseArray = new JSONArray(response.body().string());
//        ArrayList<String> descriptions = new ArrayList<>();
//        for (int i = 0; i < responseArray.length(); i++) {
//            descriptions.add(responseArray.getJSONObject(i).getString("Name"));
//        }
//        return descriptions;
//    }
//
//}
