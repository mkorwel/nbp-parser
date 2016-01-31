package pl.parser.nbp.currency.client;

import pl.parser.nbp.currency.model.CurrencyFile;
import pl.parser.nbp.currency.model.ExchangeRateTable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mateusz on 30/01/16.
 */
public interface CurrencyRestClient {
	@GET("/kursy/xml/{fileName}.xml")
	ExchangeRateTable list(@Path("fileName") CurrencyFile.FileName fileName);
}
