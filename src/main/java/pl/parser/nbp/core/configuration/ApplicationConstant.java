package pl.parser.nbp.core.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by mateusz on 30/01/16.
 */
public class ApplicationConstant {
	public static final String URL = "http://www.nbp.pl";
	public static final List VALID_CURRENCY_CODE = Collections.unmodifiableList(Arrays.asList("USD", "EUR", "CHF", "GBP"));
}
