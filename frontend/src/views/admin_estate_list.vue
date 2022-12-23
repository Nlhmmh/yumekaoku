<template>
  <v-card>
    <v-card-title>
      Estates
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
      :items="estatelist"
      :items-per-page="10"
      :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
import http from "@/utils/http";

export default {
  name: "admin_estate_list",

  data: () => {
    return {
      headers: [
        { text: "ID", value: "id", sortable: true },
        { text: "Title", value: "title", sortable: true },
        { text: "Monthly Rent Fee", value: "rent_fee", sortable: false },
      ],

      estatelist: [],
    };
  },
  async created() {
    this.fetchEstates();
  },

  methods: {
    async fetchEstates() {
      const res = await http.get("/estates");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.estatelist = data;
        }
      }
    },
  },
};
</script>