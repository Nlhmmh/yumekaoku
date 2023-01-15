<template>
  <v-card class="ma-1" max-width="300" height="370" @click="goToRoute(id)">
    <v-img
      width="100%"
      v-if="imagePath"
      :src="apiURL + imagePath"
      height="200px"
    ></v-img>
    <v-img
      width="100%"
      v-else
      :src="require('/src/assets/default-estate.jpg')"
      height="200px"
    ></v-img>

    <v-card-title> {{ title }} </v-card-title>

    <v-card-subtitle> {{ location }} </v-card-subtitle>

    <v-card-text class="d-flex justify-between align-center">
      <v-chip label color="secondary">
        {{ category.name }}
      </v-chip>
      <v-spacer></v-spacer>
      <div style="color: green">{{ formatCurrency(rentFee) }}</div>
    </v-card-text>
  </v-card>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "estate_card",

  props: ["id", "title", "location", "rentFee", "category", "imagePath"],

  data: () => ({
    apiURL: `${utils.constant.LOCAL_API_URL}/api`,
  }),

  methods: {
    goToRoute(id) {
      this.$router.push({ path: `/estates/${id}` });
    },

    formatCurrency: (value) => utils.formatCurrency(value),
  },
};
</script>