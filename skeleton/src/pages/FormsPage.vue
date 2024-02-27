<!-- src/pages/Forms.vue -->

<template>
  <form class="formBox">
    <fieldset>
      <h1 class="formSections">Personal information</h1>

      <div class="flex">
        <label class="labelName">First Name:</label>

        <label class="labelName">Last Name:</label>
      </div>

      <div class="flex">
        <input class="userInput" type="First name" required v-model="firstName"/>
        <input class="userInput" type="Last name" required v-model="lastName" />
      </div>

      <label class="labelName">E-mail:</label>

      <input class="userInput" type="E-mail" required v-model="email" />

      <label class="labelName">Phone Number:</label>
      <input
        class="userInput"
        type="Phone number"
        required
        v-model="phoneNumber"
      />

      <v-select v-model="select" :items="genders" label="Gender:" required>
      </v-select>
    </fieldset>

    
    <fieldset>
        <h2 class="formSections">Additional information</h2>
        <div class="input-group">
        <textarea class="textareaFormat"
        v-model="additionalInfo" 
        name="additionalInfo" 
        placeholder="If there are any more additional information the schools should know write here"></textarea>
      </div>
    </fieldset>
  </form>
</template>

<style scoped>
@import "../assets/forms.css";
</style>

<!--TODO Finne ut hvor dataen skal bli sendt til?????????-->
<script>
export default {
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      select: null,
      genders: ["Male", "Female", "Other", "Prefer not to say", "Femboy"],
      additionalInfo: '',


    };
  },
};
</script>
