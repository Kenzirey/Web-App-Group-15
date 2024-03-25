package no.ntnu.currency;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Informs the IDE of custom properties in the "application.properties" file
 */
@Configuration
@ConfigurationProperties(prefix = "exchange-rates")
public class ExchangeRatesConfigurator {
	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
