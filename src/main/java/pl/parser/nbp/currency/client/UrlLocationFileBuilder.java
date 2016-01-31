package pl.parser.nbp.currency.client;

import pl.parser.nbp.core.client.exception.HttpException;
import pl.parser.nbp.core.configuration.ApplicationConstant;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;

/**
 * Created by mateusz on 30/01/16.
 */
class UrlLocationFileBuilder {
	public static URL buildFor(Year year){
		try {
			return new URL(ApplicationConstant.URL + "/kursy/xml/dir" + getYearPart(year) + ".txt");
		} catch (MalformedURLException e) {
			throw new HttpException();
		}
	}

	private static String getYearPart(Year year) {
		return Year.now().equals(year) ? "" : year.toString();
	}
}
