package Controler;

import Service.WalutaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Classes.Rates;
import main.Classes.Waluty;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RootControler implements Initializable
{
    @FXML
    private Label labelData;

    private Alert alert;

    @FXML
    private TableView <Rates> tableView;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn <Rates,String> nazwaWaluty;

    @FXML
    private TableColumn <Rates,Double> kupno;

    @FXML
    private TableColumn <Rates,Double> sprzedaz;

    @FXML
    private TextField output;

    @FXML
    private Label result;

    private String itemsToChoiceBox[] = {"Wszystkie","Dolar amerykański","Dolar australijski","Dolar kanadyjski","Euro","Forint (Węgry)","Frank szwajcarski","Funt szterling","Jen (Japonia)","Korona czeska","Korona duńska","Korona norweska","Korona szwedzka","SDR (MFW)"};
    private LocalDate today = LocalDate.now();
    private WalutaService walutaService;

    private Rates rates;
    private Waluty waluty;


    private ObservableList<Rates> currencyList = FXCollections.observableArrayList();


    public void wyszukaj()
    {
        String data = String.valueOf(datePicker.getValue());
        String currency = String.valueOf(choiceBox.getValue()).toLowerCase();

        if (datePicker.getValue().isAfter(today))
        {
            alert.setContentText("Wybrana data jest w przyszłości. Wybierz inną datę");
            alert.showAndWait();
        }
        else {
            walutaService = new WalutaService("http://api.nbp.pl/api/exchangerates/tables/C/");
            currencyList.clear();

            if (currency.equals("wszystkie")) {

                waluty = walutaService.getAllCurrencyFromDay(data);

                if (waluty == null)
                {
                    alert.setContentText("Brak danych dla określonej daty");
                    alert.showAndWait();
                }
                else {
                    for (int i = 0; i < waluty.getRates().size(); i++) {
                        currencyList.add(waluty.getRates().get(i));
                    }
                }


            } else {


                rates = walutaService.getCurrencyRatio(currency, data);

                if (rates == null)
                {
                    alert.setContentText("Brak danych dla określonej daty");
                    alert.showAndWait();
                }
                else {
                    currencyList.add(rates);
                }
            }

                labelData.setText(data);
                nazwaWaluty.setCellValueFactory(new PropertyValueFactory<Rates, String>("currency"));
                kupno.setCellValueFactory(new PropertyValueFactory<Rates, Double>("bid"));
                sprzedaz.setCellValueFactory(new PropertyValueFactory<Rates, Double>("ask"));


        }
    }
    public void konwertuj()
    {
        if ((!choiceBox.getValue().equals("Wszystkie") ) && rates !=null) {
            BigDecimal PLN;


            try {
                int wartoscPLN = Integer.parseInt(output.getText());
                if (wartoscPLN < 0) {
                    wartoscPLN = wartoscPLN * -1;
                }
                PLN = BigDecimal.valueOf(wartoscPLN);
                BigDecimal obecnyKursWaluty = BigDecimal.valueOf(rates.getAsk());
                String wynik = String.valueOf(PLN.divide(obecnyKursWaluty, 0, BigDecimal.ROUND_FLOOR));
                result.setText("Za " + wartoscPLN + " PLN dostaniesz " + wynik + " " + rates.getCurrency());
            } catch (NumberFormatException ex) {
                alert.setContentText("Bledna wartosc");
                alert.showAndWait();

            }

        }
        else
        {
            alert.setContentText("Wybierz jedna walute.");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        choiceBox.getItems().addAll(itemsToChoiceBox);
        choiceBox.setValue(itemsToChoiceBox[4]);
        choiceBox.getSelectionModel().selectFirst();
        datePicker.setValue(today);
        currencyList.add(rates);
        tableView.setItems(currencyList);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
    }

}
