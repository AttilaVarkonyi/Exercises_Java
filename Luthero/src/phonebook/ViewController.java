package phonebook;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ViewController implements Initializable {
    
//<editor-fold defaultstate="collapsed" desc="@FXML imports">
    @FXML
            TableView table;
    @FXML
            TextField inputLastname;
    @FXML
            TextField inputFirstname;
    @FXML
            TextField inputEmail;
    @FXML
            Button addNewContactButton;
    @FXML
            StackPane menuPane;
    @FXML
            Pane contactPane;
    @FXML
            Pane exportPane;
    @FXML
            TextField inputExportName;
    @FXML
            Button exportButton;
//</editor-fold>
    
    DB db = new DB();
    
    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_WEBSERVICE = "Webszolgáltatások";
    private final String MENU_WEBPAGE = "Weblap";
    private final String MENU_WEBSERVER = "Webserver";
    private final String MENU_EXIT = "Kilépés";
    
    private final ObservableList<Person> data = FXCollections.observableArrayList();
    
    @FXML
    private void addContact(ActionEvent event) {
        String email = inputEmail.getText();
        if (email.length() > 3 && email.contains("@") && email.contains(".")) {
            Person newPerson = new Person(inputFirstname.getText(),inputLastname.getText(),inputEmail.getText());
            data.add(newPerson);
            db.addContact(newPerson);
            inputLastname.clear();
            inputFirstname.clear();
            inputEmail.clear();
        }
    }
    
    @FXML
    private void exportList(ActionEvent event) {
        String fileName = inputExportName.getText();
        fileName = fileName.replaceAll("\\s+","");
        if (fileName != null && !fileName.equals("")) {
            PdfGeneration pdfCreator = new PdfGeneration();
            pdfCreator.pdfGeneration(fileName, data);
        }
        
    }        
    
    public void setTableData() {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        
        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) {
                        Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualPerson.setLastName(t.getNewValue());
                        db.updateContact(actualPerson);
                    }
                }
        );
        
        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        
        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) {
                        Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualPerson.setFirstName(t.getNewValue());
                        db.updateContact(actualPerson);                        
                    }
                }
        );
        
        TableColumn emailCol = new TableColumn("Email cím");
        emailCol.setMinWidth(100);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        
        emailCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) {
                        Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualPerson.setEmail(t.getNewValue());
                        db.updateContact(actualPerson);
                    }
                }
        );
        
        table.getColumns().addAll(lastNameCol, firstNameCol, emailCol);
        
        data.addAll(db.getAllContacts());
        
        table.setItems(data);
    }
    
    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);
        
        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);
        TreeItem<String> nodeItemC = new TreeItem<>(MENU_WEBSERVICE);
        
        nodeItemA.setExpanded(true);
        nodeItemC.setExpanded(true);
        
        Node contactsNode = new ImageView(new Image(getClass().getResourceAsStream("/contact.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        Node webNode = new ImageView(new Image(getClass().getResourceAsStream("/web.png")));
        Node serverNode = new ImageView(new Image(getClass().getResourceAsStream("/server.png")));
        
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST, contactsNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);
        TreeItem<String> nodeItemC1 = new TreeItem<>(MENU_WEBPAGE, webNode);
        TreeItem<String> nodeItemC2 = new TreeItem<>(MENU_WEBSERVER, serverNode);
        
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        nodeItemC.getChildren().addAll(nodeItemC1, nodeItemC2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemC, nodeItemB);
        menuPane.getChildren().add(treeView);
        
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();
                
                if (null != selectedMenu) {
                    
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            try {
                                selectedItem.setExpanded(true);
                            } catch (Exception ex) {
                            }
                            break;
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_WEBPAGE:
                            try {
                                Desktop.getDesktop().browse(new URI("https://dunaujvaros.lutheran.hu/"));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (URISyntaxException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case MENU_WEBSERVER:
                            try {
                                Desktop.getDesktop().browse(new URI("https://zope.lutheran.hu/honlapok/dunaujvaros/manage"));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (URISyntaxException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case MENU_EXIT:
                            System.exit(0); 
                            break;
                    }
                }
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();                
    }    



    
    
}
