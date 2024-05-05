<template>
  <v-card title="SQL for beginners" variant="outlined">
    <v-card-text>
      <!-- Fyll in  text her med info om course-->

      <div class="info-item">
        <span class="key">Course Providers:</span>
        <span class="value">NTNU</span>
      </div>
      <div class="info-item">
        <span class="key">Difficulty Level:</span>
        <span class="value">Beginner</span>
      </div>
      <div class="info-item">
        <span class="key">Course Size:</span>
        <span class="value">ECTs Credits</span>
      </div>
    </v-card-text>

    <v-card-actions>
      <v-btn
        text="Apply to course"
        type="apply"
        href="/forms"
        variant="outlined"
      ></v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "InformationCardCourse",
};
</script>
