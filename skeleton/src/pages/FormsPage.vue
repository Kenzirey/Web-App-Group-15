<!-- src/pages/Forms.vue -->

<template>
  <form class="formBox">
    <fieldset>
      <h1 class="formSections">Personal information</h1>

      <div class="flex">
        <div class="inputGroup">
          <label class="labelNameSide">First Name:</label>
          <input
            class="userInputSide"
            type="First name"
            required
            v-model="firstName"
            hide-details
          />
        </div>

        <div class="inputGroup">
          <label class="labelNameSide">Last Name:</label>
          <input
            class="userInput"
            type="Last name"
            required
            v-model="lastName"
          />
        </div>
      </div>

      <div class="flex">
        <div class="inputGroup">
          <label class="labelNameSide">E-mail:</label>
          <input class="userInputSide" type="E-mail" required v-model="email" />
        </div>

        <div class="inputGroup">
          <label class="labelNameSide">Phone Number:</label>
          <input
            class="userInputSide"
            type="Phone number"
            required
            v-model="phoneNumber"
          />
        </div>
      </div>

      <v-select
        v-model="select"
        :items="genders"
        label="Gender:"
        required
      ></v-select>

      <v-container>
        <v-row justify="start">
          <v-date-picker
            title="Select birth date"
            show-adjacent-months
            color="primary"
            width="500"
          ></v-date-picker>
        </v-row>
      </v-container>
    </fieldset>

    <fieldset>
      <h2 class="formSections">Education Background</h2>
      <p class="labelParagraph">List your previous schools</p>
      <label class="labelName">HighSchool address</label>
      <input
        class="userInput"
        type="HighSchool"
        required
        v-model="highschool"
      />

      <label class="labelName">Date graduated</label>
      <input
        class="userInput"
        type="Date graduated"
        required
        v-model="dateGraduated"
      />
    </fieldset>

    <!--Temporary name finner på noe senere enn Application Details-->
    <fieldset>
      <h3 class="formSections">Application Details</h3>
      <p class="labelParagraph">Information about the course</p>

      <label class="labelName">Course Name</label>
      <!--Få denne til å lese fra databasen?-->
      <input
        class="userInput"
        type="Course Name"
        required
        v-model="courseName"
      />

      <label class="labelName">University</label>
      <input
        class="userInput"
        type="University"
        required
        v-model="university"
      />

      <label class="labelName">Course proivder</label>
      <input
        class="userInput"
        type="Course proivder"
        required
        v-model="courseProivder"
      />

      <label class="labelName">Course id</label>
      <input class="userInput" type="Course idy" required v-model="courseId" />
    </fieldset>

    <fieldset>
      <h4 class="formSections">Additional information</h4>
      <div class="input-group">
        <textarea
          class="textareaFormat"
          v-model="additionalInfo"
          name="additionalInfo"
          placeholder="If there are any more additional information the schools should know write here"
        ></textarea>
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

      highschool: "",
      dateGraduated: "",

      courseName: "",
      university: "",
      courseProivder: "",
      courseId: "",

      additionalInfo: "",
    };
  },
};
</script>
