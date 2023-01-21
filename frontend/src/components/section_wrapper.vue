<template>
  <v-container fluid style="margin: 32px 16px">
    <h2 class="display-1 font-weight-bold text-center">{{ title }}</h2>
    <p class="text-center mt-2">{{ subtitle }}</p>
    <slot></slot>
  </v-container>
</template>

<script>
export default {
  name: "section_wrapper",

  props: ["title", "subtitle"],
};
</script>