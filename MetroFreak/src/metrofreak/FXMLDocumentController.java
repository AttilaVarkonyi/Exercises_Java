/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrofreak;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author varco
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    TextArea txtbx;
    @FXML
    StackPane menuPane;
    @FXML
    Pane connectionPane;
    @FXML
    Pane electroPane;
    @FXML
    private Label label;
    @FXML
    Button btnRefresh;
    @FXML
    private NumberAxis axisYSignal;
    @FXML
    private CategoryAxis axisXRate;
    @FXML
    private CategoryAxis axisXSignal;
    @FXML
    private NumberAxis axisYRate;
    @FXML
    private BarChart<?, ?> signalGrafikon;
    @FXML
    private BarChart<?, ?> rateGrafikon;
    
    MessageElements myMe = new MessageElements();
    
    @FXML
    private void refreshData(ActionEvent event){
        refreshCount++;
        RuntimeExec re = new RuntimeExec();
        re.getMyMessage(myMe);
        writeDetails();
        setBarchart();
    }

//<editor-fold defaultstate="collapsed" desc="final MENU Strings">
    private final String MENU_PARAMETERS = "Parameters";
    private final String MENU_CONNECTION = "Connection";
    private final String MENU_ELECTRO = "Electronics";
    private final String MENU_EXIT = "Kilépés";
//</editor-fold>
    
    int refreshCount = 1;
    
    private void setMenuData(){
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);
        
        TreeItem<String> nodeItemA = new TreeItem<>(MENU_PARAMETERS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);
        
        nodeItemA.setExpanded(true);
        
        Node connectionNode = new ImageView(new Image(getClass().getResourceAsStream("/connection.png")));
        Node electroNode = new ImageView(new Image(getClass().getResourceAsStream("/electro.png")));
        
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_CONNECTION, connectionNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_ELECTRO, electroNode);
        
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);
        menuPane.getChildren().add(treeView);
        
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();
                
                if (null != selectedMenu) {
                    
                    switch (selectedMenu) {
                        case MENU_PARAMETERS:
                            try {
                                selectedItem.setExpanded(true);
                            } catch (Exception ex) {
                            }
                            break;
                        case MENU_CONNECTION:
                            connectionPane.setVisible(true);
                            electroPane.setVisible(false);
                            break;
                        case MENU_ELECTRO:
                            connectionPane.setVisible(false);
                            electroPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0); 
                            break;
                    }
                }
            }
        }); 
    }
    
    private void writeDetails(){
        txtbx.appendText("*********************************************" + "\n");
        txtbx.appendText("Connection details " + refreshCount + "\n");
        txtbx.appendText("*********************************************" + "\n");
        
        txtbx.appendText(myMe.name + "\n");
        txtbx.appendText(myMe.description + "\n");
        txtbx.appendText(myMe.guid + "\n");
        txtbx.appendText(myMe.physicalAddress + "\n");
        txtbx.appendText(myMe.state + "\n");
        txtbx.appendText(myMe.ssid + "\n");
        txtbx.appendText(myMe.bssid + "\n");
        txtbx.appendText(myMe.networkType + "\n");
        txtbx.appendText(myMe.radioType + "\n");
        txtbx.appendText(myMe.authentification + "\n");
        txtbx.appendText(myMe.cipher + "\n");
        txtbx.appendText(myMe.channel + "\n");
        txtbx.appendText(myMe.receiveRateMbps + "\n");
        txtbx.appendText(myMe.transmitRateMbps + "\n");
        txtbx.appendText(myMe.signal + "\n");
        txtbx.appendText(myMe.profile + "\n");
    }
    
    private void setBarchart(){
        String signalPercent = myMe.signal;
        String signal = signalPercent.substring(0, signalPercent.length()-1);
        int iSignal = Integer.parseInt(signal);
        
        XYChart.Series set1 = new XYChart.Series<>();
        set1.setName("Connection " + refreshCount);
        set1.getData().add(new XYChart.Data("Connection", iSignal));
        signalGrafikon.getData().addAll(set1);
        
        String receive = myMe.receiveRateMbps;
        String transmit = myMe.transmitRateMbps;
        float iReceive = Float.parseFloat(receive);
        float iTransmit = Float.parseFloat(transmit);
        
        XYChart.Series set2 = new XYChart.Series<>();
        XYChart.Series set3 = new XYChart.Series<>();
        set2.setName("Receive " + refreshCount);
        set3.setName("Transmit " + refreshCount);
        set2.getData().add(new XYChart.Data("Receive", iReceive));
        set3.getData().add(new XYChart.Data("Transfer", iTransmit));
        rateGrafikon.getData().addAll(set2, set3);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMenuData();
        RuntimeExec re = new RuntimeExec();
        re.getMyMessage(myMe);
        writeDetails();
        setBarchart();
    }    
    
}
