<template>
  <div>
    <v-img
      lazy-src="https://picsum.photos/id/11/10/6"
      max-height="350"
      max-width="100%"
      src="https://picsum.photos/id/11/500/300"
    ></v-img>
    <section_wrapper
      title="Featured Homes"
      subtitle="With wagaya Japan, everything related to finding an apartment can be done online"
    ></section_wrapper>
    <v-row dense>
      <v-col v-for="n in 4" :key="n">
        <estate_card
          title="Top western road trips"
          description="1,000 miles of wonder"
          price="195000"
        ></estate_card>
      </v-col>
    </v-row>
    <section_wrapper
      title="Categories"
      subtitle="A collection of properties carefully chosen by the staff of wagaya organized by category."
    ></section_wrapper>
    <v-row dense>
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
  </div>
</template>

<script>
import section_wrapper from "../components/section_wrapper.vue";
import estate_card from "../components/estate_card.vue";
export default {
  name: "home",
  components: { section_wrapper, estate_card },

  data: () => ({
    cards: [
      {
        title: "Pre-fab homes",
        src: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
        flex: 12,
      },
      {
        title: "Favorite road trips",
        src: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
        flex: 6,
      },
      {
        title: "Best airlines",
        src: "https://cdn.vuetifyjs.com/images/cards/plane.jpg",
        flex: 6,
      },
    ],
  }),
};
</script>