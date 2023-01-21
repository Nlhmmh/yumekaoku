<template>
  <div class="password-update-page">
    <v-card width="500px" class="pa-5">
      <h2 class="text-center my-5">Password Update Form</h2>
      <!-- Form -->
      <v-form ref="passwordUpdateForm" v-model="passwordUpdateForm">
        <v-text-field v-show="false" v-model="ori_id" hidden></v-text-field>

        <!-- Current Password -->
        <v-text-field
          v-model="currentPassword"
          :counter="10"
          label="Current Password"
          required
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 10 characters',
          ]"
          :type="passwordShow ? 'text' : 'password'"
          :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="passwordShow = !passwordShow"
        ></v-text-field>

        <!-- New Password -->
        <v-text-field
          v-model="newPassword"
          label="New Password"
          required
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 10 characters',
          ]"
          :type="passwordShow ? 'text' : 'password'"
          :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="passwordShow = !passwordShow"
        ></v-text-field>

        <!-- Confirm New Password -->
        <v-text-field
          v-model="confirmPassword"
          label="Confirm New Password"
          required
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 10 characters',
            (v) =>
              (v && v === newPassword) ||
              `The confirm password you entered don't match`,
          ]"
          :type="passwordShow ? 'text' : 'password'"
          :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="passwordShow = !passwordShow"
        ></v-text-field>

        <div class="d-flex justify-end my-5">
          <v-btn @click="cancel()"> cancel </v-btn>

          <v-btn
            class="mx-4"
            @click="submit"
            :disabled="!passwordUpdateForm"
            color="#982f3b"
            :dark="passwordUpdateForm"
          >
            submit
          </v-btn>
        </div>

        <!-- Error Alert -->
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          {{ errMsg }} <br />
        </v-alert>
      </v-form>
    </v-card>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "change_password",
  data() {
    return {
      currentPassword: "",
      newPassword: "",
      confirmPassword: "",
      ori_id: "",
      errorAlert: false,
      errMsg: "",
      loading: false,
      passwordUpdateForm: false,
      passwordShow: false,
    };
  },
  async created() {
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal, oldVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );
  },
  methods: {
    async submit() {
      if (this.$refs.passwordUpdateForm.validate()) {
        this.errorAlert = false;
        try {
          this.loading = true;
          const res = await utils.http.put("/api/user/password/update", {
            ori_id: this.loginUser.id,
            currentPassword: this.currentPassword,
            newPassword: this.newPassword,
            confirmPassword: this.confirmPassword,
          });
          if (res.status === 200) {
            this.$router.push({ path: "/login" });
          } else {
            this.errorAlert = true;
            const data = await res.json();
            this.errMsg = data.message;
          }
        } catch (error) {
          console.log(error);
        } finally {
          this.loading = false;
        }
      }
    },
    cancel() {
      this.currentPassword = "";
      this.newPassword = "";
      this.confirmPassword = "";
      this.$router.back();
    },
  },
};
</script>
<style>
.password-update-page {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>