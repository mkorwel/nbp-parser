package pl.parser.nbp.currency.client

import spock.lang.Specification

import java.time.Year

/**
 * Created by mateusz on 31/01/16.
 */
class UrlLocationFileBuilderSpec extends Specification {
    def "should return valid url to file"() {
        expect:
        UrlLocationFileBuilder.buildFor(year) == url

        where:
        year               || url
        Year.parse("2016") || new URL("http://www.nbp.pl/kursy/xml/dir.txt")
        Year.parse("2015") || new URL("http://www.nbp.pl/kursy/xml/dir2015.txt")
        Year.parse("2014") || new URL("http://www.nbp.pl/kursy/xml/dir2014.txt")
    }
}
