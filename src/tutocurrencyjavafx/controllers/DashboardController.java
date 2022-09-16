package tutocurrencyjavafx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tutocurrencyjavafx.connectionApi.ApiConnection;
import tutocurrencyjavafx.connectionApi.JsonConversion;
import tutocurrencyjavafx.models.Currency;
import tutocurrencyjavafx.repository.Converter;
import tutocurrencyjavafx.repository.ConverterImp;
import tutocurrencyjavafx.services.CurrencyService;
import tutocurrencyjavafx.services.HistoricalCurrencyService;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import tutocurrencyjavafx.models.HistoricalCurrency;

public class DashboardController implements Initializable  {

    @FXML
    private AnchorPane anchorPaneConv;

    @FXML
    private AnchorPane anchorPaneHistCurr;

    @FXML
    private AnchorPane anchorPaneSymbCurr;

    @FXML
    private TableColumn<Currency, String> col1Symbols;

    @FXML
    private TableColumn<Currency, String> col2Signification;

    @FXML
    private ComboBox comboBoxChooSymb;

    @FXML
    private ComboBox comboBoxFrom;

    @FXML
    private ComboBox comboBoxTo;

    @FXML
    private Text convLabel1;

    @FXML
    private Text convLabel2;

    @FXML
    private Text convLabel3;

    @FXML
    private Button convertBtn;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private Button sbConvBtn;

    @FXML
    private Button sbHistCurr;

    @FXML
    private Button sbSymbCurr;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView<Currency> tableView;

    @FXML
    private Text tbTitle1;

    @FXML
    private Text tbTitle2;

    @FXML
    private TextField txtFieldAmount;

    @FXML
    private TextField txtFieldSearch;
    
    CurrencyService currencyService;
    
    List<Currency> listCurrency;
    private final Integer DAYS=8;
    private final String BASE="EUR";
    @Override
    public void initialize(URL uri, ResourceBundle rb){
        try { String url = "https://api.apilayer.com/fixer";
        String apiKey =  "4kDFTNoTVJWBVNYkKb2WfTASe7UEakNS";
        ApiConnection apiConnection=new ApiConnection(url, apiKey);
        JsonConversion jsonConversion=new JsonConversion();
        Converter converter=new ConverterImp(apiConnection, jsonConversion);
        currencyService=new CurrencyService(converter);
       
            listCurrency=currencyService.getAllCurrencies();
            List<String> listSymbols=currencyService.getAllSymbols(listCurrency);
            comboBoxFrom.getItems().addAll(listSymbols);
            comboBoxTo.getItems().addAll(listSymbols);
            comboBoxChooSymb.getItems().addAll(listSymbols);
            fillTableViewCurrency(listCurrency);
            
            HistoricalCurrencyService historicalCurrencyService=
                    new HistoricalCurrencyService(converter);
            comboBoxChooSymb.setOnAction((event) -> {
                try {
                String symbol=comboBoxChooSymb.getSelectionModel()
                        .getSelectedItem().toString();
            
                List<HistoricalCurrency> list=historicalCurrencyService
                        .HistoricalDataCurrency(BASE, DAYS, symbol);
                drawChart(list,symbol);
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
            });
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void eventComboBoxGraph(ActionEvent event) {

    }

    @FXML
    void eventConvBtn(ActionEvent event) throws IOException {
        try{
        String from=comboBoxFrom.getSelectionModel().getSelectedItem().toString();
        String to=comboBoxTo.getSelectionModel().getSelectedItem().toString();
        Double amount=Double.parseDouble(txtFieldAmount.getText());
                Double result=currencyService.convert(from, to, amount);
        convLabel1.setText(amount+" "+from);
        convLabel2.setText(result+" "+to);
        convLabel3.setText("1 "+to +" = "+amount/result+" "+from);
        convLabel1.setVisible(true);
        convLabel2.setVisible(true);
        convLabel3.setVisible(true);
        }catch(Exception e){
              alertMessage();
              System.err.println(e.getMessage());
        }

    }

    @FXML
    void eventSearchBtn(ActionEvent event) {
      String symbol=txtFieldSearch.getText();
      if(listCurrency==null)
          throw new IllegalStateException("the list currency should not be null");
      List<Currency> list=currencyService.findCurrency(listCurrency, symbol);
        fillTableViewCurrency(list);
    }
    

    @FXML
    void eventSideBar(ActionEvent event) {
        if(event.getSource()==sbConvBtn){
            anchorPaneConv.setVisible(true);
            anchorPaneSymbCurr.setVisible(false);
            anchorPaneHistCurr.setVisible(false);
            tbTitle1.setText("Home/converter");
            tbTitle2.setText("Converter");
            
        }
        else if(event.getSource()==sbSymbCurr){
            anchorPaneConv.setVisible(false);
            anchorPaneSymbCurr.setVisible(true);
            anchorPaneHistCurr.setVisible(false);
            tbTitle1.setText("Home/symbol currencies");
            tbTitle2.setText("SymbolCurrencies");
            
        }
        else if(event.getSource()==sbHistCurr){
            anchorPaneConv.setVisible(false);
            anchorPaneSymbCurr.setVisible(false);
            anchorPaneHistCurr.setVisible(true);
            tbTitle1.setText("Home/historical currency");
            tbTitle2.setText("HistoricalCurrency");
            
        }
        

    }

    private void fillTableViewCurrency(List<Currency> listCurrency) {
       col1Symbols.setCellValueFactory(new PropertyValueFactory<>("symbol"));
       col2Signification.setCellValueFactory(new PropertyValueFactory<>("signification"));
       ObservableList<Currency> ob=FXCollections.observableArrayList(listCurrency);
       tableView.setItems(ob);
    }

    private void alertMessage() {
       Alert alert=new Alert(Alert.AlertType.ERROR);
       alert.setTitle("invalid data input");
       alert.setContentText("please fill all the fields");
       alert.showAndWait();
    }

    private void drawChart(List<HistoricalCurrency> list, String symbol) {
   XYChart.Series serie=new XYChart.Series();
   serie.setName("Currency Rate "+symbol+" To "+BASE);
        for (HistoricalCurrency historicalCurrency : list) {
            serie.getData().add(new XYChart.Data(historicalCurrency.getDay()
                    ,historicalCurrency.getValue()));
        }
        if(lineChart.getData().size()>2){
            lineChart.getData().set(2, serie);
        }else{
            lineChart.getData().add(serie);
        }
    }

    

}
