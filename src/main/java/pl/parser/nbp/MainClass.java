package pl.parser.nbp;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;
import pl.parser.nbp.core.client.adapter.SynchronousCallAdapterFactory;
import pl.parser.nbp.core.configuration.ApplicationConstant;
import pl.parser.nbp.currency.client.CurrencyLocationFileClient;
import pl.parser.nbp.core.client.transform.CurrencyRateTransform;
import pl.parser.nbp.currency.client.CurrencyRestClient;
import pl.parser.nbp.currency.model.CurrencyRate;
import pl.parser.nbp.currency.service.CalculationService;
import pl.parser.nbp.currency.service.CurrencyLocationFileService;
import pl.parser.nbp.currency.usecase.CalculateStatisticForCurrency;
import pl.parser.nbp.currency.usecase.model.CalculateStatisticInput;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;

/**
 * Created by mateusz on 30/01/16.
 */
public class MainClass {
	private static CurrencyLocationFileClient currencyLocationFileClient;
	private static CurrencyLocationFileService currencyLocationFileService;
	private static CalculationService calculateService;
	private static CalculateStatisticForCurrency calculateStatisticForCurrency;
	private static CurrencyRestClient currencyRestClient;

	public static void main(String... arg) {
		initApplication();

		calculateStatisticForCurrency.execute(CalculateStatisticInput.createValid(arg));
	}

	private static void initApplication() {
		Retrofit retrofit = initRetrofit();

		currencyRestClient = retrofit.create(CurrencyRestClient.class);

		currencyLocationFileClient = new CurrencyLocationFileClient();
		currencyLocationFileService = new CurrencyLocationFileService(currencyLocationFileClient, currencyRestClient);

		calculateService = new CalculationService();
		calculateStatisticForCurrency = new CalculateStatisticForCurrency(currencyLocationFileService, calculateService);
	}

	private static Retrofit initRetrofit() {
		RegistryMatcher registryMatcher = new RegistryMatcher();
		registryMatcher.bind(CurrencyRate.class, new CurrencyRateTransform());

		return new Retrofit.Builder()
				.baseUrl(ApplicationConstant.URL)
				.addConverterFactory(SimpleXmlConverterFactory.create(new Persister(registryMatcher)))
				.addCallAdapterFactory(SynchronousCallAdapterFactory.create())
				.build();
	}
}
