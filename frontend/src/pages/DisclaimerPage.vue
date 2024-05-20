<template>
    <article class="disclaimer-page">
      <h1>Data Disclaimer</h1>
      <p>This site respects your privacy. We do <strong>not</strong> track, store, or analyze your browsing behavior for marketing purposes.
        Our website uses cookies solely for authentication to enhance security and to store your currency choice. These cookies are necessary for you to access and navigate our features securely.
        No personal data is captured or stored without your explicit consent, and we ensure that your data remains confidential and used strictly for the purposes stated. </p>
        <aside class="gdpr-link">
      <p>For more information on your data protection rights under the General Data Protection Regulation (GDPR), please visit the <a href="https://gdpr-info.eu/" target="_blank" rel="noopener noreferrer">GDPR Info Page</a>.</p>
    </aside>
    </article>
  </template>

  <script>
  export default {
    name: 'DisclaimerPage',
  };
  </script>

  <style scoped>
  .disclaimer-page {
    max-width: 700px;
    margin: 0 auto;
    padding: 20px;
  }

  .disclaimer-page h1 {
    text-align: center;
    margin-bottom: 20px;
  }

  .disclaimer-page p {
    text-align: left;
    line-height: 1.6;
    margin-bottom: 30px;
  }

  @media screen and (max-width: 600px) {
    .disclaimer-page {
        max-width: 65vh;
    }
  }
</style>
