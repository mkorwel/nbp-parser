package pl.parser.nbp.currency.model

import spock.lang.Specification

/**
 * Created by mateusz on 31/01/16.
 */
class CurrencyRateSpec extends Specification {
    def "should calculate sqrt for currency rate"() {
        expect:
        rate.sqrt() == sqrt

        where:
        rate                                                  || sqrt
        new CurrencyRate(1)                                   || new CurrencyRate(1)
        new CurrencyRate(4)                                   || new CurrencyRate(2)
        new CurrencyRate(9)                                   || new CurrencyRate(3)
        new CurrencyRate(BigDecimal.valueOf(10).setScale(4))  || new CurrencyRate(3.1623)
        new CurrencyRate(BigDecimal.valueOf(1.5).setScale(4)) || new CurrencyRate(1.2248)
        new CurrencyRate(0.00015568)                          || new CurrencyRate(0.01247718)
    }

    def "should add currency rates"() {
        expect:
        rateOne.plus(rateTwo) == resule

        where:
        rateOne               | rateTwo               || resule
        new CurrencyRate(1)   | new CurrencyRate(1)   || new CurrencyRate(2)
        new CurrencyRate(-4)  | new CurrencyRate(1)   || new CurrencyRate(-3)
        new CurrencyRate(9)   | new CurrencyRate(15)  || new CurrencyRate(24)
        new CurrencyRate(1.5) | new CurrencyRate(1.2) || new CurrencyRate(2.7)
    }

    def "should subtract currency rates"() {
        expect:
        rateOne.minus(rateTwo) == resule

        where:
        rateOne               | rateTwo               || resule
        new CurrencyRate(1)   | new CurrencyRate(1)   || new CurrencyRate(0)
        new CurrencyRate(-4)  | new CurrencyRate(1)   || new CurrencyRate(-5)
        new CurrencyRate(23)  | new CurrencyRate(5)   || new CurrencyRate(18)
        new CurrencyRate(1.5) | new CurrencyRate(1.2) || new CurrencyRate(0.3)
    }

    def "should divide currency rate"() {
        expect:
        rate.divide(number) == resule

        where:
        rate                  | number || resule
        new CurrencyRate(1)   | 2      || new CurrencyRate(0.5)
        new CurrencyRate(-4)  | 2      || new CurrencyRate(-2)
        new CurrencyRate(23)  | 5      || new CurrencyRate(4.6)
        new CurrencyRate(1.5) | 2      || new CurrencyRate(0.8)
    }

    def "should raise to the power currency rate"() {
        expect:
        rate.pow(level) == resule

        where:
        rate                  | level || resule
        new CurrencyRate(1)   | 2     || new CurrencyRate(new BigDecimal(1).setScale(2))
        new CurrencyRate(-4)  | 2     || new CurrencyRate(new BigDecimal(16).setScale(2))
        new CurrencyRate(3)   | 2     || new CurrencyRate(new BigDecimal(9).setScale(2))
        new CurrencyRate(1.5) | 2     || new CurrencyRate(new BigDecimal(2.25).setScale(2))
    }
}
