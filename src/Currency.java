import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Krzysztof Lichota on 2015-03-10.
 * krzysztoflichota.com
 */
@XmlRootElement(name = "pozycja")
public class Currency {
    private String currName, currCode;
    private int encounter;
    private String rate;

    public Currency(){}

    public Currency(String currName, String currCode, int encounter, String rate) {
        this.currName = currName;
        this.currCode = currCode;
        this.encounter = encounter;
        this.rate = rate;
    }

    @XmlElement(name = "nazwa_waluty")
    public String getCurrName() {
        return currName;
    }

    public void setCurrName(String currName) {
        this.currName = currName;
    }

    @XmlElement(name = "kurs_sredni")
    public String getRate() {
        return rate.replace(',', '.');
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @XmlElement(name = "kod_waluty")
    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    @XmlElement(name = "przelicznik")
    public int getEncounter() {
        return encounter;
    }

    public void setEncounter(int encounter) {
        this.encounter = encounter;
    }

    @Override
    public String toString() {
        return getCurrCode();
    }
}
