<template>
  <div>
    <!-- :src="require('/src/assets/landing-bg.jpg')" -->
    <!-- Advanced search form in landing page -->
    <div class="landing">
      <div class="advanced-form">
        <h2 style="color: white" class="my-2">
          Find a home in Japan with Yumekaoku
        </h2>

        <div class="d-flex">
          <v-text-field
            style="width: 350px"
            v-model="search"
            solo
            label="Search by location"
            clearable
            class="mx-1"
          ></v-text-field>
          <v-select
            v-model="category"
            :items="categories"
            label="Property Category"
            item-text="name"
            item-value="id"
            solo
            class="mx-1"
          ></v-select>

          <!-- :disabled="!search || categoryId === 0" -->
          <v-btn
            color="#982f3b"
            dark
            class="mx-1"
            large
            @click="onSearch()"
            style="height: 46px"
          >
            Search
            <v-icon right> mdi-magnify </v-icon>
          </v-btn>
        </div>
      </div>
    </div>

    <!-- Featured properties section -->
    <section_wrapper
      title="Featured Properties"
      subtitle="With Yumekaoku, everything related to finding an apartment can be done online"
    >
      <v-row dense style="padding: 0 100px">
        <v-col v-for="estate in estates.slice(0, 8)" :key="estate.id" cols="3">
          <estate_card
            :id="estate.id"
            :title="estate.title"
            :location="estate.location"
            :rentFee="estate.rentFee"
            :category="estate.category"
            :imagePath="estate.imagePath"
          ></estate_card>
        </v-col>
      </v-row>
    </section_wrapper>

    <!-- Popular locations section -->
    <section_wrapper
      title="Popular Locations"
      subtitle="With Yumekaoku, everything related to finding an apartment can be done online"
    >
      <div class="location-wrapper">
        <v-row dense>
          <v-col v-for="location in locations" :key="location" cols="3">
            <router-link
              :to="'/estates?search=' + location"
              style="text-decoration: none; color: black"
            >
              <v-icon color="#982f3b">mdi-map-marker</v-icon>
              {{ location }}
            </router-link>
          </v-col>
        </v-row>
      </div>
    </section_wrapper>

    <!-- Parallax Image section -->
    <v-parallax
      dark
      src="https://www.relonetworkasia.com/wp-content/uploads/2013/11/japan-housing-1.jpg"
    >
      <v-row align="center" justify="center">
        <v-col class="text-center" cols="12">
          <h1 class="text-h4 font-weight-thin mb-4">Yumekaoku</h1>
          <h4 class="subheading">
            We support you with useful information about Japan, your locality,
            apartments, daily life etc.
          </h4>
        </v-col>
      </v-row>
    </v-parallax>

    <!-- Home Categories Section -->
    <section_wrapper
      title="Categories"
      subtitle="A collection of properties carefully chosen organized by category."
    >
      <v-row class="my-5" dense style="padding: 0 100px">
        <v-col
          v-for="cat in categories"
          :key="cat.id"
          :cols="cat?.id > 3 ? 3 : categories?.length > 7 ? 3 : 4"
        >
          <v-card @click="onRouteChange(cat.id)">
            <v-img
              :src="catImages[cat.id - 1]"
              class="white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
              <v-card-title v-text="cat.name"></v-card-title>
            </v-img>
          </v-card>
        </v-col>
      </v-row>
    </section_wrapper>

    <!-- Latest properties section -->
    <section_wrapper
      title="Latest Properties"
      subtitle="Get instant access to homes for rent in popular locations across Japan."
    >
      <v-row dense style="padding: 0 100px">
        <v-col v-for="estate in estates.slice(8, 17)" :key="estate.id" cols="3">
          <estate_card
            :id="estate.id"
            :title="estate.title"
            :location="estate.location"
            :rentFee="estate.rentFee"
            :category="estate.category"
            :imagePath="estate.imagePath"
          ></estate_card>
        </v-col>
      </v-row>
    </section_wrapper>
  </div>
</template>

<script>
import section_wrapper from "../components/section_wrapper.vue";
import estate_card from "../components/estate_card.vue";
import utils from "@/utils/utils";

export default {
  name: "home",
  components: { section_wrapper, estate_card },

  data: () => ({
    search: null,
    category: "all",
    estates: [],
    categories: [],
    locations: [
      "Minato",
      "Shinjuku",
      "Kanagawa",
      "Itabashi",
      "Nakano",
      "Shibuya",
      "Fukuoka",
      "Saitama",
    ],
    catImages: [
      require("/src/assets/1ldk_apartment.jpg"),
      require("/src/assets/2ldk_apartment.jpg"),
      require("/src/assets/3ldk_apartment.png"),
      require("/src/assets/1k_apartment.jpg"),
      require("/src/assets/1R_apartment1.jpg"),
      require("/src/assets/retail_shop.jpg"),
      require("/src/assets/sharehouse.jpg"),
      require("/src/assets/office.jpg"),
    ],
  }),

  async created() {
    await this.fetchEstates();
    await this.fetchCategories();
  },

  methods: {
    async fetchEstates() {
      const res = await utils.http.get("/api/estates");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.estates = data;
        }
      }
    },

    async fetchCategories() {
      const res = await utils.http.get("/api/categories");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.categories = data;
        }
      }
    },

    onRouteChange(id) {
      this.$router.push({ path: `/categories/${id}/estates` });
    },

    onSearch() {
      this.$router.push({
        path: "/estates",
        query: {
          search: this.search ?? "",
          category: this.category,
        },
      });
    },
  },
};
</script>


<style scoped>
.landing {
  width: 100%;
  height: 70vh;
  background: url("/src/assets/landing.jpg") center center no-repeat;
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
}
.advanced-form {
  width: 700px;
  border-radius: 10px;
  padding: 20px;
  background: rgba(116, 109, 109, 0.5);
}

.location-wrapper {
  width: 500px;
  margin: 0 auto;
  padding: 20px 0;
}
</style>