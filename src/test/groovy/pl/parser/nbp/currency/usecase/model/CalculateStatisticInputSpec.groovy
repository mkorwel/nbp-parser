package pl.parser.nbp.currency.usecase.model

import pl.parser.nbp.core.validate.exception.ValidateException
import spock.lang.Specification

import java.time.LocalDate

/**
 * Created by mateusz on 31/01/16.
 */
class CalculateStatisticInputSpec extends Specification {
    def "should create a valid object"() {
        given:
        String code = "EUR"
        String start = "2016-01-01"
        String end = "2016-01-31"
        String[] params = [code, start, end]

        when:
        CalculateStatisticInput input = CalculateStatisticInput.createValid(params)

        then:
        input.code == Currency.getInstance(code)
        input.startDate == LocalDate.parse(start)
        input.endDate == LocalDate.parse(end)
    }

    def "should throw exception when input is null"() {
        when:
        CalculateStatisticInput.createValid(null)

        then:
        thrown(ValidateException)
    }

    def "should throw exception when input contains wrong number of parameters"() {
        given:
        String code = "EUR"
        String start = "2016-01-01"
        String[] params = [code, start]

        when:
        CalculateStatisticInput.createValid(params)

        then:
        thrown(ValidateException)
    }

    def "should throw exception when currency is invalid"() {
        given:
        String code = "PLN"
        String start = "2016-01-01"
        String end = "2016-01-31"
        String[] params = [code, start, end]

        when:
        CalculateStatisticInput.createValid(params)

        then:
        thrown(ValidateException)
    }

    def "should throw exception when start date is invalid"() {
        given:
        String code = "EUR"
        String start = "startDate"
        String end = "2016-01-31"
        String[] params = [code, start, end]

        when:
        CalculateStatisticInput.createValid(params)

        then:
        thrown(ValidateException)
    }

    def "should throw exception when end date is invalid"() {
        given:
        String code = "EUR"
        String start = "2016-01-01"
        String end = "endDate"
        String[] params = [code, start, end]

        when:
        CalculateStatisticInput.createValid(params)

        then:
        thrown(ValidateException)
    }
}
