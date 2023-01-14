<template>
  <div>
    <v-container style="padding: 20px 100px; width: 100%">
      <v-row>
        <v-col cols="10">
          <v-sheet color="white" elevation="1" class="pa-5">
            <!-- <v-carousel>
            <v-carousel-item
              v-for="(item, i) in items"
              :key="i"
              :src="item.src"
              reverse-transition="fade-transition"
              transition="fade-transition"
            ></v-carousel-item>
          </v-carousel> -->
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
                {{ estate.category.name }}
              </v-chip>
              <v-btn color="primary" @click="openAppointmentDialog()">
                Book appointment
              </v-btn>
            </div>
            <div class="my-2">
              <h4 v-if="estate.size">Size: {{ estate.size }}</h4>
              <h4>Description</h4>
              <p>{{ estate.description }}</p>
              <!-- <p>
              Totally Brand New Building (Completion: January 10 2023) No
              Guarantor, No Key Money, No Agent Fee 2 months deposit only High
              Grade Housing Equipment and Fully Furnished with all new high
              grade electrical appliances and furniture decorated by the
              interior coordinator High Speed Internet, 87 channel Cable TV
              Ideal for Home or Home/Office- 2 work spaces available Excellent
              Sound Proofing – Music Instruments May Be Ok (please inquire)
              Central Heating and Cooling
            </p> -->
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
                  <td class="text-right">{{ estate.rentFee }}</td>
                </tr>
                <tr>
                  <td>Maintanence Fee</td>
                  <td>-</td>
                  <td class="text-right">{{ estate.maintenanceFee || 0 }}</td>
                </tr>
                <tr>
                  <td>Total Fee</td>
                  <td>-</td>
                  <td class="text-right">
                    {{ estate.rentFee + estate.maintenanceFee }}
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
        <!-- <v-col cols="2">
        <v-sheet color="white" elevation="1" class="pa-5">
          <h4>Search Properties</h4>
        </v-sheet>
      </v-col> -->
      </v-row>
    </v-container>

    <v-dialog v-model="appointmentDialog" width="500">
      <v-card>
        <v-card-title>Booking appointment</v-card-title>
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
                  readonly
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
              message =
                'I would like to make an appointment for this property.';
            "
            >Cancel</v-btn
          >
          <v-btn color="success" @click="addAppointment()">Book</v-btn>
        </v-card-actions>
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
      appointmentDialog: false,
      addAppointmentForm: false,
      loginUser: {},

      menu: "",
      appointmentDate: "",
      message: "I would like to make an appointment for this property.",
    };
  },

  async created() {
    await this.fetchEstate();
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

    openAppointmentDialog() {
      this.appointmentDialog = true;
    },

    async addAppointment() {
      const res = await utils.http.post("/api/user/appointments/add", {
        userId: this.loginUser.id,
        estateId: this.estate.id,
        appointmentDate: this.appointmentDate,
        message: this.message,
      });

      if (res && res.status === 200) {
        this.appointmentDialog = false;
        this.message = "I would like to make an appointment for this property.";
      }
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