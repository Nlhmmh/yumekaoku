<template>
  <div>
    <v-card width="500px" class="pa-5 my-5" style="margin: 0 auto">
      <h2 class="text-center my-5">Register Form</h2>
      <!-- Form -->
      <v-form ref="registerForm" v-model="registerForm">
        <!-- Name Text Field -->
        <v-text-field
          v-model="name"
          label="Name"
          placeholder="Enter User Name"
          :rules="[(v) => !!v || 'Required']"
          required
        ></v-text-field>

        <!-- Mail Address Text Field -->
        <v-text-field
          v-model="email"
          label="Email"
          placeholder="test@gmail.com"
          :rules="[
            (v) => !!v || 'Required',
            (v) => (v && /.+@.+\..+/.test(v)) || 'E-mail must be valid',
          ]"
          required
        ></v-text-field>

        <!-- Phone No Text Field -->
        <v-text-field
          v-model="phoneNumber"
          label="Phone Number"
          placeholder="Enter phone number"
          :rules="[(v) => !!v || 'Required']"
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

        <!-- Register Btn -->
        <v-btn
          width="100%"
          :disabled="!registerForm"
          color="success"
          class="mr-4"
          @click="register()"
        >
          <span v-if="!loading">Register</span>
          <v-progress-circular
            v-else
            indeterminate
            color="primary"
          ></v-progress-circular>
        </v-btn>

        <!-- Error Alert -->
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          Register Failed!
        </v-alert>
      </v-form>
    </v-card>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "register",
  data() {
    return {
      registerForm: false,
      name: "",
      email: "",
      phoneNumber: "",
      password: "",
      passwordShow: false,
      errorAlert: false,
      loading: false,
    };
  },

  methods: {
    async register() {
      if (this.$refs.registerForm.validate()) {
        this.errorAlert = false;
        try {
          this.loading = true;
          // API Call
          const res = await utils.http.post("/api/user/register", {
            name: this.name,
            email: this.email,
            phoneNumber: this.phoneNumber,
            password: this.password,
            role: "user",
          });
          if (res.status === 200) {
            const data = await res.json();
            if (data) {
              // Store Login Info in Vuex
              this.$store.commit("setLoginUser", data);
              // After register success, go to login
              this.$router.push({ path: "/login" });
            }
          } else {
            this.errorAlert = true;
          }
        } catch (error) {
          console.log(error);
        } finally {
          this.loading = false;
        }
      }
    },
  },
};
</script>
