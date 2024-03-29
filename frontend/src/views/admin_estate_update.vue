<template>
  <!-- <v-container fluid> -->
  <v-card class="pa-5 my-5">
    <v-card-title>Estate Update Form</v-card-title>
    <v-form ref="estateUpdateForm" v-model="estateUpdateForm">
      <v-row>
        <v-col cols="12" sm="6">
          <v-text-field
            v-model="initEstate.title"
            :rules="[(v) => !!v || 'Required']"
            label="Title"
            required
          ></v-text-field>
        </v-col>
        <v-col cols="12" sm="6">
          <v-select
            v-model="category"
            :items="categoryList"
            item-text="name"
            item-value="id"
            :rules="[(v) => !!v || 'Required']"
            label="Category"
            required
          >
          </v-select>
        </v-col>

        <v-col cols="12" sm="6">
          <v-text-field
            v-model="initEstate.rentFee"
            type="number"
            suffix="¥"
            min="1"
            :rules="[
              (v) => !!v || 'Required',
              (v) => (v && v > 0) || 'Rent Fee must be greater than 0',
            ]"
            label="Rent Fee"
            required
          ></v-text-field>
        </v-col>
        <v-col cols="12" sm="6">
          <v-text-field
            v-model="initEstate.maintenanceFee"
            type="number"
            suffix="¥"
            min="0"
            label="Maintenance Fee"
          ></v-text-field>
        </v-col>

        <v-col cols="12" sm="6">
          <v-text-field
            v-model="initEstate.size"
            type="number"
            min="1"
            :rules="[
              (v) => !!v || 'Required',
              (v) => (v && v > 0) || 'size must be greater than 0',
            ]"
            label="Size"
            required
          ></v-text-field>
        </v-col>
        <v-col cols="12" sm="6">
          <v-text-field
            v-model="initEstate.location"
            :counter="1000"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 1000) ||
                'Location must be less than 1000 characters',
            ]"
            label="location"
            required
          ></v-text-field>
        </v-col>

        <v-col cols="6">
          <v-textarea v-model="initEstate.description">
            <template v-slot:label>
              <div>Description <small>(optional)</small></div>
            </template>
          </v-textarea>
        </v-col>
        <v-col cols="6">
          <v-textarea v-model="initEstate.remarks">
            <template v-slot:label>
              <div>Remarks <small>(optional)</small></div>
            </template>
          </v-textarea>
        </v-col>

        <!-- Image -->
        <v-col cols="6">
          <v-file-input
            v-model="image"
            label="Estate Image"
            show-size
            prepend-icon="mdi-camera"
            placeholder="Choose Estate Image"
            accept="image/png, image/jpeg, image/jpg"
            @change="onChangeImage"
            @click:clear="
              imagePath = null;
              image = null;
            "
          ></v-file-input>

          <!-- Image Preview -->
          <v-img
            v-if="initEstate.imagePath != null && imagePath == null"
            :src="apiURL + initEstate.imagePath"
            width="100%"
            contain
          >
          </v-img>
          <v-img v-if="imagePath != null" :src="imagePath" width="100%" contain>
          </v-img>
        </v-col>

        <!-- Video -->
        <v-col cols="6">
          <v-file-input
            v-model="video"
            label="Estate Video"
            show-size
            prepend-icon="mdi-video"
            placeholder="Choose Estate video"
            accept="video/mp4"
            @change="onChangeVideo"
            @click:clear="
              videoPath = null;
              video = null;
            "
          ></v-file-input>

          <!-- Video Preview -->
          <video
            v-if="initEstate.videoPath != null && videoPath == null"
            class="mb-2"
            width="100%"
            :src="apiURL + initEstate.videoPath"
            controls
          ></video>
          <video
            v-if="videoPath != null"
            class="mb-2"
            width="100%"
            :src="videoPath"
            controls
          ></video>
        </v-col>
      </v-row>

      <!-- Update Btn -->
      <v-btn
        :disabled="!estateUpdateForm"
        color="#982f3b"
        :dark="estateUpdateForm"
        class="mt-4 mr-4"
        @click="updateEstate()"
      >
        <span v-if="!loading">Update</span>
        <v-progress-circular
          v-else
          indeterminate
          color="primary"
        ></v-progress-circular>
      </v-btn>

      <!-- Error Alert For Estate -->
      <v-alert class="mt-3" v-show="errorAlert" dense type="error">
        Update Estate Failed!
      </v-alert>
    </v-form>
  </v-card>
  <!-- </v-container> -->
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "admin_estate_update",

  data: () => {
    return {
      apiURL: `${utils.constant.LOCAL_API_URL}/api`,
      initEstate: {
        title: "",
        description: "",
        location: "",
        remarks: "",
        size: "",
        rentFee: "",
        maintenanceFee: "",
        category: "",
        imagePath: null,
        videoPath: null,
        rentOut: false,
      },
      estateUpdateForm: false,
      categoryList: [],
      category: "",

      errorAlert: false,
      loading: false,

      image: null,
      imagePath: null,
      video: null,
      videoPath: null,
    };
  },

  async created() {
    await this.fetchCategories();
    await this.fetchEstate();
    this.category = this.initEstate?.category?.id;
  },

  methods: {
    async fetchEstate() {
      const res = await utils.http.get(
        "/api/admin/estates/" + this.$route.params.id
      );
      console.log(res);
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.initEstate = data;
        }
      }
    },
    async fetchCategories() {
      const res = await utils.http.get("/api/categories");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.categoryList = data;
        }
      }
    },

    async updateEstate() {
      // Form Validation
      if (this.$refs.estateUpdateForm.validate()) {
        this.errorAlert = false;

        this.loading = true;

        let imagePath = this.initEstate.imagePath;
        let videoPath = this.initEstate.videoPath;

        // Update Image
        if (this.image != null) {
          const resImg = await utils.http.putMedia(
            "/api/admin/file/update",
            this.image,
            this.image.type,
            this.initEstate.imagePath
          );
          if (resImg && resImg.status === 200) {
            imagePath = await resImg.text();
          } else {
            console.debug("Image Update Failed");
          }
        }

        // Update Video
        if (this.video != null) {
          const resVideo = await utils.http.putMedia(
            "/api/admin/file/update",
            this.video,
            this.video.type,
            this.initEstate.videoPath
          );
          if (resVideo && resVideo.status === 200) {
            videoPath = await resVideo.text();
          } else {
            console.log("Video Update Failed");
          }
        }

        // Step 3 -> Update Estate
        const response = await utils.http.put(
          "/api/admin/estates/update/" + this.initEstate.id,
          {
            title: this.initEstate.title,
            description: this.initEstate.description,
            rentFee: this.initEstate.rentFee,
            maintenanceFee: this.initEstate.maintenanceFee,
            size: this.initEstate.size,
            category: { id: this.category },
            location: this.initEstate.location,
            remarks: this.initEstate.remarks,
            imagePath: imagePath,
            rentOut: this.initEstate.rentOut,
            videoPath: videoPath,
          }
        );
        if (response && response.status === 200) {
          this.image = null;
          this.video = null;
          this.$router.push({ path: "/admin/estates" });
        } else {
          this.errorAlert = true;
        }

        this.loading = false;
      }
    },
    onChangeImage(image) {
      this.imagePath = URL.createObjectURL(image);
    },

    onChangeVideo(video) {
      this.videoPath = URL.createObjectURL(video);
    },
  },
};
</script>