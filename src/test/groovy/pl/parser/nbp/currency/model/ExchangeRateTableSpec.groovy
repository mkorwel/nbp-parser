package pl.parser.nbp.currency.model

import spock.lang.Specification

/**
 * Created by mateusz on 31/01/16.
 */
class ExchangeRateTableSpec extends Specification {
    def "should return searched position when exists"() {
        given:
        Currency currency = Currency.getInstance("EUR")

        CurrencyPosition eurCurrencyPosition = new CurrencyPosition(code: currency)
        CurrencyPosition otherCurrencyPosition = new CurrencyPosition(code: Currency.getInstance("USD"))

        ExchangeRateTable table = new ExchangeRateTable("currencies": Arrays.asList(eurCurrencyPosition, otherCurrencyPosition))

        when:
        CurrencyPosition currencyPosition = table.getCurrency(currency)

        then:
        currencyPosition != null
        currencyPosition.equals(eurCurrencyPosition)
    }

    def "should throw exception searched position when not exists"() {
        given:
        CurrencyPosition eurCurrencyPosition = new CurrencyPosition(code: Currency.getInstance("EUR"))
        CurrencyPosition usdCurrencyPosition = new CurrencyPosition(code: Currency.getInstance("USD"))

        ExchangeRateTable table = new ExchangeRateTable("currencies": Arrays.asList(eurCurrencyPosition, usdCurrencyPosition))

        when:
        table.getCurrency(Currency.getInstance("PLN"))

        then:
        thrown(NoSuchElementException)
    }
}