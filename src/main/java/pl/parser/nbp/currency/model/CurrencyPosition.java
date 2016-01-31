package pl.parser.nbp.currency.model;

import lombok.Getter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Currency;

/**
 * Created by mateusz on 30/01/16.
 */
@Getter
@Root(name = "pozycja")
public class CurrencyPosition {
	@Element(name = "nazwa_waluty")
	private String name;
	@Element(name  = "przelicznik")
	private String conversionRate;
	@Element(name = "kod_waluty")
	private Currency code;
	@Element(name = "kurs_kupna")
	private CurrencyRate buyRate;
	@Element(name = "kurs_sprzedazy")
	private CurrencyRate sellRate;
}
