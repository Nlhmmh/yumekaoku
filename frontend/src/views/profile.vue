<template>
  <div>
    <!-- Profile Card -->
    <v-row class="ma-10">
      <v-col cols="4">
        <v-card class="pa-5">
          <v-card-text>
            <div>Welcome</div>
            <p class="text-h4 text--primary">
              {{ loginUser.name }}
            </p>

            <p>Information</p>
            <div class="text--primary">
              <div v-if="loginUser.role == 'admin'">Admin</div>
              <div>{{ loginUser.email }}</div>
              <div>{{ loginUser.phoneNumber }}</div>
            </div>
          </v-card-text>

          <!-- Change Pwd Btn -->
          <v-btn text color="primary" @click="onUpdateProfile()">
            Update Profile
          </v-btn>
          <v-btn text color="primary" @click="changePwd()">
            Update Password
          </v-btn>
        </v-card>
      </v-col>
      <v-col cols="8">
        <v-card class="pa-5" v-if="loginUser && loginUser?.role === 'user'">
          <v-card-title>
            Appointment List
            <v-spacer></v-spacer>
          </v-card-title>
          <v-data-table
            :headers="headers"
            :items="appointments"
            :items-per-page="10"
          >
            <template v-slot:item.user="{ item }">
              {{ item?.user?.name }} - {{ item?.user?.phoneNumber }}
              <br />
              <small>
                {{ item?.user?.email }}
              </small>
            </template>
            <template v-slot:item.estate="{ item }">
              <router-link :to="'/estates/' + item?.estate?.id">{{
                item?.estate?.title
              }}</router-link>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>

    <v-dialog v-model="profileDialog" width="500">
      <v-card>
        <v-card-title>Update Profile</v-card-title>
        <v-card-text>
          <v-form ref="profileUpdateForm" v-model="profileUpdateForm">
            <v-text-field
              v-model="loginUser.name"
              label="Name"
              placeholder="Enter user name"
              required
              :rules="[(v) => !!v || 'Required']"
            >
            </v-text-field>
            <v-text-field
              v-model="loginUser.email"
              label="Email"
              placeholder="Enter email"
              required
              :rules="[
                (v) => !!v || 'Required',
                (v) => (v && /.+@.+\..+/.test(v)) || 'E-mail must be valid',
              ]"
            >
            </v-text-field>
            <v-text-field
              v-model="loginUser.phoneNumber"
              label="Phone Number"
              placeholder="Enter phone number"
            >
            </v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="profileDialog = false">Cancel</v-btn>
          <v-btn
            color="success"
            @click="updateProfile()"
            :disabled="!profileUpdateForm"
            >Submit</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "profile",

  data() {
    return {
      loginUser: {
        name: "",
        email: "",
        phoneNumber: "",
      },
      profileDialog: false,
      profileUpdateForm: false,
      errorAlert: false,
      errMsg: "",
      loading: false,
      appointments: [],
      headers: [
        { text: "User", value: "user", sortable: false },
        { text: "Estate", value: "estate", sortable: false },
        { text: "Appointment Date", value: "appointmentDate", sortable: false },
        // { text: "Status", value: "status", sortable: false },
        { text: "Message", value: "message", sortable: false },
      ],
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

    await this.getProfile();

    if (this.loginUser && this.loginUser?.role === "user") {
      await this.fetchAppointments();
    }
  },

  methods: {
    changePwd() {
      this.$router.push({ path: "/change-password" });
    },

    onUpdateProfile() {
      this.profileDialog = true;
    },

    async getProfile() {
      const res = await utils.http.get("/api/user/profile", {
        id: this.loginUser.id,
      });
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.loginUser = data;
        }
      }
    },

    async updateProfile() {
      if (this.$refs.profileUpdateForm.validate()) {
        this.errorAlert = false;
        try {
          this.loading = true;
          const res = await utils.http.put("/api/user/profile/update", {
            id: this.loginUser.id,
            name: this.loginUser.name,
            email: this.loginUser.email,
            phoneNumber: this.loginUser.phoneNumber,
            password: this.loginUser.password,
          });
          if (res && res.status === 200) {
            await this.getProfile();
            this.profileDialog = false;
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

    async fetchAppointments() {
      const res = await utils.http.get(
        `/api/user/${this.loginUser?.id}/appointments`
      );
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.appointments = data;
        }
      }
    },
  },
};
</script>
