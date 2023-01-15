<template>
  <!-- <v-container fluid> -->
  <v-card class="pa-5 my-5">
    <v-card-title>Estate Create Form</v-card-title>
    <v-form ref="estateCreateForm" v-model="estateCreateForm">
      <v-row>
        <v-col cols="12" sm="6">
          <v-text-field
            v-model="title"
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
            v-model="rentFee"
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
            v-model="maintenanceFee"
            type="number"
            suffix="¥"
            min="0"
            label="Maintenance Fee"
          ></v-text-field>

         
        </v-col>
        <v-col cols="12" sm="6">
          <v-text-field
            v-model="size"
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
            v-model="location"
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
          <v-textarea v-model="description">
            <template v-slot:label>
              <div>Description <small>(optional)</small></div>
            </template>
          </v-textarea>
        </v-col>
        <v-col cols="6">
          <v-textarea v-model="remarks">
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
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                !v ||
                v.size < 10000000 ||
                'Image size should be less than 10 MB!',
            ]"
            @change="onChangeImage"
            @click:clear="
              imagePath = null;
              image = null;
            "
          ></v-file-input>

          <!-- Image Preview -->
          <v-img
            v-if="imagePath != null"
            :src="imagePath"
            width="200"
            height="150"
            contain
          >
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
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                !v ||
                v.size < 100000000 ||
                'Video size should be less than 100 MB!',
            ]"
            @change="onChangeVideo"
            @click:clear="
              videoPath = null;
              video = null;
            "
          ></v-file-input>

          <!-- Video Preview -->
          <video
            v-if="videoPath != null"
            class="mb-2"
            width="100%"
            :src="videoPath"
            controls
          ></video>
        </v-col>
      </v-row>

      <!-- Create Btn -->
      <v-btn
        :disabled="!estateCreateForm"
        color="success"
        class="mt-4 mr-4"
        @click="createEstate()"
      >
        <span v-if="!loading">Create</span>
        <v-progress-circular
          v-else
          indeterminate
          color="primary"
        ></v-progress-circular>
      </v-btn>

      <!-- Error Alert For Movie -->
      <v-alert class="mt-3" v-show="errorAlert" dense type="error">
        Create Estate Failed!
      </v-alert>
    </v-form>
  </v-card>
  <!-- </v-container> -->
</template>

<script>
import http from "@/utils/http";

export default {
  name: "admin_estate_create",

  data: () => {
    return {
      estateCreateForm: false,
      categoryList: [],
      title: "",
      description: "",
      location: "",
      remarks: "",
      size: "",
      rentFee: "",
      maintenanceFee: "",
      category: "",
      image: null,
      imagePath: null,
      video: null,
      videoPath: null,

      errorAlert: false,

      loading: false,
    };
  },
  async created() {
    await this.fetchCategories();
  },
  methods: {
    async fetchCategories() {
      const res = await http.get("/api/categories");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.categoryList = data;
        }
      }
    },

    async createEstate() {
      // Form Validation
      if (this.$refs.estateCreateForm.validate()) {
        this.errorAlert = false;

        this.loading = true;

        let resImgData = null;
        let resVideoData = null;

        // Step 1 -> Post Image

        const resImage = await http.postMedia(
          "/api/admin/file/create",
          this.image,
          this.image.type
        );
        if (resImage.status === 200) {
          resImgData = await resImage.text();
        } else {
          this.errorAlert = true;
        }

        // Step 2 -> Post Video

        const resVideo = await http.postMedia(
          "/api/admin/file/create",
          this.video,
          this.video.type
        );
        console.log("resVideo", resVideo);
        if (resVideo.status === 200) {
          resVideoData = await resVideo.text();
        } else {
          this.errorAlert = true;
        }

        console.log(resImgData, resVideoData);
        // Step 3 -> Create Estate

        if (resImgData && resVideoData) {
          const response = await http.post("/api/admin/estates/create", {
            title: this.title,
            description: this.description,
            rentFee: this.rentFee,
            maintenanceFee: this.maintenanceFee,
            size: this.size,
            category: { id: this.category },
            location: this.location,
            remarks: this.remarks,
            imagePath: resImgData,
            videoPath: resVideoData,
          });
          if (response && response.status === 200) {
            this.$router.push({ path: "/admin/estates" });
          } else {
            this.errorAlert = true;
          }
        }
      }

      this.loading = false;
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