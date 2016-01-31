package pl.parser.nbp.currency.client

import org.junit.Rule
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.rule.PowerMockRule
import pl.parser.nbp.currency.model.CurrencyFile
import spock.lang.Specification

import java.time.Year

import static org.powermock.api.mockito.PowerMockito.mockStatic
import static org.powermock.api.mockito.PowerMockito.when

/**
 * Created by mateusz on 31/01/16.
 */
@PrepareForTest(UrlLocationFileBuilder.class)
class CurrencyLocationFileClientSpec extends Specification {
    @Rule
    PowerMockRule powerMockRule = new PowerMockRule();

    CurrencyLocationFileClient client = new CurrencyLocationFileClient()
    String mockedUrlFor2016
    String mockedUrlFor2015

    def setup() {
        mockedUrlFor2016 = "file:" + getClass().getResource("/test-dir.txt").getPath()
        mockedUrlFor2015 = "file:" + getClass().getResource("/test-dir-2015.txt").getPath()

        mockStatic(UrlLocationFileBuilder.class)
    }

    def "should return currency files for 2016"() {
        given:
        initMock()

        when:
        List<CurrencyFile> files = client.getCurrencyFiles(Year.parse("2016"), Year.parse("2016"))

        then:
        files.size() == 2
        files.containsAll(
                Arrays.asList(new CurrencyFile("c001z160104"), new CurrencyFile("h001z160104"))
        )
    }

    def "should return currency files for 2015 and 2016"() {
        given:
        initMock()

        when:
        List<CurrencyFile> files = client.getCurrencyFiles(Year.parse("2015"), Year.parse("2016"))

        then:
        files.size() == 4
        files.containsAll(
                Arrays.asList(new CurrencyFile("c001z160104"), new CurrencyFile("h001z160104"),
                        new CurrencyFile("c001z150104"), new CurrencyFile("h001z150104"))
        )
    }

    void initMock() {
        mockStatic(UrlLocationFileBuilder.class)
        when(UrlLocationFileBuilder.buildFor(Year.parse("2016"))).thenReturn(new URL(mockedUrlFor2016))
        when(UrlLocationFileBuilder.buildFor(Year.parse("2015"))).thenReturn(new URL(mockedUrlFor2015))
    }
}
