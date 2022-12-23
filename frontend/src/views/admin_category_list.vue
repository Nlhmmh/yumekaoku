<template>
  <v-card>
    <v-card-title>
      Categories
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="categorylist"
      :items-per-page="10"
      :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
import http from "@/utils/http";

export default {
  name: "admin_category_list",

  data: () => {
    return {
      headers: [
        { text: "ID", value: "id", sortable: true },
        { text: "Name", value: "name", sortable: true },
      ],

      categorylist: [],
    };
  },
  async created() {
    this.fetchCategories();
  },

  methods: {
    async fetchCategories() {
      const res = await http.get("/api/categories");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.categorylist = data;
        }
      }
    },
  },
};
</script>