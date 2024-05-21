<template>

  <footer class="footer-container">
    <div class="footer-links">
      <v-btn id="about-us-button" @click="navigateToAboutUs" variant="text" size="large">About Us</v-btn>
      <v-btn id="contact-us-button" @click="navigateToContactUs" variant="text" size="large">Contact Us</v-btn>
      <v-btn id="disclaimer-button" @click="navigateDisclaimer" variant="text" size="large">Disclaimer</v-btn>
      <v-select :items="currencies" variant="outlined" :model-value="$currency"></v-select>
    </div>
    <p>This website is a result of a university group project performed in the course IDATA2301 Web technologies at
      NTNU. All the information provided here is a result of imagination. Any resemblance with real companies or
      products is a coincidence.</p>
  </footer>

</template>

<script>
import { getCookie, setCookie } from '@/utility/cookieHelper';
import { watch } from 'vue';

export default {
	name: 'Footer',
	data() {
		return {
			currencies: ["NOK", "SEK", "DKK", "EUR", "USD", "GBP"]
		}
	},
	methods: {
		navigateToAboutUs() {
      this.$router.push('/about')
      window.scrollTo({top:0});
    },
    navigateToContactUs() {
      this.$router.push('/contact')
      window.scrollTo({top:0});
    },
    navigateDisclaimer() {
      this.$router.push('/disclaimer')
      window.scrollTo({top:0});
    },
		checkLang(langSubstring) {
			return navigator.language.replace(/-/g, "_").toUpperCase().includes(`_${langSubstring.toUpperCase()}`);
		},
		checkLangAny(...langSubstrings) {
			return langSubstrings.some(langSubstring => this.checkLang(langSubstring));
		},
		getFirstCurrency(...currencies) {
			let i = 0;
			let next = null;
			while (i < currencies.length && !this.currencies.includes(next)) {
				next = currencies[i].toUpperCase();
				i++;
			}
			return next || "USD";
		},
		getDefaultCurrency() {
			let currency;
			if (this.checkLangAny("US", "VG", "EC", "SV", "GU", "TL", "MH", "FM", "PW", "MP", "PR", "TC", "VI", "IO", "BQ", "UM")) {
				currency = this.getFirstCurrency("USD");
			} else if (this.checkLang("CA")) {
				currency = this.getFirstCurrency("CAD", "USD");
			} else if (this.checkLangAny("AU", "CX", "CC", "NR", "NF")) {
				currency = this.getFirstCurrency("AUS", "USD");
			} else if (this.checkLangAny(
				"NL", "AD", "BE", "ES", "GP", "IE", "AT", "GR", "HR", "CY", "LV", "LT", "LU", "MT", "MQ", "YT", "MC",
				"PT", "FR", "GF", "RE", "PM", "DE", "SM", "SK", "SI", "FI", "VA", "EE", "ME", "BL", "XK", "AX", "MF"
			)) {
				currency = this.getFirstCurrency("EUR");
			} else if (this.checkLangAny("NO", "SJ")) {
				currency = this.getFirstCurrency("NOK", "EUR");
			} else if (this.checkLang("SE")) {
				currency = this.getFirstCurrency("SEK", "EUR");
			} else if (this.checkLangAny("DK", "GL", "FO")) {
				currency = this.getFirstCurrency("DKK", "EUR");
			} else if (this.checkLang("GB")) {
				currency = this.getFirstCurrency("GBP", "EUR");
			} else {
				currency = this.getFirstCurrency();
			}
			return currency;
		}
	},
	created() {
		watch(this.$currency, newVal => setCookie("currency", newVal));
		fetch(this.$backendUrl + "exchange").then(response => response.json()).then(currencies => {
			this.currencies = currencies.map(currency => currency.toUpperCase());
			this.currencies.sort();
			this.$currency.value = getCookie("currency") || this.getDefaultCurrency();
		});
	},
}
</script>

<style scoped lang="scss">
.footer-container>p {
	flex-basis: 100%;
	/* Ensure the paragraph takes the full width when wrapping */
	text-align: center;
	/* Center-align the text of the paragraph */
	padding-top: 10px;
}

.v-btn {
	color: rgb(var(--v-theme-background));
}

.footer-container {
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	align-items: center;
	flex-wrap: wrap;
	background-image: linear-gradient(to right, rgb(var(--v-theme-gradiantOne)), rgb(var(--v-theme-gradiantTwo)));
	/* Top&bottom, left&right. Now we avoid multiple padding declarations*/
	padding: 24px 16px;
	color: rgb(var(--v-theme-background));
}

.footer-links {
	display: grid;
	grid-template-columns: 1fr auto auto auto 1fr;
	width: 100%;
	justify-content: center;
	gap: 15px;
}

#about-us-button {
	grid-column-start: 2;
	grid-column-end: 3;
}

#contact-us-button {
	grid-column-start: 3;
	grid-column-end: 4;
}

#disclaimer-button {
  grid-column-start: 4;
  grid-column-end: 5;
}

.footer-links .v-select {
	justify-self: end;
	grid-column-start: 5;
	grid-column-end: 6;
}

@media screen and (max-width: 768px) {
	.footer-links .v-select {
		justify-self: center;
		grid-column-start: 1;
		grid-column-end: 6;
		grid-row-start: 2;
		grid-row-end: 3;
	}
}

@media screen and (max-width: 480px) {
	.footer-links {
		justify-content: center;
		display: flex;
    flex-direction: column;
	}

  .v-btn {
      padding: 6px 8px;
    }
}

</style>
