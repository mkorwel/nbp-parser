package pl.parser.nbp.currency.service

import pl.parser.nbp.currency.client.CurrencyLocationFileClient
import pl.parser.nbp.currency.client.CurrencyRestClient
import pl.parser.nbp.currency.model.CurrencyFile
import pl.parser.nbp.currency.model.ExchangeRateTable
import spock.lang.Specification

import java.time.LocalDate


/**
 * Created by mateusz on 31/01/16.
 */
class CurrencyLocationFileServiceSpec extends Specification {
    CurrencyLocationFileService service;
    CurrencyLocationFileClient currencyLocationFileClient;
    CurrencyRestClient currencyRestClient;

    def setup(){
        currencyLocationFileClient = Mock(CurrencyLocationFileClient)
        currencyRestClient = Mock(CurrencyRestClient)

        service = new CurrencyLocationFileService(currencyLocationFileClient, currencyRestClient)
    }

    def "should return data for only one valid file"() {
        given:
        LocalDate start = LocalDate.parse("2016-01-01")
        LocalDate end = LocalDate.parse("2016-01-31")

        currencyLocationFileClient.getCurrencyFiles(_, _) >> Arrays.asList(
                new CurrencyFile("c001z160104"), new CurrencyFile("h001z160104"),
                new CurrencyFile("c001z150104"), new CurrencyFile("h001z150104")
        )

        currencyRestClient.list(_) >> new ExchangeRateTable()

        when:
        List<ExchangeRateTable> exchangeRateTables = service.getExchangeRateTables(start, end)

        then:
        exchangeRateTables.size() == 1
        1 * currencyRestClient.list(_)
    }

    def "should return data for all files when no one are invalid"() {
        given:
        LocalDate start = LocalDate.parse("2016-01-01")
        LocalDate end = LocalDate.parse("2016-01-31")

        currencyLocationFileClient.getCurrencyFiles(_, _) >> Arrays.asList(
                new CurrencyFile("c001z160104"), new CurrencyFile("c001z160105"),
                new CurrencyFile("c001z160106"), new CurrencyFile("c001z160107")
        )

        currencyRestClient.list(_) >> new ExchangeRateTable()

        when:
        List<ExchangeRateTable> exchangeRateTables = service.getExchangeRateTables(start, end)

        then:
        exchangeRateTables.size() == 4
        4 * currencyRestClient.list(_)
    }
}
