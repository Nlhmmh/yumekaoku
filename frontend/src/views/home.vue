<style>
  @import '@/assets/main.css';
</style>
<template>
  <v-container class="no-pd-mr" fill-height fluid>
    <!-- Advanced search form in landing page -->
    <v-img
      lazy-src="https://picsum.photos/id/11/10/6"
      max-height="350"
      max-width="100%"
      src="https://picsum.photos/id/11/500/300"
    ></v-img>

    <!-- Featured homes section -->
    <section_wrapper
      title="Featured Homes"
      subtitle="With Yumekaoku, everything related to finding an apartment can be done online"
    >
      <v-row dense align="center"
      justify="center">
        <v-col v-for="estate in estates" :key="estate.id">
          <estate_card
            :id="estate.id"
            :title="estate.title"
            :location="estate.location"
            :rentFee="estate.rentFee"
            :category="estate.category"
          ></estate_card>
        </v-col> </v-row
    ></section_wrapper>

    <!-- Home Categories Section -->
    <section_wrapper
      title="Categories"
      subtitle="A collection of properties carefully chosen organized by category."
    >
      <v-row dense class="my-5">
        <v-col v-for="card in cards" :key="card.title" :cols="card.flex">
          <v-card>
            <v-img
              :src="card.src"
              class="white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
              <v-card-title v-text="card.title"></v-card-title>
            </v-img>
          </v-card>
        </v-col>
      </v-row>
    </section_wrapper>
  </v-container>
</template>

<script>
import section_wrapper from "../components/section_wrapper.vue";
import estate_card from "../components/estate_card.vue";
import http from "@/utils/http";

export default {
  name: "home",
  components: { section_wrapper, estate_card },

  data: () => ({
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
  },
};
</script>