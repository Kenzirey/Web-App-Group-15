<template>

  <footer class="footer-container">
    <div class="footer-links">
      <v-btn id="about-us-button" @click="navigateTo('/about')" variant="text" size="large">About Us</v-btn>
      <v-btn id="contact-us-button" @click="navigateTo('/contact')" variant="text" size="large">Contact Us</v-btn>
      <v-btn id="disclaimer-button" @click="navigateTo('/disclaimer')" variant="text" size="large">Disclaimer</v-btn>
      <v-select :items="$currencies.value" variant="outlined" :model-value="$currency"></v-select>
    </div>
    <p>This website is a result of a university group project performed in the course IDATA2301 Web technologies at
      NTNU. All the information provided here is a result of imagination. Any resemblance with real companies or
      products is a coincidence.</p>
  </footer>

</template>

<script>
export default {
	name: 'Footer',
	methods: {
		navigateTo(route) {
			this.$router.push(route);
			window.scrollTo({top:0});
		}
	}
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
