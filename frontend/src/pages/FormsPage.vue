<!-- src/pages/FormsPage.vue -->

<template>
  <v-form v-model="valid" class="formBox" ref="form">
    <fieldset class="fieldBox">
      <h1 class="formSections">Personal information</h1>

      <!-- First name, Last name, Email, Phone number, Gender -->

      <v-container>
        <v-row>
          <v-col cols="12" md="6">
            <v-text-field
              v-model="firstName"
              :rules="InputRules"
              label="First name"
              required
            ></v-text-field>
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              v-model="lastName"
              :rules="InputRules"
              label="Last name"
              required
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>

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
              v-model="phoneNumber"
              @keypress="filter(event)"
              :counter="8"
              :rules="InputRules"
              maxlength="8"
              label="Phone number"
              required
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>

      <v-col cols="12" md="15">
        <v-select
          v-model="selectGender"
          :items="genders"
          :rules="[(v) => !!v || 'Please select a gender']"
          label="Gender:"
          required
          placeholder="Select gender"
        ></v-select>
      </v-col>

      <v-container>
        <v-row justify="start">
          <v-date-picker 
            title="Select birth date"
            show-adjacent-months
            width="500px"
            color="gradiantTwo"
          ></v-date-picker>
        </v-row>
      </v-container>

    </fieldset>

    <!-- Address, city postcode, country-->

    <fieldset class="fieldBox">
      <h1 class="formSections">Address</h1>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="address"
          label="Address"
          :rules="InputRules"
          hide-details
          required
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="4"> </v-col>
      <v-container>
        <v-row>
          <v-col cols="15" md="6">
            <v-text-field
              v-model="cityName"
              label="City"
              :rules="InputRules"
              required
            ></v-text-field>
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              v-model="postCode"
              @keypress="filter(event)"
              :rules="InputRules"
              label="Postcode"
              required
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>

      <v-col cols="12" md="4"> </v-col>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="country"
          label="Country"
          :rules="InputRules"
          hide-details
          required
        ></v-text-field>
      </v-col>
    </fieldset>

    <!-- Application Details -->
    <fieldset class="fieldBox">
      <h3 class="formSections">Application Details</h3>
      <p class="labelParagraph">
        Information about the course you are attending
      </p>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="courseName"
          label="Course Name"
          :rules="InputRules"
          required
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="15">
        <v-text-field
          v-model="university"
          label="University"
          :rules="InputRules"
          required
        ></v-text-field>
      </v-col>
    </fieldset>

    <!-- Additional information -->
    <fieldset class="fieldBox">
      <h4 class="formSections">Additional information</h4>

      <v-textarea
        v-model="additionalInfo"
        placeholder="If there is any more additional information the school should know, please write here"
      ></v-textarea>
    </fieldset>

    <v-btn text="submit" type="submit" @click.prevent="validate" class="mr-3"
      >Submit</v-btn
    >
    <v-btn @click="clearForm">Clear</v-btn>
  </v-form>
</template>

<style lang="scss" scoped>
.formBox {
  max-width: 1049px;
  margin: auto;
  background: linear-gradient(
    to right,
    rgb(var(--v-theme-gradiantOne)),
    rgb(var(--v-theme-gradiantTwo))
  );
  padding: 40px;

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
  props: ["courseId"],
  data() {
    return {
      // Data properties for form fields
      course: null,
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      genders: [
        "Male",
        "Female",
        "Third other thing",
        "Other",
        "Prefer not to say",
      ],

      address: "",
      cityName: "",
      postCode: "",
      country: "",
      courseName: "",
      university: "",
      additionalInfo: "",

      // Validation rules for form fields
      InputRules: [
        (value) => {
          if (value) return true;

          return "Input is required.";
        },
      ],
      emailRules: [
        (value) => !!value || "E-mail is required.",
        (value) => /.+@.+\..+/.test(value) || "E-mail must be valid. example: john@something.com",
      ],
    };
  },
  methods: {
    filter: function (evt) {
      evt = evt ? evt : window.event;
      let expect = evt.target.value.toString() + evt.key.toString();

      if (!/^[-+]?[0-9]*\.?[0-9]*$/.test(expect)) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    clearForm() {
      this.firstName = "";
      this.lastName = "";
      this.email = "";
      this.phoneNumber = "";
      this.selectGender = "";
      this.address = "";
      this.cityName = "";
      this.postCode = "";
      this.country = "";
      this.courseName = "";
      this.university = "";
      this.additionalInfo = "";
    },
    async validate() {
      const { valid } = await this.$refs.form.validate();

      if (valid) {
        this.$router.push("/submit");
      }
    },
  },
};
</script>
