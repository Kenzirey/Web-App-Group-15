package no.ntnu.database.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;
import no.ntnu.currency.ExchangeRateTracker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for getting updated currency exchange rates.
 */
@CrossOrigin
@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {
	private final ExchangeRateTracker exchangeRateTracker;

	public ExchangeRateController(@Value("${exchange-rates.api-key}") String apiKey) {
		this.exchangeRateTracker = new ExchangeRateTracker(apiKey);
		exchangeRateTracker.startTracking();
	}

	/**
	 * Gets all available exchange rates.
	 *
	 * @param fromCurrency The currency to use as the base currency for all the exchange rates
	 *
	 * @return A map of all exchange rates
	 */
	@ApiResponse(
			responseCode = "200",
			description = "Exchange rates acquired successfully"
	)
	@ApiResponse(
			responseCode = "400",
			description = "The provided base currency was not recognized"
	)
	@GetMapping(value = "/{fromCurrency}", produces = {"application/json"})
	public ResponseEntity<Map<String, Double>> getExchangeRates(@PathVariable String fromCurrency) {
		ResponseEntity<Map<String, Double>> response;
		try {
			response = ResponseEntity.ok(exchangeRateTracker.getRates(fromCurrency));
		} catch (IllegalArgumentException iae) {
			response = ResponseEntity.badRequest().build();
		}
		return response;

	}

	/**
	 * Endpoint for getting the specific exchange rate from one currency to another.
	 *
	 * @param fromCurrency The currency to exchange from
	 * @param toCurrency The currency to exchange to
	 *
	 * @return The exchange rate between the two currencies
	 */
	@ApiResponse(
			responseCode = "200",
			description = "Exchange rate acquired successfully"
	)
	@ApiResponse(
			responseCode = "400",
			description = "Any of the provided currencies was not recognized"
	)
	@GetMapping(value = "/{fromCurrency}/{toCurrency}", produces = {"application/json"})
	public ResponseEntity<Double> getExchangeRate(
			@PathVariable String fromCurrency,
			@PathVariable String toCurrency
	) {
		ResponseEntity<Double> response;
		try {
			response = ResponseEntity.ok(exchangeRateTracker.getRate(fromCurrency, toCurrency));
		} catch (IllegalArgumentException iae) {
			response = ResponseEntity.badRequest().build();
		}
		return response;
	}
}
