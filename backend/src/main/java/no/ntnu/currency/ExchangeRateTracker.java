package no.ntnu.currency;

import currencyexchanger.RatesManager;
import currencyexchanger.RatesUpdateListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Tracks stores exchange rates, and updates the exchange rates every 30 minutes.
 */
public class ExchangeRateTracker implements RatesUpdateListener {
	private final Map<String, Double> rates;
	private final RatesManager ratesManager;

	/**
	 * Constructor.
	 *
	 * @param apiKey The api key to use for creating
	 *               {@link RatesManager.Builder#Builder(String) RatesManager.Builder}
	 */
	public ExchangeRateTracker(String apiKey) {
		this.rates = new HashMap<>();
		this.ratesManager = new RatesManager
				.Builder(apiKey)
				.setBaseCurrency("USD")
				.build();
		this.ratesManager.addListener(this);
	}

	public void startTracking() {
		ratesManager.start(30, TimeUnit.MINUTES);
	}

	@Override
	public void onUpdate(Map<String, Double> rates) {
		this.rates.clear();
		rates.forEach((currency, rate) -> this.rates.put(currency.toLowerCase(), rate));
	}

	/**
	 * Gets the specific exchange rate from one currency to another.
	 *
	 * @param fromCurrency The currency to exchange from
	 * @param toCurrency The currency to exchange to
	 * @return The exchange rate between the two currencies
	 */
	public double getRate(String fromCurrency, String toCurrency) {
		for (String currency : new String[]{fromCurrency, toCurrency}) {
			if (!rates.containsKey(currency)) {
				throw new IllegalArgumentException(String
						.format("No currency with name \"%s\" is being tracked", currency));
			}
		}
		return rates.get(toCurrency.toLowerCase())
				* Math.pow(rates.get(fromCurrency.toLowerCase()), -1);
	}

	/**
	 * Gets all available exchange rates.
	 *
	 * @param fromCurrency The currency to use as the base currency for all the exchange rates
	 * @return A map of all exchange rates
	 */
	public Map<String, Double> getRates(String fromCurrency) {
		if (!rates.containsKey(fromCurrency)) {
			throw new IllegalArgumentException(String
					.format("No currency with name \"%s\" is being tracked", fromCurrency));
		}
		return rates
				.keySet()
				.stream()
				.map(currency -> Map.entry(currency, getRate(fromCurrency, currency)))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
