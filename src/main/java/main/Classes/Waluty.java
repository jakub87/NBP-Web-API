package main.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Waluty {
    private String table;
    private String no;
    private String tradingDate;
    private String effectiveDate;
    private ArrayList<Rates> rates;

}
