<template>

  <footer class="footer-container">
    <div class="footer-links">
      <v-btn @click="navigateToAboutUs" variant="text" size="large">About Us</v-btn>
      <v-btn @click="navigateToContactUs" variant="text" size="large">Contact Us</v-btn>
      <v-btn @click="navigateDisclaimer" variant="text" size="large">Disclaimer</v-btn>
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
  display: flex;
  width: 100%;
  justify-content: center;
  gap: 15px;
}
</style>
