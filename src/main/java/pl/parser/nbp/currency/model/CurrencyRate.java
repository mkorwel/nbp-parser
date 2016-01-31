package pl.parser.nbp.currency.model;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by mateusz on 31/01/16.
 */
@EqualsAndHashCode
public class CurrencyRate {
	public static final CurrencyRate ZERO = new CurrencyRate(BigDecimal.ZERO);

	private BigDecimal value;

	public CurrencyRate(BigDecimal value) {
		this.value = value;
	}

	public CurrencyRate(double value) {
		this(BigDecimal.valueOf(value));
	}

	public CurrencyRate plus(CurrencyRate rate) {
		return new CurrencyRate(value.add(rate.value));
	}

	public CurrencyRate minus(CurrencyRate rate) {
		return new CurrencyRate(value.subtract(rate.value));
	}

	public CurrencyRate divide(int size) {
		return new CurrencyRate(value.divide(BigDecimal.valueOf(size), RoundingMode.HALF_UP));
	}

	public CurrencyRate pow(int level) {
		return new CurrencyRate(value.pow(level));
	}

	public CurrencyRate sqrt() {
		BigDecimal number = value;
		BigDecimal level = BigDecimal.ONE;
		RoundingMode rounding = RoundingMode.HALF_UP;
		BigDecimal two = BigDecimal.valueOf(2);

		BigDecimal result = BigDecimal.ZERO;
		BigDecimal flipA = result;
		BigDecimal flipB = result;
		boolean first = true;

		while (result.compareTo(level) != 0) {
			if (!first) {
				level = result;
			} else {
				first = false;
			}

			result = number.divide(level, rounding).add(level).divide(two, rounding);
			if (result.equals(flipB)) {
				return new CurrencyRate(flipA);
			}

			flipB = flipA;
			flipA = result;
		}

		return new CurrencyRate(result);
	}

	@Override
	public String toString() {
		return value.setScale(4, RoundingMode.HALF_UP).toString();
	}
}
