<template>
  <div>
    <v-container style="padding: 20px 100px; width: 100%">
      <v-row>
        <v-col cols="10">
          <v-sheet color="white" elevation="1" class="pa-5">
            <v-img
              v-if="estate.imagePath"
              :src="apiURL + estate.imagePath"
              width="100%"
              height="200"
              contain
            >
            </v-img>
            <v-img
              v-else
              :src="require('/src/assets/default-estate.jpg')"
              width="100%"
              height="200"
              contain
            >
            </v-img>
            <h2 class="my-2">{{ estate.title }}</h2>
            <div class="d-flex justify-content-between">
              <v-chip label color="secondary">
                {{ estate?.category?.name }}
              </v-chip>
              <v-btn
                color="primary"
                @click="
                  !isLogin ? openGoLoginDialog() : openAppointmentDialog()
                "
              >
                Book appointment
              </v-btn>
            </div>
            <div class="my-2">
              <h4 v-if="estate.size">Size: {{ estate.size }} m<sup>2</sup></h4>
              <h4>Description</h4>
              <p>{{ estate.description }}</p>
            </div>

            <v-divider></v-divider>
            <div>
              <h4 class="my-2">Location</h4>
              <p>{{ estate.location }}</p>
            </div>
            <v-divider></v-divider>
            <h4 class="my-2">Monthly Costs</h4>
            <table class="my-2">
              <tbody>
                <tr>
                  <td>Rental Fee</td>
                  <td>-</td>
                  <td class="text-right">
                    {{ formatCurrency(estate.rentFee) }}
                  </td>
                </tr>
                <tr>
                  <td>Maintanence Fee</td>
                  <td>-</td>
                  <td class="text-right">
                    {{ formatCurrency(estate.maintenanceFee) || 0 }}
                  </td>
                </tr>
                <tr>
                  <td>Total Fee</td>
                  <td>-</td>
                  <td class="text-right" style="color: red">
                    {{ formatCurrency(estate.rentFee + estate.maintenanceFee) }}
                  </td>
                </tr>
              </tbody>
            </table>
            <v-divider></v-divider>
            <div>
              <div class="my-2" v-if="estate.remarks">
                <h4>Remarks</h4>
                <p>{{ estate.remarks }}</p>
              </div>

              <video
                v-if="estate.videoPath"
                class="my-2"
                width="100%"
                :src="apiURL + estate.videoPath"
                controls
              ></video>
            </div>
          </v-sheet>
        </v-col>
      </v-row>
    </v-container>

    <v-dialog v-model="appointmentDialog" width="500">
      <v-card>
        <v-card-title>Make an appointment?</v-card-title>
        <v-card-text>
          <v-form ref="addAppointmentForm" v-model="addAppointmentForm">
            <v-menu
              ref="menu"
              v-model="menu"
              :close-on-content-click="false"
              :return-value.sync="appointmentDate"
              transition="scale-transition"
              offset-y
              min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="appointmentDate"
                  label="Appointment Date"
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="appointmentDate" no-title scrollable>
                <v-spacer></v-spacer>
                <v-btn text @click="menu = false"> Cancel </v-btn>
                <v-btn
                  text
                  color="success"
                  @click="$refs.menu.save(appointmentDate)"
                >
                  OK
                </v-btn>
              </v-date-picker>
            </v-menu>
            <span class="subheading">Select time</span>

            <v-chip-group
              v-model="selectTime"
              active-class="deep-purple--text text--accent-4"
              mandatory
            >
              <v-chip v-for="time in times" :key="time" :value="time">
                {{ time }}
              </v-chip>
            </v-chip-group>
            <v-text-field
              v-model="message"
              :counter="1000"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 1000) ||
                  'Location must be less than 1000 characters',
              ]"
              label="Message"
              required
            ></v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            @click="
              appointmentDialog = false;
              appointmentDate = '';
              message =
                'I would like to make an appointment for this property.';
            "
            >Cancel</v-btn
          >
          <v-btn color="success" @click="addAppointment()">Book</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="goLoginDialog" width="500">
      <v-card>
        <v-card-title>Make an appointment?</v-card-title>
        <v-card-text>
          Please Login first to make an appointment for this property.
          <br />
          <v-btn
            width="100%"
            color="primary"
            class="my-2"
            @click="onRoute('/login')"
            >Login</v-btn
          >
          <v-divider class="my-3"></v-divider>
          If you don't have an account, you can create one.
          <br />
          <v-btn width="100%" class="my-2" @click="onRoute('/register')"
            >Register</v-btn
          >
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
import utils from "@/utils/utils";

export default {
  name: "estate_detail",

  data() {
    return {
      apiURL: `${utils.constant.LOCAL_API_URL}/api`,
      estate: {},

      loginUser: {},
      isLogin: false,
      goLoginDialog: false,

      //Add appointment Form
      alertType: "success",
      appointmentDialog: false,
      addAppointmentForm: false,
      times: ["10:00", "11:00", "13:00", "14:00", "15:00", "16:00"],
      selectTime: "10:00",
      menu: "",
      appointmentDate: "",
      message: "I would like to make an appointment for this property.",
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

    this.isLogin = this.$store.state.isLogin;
    this.$store.watch(
      () => {
        return this.$store.state.isLogin;
      },
      (newVal, oldVal) => {
        this.isLogin = newVal;
      },
      { deep: true }
    );

    await this.fetchEstate();
  },

  methods: {
    formatCurrency: (value) => utils.formatCurrency(value),

    async fetchEstate() {
      const res = await utils.http.get(
        "/api/admin/estates/" + this.$route.params.id
      );
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.estate = data;
        }
      }
    },

    async addAppointment() {
      console.log("tt", this.appointmentDate, this.selectTime);
      const res = await utils.http.post("/api/user/appointments/add", {
        userId: this.loginUser.id,
        estateId: this.estate.id,
        appointmentDate: this.appointmentDate + "T" + this.selectTime,
        message: this.message,
      });

      if (res && res.status === 200) {
        this.appointmentDialog = false;
        this.alertType = "success";
        this.message = "I would like to make an appointment for this property.";
      }
    },

    openAppointmentDialog() {
      this.appointmentDialog = true;
    },

    openGoLoginDialog() {
      this.goLoginDialog = true;
    },

    onRoute(path) {
      this.goLoginDialog = false;
      this.$router.push({ path });
    },
  },
};
</script>
<style scoped>
.d-flex {
  display: flex;
}

.justify-content-between {
  justify-content: space-between;
}
</style>