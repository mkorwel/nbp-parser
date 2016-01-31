package pl.parser.nbp.currency.service;

import lombok.RequiredArgsConstructor;
import pl.parser.nbp.currency.client.CurrencyLocationFileClient;
import pl.parser.nbp.currency.client.CurrencyRestClient;
import pl.parser.nbp.currency.model.CurrencyFile;
import pl.parser.nbp.currency.model.ExchangeRateTable;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mateusz on 30/01/16.
 */
@RequiredArgsConstructor
public class CurrencyLocationFileService {
	private final CurrencyLocationFileClient currencyLocationFileClient;
	private final CurrencyRestClient currencyRestClient;

	public List<ExchangeRateTable> getExchangeRateTables(LocalDate startDate, LocalDate endDate) {
		List<CurrencyFile> allFiles = currencyLocationFileClient.getCurrencyFiles(Year.from(startDate), Year.from(endDate));

		return allFiles.stream()
				.filter(x -> filterUnused(x, startDate, endDate))
				.map(x -> currencyRestClient.list(x.getFileName()))
				.collect(Collectors.toList());
	}

	private boolean filterUnused(CurrencyFile file, LocalDate startDate, LocalDate endDate) {
		return file.isBetweenDates(startDate, endDate) && file.isBuyAndSell();
	}
}
