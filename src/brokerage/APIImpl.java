package brokerage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * this class implements the API interface. This implements makes an api call to fetch the stock
 * price in the form of csv file.
 */
public class APIImpl implements API {

  @Override
  public double stockCurrentValueFromAPI(String ticker, LocalDate valueOnDate) {
    double db = 0;
    String apiKey = "FJJ46FMEUVTVQPCE";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
          + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;
      in.read();
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + ticker);
    }
    String[] str = output.toString().split("\n");
    String[] modifiedStr = Arrays.copyOfRange(str, 1, str.length);

    for (int i = 0; i < modifiedStr.length; i++) {
      String[] str1 = modifiedStr[i].split(",");
      if (valueOnDate.isEqual(
          LocalDate.parse(str1[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
        db = Double.parseDouble(str1[4]);
        break;
      }
    }
    return db;
  }

}
