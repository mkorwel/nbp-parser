package pl.parser.nbp.currency.model;

import lombok.Getter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by mateusz on 30/01/16.
 */
@Getter
@Root(name = "tabela_kursow")
public class ExchangeRateTable {
	@Element(name = "numer_tabeli")
	private String tableNumber;
	@Attribute(name = "typ")
	private String type;
	@Attribute
	private String uid;
	@Element(name = "data_publikacji")
	private String publishDate;
	@Element(name = "data_notowania")
	private String listingDate;
	@ElementList(inline = true)
	private List<CurrencyPosition> currencies;

	public CurrencyPosition getCurrency(Currency code){
		return getCurrencies().stream()
				.filter(x -> x.getCode().equals(code)).findFirst()
				.orElseThrow(NoSuchElementException::new);
	}
}
