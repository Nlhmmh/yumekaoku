<template>
  <div>
    <!-- Advanced search form in landing page -->
    <div class="landing">
      <!-- <v-img
        max-height="350"
        max-width="100%"
        :src="require('/src/assets/landing-bg.jpg')"
      ></v-img> -->
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
            color="success"
            class="mx-1"
            large
            @click="onSearch()"
            style="height: 46px"
          >
            Search
            <v-icon right> mdi-search </v-icon>
          </v-btn>
        </div>
      </div>
    </div>

    <!-- Featured homes section -->
    <section_wrapper
      title="Featured Homes"
      subtitle="With Yumekaoku, everything related to finding an apartment can be done online"
    >
      <v-row dense style="padding: 0 50px">
        <v-col v-for="estate in estates" :key="estate.id" cols="3">
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

    <!-- Home Categories Section -->
    <section_wrapper
      title="Categories"
      subtitle="A collection of properties carefully chosen organized by category."
    >
      <v-row class="my-5" dense>
        <v-col v-for="cat in categories" :key="cat.id" :cols="4">
          <v-card @click="onRouteChange(cat.id)" class="my-1">
            <v-img
              src="https://cdn.vuetifyjs.com/images/cards/house.jpg"
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
  </div>
</template>

<script>
import section_wrapper from "../components/section_wrapper.vue";
import estate_card from "../components/estate_card.vue";
import http from "@/utils/http";

export default {
  name: "home",
  components: { section_wrapper, estate_card },

  data: () => ({
    search: null,
    category: "all",
    estates: [],
    categories: [],
    cards: [
      {
        title: "Pre-fab homes",
        src: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
        flex: 4,
      },
      {
        title: "Favorite road trips",
        src: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
        flex: 4,
      },
      {
        title: "Best airlines",
        src: "https://cdn.vuetifyjs.com/images/cards/plane.jpg",
        flex: 4,
      },
      {
        title: "Pre-fab homes",
        src: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
        flex: 3,
      },
      {
        title: "Favorite road trips",
        src: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
        flex: 3,
      },
      {
        title: "Best airlines",
        src: "https://cdn.vuetifyjs.com/images/cards/plane.jpg",
        flex: 3,
      },
      {
        title: "Pre-fab homes",
        src: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
        flex: 3,
      },
    ],
  }),

  async created() {
    await this.fetchEstates();
    await this.fetchCategories();
  },

  methods: {
    async fetchEstates() {
      const res = await http.get("/api/estates");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.estates = data;
        }
      }
    },

    async fetchCategories() {
      const res = await http.get("/api/categories");
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
  background: url("/src/assets/landing-bg.jpg") center center no-repeat;
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
}
.advanced-form {
  width: 700px;
  border-radius: 10px;
  padding: 20px;
  background: rgba(247, 246, 246, 0.5);
}
</style>