import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Krzysztof Lichota on 2015-03-07.
 * krzysztoflichota.com
 */
public class CurrenciesCalc extends JFrame{

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                /*try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }*/

                JFrame f = new CurrenciesCalc();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });
    }

    public static final String tableA = "http://www.nbp.pl/kursy/xml/lasta.xml";
    public static final String tableB = "http://www.nbp.pl/kursy/xml/lastb.xml";

    private JComboBox<Currency> curr1, curr2;
    private JButton refresh, calc;
    private JTextField input, output;
    private JLabel date;

    private Currencies currenciesA;

    public CurrenciesCalc() {
        super("Currencies Converter");
        initGUI();
        refresh();
        this.pack();
        this.setResizable(false);
    }

    private void initGUI(){
        curr1 = new JComboBox<>();
        curr2 = new JComboBox<>();
        refresh = new JButton("Refresh");
        calc = new JButton("Convert");
        input = new JTextField();
        input.setColumns(10);
        input.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        output = new JTextField();
        output.setColumns(10);
        output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        output.setEditable(false);
        output.setForeground(Color.RED);
        output.setBackground(Color.WHITE);
        date = new JLabel("Date: 00-00-0000");

        JPanel currencies = new JPanel();
        currencies.add(new JLabel("From: "));
        currencies.add(curr1);
        currencies.add(new JLabel("To: "));
        currencies.add(curr2);

        this.add(currencies, BorderLayout.NORTH);

        JPanel values = new JPanel();
        values.setLayout(new BoxLayout(values, BoxLayout.Y_AXIS));
        JPanel value = new JPanel();
        values.add(date);
        value.add(new JLabel("Value:  "));
        value.add(input);
        values.add(value);
        value = new JPanel();
        value.add(new JLabel("Result: "));
        value.add(output);
        values.add(value);

        this.add(values, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.add(refresh);
        buttons.add(calc);

        this.add(buttons, BorderLayout.SOUTH);

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        calc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convert();
            }
        });
    }

    private static Currencies XMLtoObjects(URL url) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Currencies.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Currencies) unmarshaller.unmarshal(url);
    }

    private void refresh(){
        try {
            Currencies currenciesA = XMLtoObjects(new URL(tableA));
            Currencies currenciesB = XMLtoObjects(new URL(tableB));
            date.setText("Date: " + currenciesA.getDate());
            curr1.removeAllItems();
            curr2.removeAllItems();

            Currency pln = new Currency("polski złoty", "PLN", 1, "1.00");
            curr1.addItem(pln);
            curr2.addItem(pln);

            for(Currency c : currenciesA.getCurrencies()){
                curr1.addItem(c);
                curr2.addItem(c);
            }
            for(Currency c : currenciesB.getCurrencies()){
                curr1.addItem(c);
                curr2.addItem(c);
            }

        } catch (JAXBException | MalformedURLException e) {
            JOptionPane.showMessageDialog(this, "Nie można pobrać danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void convert(){
        try {
            BigDecimal val = new BigDecimal(input.getText());

            Currency a = (Currency) curr1.getSelectedItem();
            Currency b = (Currency) curr2.getSelectedItem();

            BigDecimal m = (new BigDecimal(b.getRate())).multiply(new BigDecimal(a.getEncounter()));
            BigDecimal result = val.multiply(new BigDecimal(a.getRate())).multiply(new BigDecimal(b.getEncounter()));
            result = result.divide(m, 4, BigDecimal.ROUND_HALF_UP);
            output.setText(result.toString());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Źle wprowadzona wartość!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}
