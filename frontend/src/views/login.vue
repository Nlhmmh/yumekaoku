<template>
  <div>
    <v-container>
      <!-- Login Form -->
      <v-form ref="loginForm" v-model="loginForm">
        <!-- Email -->
        <v-text-field
          v-model="email"
          :rules="[
            (v) => !!v || 'Required',
            (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
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
          :disabled="!loginForm"
          color="success"
          class="mr-4"
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
    </v-container>
  </div>
</template>

<script>
// import utils from "../utils/utils";

export default {
  name: "login",

  components: {},

  data() {
    return {
      loginForm: false,
      email: "",
      password: "",
      passwordShow: false,
      // email: "admin@gmail.com",
      // password: "1111",
      errorAlert: false,
      loading: false,
    };
  },
  methods: {
    async login() {
      if (this.$refs.loginForm.validate()) {
        // TODO : Call API

        // Store in Vuex
        let dummyLoginUser = {
          email: this.email,
          // TODO : Data must be from API... Added as manual for Testing
          name: "tester",
          phoneNo: "08099992222",
          role: "user",
          status: "active",
        };
        this.$store.commit("setLoginUser", dummyLoginUser);

        if (dummyLoginUser.role === "admin") {
          // Go to admin Screen
          this.$router.push({ path: "/admin/estates" });
        } else {
          // Go to home Screen
          this.$router.push({ path: "/" });
        }
      }
    },
  },
};
</script>
