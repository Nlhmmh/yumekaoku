<template>
  <v-card class="mx-auto" max-width="300" @click="goToRoute(id)">
    <v-img
      src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
      height="200px"
    ></v-img>

    <v-card-title> {{ title }} </v-card-title>

    <v-card-subtitle> {{ location }} </v-card-subtitle>

    <v-card-text class="d-flex justify-between align-center">
      <v-chip label color="secondary">
        {{ category.name }}
      </v-chip>
      <v-spacer></v-spacer>
      <div class="text-red">¥{{ rentFee }}</div>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "estate_card",

  props: ["id", "title", "location", "rentFee", "category"],
  methods: {
    goToRoute(id) {
      this.$router.push({ path: `/estates/${id}/detail` });
    },
  },
};
</script>