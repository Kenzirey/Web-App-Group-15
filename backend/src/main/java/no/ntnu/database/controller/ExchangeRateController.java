package no.ntnu.database.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Optional;
import java.util.Set;
import no.ntnu.database.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/exchange")
public class ExchangeRateController {
	private final ExchangeRateService exchangeRateService;

	@Autowired
	public ExchangeRateController(ExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}

	/**
	 * Gets all available currencies.
	 *
	 * @return A set of all currencies
	 */
	@ApiResponse(
			responseCode = "200",
			description = "Currencies acquired successfully"
	)
	@GetMapping(produces = {"application/json"})
	public Set<String> getExchangeRates() {
		return exchangeRateService.getCurrencies();

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
	@GetMapping(value = "/{fromCurrency}/{toCurrency}/{amount}", produces = {"application/json"})
	public ResponseEntity<Double> exchange(
			@PathVariable String fromCurrency,
			@PathVariable String toCurrency,
			@PathVariable(required = false) Optional<Double> amount
	) {
		ResponseEntity<Double> response;
		try {
			response = ResponseEntity.ok(exchangeRateService
					.exchangeAmount(amount.orElse(1d), fromCurrency, toCurrency)
			);
		} catch (IllegalArgumentException iae) {
			response = ResponseEntity.badRequest().build();
		}
		return response;
	}
}
