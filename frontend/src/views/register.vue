<template>
  <div>
    <v-container>
      <!-- Form -->
      <v-form ref="registerForm">
        <!-- Name Text Field -->
        <v-text-field
          v-model="name"
          label="Name"
          placeholder="Mg Mg"
          :rules="[(v) => !!v || 'Required']"
          required
        ></v-text-field>

        <!-- Mail Address Text Field -->
        <v-text-field
          v-model="mail"
          label="Mail Address"
          placeholder="test@gmail.com"
          :rules="[(v) => !!v || 'Required']"
          required
        ></v-text-field>

        <!-- Phone No Text Field -->
        <v-text-field
          v-model="phoneNo"
          label="Phone Number"
          placeholder="10"
          type="number"
          :rules="[
            (v) => !!v || 'Required',
            (v) => (v && v > 0) || 'Phone Number must be greater than 0',
            (v) => (v && v <= 20) || 'Phone Number must be less than 20',
          ]"
          required
        ></v-text-field>

        <!-- Password -->
        <v-text-field
          v-model="password"
          :counter="150"
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 150 characters',
          ]"
          :type="passwordShow ? 'text' : 'password'"
          :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="passwordShow = !passwordShow"
          label="Password"
          required
        ></v-text-field>

        <!-- @click= and must write at methods: and  -->
        <v-btn class="mt-5 width-100" color="success" @click="register()"
          >Register</v-btn
        >
      </v-form>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "register",
  data() {
    return {
      name: "",
      mail: "",
      phoneNo: 0,
      password: "",
    };
  },

  methods: {
    async register() {
      if (this.$refs.registerForm.validate()) {
        // TODO : Call API

        // Go to home Screen
        this.$router.push({ path: "/" });
      }
    },
  },
};
</script>
