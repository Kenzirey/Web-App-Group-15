package no.ntnu.database.service;

import java.util.Set;
import no.ntnu.currency.ExchangeRateTracker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for exchanging currencies.
 */
@Service
public class ExchangeRateService {
	private final ExchangeRateTracker exchangeRateTracker;

	/**
	 * Constructor.
	 *
	 * @param apiKey The api key to use if {@link ExchangeRateTracker}
	 *               hasn't been {@link ExchangeRateTracker#startTracking(String) started} yet.
	 *               This is autowired from the {@code exchange-rates.api-key} environment variable
	 */
	public ExchangeRateService(@Value("${exchange-rates.api-key}") String apiKey) {
		if (!ExchangeRateTracker.isStarted()) {
			ExchangeRateTracker.startTracking(apiKey);
		}
		exchangeRateTracker = new ExchangeRateTracker();
	}

	public double exchangeAmount(double amount, String fromCurrency, String toCurrency) {
		return amount * exchangeRateTracker.getRate(fromCurrency, toCurrency);
	}

	public Set<String> getCurrencies() {
		return exchangeRateTracker.getCurrencies();
	}
}
