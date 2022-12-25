<template>
  <v-form ref="formName" v-model="formName">
    <v-text-field
      v-model="name"
      label="Name"
      placeholder="Enter your name"
      required
      :rules="[(v) => !!v || 'Required']"
    >
    </v-text-field>
  </v-form>
</template>
<script>
export default {
  name: "category_form",
  props: ["formName"],
};
</script>