package pl.parser.nbp.currency.usecase.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.parser.nbp.core.configuration.ApplicationConstant;
import pl.parser.nbp.core.validate.exception.ValidateException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;

/**
 * Created by mateusz on 31/01/16.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateStatisticInput {
	private final Currency code;
	private final LocalDate startDate;
	private final LocalDate endDate;

	public static CalculateStatisticInput createValid(String... params) {
		if (params == null || params.length != 3) {
			throw new ValidateException("Wrong params number");
		}

		Currency code = getCurrencyOrException(params[0]);
		LocalDate startDate = getLocalDateOrException(params[1]);
		LocalDate endDate = getLocalDateOrException(params[2]);

		if (startDate.isAfter(endDate)) {
			throw new ValidateException("End date cannot be before start date.");
		}

		return new CalculateStatisticInput(code, startDate, endDate);
	}

	private static Currency getCurrencyOrException(String param) {
		if (ApplicationConstant.VALID_CURRENCY_CODE.contains(param)) {
			return Currency.getInstance(param);
		}

		throw new ValidateException("Illegal currency code.");
	}

	private static LocalDate getLocalDateOrException(String param) {
		try {
			return LocalDate.parse(param);
		} catch (DateTimeParseException e) {
			throw new ValidateException("Wrong date format", e);
		}
	}
}
