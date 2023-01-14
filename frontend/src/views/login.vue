<template>
  <div>
    <!-- <v-container> -->
    <v-card width="500px" class="pa-5 my-5" style="margin: 0 auto">
      <h2 class="text-center my-5">Login Form</h2>
      <v-form ref="loginForm" v-model="loginForm">
        <!-- Email -->
        <v-text-field
          v-model="email"
          :rules="[
            (v) => !!v || 'Required',
            (v) => (v && /.+@.+\..+/.test(v)) || 'E-mail must be valid',
          ]"
          label="E-mail"
          required
        ></v-text-field>

        <!-- Password -->
        <v-text-field
          v-model="password"
          :counter="10"
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 10 characters',
          ]"
          :type="passwordShow ? 'text' : 'password'"
          :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="passwordShow = !passwordShow"
          label="Password"
          required
        ></v-text-field>

        <!-- Login Btn -->
        <v-btn
          width="100%"
          :disabled="!loginForm"
          color="success"
          class="mr-4 my-5"
          @click="login()"
        >
          <span v-if="!loading">Login</span>
          <v-progress-circular
            v-else
            indeterminate
            color="primary"
          ></v-progress-circular>
        </v-btn>

        <!-- Error Msg -->
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          Login Failed! <br />
          Email or Password is wrong!
        </v-alert>
      </v-form>
    </v-card>
    <!-- </v-container> -->
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "login",

  components: {},

  data() {
    return {
      loginForm: false,
      email: "",
      password: "",
      passwordShow: false,
      errorAlert: false,
      loading: false,
    };
  },
  methods: {
    async login() {
      if (this.$refs.loginForm.validate()) {
        this.errorAlert = false;
        try {
          this.loading = true;

          const res = await utils.http.post("/api/user/login", {
            email: this.email,
            password: this.password,
          });
          if (res && res.status === 200) {
            const data = await res.json();
            if (data) {
              // Store Login Info in Vuex
              this.$store.commit("setLoginUser", data);
              // If Admin -> Go to Admin estates page
              if (data.role == "admin") {
                this.$router.push({ path: "/admin/estates" });
              } else {
                // If User -> Go to Home
                this.$router.push({ path: "/" });
              }
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


