package pl.parser.nbp.currency.service;

import pl.parser.nbp.currency.model.CurrencyRate;

import java.util.List;

/**
 * Created by mateusz on 31/01/16.
 */
public class CalculationService {
	public CurrencyRate calculateAverage(List<CurrencyRate> rates) {
		CurrencyRate sumOfRates = rates.stream()
				.reduce(CurrencyRate.ZERO, (a, b) -> a.plus(b));

		return sumOfRates.divide(rates.size());
	}

	public CurrencyRate calculateStandardDeviation(List<CurrencyRate> rates) {
		CurrencyRate average = calculateAverage(rates);

		CurrencyRate sumOfAverageDeviation = rates.stream()
				.map(x -> x.minus(average).pow(2))
				.reduce(CurrencyRate.ZERO, (a, b) -> a.plus(b));

		CurrencyRate variation = sumOfAverageDeviation
				.divide(rates.size());

		return variation.sqrt();
	}
}
