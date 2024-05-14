<template>
  <v-container>
    <div class="user-form-container">
      <v-form ref="form" @submit.prevent="handleSubmit">
        <v-text-field
          label="Name"
          v-model="user.name"
          required
        ></v-text-field>

        <v-text-field
          label="Email"
          type="email"
          v-model="user.email"
          required
        ></v-text-field>

        <v-text-field
          label="Password"
          :type="showPassword ? 'text' : 'password'"
          v-model="user.password"
          required
          :append-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
          @click:append="togglePasswordVisibility"
        ></v-text-field>

        <v-select
          label="Role"
          :items="['admin', 'user']"
          v-model="user.role"
          required
        ></v-select>

        <v-checkbox
          label="2FA Enabled"
          v-model="user.twoFactorEnabled"
        ></v-checkbox>

        <v-btn color="success" type="submit" class="mt-3">Submit</v-btn>
      </v-form>
    </div> 
  </v-container>
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
    justify-content: space-between ;
 }

 .textareaFormat {
   width: 100%;
   height: 200px;
 }

.form-container {
  max-width: 600px;
  margin: 0 auto;
  background: #fff;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input[type="text"],
input[type="email"],
select {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

input[type="checkbox"] {
  margin-top: 0.5rem;
}

button {
  background-color: #5cb85c;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.2s;
}

button:hover {
  background-color: #4cae4c;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>

<script>
export default {
  name: 'UserForm',
  props: {
    initialUser: {
      type: Object,
      default: () => ({ name: '', email: '', role: '', twoFactorEnabled: false, password: '' })
    }
  },
  data() {
    return {
      user: { ...this.initialUser },
      showPassword: false,
    };
  },
  methods: {
    async handleSubmit() {
      try {
        const method = this.user.id ? 'put' : 'post';
        const url = this.user.id ? `${this.apiEndpoint}/${this.user.id}` : this.apiEndpoint;
        
        const response = await axios[method](url, this.user);
        console.log(response.data);
        
        this.$emit('user-submitted', response.data); 
        
        this.resetForm();
      } catch (error) {
        console.error('Error submitting form:', error);
      }
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
    resetForm() {
      this.user = { ...this.initialUser };
      this.showPassword = false;
      if (this.$refs.form) {
        this.$refs.form.resetValidation();
      }
    }
  },
};
</script>
<style scoped>
.user-form-container {
  max-width: 600px;
  margin: 20px auto;
}
</style>