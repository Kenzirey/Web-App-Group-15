<!-- src/pages/FormsPage.vue -->

<template>

	<h1 class="title"> Place Order </h1>
	<v-form class="formBox margin-bottom" ref="form">
		<section class="fieldBox">
			<h2 class="formTitle">Personal information</h2>

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
						<v-text-field v-model="email" :rules="[...InputRules, ...emailRules]" label="E-mail" required></v-text-field>
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

		</section>

		<!-- Address, city postcode, country-->

		<section class="fieldBox">
			<h2 class="formTitle">Address</h2>

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
		</section>

		<!-- Application Details -->
		<section class="fieldBox">
			<h2 class="formTitle">Application Details</h2>
			<p class="labelParagraph">
				Information about the course you are attending
			</p>

			<v-col cols="12" md="15">
				<v-text-field v-model="title" label="Course Name" :rules="InputRules" required></v-text-field>
			</v-col>

			<v-col cols="12" md="15">
				<v-text-field v-model="provider" label="Provider" :rules="InputRules" required></v-text-field>
			</v-col>

		</section>

		<!-- Additional information -->
		<section class="fieldBox">
			<h2 class="formTitle">Additional information</h2>

			<v-textarea v-model="additionalInfo"
						placeholder="If there are any more additional information the schools should know, please write here"
						hide-details="auto"
			></v-textarea>
		</section>

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
			provider: "",
			additionalInfo: "",


			// Validation rules for form fields
			InputRules: [
				(value) => {
					if (value) return true;

					return "Input is required.";
				},
			],
			emailRules: [

				(value) => /.+@.+\..+/.test(value) || "example: john@something.com",
			],
		};
	},
	methods: {
		filter: function (evt) {
			evt = evt ? evt : window.event;
			let expect = evt.target.value.toString() + evt.key.toString();
			if (!/^[-+]?\d*\.?\d*$/.test(expect)) {
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
	border: 2px groove ButtonFace;
	border-top-width: 0;
	padding: .35em .625em .75em;
	position: relative;
	margin: 0;
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

.formTitle {
	padding: 10px;
	margin: 10px 0;
	text-align: left;
	font-size: x-large;
	letter-spacing: 1px;
}


.margin-bottom {
	margin-bottom: 20px;
}

.button-margin {
	margin-top: 20px;
}
</style>
