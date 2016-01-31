package pl.parser.nbp.core.client.transform

import pl.parser.nbp.currency.model.CurrencyRate
import spock.lang.Specification

/**
 * Created by mateusz on 31/01/16.
 */
class CurrencyRateTransformSpec extends Specification {
    def "should read valid from string"() {
        given:
        CurrencyRateTransform transform = new CurrencyRateTransform();

        expect:
        transform.read(value) == currencyRate

        where:
        value || currencyRate
        "2"   || new CurrencyRate(new BigDecimal("2"))
        "2.5" || new CurrencyRate(new BigDecimal("2.5"))
        "2,5" || new CurrencyRate(new BigDecimal("2.5"))
        null  || null
    }

    def "should write value as string"() {
        given:
        CurrencyRateTransform transform = new CurrencyRateTransform();

        expect:
        transform.write(currencyRate) == result

        where:
        currencyRate                            || result
        new CurrencyRate(new BigDecimal("2"))   || "2.0000"
        new CurrencyRate(new BigDecimal("2.5")) || "2.5000"
        new CurrencyRate(new BigDecimal("2.5")) || "2.5000"
        null                                    || null
    }
}
