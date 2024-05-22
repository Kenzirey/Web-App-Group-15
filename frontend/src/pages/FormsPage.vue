<!-- src/pages/FormsPage.vue -->

<template>

	<h1 class="title"> Place Order </h1>
	<v-form v-model="valid" class="formBox margin-bottom" ref="form">
		<fieldset class="fieldBox">
			<section class="formSections">Personal information</section>

			<!-- First name, Last name, Email, Phone number, Gender -->

			<v-container>
				<v-row>
					<v-col cols="12" md="6">
						<v-text-field v-model="firstName" :rules="InputRules" label="First name" required
						></v-text-field>
					</v-col>

					<v-col cols="12" md="6">
						<v-text-field v-model="lastName" :rules="InputRules" label="Last name" required persistent-hint
						></v-text-field>
					</v-col>
				</v-row>
			</v-container>

			<v-container>
				<v-row>
					<v-col cols="12" md="15">
						<v-text-field v-model="email" :rules="emailRules" label="E-mail" required></v-text-field>
					</v-col>

					<v-col cols="12" md="15">
						<v-text-field v-model="phoneNumber" @keypress="filter(event)" :rules="InputRules"
									  label="Phone number" required persistent-hint></v-text-field>
					</v-col>
				</v-row>
			</v-container>


			<v-col cols="12" md="15">
				<v-select v-model="selectGender" :items="genders" :rules="[(v) => !!v || 'Please select a gender']"
						  label="Gender:" required placeholder="Select Gender"></v-select>
			</v-col>

			<v-container>
				<v-row justify="start">
					<v-date-picker title="Select birth date" show-adjacent-months width="500px"
								   aria-label="Select birth date"></v-date-picker>
				</v-row>
			</v-container>

		</fieldset>

		<!-- Address, city postcode, country-->

		<fieldset class="fieldBox">
			<h1 class="formSections">Address</h1>

			<v-col cols="12" md="15">
				<v-text-field v-model="address" label="Address" :rules="InputRules" required></v-text-field>
			</v-col>

			<v-container>
				<v-row>
					<v-col cols="15" md="6">
						<v-text-field v-model="cityName" label="City" :rules="InputRules" required></v-text-field>
					</v-col>

					<v-col cols="12" md="6">
						<v-text-field v-model="postCode" @keypress="filter(event)" label="Postcode" :rules="InputRules"
									  required></v-text-field>
					</v-col>
				</v-row>
			</v-container>


			<v-col cols="12" md="15">
				<v-text-field v-model="country" label="Country" :rules="InputRules"
							  required></v-text-field>
			</v-col>
		</fieldset>

		<!-- Application Details -->
		<fieldset class="fieldBox">
			<h2 class="formSections">Application Details</h2>
			<p class="labelParagraph">
				Information about the course you are attending
			</p>

			<v-col cols="12" md="15">
				<v-text-field v-model="title" label="Course Name" :rules="InputRules" required></v-text-field>
			</v-col>

			<v-col cols="12" md="15">
				<v-text-field v-model="university" label="University:" :rules="InputRules" required></v-text-field>
			</v-col>

		</fieldset>

		<!-- Additional information -->
		<fieldset class="fieldBox">
			<h2 class="formSections">Additional information</h2>

			<v-textarea v-model="additionalInfo"
						placeholder="If there are any more additional information the schools should know, please write here"></v-textarea>
		</fieldset>

		<v-col class="button-margin">
			<v-btn prepend-icon="mdi-check-underline" text="submit" type="submit" @click.prevent="validate"
				   class="mr-3 submit-button">
				Submit
			</v-btn>

		</v-col>

	</v-form>
</template>

<script>
export default {
	props: ["courseId", "title"],
	data() {
		return {
			firstName: "",
			lastName: "",
			email: "",
			phoneNumber: "",
			selectGender: "",
			genders: [
				"Male",
				"Female",
				"Other",
				"Prefer not to say",
			],

			address: "",
			cityName: "",
			postCode: "",
			country: "",
			title: this.title,
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
				(value) => /.+@.+\..+/.test(value) || "example: john@something.com",
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
			this.title = "";
			this.university = "";
			this.additionalInfo = "";
		},
		async validate() {
			const {valid} = await this.$refs.form.validate();
			if (valid) {
				this.$router.push("/submit");
			}
		},
	},
};


</script>

<style lang="scss" scoped>
/* Updated as ::v-deep is deprecated */
:deep(.v-date-picker-header) {
	background: linear-gradient(to right,
		rgb(var(--v-theme-gradiantOne)),
		rgb(var(--v-theme-gradiantTwo))) !important;
	color: white;
}

:deep(.v-picker-title) {
	background: linear-gradient(to right,
		rgb(var(--v-theme-gradiantOne)),
		rgb(var(--v-theme-gradiantTwo)));
	color: white;
}

:deep(.v-label) {
	color: rgb(79, 79, 79);
	opacity: 1;
}

.submit-button {
	background-color: transparent;
}

.v-btn {
	color: rgb(var(--v-theme-background));
}


.formBox {
	max-width: fit-content;
	margin: auto;
	margin-bottom: 20px;
	background: linear-gradient(to right,
		rgb(var(--v-theme-gradiantOne)),
		rgb(var(--v-theme-gradiantTwo)));
	padding: 40px;
}

.title {
	margin-bottom: 20px;
}

.fieldBox {
	background: white;
}

.labelParagraph {
	padding: 10px;
	display: block;
	margin: 10px 0;
	text-align: left;
	font-size: 15px;
	letter-spacing: 1px;
	font-weight: bold;
}

.formSections {
	padding: 10px;
	margin: 10px 0;
	text-align: left;
	font-size: x-large;
	letter-spacing: 1px;
	font-weight: bold;
	max-width: fit-content;
}


.margin-bottom {
	margin-bottom: 20px;
}

.button-margin {
	margin-top: 20px;
}
</style>
