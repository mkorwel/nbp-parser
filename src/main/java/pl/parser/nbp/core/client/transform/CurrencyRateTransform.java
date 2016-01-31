package pl.parser.nbp.core.client.transform;

import org.simpleframework.xml.transform.Transform;
import pl.parser.nbp.currency.model.CurrencyRate;

import java.math.BigDecimal;

/**
 * Created by mateusz on 31/01/16.
 */
public class CurrencyRateTransform implements Transform<CurrencyRate> {
	@Override
	public CurrencyRate read(String value) throws Exception {
		if (value == null) {
			return null;
		}

		return new CurrencyRate(new BigDecimal(value.replace(",", ".")));
	}

	@Override
	public String write(CurrencyRate value) throws Exception {
		if (value == null) {
			return null;
		}

		return value.toString();
	}
}
