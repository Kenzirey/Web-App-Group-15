<!-- src/pages/FormsPage.vue -->

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
    </fieldset>

    <fieldset class="fieldBox">
      <h4 class="formSections">Additional information</h4>

      <v-textarea
        v-model="additionalInfo"
        placeholder="If there are any more additional information the schools should know write here"
      ></v-textarea>
    </fieldset>

    <!--TODO: Action-->
    <v-btn text="submit" type="submit" href="/submit" />
  </form>

  <!-- Vue card med generel info om course, knapp går til forms der brukeren kan fylle ut info-->
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
      <v-btn text="Apply to course" type="apply" href="/forms"></v-btn>
    </v-card-actions>
  </v-card>
</template>

<style lang="scss" scoped>
.formBox {
  max-width: 1049px;
  margin: auto;
  background: rgb(var(--v-theme-primary));
  padding: 40px;
  border-radius: 4px;
  border-style: solid;
}

.fieldBox {
  background: white;
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
</style>

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
