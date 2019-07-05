package Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import main.Classes.Rates;
import main.Classes.Waluty;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;


public class WalutaService
{
   private String finalURL;
   private final String extension = "?format=json";
   private ObjectMapper objectMapper = new ObjectMapper();;
   private URL url;
   private List<Rates> list;

   public WalutaService(String apiKey)
   {
        finalURL = apiKey;
   }

   public Waluty getAllCurrencyFromDay (String date)
   {
       Waluty waluty;

       try
       {
           finalURL = finalURL + date + extension;
           url = new URL(finalURL);
           Waluty[] waluta = objectMapper.readValue(url,Waluty[].class);
           waluty = waluta[0];

       }
       catch (IOException e)
       {
           waluty = null;
       }


       return waluty;
   }



   public Rates getCurrencyRatio(String nameOfCurrency, String date)
   {
       Rates rates;
       finalURL = finalURL + date + extension;
       try
       {
           url = new URL(finalURL);
           Waluty[] waluta = objectMapper.readValue(url,Waluty[].class);


               list = waluta[0].getRates().stream()
                       .peek(x -> x.setCurrency(x.getCurrency().toLowerCase()))
                       .filter(x -> x.getCurrency().equals(nameOfCurrency))
                       .collect(Collectors.toList());


           rates = list.get(0);


       }
       catch (IOException e)
       {
          rates = null;
       }


       return rates;
   }


}
