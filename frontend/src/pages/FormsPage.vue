<!-- src/pages/Forms.vue -->

<template>
  <form class="formBox">
    <fieldset class="fieldBox">
      <h1 class="formSections">Personal information</h1>

      <v-form v-model="valid">
        <v-container>
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="firstName"
                :rules="nameRules"
                label="First name"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="lastName"
                :rules="nameRules"
                label="Last name"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-form>

      <!-- Email, Phone number -->

      <v-form v-model="valid">
        <v-container>
          <v-row>
            <v-col cols="12" md="15">
              <v-text-field
                v-model="email"
                :rules="emailRules"
                label="E-mail"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="15">
              <v-text-field
                v-model.number="age"
                :counter="8"
                label="Phone number"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-form>

      <v-col cols="12" md="15">
        <v-select
          v-model="selectGender"
          :items="genders"
          label="Gender:"
          required
        ></v-select>
      </v-col>

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

    <!-- Address og by-->

    <fieldset class="fieldBox">
      <h1 class="formSections">Address</h1>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="address"
          :counter="10"
          label="Address"
          hide-details
          required
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="4"> </v-col>

      <v-row>
        <v-col cols="15" md="6">
          <v-text-field
            v-model="cityName"
            :counter="10"
            label="City"
            required
          ></v-text-field>
        </v-col>

        <v-col cols="12" md="6">
          <v-text-field
            v-model="postCode"
            :counter="10"
            label="Postcode"
            required
          ></v-text-field>
        </v-col>

        <v-col cols="12" md="4"> </v-col>
      </v-row>

      <!-- TODO legg til land der brukeren bor-->
      <v-select
        class="selectFormat"
        v-model="selectCountry"
        :items="Country"
        label="Country"
        required
      ></v-select>
    </fieldset>

    <!--Education background-->

    <fieldset class="fieldBox">
      <h2 class="formSections">Education Background</h2>
      <p class="labelParagraph">List your previous schools</p>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="highSchoolAddress"
          label="HighSchool address"
          required
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="dateGraduated"
          label="Date graduated"
          required
        ></v-text-field>
      </v-col>
    </fieldset>

    <!-- Application Details -->
    <!--Temporary name finner på noe senere enn Application Details-->
    <fieldset class="fieldBox">
      <h3 class="formSections">Application Details</h3>
      <p class="labelParagraph">
        Information about the course you are attendending
      </p>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="courseName"
          label="Course Name"
          required
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="university"
          label="University:"
          required
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="courseProivder"
          label="Course provider:"
          required
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="courseId"
          label="Course id:"
          required
        ></v-text-field>
      </v-col>
    </fieldset>

    <fieldset class="fieldBox">
      <h4 class="formSections">Additional information</h4>

      <v-textarea
        v-model="additionalInfo"
        placeholder="If there are any more additional information the schools should know write here"
      ></v-textarea>
    </fieldset>

    <!--TODO: Action-->
    <v-btn text="submit" type="submit" href="/submit"/>

  

  </form>
</template>

<style scoped>
.formBox {
  max-width: 1049px;
  margin: auto;
  background: rgb(81, 132, 190);
  padding: 40px;
  border-radius: 4px;
  border-style: solid;
  border: 3px solid black;
}

.fieldBox {
  background: white;
}

.labelName {
  color: Black;
  padding: 10px;
  display: block;
  margin: 25px 0 15px;
  text-align: left;
  font-size: 15px;
  letter-spacing: 1px;
  font-weight: bold;
}

.userInput {
  font-size: 20px;
  border-style: solid;
  border-radius: 4px;
  border-width: 1px;
  border-color: #474b56;
  margin-bottom: 20px;
}

.userInputLong {
  width: 475px;
  height: 32px;
  font-size: 20px;
  border-style: solid;
  border-radius: 4px;
  border-width: 1px;
  border-color: #474b56;
  margin-bottom: 20px;
}

.inputGroup {
  display: flex;
  flex-direction: column;
  width: 45%;
}

.labelNameSide {
  color: Black;
  padding: 10px;
  margin: 25px 0 15px;
  text-align: left;
  font-size: 15px;
  letter-spacing: 1px;
  font-weight: bold;
}

.labelParagraph {
  color: Black;
  padding: 10px;
  display: block;
  margin: 25px 5 15px;
  text-align: left;
  font-size: 15px;
  letter-spacing: 1px;
  font-weight: bold;
}

.formSections {
  color: Black;
  padding: 10px;
  display: block;
  margin: 25px 5 15px;
  text-align: left;
  font-size: x-large;
  letter-spacing: 1px;
  font-weight: bold;
}

.flex {
  display: flex;
  justify-content: space-between;
}

.textareaFormat {
  width: 100%;
  height: 200px;
}
</style>

<!--TODO Finne ut hvor dataen skal bli sendt til?????????-->
<script>
export default {
  data() {
    return {
      firstName: "",
      lastName: "",
      nameRules: [
        (value) => {
          if (value) return true;

          return "Name is required.";
        },
      ],

      emailRules: [
        (value) => {
          if (value) return true;

          return "E-mail is requred.";
        },
        (value) => {
          if (/.+@.+\..+/.test(value)) return true;

          return "E-mail must be valid.";
        },
      ],

      email: "",
      phoneNumber: "",
      selectGender: "Select gender",
      selectCountry: "Select country",

      genders: [
        "Male",
        "Female",
        "Third other thing",
        "Other",
        "Prefer not to say",
      ],

      highSchoolAddress: "",
      dateGraduated: "",
      cityName: "",
      postCode: "",

      courseName: "",
      university: "",
      courseProivder: "",
      courseId: "",

      additionalInfo: "",
    };
  },
};
</script>
