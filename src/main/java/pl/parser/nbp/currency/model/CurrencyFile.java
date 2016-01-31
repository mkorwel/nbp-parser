package pl.parser.nbp.currency.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by mateusz on 30/01/16.
 */
@Getter
@EqualsAndHashCode
public class CurrencyFile {
	private final FileName fileName;
	private final LocalDate publishDate;
	private final TableType type;

	public CurrencyFile(String name) {
		this.fileName = new FileName(name);
		this.publishDate = LocalDate.parse(name.substring(5, 11), DateTimeFormatter.ofPattern("yyMMdd"));
		this.type = TableType.valueOf(name.substring(0, 1).toUpperCase());
	}

	public boolean isBuyAndSell() {
		return TableType.C == type;
	}

	public boolean isBetweenDates(LocalDate start, LocalDate end) {
		return (!start.isAfter(publishDate) && !end.isBefore(publishDate));
	}

	private enum TableType {
		A, B, C, H;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	@Getter
	@EqualsAndHashCode
	@RequiredArgsConstructor
	public class FileName {
		private final String name;

		@Override
		public String toString() {
			return name;
		}
	}
}
