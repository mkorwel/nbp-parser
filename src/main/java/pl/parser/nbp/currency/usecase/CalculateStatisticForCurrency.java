package pl.parser.nbp.currency.usecase;

import pl.parser.nbp.currency.model.CurrencyPosition;
import pl.parser.nbp.currency.model.CurrencyRate;
import pl.parser.nbp.currency.model.ExchangeRateTable;
import pl.parser.nbp.currency.service.CalculationService;
import pl.parser.nbp.currency.service.CurrencyLocationFileService;
import pl.parser.nbp.currency.usecase.model.CalculateStatisticInput;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mateusz on 31/01/16.
 */
public class CalculateStatisticForCurrency {
	private final CurrencyLocationFileService currencyLocationFileService;
	private final CalculationService calculateService;

	public CalculateStatisticForCurrency(CurrencyLocationFileService currencyLocationFileService, CalculationService calculateService) {
		this.currencyLocationFileService = currencyLocationFileService;
		this.calculateService = calculateService;
	}

	public void execute(CalculateStatisticInput params) {
		printInputData(params);

		List<ExchangeRateTable> exchangeRateTables = currencyLocationFileService
				.getExchangeRateTables(params.getStartDate(), params.getEndDate());

		List<CurrencyPosition> currencies = exchangeRateTables.stream()
				.map(x -> x.getCurrency(params.getCode()))
				.collect(Collectors.toList());

		CurrencyRate average = calculateBuyRatesAverage(currencies);
		CurrencyRate standardDeviation = calculateSellRatesStandardDeviation(currencies);

		printResult(average, standardDeviation);
	}

	private CurrencyRate calculateSellRatesStandardDeviation(List<CurrencyPosition> currencies) {
		List<CurrencyRate> sellRatesForCurrency = currencies.stream()
				.map(CurrencyPosition::getSellRate)
				.collect(Collectors.toList());

		return calculateService.calculateStandardDeviation(sellRatesForCurrency);
	}

	private CurrencyRate calculateBuyRatesAverage(List<CurrencyPosition> currencies) {
		List<CurrencyRate> buyRatesForCurrency = currencies.stream()
				.map(CurrencyPosition::getSellRate)
				.collect(Collectors.toList());

		return calculateService.calculateAverage(buyRatesForCurrency);
	}

	private void printInputData(CalculateStatisticInput params) {
		System.out.println("kod waluty: " + params.getCode());
		System.out.println("data początkowa: " + params.getStartDate());
		System.out.println("data końcowa: " + params.getEndDate());
	}

	private void printResult(CurrencyRate average, CurrencyRate standardDeviation) {
		System.out.println("średni kurs kupna: " + average);
		System.out.println("odchylenie standardowe kursów sprzedaży: " + standardDeviation);
	}
}
