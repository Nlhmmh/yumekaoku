<template>
  <v-card class="mx-auto" max-width="300">
    <v-img
      src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
      height="200px"
    ></v-img>

    <v-card-title> {{ title }} </v-card-title>

    <v-card-subtitle> {{ description }} </v-card-subtitle>

    <v-card-actions>
      <v-btn color="orange lighten-2" text> Detail </v-btn>

      <v-spacer></v-spacer>

     
    </v-card-actions>

   
  </v-card>
</template>

<script>
export default {
  name: "estate_card",

  props: ['title', 'description', 'rent_fee']
};
</script>