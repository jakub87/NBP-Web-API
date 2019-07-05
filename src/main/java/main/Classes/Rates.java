package main.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Rates
{
    private String currency;
    private String code;
    private double bid;
    private double ask;

}
