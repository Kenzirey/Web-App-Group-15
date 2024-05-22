<template>
  <v-app @click="lastClick = $event" id="courseApp">
    <!--The header is the v-app-bar from the toolbar-->
    <TopToolbar class="toolbar" :lastGlobalClick="lastClick" />


      <main class="course-content">
        <!-- The main content of the page, replaced based on the current route -->
        <router-view></router-view>
      </main>

    <!--Footer component-->
    <Footer></Footer>
  </v-app>
</template>

<script>
import TopToolbar from './components/Toolbar.vue';
import Footer from './components/Footer.vue';

// import function to register Swiper custom elements
import { register } from 'swiper/element/bundle';
import { store } from './utility/store';
import { getCookie, setCookie } from './utility/cookieHelper';
import { watch } from 'vue';
// register Swiper custom elements
register();


export default {
  name: 'App',
  components: {
    TopToolbar,
    Footer
  },
  setup() {
    const jwt = getCookie("authToken");
    if (jwt) {
      store.login(getCookie("authToken"));
    }
  },
  data() {
    return { lastClick: null }
  },
  methods: {
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
			this.$currencies.value = currencies.map(currency => currency.toUpperCase());
			this.$currencies.value.sort();
			this.$currency.value = getCookie("currency") || this.getDefaultCurrency();
		});
  }
};
</script>

<style lang="scss">
:root {
  font-family: Arial, system-ui, Avenir, Helvetica, sans-serif;
  line-height: 1.5;
  font-weight: 400;
  --swiper-navigation-color: rgb(var(--v-theme-gradiantOne));
  font-synthesis: none;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

@media (max-width: 350px) {
  .course-content {
    margin: 59px auto 0 auto;
    font-size: 16px;
    line-height: 1.4;
    max-width: none !important;
  }

  /*To override the max-width bug with vue for mobile. At small width*/
  #courseApp .v-application__wrap {
    max-width: none;
  }

}

@media (min-width: 351px) {

  /* Inspector complained about duplicate ID of "app" due to v-app inherently having an id named app
  But noticed a bug on favorites and account page if I didn't target both
  That is where app + courseApp comes from. */
  .course-content {
    /* top right bottom left */
    margin: 80px auto 0 auto;
    text-align: center;
  }
}


/* Carousel */
#app .v-carousel {
  width: 100%;
  height: auto;
}

#app {
  color: rgb(var(--v-theme-background))
}

/* Course content, i.e. everything except the toolbar(topbar) */
.course-content {
  min-height: 95vh;
  /* Ensure minimum height */
  display: flex;
  flex-direction: column;
}

#contact {
  /* Ensure that the contact section does not take up more space than necessary */
  flex-grow: 1;
}



button {
  border-radius: 8px;
  border: 1px solid transparent;
  padding: 0.6em 1.2em;
  font-size: 1em;
  font-weight: 500;
  font-family: inherit;
  cursor: pointer;
  transition: border-color 0.25s;

  &:hover {
    background-color: rgb(var(--v-theme-buttonHover))
  }

  &:focus,
  button:focus-visible {
    outline: 4px auto rgb(var(--v-theme-buttonHover));
  }
}


h1 {
  font-size: 2.2em;
  line-height: 1.1;
}
</style>
