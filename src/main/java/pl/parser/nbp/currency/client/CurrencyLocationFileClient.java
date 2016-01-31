package pl.parser.nbp.currency.client;

import pl.parser.nbp.currency.model.CurrencyFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mateusz on 30/01/16.
 */
public class CurrencyLocationFileClient {
	private final static String UTF8_BOM = "\uFEFF";

	public List<CurrencyFile> getCurrencyFiles(Year start, Year end) {
		List<CurrencyFile> fileNames = new ArrayList<>();
		Year forYear = start;

		while (!forYear.isAfter(end)) {
			fileNames.addAll(getCurrencyFiles(forYear));

			forYear = forYear.plusYears(1);
		}

		return Collections.unmodifiableList(fileNames);
	}

	private List<CurrencyFile> getCurrencyFiles(Year year) {
		try (InputStream is = UrlLocationFileBuilder.buildFor(year).openStream()) {
			return new BufferedReader(new InputStreamReader(is))
					.lines()
					.map(this::convertToCurrencyFile)
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	private CurrencyFile convertToCurrencyFile(String next) {
		if (next.startsWith(UTF8_BOM)) {
			next = next.substring(1);
		}

		return new CurrencyFile(next);
	}
}
