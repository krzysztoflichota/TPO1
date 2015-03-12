import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Lichota on 2015-03-10.
 * krzysztoflichota.com
 */
@XmlRootElement(name = "tabela_kursow")
public class Currencies {
    private String tableNumber, date;

    private List<Currency> currencies= new ArrayList<>();

    @XmlElement(name = "numer_tabeli")
    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    @XmlElement(name = "data_publikacji")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "pozycja")
    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}
