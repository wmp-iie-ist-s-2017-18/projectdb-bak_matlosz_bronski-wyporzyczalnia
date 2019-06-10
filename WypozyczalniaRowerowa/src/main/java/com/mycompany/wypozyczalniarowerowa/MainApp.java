package com.mycompany.wypozyczalniarowerowa;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class MainApp extends Application {
    String buttonStyle = "-fx-padding: 10px; -fx-border-insets: 5px; -fx-background-insets: 5px;";
    String bgcolor = "-fx-background-color: #f0f0f0";
    String style = "-fx-font-weight:normal; -fx-color: #f0f0f0; -fx-font-size:11; -fx-font-family: Verdana;";
    String redborderStyle = "-fx-text-box-border: red; -fx-focus-color: red; -fx-inner-border: red;";
    String centerLeft = "-fx-alignment: CENTER-LEFT;";
    String centerRight = "-fx-alignment: CENTER-RIGHT;";
            
    private final Image imageBike = new Image("bike64x64.png");
    private final Image imageAdd = new Image("add16x16.png");
    private final Image imageRemove = new Image("remove16x16.png");
    private final Image imageEdit = new Image("edit16x16.png");
    private final Image imageSave = new Image("save16x16.png");        
    private final Image imageBack = new Image("back16x16.png");   
        
    private final TableView<Klient> kliencitableview = new TableView<>();
    private ObservableList<Klient> kliencilist = FXCollections.observableArrayList();
    private final ZarzadzanieKlientami zarzadzanieKlientami = new ZarzadzanieKlientami();
        
    private final TableView<Rower> rowerytableview = new TableView<>();
    private ObservableList<Rower> rowerylist = FXCollections.observableArrayList();
    private final ZarzadzanieRowerami zarzadzanieRowerami = new ZarzadzanieRowerami();
        
    private final TableView<Wypozyczenie> wypozyczeniatableview = new TableView<>();
    private ObservableList<Wypozyczenie> wypozyczenialist = FXCollections.observableArrayList();
    private final ZarzadzanieWypozyczeniami zarzadzanieWypozyczeniami = new ZarzadzanieWypozyczeniami();
        
    private Stage primaryStage = null;
    private TabPane tabPane = null;
    
     /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.getIcons().add(imageBike);
         
        Group root = new Group();
        Scene scene = new Scene(root);
        tabPane = new TabPane();
        
        Tab wypozyczeniatab = new Tab();
        wypozyczeniatab.setText("Wypożyczenia");
        wypozyczeniatab.setClosable(false);
        BorderPane wypozyczeniaborderpane = new BorderPane();
        Button dodajwypozyczeniebutton = new Button("Dodaj wypożyczenie", new ImageView(imageAdd));
        dodajwypozyczeniebutton.setStyle(buttonStyle);
        dodajwypozyczeniebutton.setOnAction(event -> {
            //bez parametrów ponieważ klient ani rower nie został wybrany z listy
            dodajWypozyczenieOkno(null, null);
        });
        wypozyczeniaborderpane.setTop(dodajwypozyczeniebutton);
        wypozyczeniaborderpane.setCenter(wypozyczeniatableview);
        wypozyczeniaborderpane.setStyle(bgcolor);
        wypozyczeniatab.setContent(wypozyczeniaborderpane);
        tabPane.getTabs().add(wypozyczeniatab);
        aktualizujListeWypozyczen();
        
        Tab rowerytab = new Tab();
        rowerytab.setText("Rowery");
        rowerytab.setClosable(false);
        BorderPane roweryborderpane = new BorderPane();
        Button dodajrowerbutton = new Button("Dodaj rower", new ImageView(imageAdd));
        dodajrowerbutton.setOnAction(event -> {
            dodajRowerOkno();
        });
        dodajrowerbutton.setStyle(buttonStyle);
        roweryborderpane.setTop(dodajrowerbutton);
        roweryborderpane.setCenter(rowerytableview);
        roweryborderpane.setStyle(bgcolor);
        rowerytab.setContent(roweryborderpane);
        tabPane.getTabs().add(rowerytab);
        aktualizujListeRowerow();        
        
        Tab kliencitab = new Tab();
        kliencitab.setText("Klienci");
        kliencitab.setClosable(false);
        BorderPane klienciborderpane = new BorderPane();
        Button dodajklientabutton = new Button("Dodaj klienta", new ImageView(imageAdd));
        dodajklientabutton.setStyle(buttonStyle);
        dodajklientabutton.setOnAction(event -> { 
            dodajKlientaOkno();
        });
        klienciborderpane.setTop(dodajklientabutton);
        klienciborderpane.setCenter(kliencitableview);
        klienciborderpane.setStyle(bgcolor);
        kliencitab.setContent(klienciborderpane);
        tabPane.getTabs().add(kliencitab);
        aktualizujListeKlientow();       
                
        BorderPane borderpane = new BorderPane();
        borderpane.prefHeightProperty().bind(scene.heightProperty());
        borderpane.prefWidthProperty().bind(scene.widthProperty());
        borderpane.setCenter(tabPane);
        root.getChildren().add(borderpane);
        
        stage.setTitle("Wypożyczalnia Rowerowa");
        stage.setScene(scene);
        stage.show();
    }

    private void aktualizujListeKlientow() {
        kliencitableview.getItems().clear();
        kliencitableview.setStyle(style);
        kliencitableview.setItems(pobierzKlientow());        
        
        TableColumn<Klient,Integer> idkolumn = new TableColumn<Klient, Integer>("Id");
        idkolumn.setCellValueFactory(new PropertyValueFactory<Klient, Integer>("id"));
        idkolumn.setStyle(centerRight);
         
        TableColumn<Klient,Integer> imiekolumn = new TableColumn<Klient, Integer>("Imie");
        imiekolumn.setCellValueFactory(new PropertyValueFactory<Klient, Integer>("imie"));
        imiekolumn.setStyle(centerLeft);
        
        TableColumn<Klient,Integer> nazwiskokolumn = new TableColumn<Klient, Integer>("Nazwisko");
        nazwiskokolumn.setCellValueFactory(new PropertyValueFactory<Klient, Integer>("nazwisko"));
        nazwiskokolumn.setStyle(centerLeft);
        
        TableColumn<Klient,Integer> nrdokumentukolumn = new TableColumn<Klient, Integer>("Nr dokumentu");
        nrdokumentukolumn.setCellValueFactory(new PropertyValueFactory<Klient, Integer>("nrdokumentu"));
        nrdokumentukolumn.setStyle(centerLeft);
        
        TableColumn<Klient,Integer> telefonkolumn = new TableColumn<Klient, Integer>("Nr telefonu");
        telefonkolumn.setCellValueFactory(new PropertyValueFactory<Klient, Integer>("nrtelefonu"));
        telefonkolumn.setStyle(centerLeft);
        
        TableColumn usunkolumn = new TableColumn();
        usunkolumn.setCellValueFactory(new PropertyValueFactory<>("Remove"));
        
        Callback<TableColumn<Klient, String>, TableCell<Klient, String>> usunCellFactory;
        usunCellFactory = (final TableColumn<Klient, String> param) -> {
            final TableCell<Klient, String> cell = new TableCell<Klient, String>() {
                final Button button = new Button("Usuń", new ImageView(imageRemove));
                
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.setOnAction(event -> {
                            Klient klient = getTableView().getItems().get(getIndex());
                            usunKlientaOkno(klient);
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        usunkolumn.setCellFactory(usunCellFactory);
        
        TableColumn edytujkolumn = new TableColumn();
        edytujkolumn.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        
        Callback<TableColumn<Klient, String>, TableCell<Klient, String>> edytujCellFactory;
        edytujCellFactory = (final TableColumn<Klient, String> param) -> {
            final TableCell<Klient, String> cell = new TableCell<Klient, String>() {
                final Button btn = new Button("Edytuj", new ImageView(imageEdit));
                        
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            Klient klient = getTableView().getItems().get(getIndex());
                            edytujKlientaOkno(klient);
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        edytujkolumn.setCellFactory(edytujCellFactory);
        
        TableColumn wypozyczkolumn = new TableColumn();
        wypozyczkolumn.setCellValueFactory(new PropertyValueFactory<>("Wypozycz"));
        
        Callback<TableColumn<Klient, String>, TableCell<Klient, String>> wypozyczCellFactory;
        wypozyczCellFactory = (final TableColumn<Klient, String> param) -> {
            final TableCell<Klient, String> cell = new TableCell<Klient, String>() {
                final Button btn = new Button("Wypożyczenie", new ImageView(imageAdd));
                        
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            Klient klient = getTableView().getItems().get(getIndex());
                            //tworzenie wypożyczenia z zakadki klienci - rower nie został wybrany
                            dodajWypozyczenieOkno(klient, null);
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        wypozyczkolumn.setCellFactory(wypozyczCellFactory);
        
        kliencitableview.getColumns().setAll(idkolumn, imiekolumn, nazwiskokolumn, nrdokumentukolumn, telefonkolumn, usunkolumn, edytujkolumn, wypozyczkolumn);        
    }
    
    private void aktualizujListeRowerow() {
        rowerytableview.getItems().clear();
        rowerytableview.setStyle(style);
        rowerytableview.setItems(pobierzRowery());        
        
        TableColumn<Rower,Integer> idkolumn = new TableColumn<Rower, Integer>("Id");
        idkolumn.setCellValueFactory(new PropertyValueFactory<Rower, Integer>("id"));
        idkolumn.setStyle(centerRight);
        
        TableColumn<Rower,Integer> modelkolumn = new TableColumn<Rower, Integer>("Model");
        modelkolumn.setCellValueFactory(new PropertyValueFactory<Rower, Integer>("model"));
        modelkolumn.setStyle(centerLeft);
        
        TableColumn<Rower,Integer> stawkakolumn = new TableColumn<Rower, Integer>("Stawka");
        stawkakolumn.setCellValueFactory(new PropertyValueFactory<Rower, Integer>("stawka"));
        stawkakolumn.setStyle(centerRight);
        
        TableColumn usunkolumn = new TableColumn();
        usunkolumn.setCellValueFactory(new PropertyValueFactory<>("Remove"));
        
        Callback<TableColumn<Rower, String>, TableCell<Rower, String>> usunCellFactory;
        usunCellFactory = (final TableColumn<Rower, String> param) -> {
            final TableCell<Rower, String> cell = new TableCell<Rower, String>() {
                final Button button = new Button("Usuń", new ImageView(imageRemove));
                
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.setOnAction(event -> {
                            Rower rower = getTableView().getItems().get(getIndex());
                            usunRowerOkno(rower);
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        usunkolumn.setCellFactory(usunCellFactory);
        
        TableColumn edytujkolumn = new TableColumn();
        edytujkolumn.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        
        Callback<TableColumn<Rower, String>, TableCell<Rower, String>> edytujCellFactory;
        edytujCellFactory = (final TableColumn<Rower, String> param) -> {
            final TableCell<Rower, String> cell = new TableCell<Rower, String>() {
                final Button btn = new Button("Edytuj", new ImageView(imageEdit));
                        
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            Rower rower = getTableView().getItems().get(getIndex());
                            edytujRowerOkno(rower);
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        edytujkolumn.setCellFactory(edytujCellFactory);
        
        TableColumn wypozyczkolumn = new TableColumn();
        wypozyczkolumn.setCellValueFactory(new PropertyValueFactory<>("Wypozycz"));
        
        Callback<TableColumn<Rower, String>, TableCell<Rower, String>> wypozyczCellFactory;
        wypozyczCellFactory = (final TableColumn<Rower, String> param) -> {
            final TableCell<Rower, String> cell = new TableCell<Rower, String>() {
                final Button btn = new Button("Wypożyczenie", new ImageView(imageAdd));
                        
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            Rower rower = getTableView().getItems().get(getIndex());
                            //tworzenie wypożyczenia z zakadki rowery - klient nie został wybrany
                            dodajWypozyczenieOkno(null, rower);
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        wypozyczkolumn.setCellFactory(wypozyczCellFactory);
                
        rowerytableview.getColumns().setAll(idkolumn, modelkolumn, stawkakolumn, usunkolumn, edytujkolumn, wypozyczkolumn);        
    }
    
    private void aktualizujListeWypozyczen() {
        wypozyczeniatableview.getItems().clear();
        wypozyczeniatableview.setStyle(style);
        wypozyczeniatableview.setItems(pobierzWypozyczenia());        
        
        TableColumn<Wypozyczenie,Integer> idkolumn = new TableColumn<Wypozyczenie, Integer>("Id");
        idkolumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, Integer>("id"));       
        idkolumn.setStyle(centerRight);

        TableColumn<Wypozyczenie, String> klientkolumn = new TableColumn<Wypozyczenie, String>("Klient");
        klientkolumn.setCellValueFactory((TableColumn.CellDataFeatures<Wypozyczenie, String> param) -> 
            new SimpleObjectProperty<>(param.getValue().getKlient()));
        klientkolumn.setStyle(centerLeft);
        
        TableColumn<Wypozyczenie, String> rowerkolumn = new TableColumn<Wypozyczenie, String>("Rower");
        rowerkolumn.setCellValueFactory((TableColumn.CellDataFeatures<Wypozyczenie, String> param) -> 
            new SimpleObjectProperty<>(param.getValue().getRower()));
        rowerkolumn.setStyle(centerLeft);
        
        TableColumn<Wypozyczenie,Date> wypozyczenieodkolumn = new TableColumn<Wypozyczenie, Date>("Wypożyczenie od");
        wypozyczenieodkolumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, Date>("wypozyczenieod"));
        wypozyczenieodkolumn.setStyle(centerLeft);
        
        TableColumn<Wypozyczenie,Date> wypozyczeniedokolumn = new TableColumn<Wypozyczenie, Date>("Wypożyczenie do");
        wypozyczeniedokolumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, Date>("wypozyczeniedo"));
        wypozyczeniedokolumn.setStyle(centerLeft);
        
        TableColumn<Wypozyczenie,Integer> kosztwypozyczeniakolumn = new TableColumn<Wypozyczenie, Integer>("Opłata");
        kosztwypozyczeniakolumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, Integer>("kosztwypozyczenia")); 
        kosztwypozyczeniakolumn.setStyle(centerRight);
        
        TableColumn usunkolumn = new TableColumn();
        usunkolumn.setCellValueFactory(new PropertyValueFactory<>("Remove"));
        
        Callback<TableColumn<Wypozyczenie, String>, TableCell<Wypozyczenie, String>> usunCellFactory;
        usunCellFactory = (final TableColumn<Wypozyczenie, String> param) -> {
            final TableCell<Wypozyczenie, String> cell = new TableCell<Wypozyczenie, String>() {
                final Button button = new Button("Usuń", new ImageView(imageRemove));
                
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.setOnAction(event -> {
                            Wypozyczenie wypozyczenie = getTableView().getItems().get(getIndex());
                            usunWypozyczenieOkno(wypozyczenie);
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        usunkolumn.setCellFactory(usunCellFactory);
        
        TableColumn zwrotkolumn = new TableColumn();
        zwrotkolumn.setCellValueFactory(new PropertyValueFactory<>("Zwrot"));
        
        Callback<TableColumn<Wypozyczenie, String>, TableCell<Wypozyczenie, String>> zwrotCellFactory;
        zwrotCellFactory = (final TableColumn<Wypozyczenie, String> param) -> {
            final TableCell<Wypozyczenie, String> cell = new TableCell<Wypozyczenie, String>() {
                final Button btn = new Button("Zwrot", new ImageView(imageBack));
                
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        btn.setVisible(false);
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setVisible(false);
                        Wypozyczenie wypozyczenie = getTableView().getItems().get(getIndex());
                        //przycisk zwrot widoczny tylko gdy wypozyczenie jest otwarte - nie zwrócono roweru i nie naliczono kosztów
                        if (wypozyczenie.getKosztwypozyczenia() == null) {
                            btn.setVisible(true);
                            btn.setOnAction(event -> {
                                Date wypozyczenieDo = new Date();
                                //pobranie liczby pełnych godzin pomiędzy data wypożyczenia a zwrotu
                                Integer hours = getFullHours(wypozyczenie.getWypozyczenieod(), wypozyczenieDo);
                                Rower rower = zarzadzanieRowerami.pobierzRower(wypozyczenie.getRowerId());
                                Integer stawka = rower.getStawka();
                                //wyliczenie opłaty jako ilość godzin * stawka
                                Integer oplata = hours * stawka;                                 
                                Wypozyczenie zaktualizowaneWypozyczenie = new Wypozyczenie(wypozyczenie.getId(), wypozyczenie.getKlientId(), wypozyczenie.getRowerId(), wypozyczenie.getKlient(), wypozyczenie.getRower(), wypozyczenie.getWypozyczenieod(), wypozyczenieDo, oplata, false);
                                zarzadzanieWypozyczeniami.aktualizujWypozyczenie(zaktualizowaneWypozyczenie);
                                aktualizujListeWypozyczen();
                                zwrotInfoOkno(zaktualizowaneWypozyczenie);
                            });
                        }
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        zwrotkolumn.setCellFactory(zwrotCellFactory);
        
        wypozyczeniatableview.getColumns().setAll(idkolumn, klientkolumn, rowerkolumn, wypozyczenieodkolumn, wypozyczeniedokolumn, kosztwypozyczeniakolumn, usunkolumn, zwrotkolumn);
    }
    
    private ObservableList<Klient> pobierzKlientow() {
        if(!kliencilist.isEmpty()) {
        kliencilist.clear();
    }
        List<Klient> lista = zarzadzanieKlientami.pobierzKlientow();
        kliencilist = FXCollections.observableList(lista);
        return kliencilist;
    }
    
    private ObservableList<Rower> pobierzRowery() {
        if(!rowerylist.isEmpty()) {
        rowerylist.clear();
    }
        List<Rower> lista = zarzadzanieRowerami.pobierzRowery();
        rowerylist = FXCollections.observableList(lista);
        return rowerylist;
    }
    
    private ObservableList<Wypozyczenie> pobierzWypozyczenia() {
        if(!wypozyczenialist.isEmpty()) {
        wypozyczenialist.clear();
    }
        List<Wypozyczenie> lista = zarzadzanieWypozyczeniami.pobierzWypozyczenia();
        wypozyczenialist = FXCollections.observableList(lista);
        return wypozyczenialist;
    }
    
    private void dodajKlientaOkno() {
        Stage newWindow = new Stage();
        newWindow.setTitle("Dodaj nowego klienta");
        newWindow.getIcons().add(imageAdd);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        Label imielabel = new Label("Imię:");
        grid.add(imielabel, 0, 1);
        TextField imietextfield = new TextField();
        grid.add(imietextfield, 1, 1);
        
        Label nazwiskolabel = new Label("Nazwisko:");
        grid.add(nazwiskolabel, 0, 2);
        TextField nazwiskotextfield = new TextField();
        grid.add(nazwiskotextfield, 1, 2);
        
        Label nrdokumentulabel = new Label("Nr dokumentu:");
        grid.add(nrdokumentulabel, 0, 3);
        TextField nrdokumentutextfield = new TextField();
        grid.add(nrdokumentutextfield, 1, 3);
        
        Label nrtelefonulabel = new Label("Telefon:");
        grid.add(nrtelefonulabel, 0, 4);
        TextField nrtelefonutextfield = new TextField();
        nrtelefonutextfield.setText("+48 ");
        grid.add(nrtelefonutextfield, 1, 4);
        
        Label errorlabel = new Label("Uzupełnij wymagane dane!");
        errorlabel.setTextFill(Color.RED);
        errorlabel.setVisible(false);
        Button zapisznowegoklientabutton = new Button("Zapisz", new ImageView(imageSave));
        
        zapisznowegoklientabutton.setOnAction(event ->{
            System.out.println("Zapisuję dane klienta");
            String imie = imietextfield.getText();
            String nazwisko = nazwiskotextfield.getText();
            String nrdokumentu = nrdokumentutextfield.getText();
            String nrtelefonu = nrtelefonutextfield.getText();
            Boolean isValid = true;
            if(isEmpty(imie)) {
                isValid = false;
                imietextfield.setStyle(redborderStyle);
            }
            else {
                imietextfield.setStyle("");
            }
            
            if(isEmpty(nazwisko)) {
                isValid = false;
                nazwiskotextfield.setStyle(redborderStyle);
            }
            else {
                nazwiskotextfield.setStyle("");
            }
            
            if(isEmpty(nrdokumentu)) {
                isValid = false;
                nrdokumentutextfield.setStyle(redborderStyle);
            }
            else {
                nrdokumentutextfield.setStyle("");
            }
            
            if(isEmpty(nrtelefonu)) {
                isValid = false;
                nrtelefonutextfield.setStyle(redborderStyle);
            }
            else {
                nrtelefonutextfield.setStyle("");
            }
            
            if(isValid) {
                errorlabel.setVisible(false);
                zarzadzanieKlientami.dodajKlienta(imie, nazwisko, nrdokumentu, nrtelefonu);
                aktualizujListeKlientow();
                newWindow.close();
            }
            else{
                errorlabel.setVisible(true);
            }
        });
        grid.add(zapisznowegoklientabutton, 1, 5);       
        
        grid.add(errorlabel, 0, 6);
        grid.setColumnSpan(errorlabel, 2);
        Scene scene = new Scene(grid, 350, 280);
        newWindow.setScene(scene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);
        newWindow.setResizable(false);
        newWindow.show();
    }
    
    private void usunKlientaOkno(Klient klient) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(imageRemove);

        alert.setTitle("Usuwanie klienta");
        
        Button anulujButton = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL );
        anulujButton.setText("Anuluj");
        
        alert.setHeaderText(klient.getImie() + " " + klient.getNazwisko());
        alert.setContentText("Czy na pewno chcesz usunąć klienta?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        	 System.out.println("Usuwanie: " + klient.getImie() + " " + klient.getNazwisko());
        	 zarzadzanieKlientami.usunKlienta(klient.getId());
        	 aktualizujListeKlientow();
        }
    }
    
    private void edytujKlientaOkno(Klient klient) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Edytuj dane klienta");                 
        newWindow.getIcons().add(imageEdit);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label imieLabel = new Label("Imię:");
        grid.add(imieLabel, 0, 1);
        TextField imieTextField = new TextField();
        imieTextField.setText(klient.getImie());
        grid.add(imieTextField, 1, 1);

        Label nazwiskoLabel = new Label("Nazwisko:");
        grid.add(nazwiskoLabel, 0, 2);
        TextField nazwiskoTextField = new TextField();
        nazwiskoTextField.setText(klient.getNazwisko());
        grid.add(nazwiskoTextField, 1, 2);

        Label nrDokumentuLabel = new Label("Nr Dokumentu:");
        grid.add(nrDokumentuLabel, 0, 3);
        TextField nrDokumentuTextField = new TextField();
        nrDokumentuTextField.setText(klient.getNrdokumentu());
        grid.add(nrDokumentuTextField, 1, 3);

        Label telefonLabel = new Label("Telefon:");
        grid.add(telefonLabel, 0, 4);
        TextField telefonTextField = new TextField();
        telefonTextField.setText(klient.getNrtelefonu());					            	
        grid.add(telefonTextField, 1, 4);

        Label errorLabel = new Label("Uzupełnij wymagane dane!");
        Button zapiszNowegoKlientaButton = new Button("Zapisz", new ImageView(imageSave));
        zapiszNowegoKlientaButton.setOnAction(event -> {
            System.out.println("zapisuje dane klienta");
            String imie = imieTextField.getText();
            String nazwisko = nazwiskoTextField.getText();
            String nrDokumentu = nrDokumentuTextField.getText();
            String telefon = telefonTextField.getText();
            Boolean isValid = true;
            if (MainApp.isEmpty(imie)) {
                imieTextField.setStyle(redborderStyle);
                isValid = false;
            }
                    else {
                            imieTextField.setStyle("");
                    }
                    if (MainApp.isEmpty(nazwisko)) {
                            nazwiskoTextField.setStyle(redborderStyle);
                            isValid = false;
                    }
                    else {
                            nazwiskoTextField.setStyle("");
                    }
                    if (MainApp.isEmpty(nrDokumentu)) {
                            nrDokumentuTextField.setStyle(redborderStyle);
                            isValid = false;
                    }
                    else {
                            nrDokumentuTextField.setStyle("");
                    }
                    if (MainApp.isEmpty(telefon)) {
                            telefonTextField.setStyle(redborderStyle);
                            isValid = false;
                    }
                    else {
                            telefonTextField.setStyle("");
                    }

                    if (isValid) {        
                            errorLabel.setVisible(false);
                            Klient zaktualizowanyklient = new Klient(klient.getId(), imie, nazwisko, nrDokumentu, telefon, false);
                            zarzadzanieKlientami.aktualizujKlienta(zaktualizowanyklient);

                            aktualizujListeKlientow();
                            newWindow.close();
                    }
                    else {
                            errorLabel.setVisible(true);
                    }         	
        });
        
        grid.add(zapiszNowegoKlientaButton, 1, 5);

        errorLabel.setTextFill(Color.web("red"));
        errorLabel.setVisible(false);
        grid.add(errorLabel, 0, 6);
        grid.setColumnSpan(errorLabel, 2);

        Scene scene = new Scene(grid, 350, 280);

        newWindow.setScene(scene);
        newWindow.initModality(Modality.WINDOW_MODAL);					                
        newWindow.initOwner(primaryStage);

        newWindow.setResizable(false);
        newWindow.show();	
    }
    
    private void usunRowerOkno(Rower rower) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(imageRemove);

        alert.setTitle("Usuwanie roweru");
        
        Button anulujButton = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL );
        anulujButton.setText("Anuluj");
        
        alert.setHeaderText(rower.getModel());
        alert.setContentText("Czy na pewno chcesz usunąć rower?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Usuwanie roweru: " + rower.getModel());
            zarzadzanieRowerami.usunRower(rower.getId());
            aktualizujListeRowerow();
        }
    }
    
    private void dodajRowerOkno() {
        Stage newWindow = new Stage();
        newWindow.setTitle("Dodaj nowy rower");
        newWindow.getIcons().add(imageAdd);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        Label modellabel = new Label("Model:");
        grid.add(modellabel, 0, 1);
        TextField modelTextField = new TextField();
        grid.add(modelTextField, 1, 1);
        
        Label stawkalabel = new Label("Stawka godzinowa:");
        grid.add(stawkalabel, 0, 2);
        TextField stawkaTextField = new TextField();
        // tylko wartości numeryczne (stawka nie może być tekstem)
        stawkaTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                stawkaTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        grid.add(stawkaTextField, 1, 2);       
        
        Label errorlabel = new Label("Uzupełnij wymagane dane!");
        errorlabel.setTextFill(Color.RED);
        errorlabel.setVisible(false);
        Button zapiszNowyRowerButton = new Button("Zapisz", new ImageView(imageSave));
        
        zapiszNowyRowerButton.setOnAction(event ->{
            System.out.println("Zapisuję dane roweru");
            String model = modelTextField.getText();
            String stawka = stawkaTextField.getText();
            Boolean isValid = true;
            if(isEmpty(model)) {
                isValid = false;
                modelTextField.setStyle(redborderStyle);
            }
            else {
                modelTextField.setStyle("");
            }
            
            if(isEmpty(stawka)) {
                isValid = false;
                stawkaTextField.setStyle(redborderStyle);
            }
            else {
                stawkaTextField.setStyle("");
            }           
 
            if(isValid) {
                errorlabel.setVisible(false);
                zarzadzanieRowerami.dodajRower(model, Integer.parseInt(stawka));
                aktualizujListeRowerow();
                newWindow.close();
            }
            else{
                errorlabel.setVisible(true);
            }
        });
        grid.add(zapiszNowyRowerButton, 1, 3);       
        
        grid.add(errorlabel, 0, 4);
        grid.setColumnSpan(errorlabel, 2);
        Scene scene = new Scene(grid, 400, 180);
        newWindow.setScene(scene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);
        newWindow.setResizable(false);
        newWindow.show();
    }
    
    private void edytujRowerOkno(Rower rower) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Edytuj dane roweru");                 
        newWindow.getIcons().add(imageEdit);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label modelLabel = new Label("Model:");
        grid.add(modelLabel, 0, 1);
        TextField modelTextField = new TextField();
        modelTextField.setText(rower.getModel());
        grid.add(modelTextField, 1, 1);

        Label stawkalabel = new Label("Stawka godzinowa:");
        grid.add(stawkalabel, 0, 2);
        TextField stawkaTextField = new TextField();
        stawkaTextField.setText(String.valueOf(rower.getStawka()));
        
        // tylko wartości numeryczne (stawka nie może być tekstem)
        stawkaTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                stawkaTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        grid.add(stawkaTextField, 1, 2);       

        Label errorLabel = new Label("Uzupełnij wymagane dane!");
        Button zapiszRowerButton = new Button("Zapisz", new ImageView(imageSave));
        zapiszRowerButton.setOnAction(event -> {
            System.out.println("zapisuje dane roweru");
            String model = modelTextField.getText();
            String stawka = stawkaTextField.getText();
            Boolean isValid = true;
            if (MainApp.isEmpty(model)) {
                modelTextField.setStyle(redborderStyle);
                isValid = false;
            }
            else {
                modelTextField.setStyle("");
            }
            if(isEmpty(stawka)) {
                isValid = false;
                stawkaTextField.setStyle(redborderStyle);
            }
            else {
                stawkaTextField.setStyle("");
            }

            if (isValid) {        
                errorLabel.setVisible(false);
                Rower zaktualizowanyRower = new Rower(rower.getId(), model, Integer.parseInt(stawka), false);
                zarzadzanieRowerami.aktualizujRower(zaktualizowanyRower);
                
                aktualizujListeRowerow();
                newWindow.close();
            } else {
                errorLabel.setVisible(true);
            }         	
        });
        
        grid.add(zapiszRowerButton, 1, 3);

        errorLabel.setTextFill(Color.web("red"));
        errorLabel.setVisible(false);
        grid.add(errorLabel, 0, 4);
        grid.setColumnSpan(errorLabel, 2);

        Scene scene = new Scene(grid, 400, 180);

        newWindow.setScene(scene);
        newWindow.initModality(Modality.WINDOW_MODAL);					                
        newWindow.initOwner(primaryStage);

        newWindow.setResizable(false);
        newWindow.show();	
    }
     
    private void usunWypozyczenieOkno(Wypozyczenie wypozyczenie) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(imageRemove);

        alert.setTitle("Usuwanie wypożyczenia");
        
        Button anulujButton = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL );
        anulujButton.setText("Anuluj");
        
        alert.setHeaderText(wypozyczenie.getKlient() + ", " + wypozyczenie.getRower());
        alert.setContentText("Czy na pewno chcesz usunąć wypożyczenie?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Usuwanie wypozyczenia");
            zarzadzanieWypozyczeniami.usunWypozyczenie(wypozyczenie.getId());
            aktualizujListeWypozyczen();
        }
    }
    
    private void dodajWypozyczenieOkno(Klient klient, Rower rower) {
        List<Klient> dostepniKlienci = zarzadzanieKlientami.pobierzKlientow();
        List<Rower> dostepneRowery = zarzadzanieRowerami.pobierzRowery();
        if (dostepniKlienci.size() <= 0 || dostepneRowery.size() <= 0) {
            Alert alert = new Alert(AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(imageAdd);
            
            alert.setTitle("Brak danych");            
            alert.setHeaderText("Brak roweru lub klienta");
            alert.setContentText("Upewnij się, że zdefiniowano rowery oraz klientów (zakładki Rowery / Klienci)");

            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        
	System.out.println("Dodaj wypozyczenie"); 
		
    	Stage newWindow = new Stage();
        newWindow.setTitle("Dodaj wypożyczenie");                 
        newWindow.getIcons().add(imageAdd);
        
    	GridPane grid = new GridPane();
    	grid.setAlignment(Pos.TOP_CENTER);
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));

    	Label klientLabel = new Label("Klient:");
    	grid.add(klientLabel, 0, 1);
    	ComboBox<Klient> klientComboBox = new ComboBox<>();
    	
        ObservableList<Klient> klienci = FXCollections.observableArrayList(dostepniKlienci);
	klientComboBox.setItems(klienci);
	if (klient != null) {
            klientComboBox.getSelectionModel().select(klient);
	}
	grid.add(klientComboBox, 1, 1);		
	klientComboBox.setConverter(new StringConverter<Klient>() {
	    @Override
	    public String toString(Klient object) {
	        return object.getImie() + " " + object.getNazwisko();
	    }
	    @Override
	    public Klient fromString(String string) {
	        return klientComboBox.getItems().stream().filter(klient -> 
	        (klient.getImie() + " " + klient.getNazwisko()).equals(string)).findFirst().orElse(null);
	    }
	});

    	Label rowerLabel = new Label("Rower:");
    	grid.add(rowerLabel, 0, 2);
    	ComboBox<Rower> rowerComboBox = new ComboBox<>();
    	
        ObservableList<Rower> rowery = FXCollections.observableArrayList(dostepneRowery);
	rowerComboBox.setItems(rowery);
	if (rower != null) {
            rowerComboBox.getSelectionModel().select(rower);
	}
	grid.add(rowerComboBox, 1, 2);		
	rowerComboBox.setConverter(new StringConverter<Rower>() {
	    @Override
	    public String toString(Rower object) {
	        return object.getModel();
	    }
	    @Override
	    public Rower fromString(String string) {
	        return rowerComboBox.getItems().stream().filter(rower -> 
	        rower.getModel().equals(string)).findFirst().orElse(null);
	    }
	});
    	
    	Label wyzpoczyczenieOdLabel = new Label("Data wypożyczenia:");
    	grid.add(wyzpoczyczenieOdLabel, 0, 3);
    	Label wyzpoczyczenieOdDataLabel = new Label((new Date()).toLocaleString());
    	grid.add(wyzpoczyczenieOdDataLabel, 1, 3);
    	    	
    	Label errorLabel = new Label("Uzupełnij wymagane dane!");
    	
    	Button zapiszNoweWypozyczenieButton = new Button("Zapisz", new ImageView(imageSave));
    	zapiszNoweWypozyczenieButton.setOnAction(event -> {
            System.out.println("zapisuje dane klienta");
            Klient wybranyKlient = klientComboBox.getValue();
            Rower wybranyRower = rowerComboBox.getValue();
            Boolean isValid = true;
            if (wybranyKlient == null) {
                klientComboBox.setStyle(redborderStyle);
                isValid = false;
	    }
            else {
                klientComboBox.setStyle("");
	    }
            if (wybranyRower == null) {
                rowerComboBox.setStyle(redborderStyle);
                isValid = false;
	    }
            else {
                rowerComboBox.setStyle("");
	    }
             
            if (isValid) {        
                errorLabel.setVisible(false);
                
                zarzadzanieWypozyczeniami.dodajWypozyczenie(wybranyKlient.getId(), wybranyRower.getId(), new Date());
              
                aktualizujListeWypozyczen();
                //aktywowanie zakładki Wypożyczenia, jeżeli tworzenie wypożyczenia z zakładki Rowery lub klienci
                if (tabPane.getSelectionModel().getSelectedIndex() > 0) {
                    tabPane.getSelectionModel().select(0);
                }
                
                newWindow.close();
            }
            else {
                errorLabel.setVisible(true);
            }
    	});
        
    	grid.add(zapiszNoweWypozyczenieButton, 1, 5);
    	
    	errorLabel.setTextFill(Color.web("red"));
    	errorLabel.setVisible(false);
    	grid.add(errorLabel, 0, 6);
    	grid.setColumnSpan(errorLabel, 2);
    	
    	Scene scene = new Scene(grid, 400, 230);
    	
        newWindow.setScene(scene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);        
        newWindow.setResizable(false);
        newWindow.show();	
    }
    
    private void zwrotInfoOkno(Wypozyczenie wypozyczenie) {
        Alert alert = new Alert(AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(imageBack);

        alert.setTitle("Zwrot roweru");        

        alert.setHeaderText(wypozyczenie.getKlient() + " zwrócił rower " + wypozyczenie.getRower());

        int hours = getFullHours(wypozyczenie.getWypozyczenieod(), wypozyczenie.getWypozyczeniedo()); 
        alert.setContentText("Czas wypożyczenia wynosi: " + hours +" h. Opłata: " + wypozyczenie.getKosztwypozyczenia() + " PLN.");

        Optional<ButtonType> result = alert.showAndWait();
    }
    
    public static boolean isEmpty( final String str ) {
	return str == null || str.trim().isEmpty();
    }   
    
    private static int getFullHours(Date start, Date end) {
        //oblicza i zwraca pełne, rozpoczęte godziny
        long diff = end.getTime() - start.getTime();
        int hours = (int) (diff / (1000 * 60 * 60));
        if (hours * 60 * 60 * 1000 < diff) {
             hours++;
        }
        
        return hours;
    }
}