package pl.parser.nbp.currency.service

import pl.parser.nbp.currency.model.CurrencyRate
import spock.lang.Specification

/**
 * Created by mateusz on 31/01/16.
 */
class CalculationServiceSpec extends Specification {
    def "should calculate average"() {
        given:
        List list = Arrays.asList(
                new CurrencyRate(4.2135),
                new CurrencyRate(4.2461),
                new CurrencyRate(4.2370),
                new CurrencyRate(4.2409)
        )

        CalculationService calculationService = new CalculationService()

        when:
        CurrencyRate average = calculationService.calculateAverage(list)

        then:
        average == new CurrencyRate(4.2344)
    }

    def "should calculate standard deviation"() {
        given:
        List list = Arrays.asList(
                new CurrencyRate(4.2135),
                new CurrencyRate(4.2461),
                new CurrencyRate(4.2370),
                new CurrencyRate(4.2409)
        )

        CalculationService calculationService = new CalculationService()

        when:
        CurrencyRate average = calculationService.calculateStandardDeviation(list)

        then:
        average == new CurrencyRate(0.01247718)
    }
}
