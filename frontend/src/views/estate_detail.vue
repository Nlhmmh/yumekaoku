<template>
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
          <h2 class="my-2">{{ estate.title }}</h2>
          <div class="d-flex justify-content-between">
            <v-chip label color="secondary">
              {{ estate.category.name }}
            </v-chip>
            <v-btn color="primary"> Book appointment </v-btn>
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
</template>
<script>
import utils from "@/utils/utils";

export default {
  name: "estate_detail",

  data() {
    return {
      apiURL: `${utils.constant.LOCAL_API_URL}/api`,
      estate: {},
      items: [
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/sky.jpg",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/bird.jpg",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg",
        },
      ],
    };
  },

  async created() {
    await this.fetchEstate();
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