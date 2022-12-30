<template>
  <v-app>
    <v-main>
      <appbar></appbar>
      <router-view class="mx-3 my-2" style="height: 100%" />
      <footing></footing>
    </v-main>
  </v-app>
</template>

<script>
import appbar from "./components/appbar.vue";
import footing from "./components/footing.vue";

export default {
  name: "App",

  components: {
    appbar,
    footing,
  },

  data: () => ({}),
};
</script>
