<!-- src/pages/Forms.vue -->

<template>
  <form class="formBox">
    <fieldset class="fieldBox">
      <h1 class="formSections">Personal information</h1>

      <div class="flex">
        <div class="inputGroup">
          <label class="labelNameSide">First Name:</label>
          <input
            class="userInput"
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
          <input class="userInput" type="E-mail" required v-model="email" />
        </div>

        <div class="inputGroup">
          <label class="labelName">Phone Number:</label>
          <input
            class="userInput"
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

    <fieldset class="fieldBox">
      <h1 class="formSections">Address</h1>

      <label class="labelName">Street Address:</label>
      <input
        class="userInputLong"
        type="Street Address"
        required
        v-model="streetAddress"
      />

      <div class="flex">
        <div class="inputGroup">
          <label class="labelName">City:</label>
          <input class="userInput" type="City" required v-model="City"/>
        </div>

        <div class="inputGroup">
          <label class="labelName">PostCode:</label>
          <input
            class="userInput"
            type="PostCode"
            required
            v-model="Postcode"
          />
        </div>
      </div>

      <!-- TODO legg til land der brukeren bor,dette gidder jeg ikke nå lol-->
      <v-select
        class="selectFormat"
        v-model="select"
        :items="Country"
        label="Country"
        required
      ></v-select>
    </fieldset>

    <fieldset class="fieldBox">
      <h2 class="formSections">Education Background</h2>
      <p class="labelParagraph">List your previous schools</p>
      <label class="labelName">HighSchool address</label>
      <input
        class="userInputLong"
        type="HighSchool"
        required
        v-model="highschool"
      />

      <label class="labelName">Date graduated</label>
      <input
        class="userInputLong"
        type="Date graduated"
        required
        v-model="dateGraduated"
      />
    </fieldset>

    <!--Temporary name finner på noe senere enn Application Details-->
    <fieldset class="fieldBox">
      <h3 class="formSections">Application Details</h3>
      <p class="labelParagraph">Information about the course</p>

      <label class="labelName">Course Name:</label>
      <!--Få denne til å lese fra databasen?-->
      <input
        class="userInputLong"
        type="Course Name"
        required
        v-model="courseName"
      />

      <label class="labelName">University:</label>
      <input
        class="userInputLong"
        type="University"
        required
        v-model="university"
      />

      <label class="labelName">Course provider:</label>
      <input
        class="userInputLong"
        type="Course proivder"
        required
        v-model="courseProivder"
      />

      <label class="labelName">Course id:</label>
      <input class="userInputLong" type="Course id" required v-model="courseId" />
    </fieldset>

    <fieldset class="fieldBox">
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
