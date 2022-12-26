<template>
  <v-container fluid class="mx-0 my-5"> 
    <h2 class="display-1 font-weight-bold text-center">{{ title }}</h2>
    <p class="text-center">{{ subtitle }}</p>
    <slot ></slot>
  </v-container>
</template>

<script>
export default {
  name: "section_wrapper",

  props: ['title', 'subtitle']
};
</script>