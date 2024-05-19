package no.ntnu.currency;

import currencyexchanger.RatesManager;
import currencyexchanger.RatesUpdateListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Tracks stores exchange rates, and updates the exchange rates every 30 minutes.
 */
public class ExchangeRateTracker implements RatesUpdateListener {
	private static RatesManager ratesManager;

	private final Map<String, Double> rates = new HashMap<>();

	/**
	 * Constructor.
	 */
	public ExchangeRateTracker() {
		if (!isStarted()) {
			throw new IllegalStateException("Tracker not yet started");
		}
		ratesManager.addListener(this);
	}

	public static boolean isStarted() {
		return ratesManager != null;
	}

	/**
	 * Starts tracking currencies. Updated every 30min.
	 *
	 * @param apiKey The api key to use for creating
	 *               {@link RatesManager.Builder#Builder(String) RatesManager.Builder}
	 */
	public static void startTracking(String apiKey) {
		if (isStarted()) {
			throw new IllegalStateException("Tracker already started");
		}
		ratesManager = new RatesManager
				.Builder(apiKey)
				.setBaseCurrency("USD")
				.build();
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
		for (String currency : new String[]{fromCurrency.toLowerCase(), toCurrency.toLowerCase()}) {
			if (!rates.containsKey(currency)) {
				throw new IllegalArgumentException(String
						.format("No currency with name \"%s\" is being tracked", currency));
			}
		}
		return rates.get(toCurrency.toLowerCase())
				* Math.pow(rates.get(fromCurrency.toLowerCase()), -1);
	}

	/**
	 * Gets all available currencies.
	 *
	 * @return A map of all available currencies
	 */
	public Set<String> getCurrencies() {
		return rates.keySet();
	}
}
