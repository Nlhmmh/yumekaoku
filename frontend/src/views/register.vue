<template>
  <v-container>
    <v-card elevation="2">
      <v-form ref="registerForm">
        <v-text-field
          v-model="email"
          label="Email"
          required
          :rules="[
            (v) => !!v || 'Required',
            (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
          ]"
        >
        </v-text-field>
        <v-text-field
          v-model="password"
          label="Password"
          required
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 10 characters',
          ]"
          :type="showPassword ? 'text' : 'password'"
          :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="showPassword = !showPassword"
        >
        </v-text-field>

        <v-btn
          block
          elevation="2"
          :disabled="!registerForm"
          color="primary"
          class="m-4"
          @click="login()"
        >
          <span v-if="!loading">Login</span>
          <v-progress-circular
            v-else
            indeterminate
            color="primary"
          ></v-progress-circular>
        </v-btn>
      </v-form>
    </v-card>
  </v-container>
</template>
  
  <script>
import http from "@/utils/http";

export default {
  name: "register",

  data: () => {
    return {
      registerForm: false,
      showPassword: false,
      loading: false,
      errorAlert: false,
      email: "",
      password: "",
    };
  },

  methods: {
    async login() {
      if (this.$refs.registerForm.validate()) {
        this.errorAlert = false;
        try {
          this.loading = true;
          const res = await http.post("/user/register", {
            name: this.name,
            phoneNumber: this.phoneNumber,
            email: this.email,
            password: this.password,
          });
          if (res && res.status === 200) {
            const data = await res.json();
            if (data) {
              this.$store.commit("setLoginUser", data);
              if (data.role === "admin") {
                this.$router.push({ path: "/admin" });
              }
              this.$router.push({ path: "/" });
            }
          } else {
            this.errorAlert = true;
          }
        } catch (error) {
          console.log(error);
        } finally {
          this.loading = true;
        }
      }
    },
  },
};
</script>