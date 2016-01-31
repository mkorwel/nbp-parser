package pl.parser.nbp.currency.model

import spock.lang.Specification

import java.time.LocalDate

/**
 * Created by mateusz on 31/01/16.
 */
class CurrencyFileSpec extends Specification {
    def "should return valid file name"() {
        expect:
        new CurrencyFile(fileNameIn).getFileName().toString() == fileNameOut

        where:
        fileNameIn    || fileNameOut
        "c001z160104" || "c001z160104"
        "h001z160104" || "h001z160104"
        "a001z160104" || "a001z160104"
        "c002z160105" || "c002z160105"
        "h002z160105" || "h002z160105"
        "a002z160105" || "a002z160105"
        "b001z160105" || "b001z160105"
    }

    def "should return information if the file is contains cuy and sell rates"() {
        expect:
        new CurrencyFile(fileName).isBuyAndSell() == result

        where:
        fileName      || result
        "c001z160104" || true
        "h001z160104" || false
        "a001z160104" || false
        "c002z160105" || true
        "h002z160105" || false
        "a002z160105" || false
        "b001z160105" || false
    }

    def "should return information if the file is between dates"() {
        expect:
        new CurrencyFile(fileName).isBetweenDates(start, end) == result

        where:
        fileName      | start                         | end                           || result
        "c001z160104" | LocalDate.parse("2016-01-04") | LocalDate.parse("2016-01-04") || true
        "c001z160104" | LocalDate.parse("2016-01-01") | LocalDate.parse("2016-01-04") || true
        "c001z160104" | LocalDate.parse("2016-01-04") | LocalDate.parse("2016-01-05") || true
        "c001z160104" | LocalDate.parse("2016-01-01") | LocalDate.parse("2016-01-02") || false
        "h002z160105" | LocalDate.parse("2016-01-05") | LocalDate.parse("2016-01-05") || true
        "h002z160105" | LocalDate.parse("2016-01-01") | LocalDate.parse("2016-01-05") || true
        "h002z160105" | LocalDate.parse("2016-01-05") | LocalDate.parse("2016-01-06") || true
        "h002z160105" | LocalDate.parse("2016-01-01") | LocalDate.parse("2016-01-02") || false
    }
}
