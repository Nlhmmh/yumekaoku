<template>
  <v-card  class="ma-10 pa-5">
    <v-card-title>
      Estate List
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn color="primary" @click="onClickCreateBtn()" class="ml-2 mt-2">
        <v-icon left> mdi-plus </v-icon>
        Add
      </v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="estatelist"
      :items-per-page="10"
      :search="search"
    >
      <template v-slot:item.isRentOut="{ item }">
        <v-switch v-model="item.isRentOut" inset></v-switch>
      </template>
      <template v-slot:item.actions="{ item }">
        <!-- Edit Estate -->
        <v-btn
          class="mr-3"
          color="primary"
          fab
          x-small
          dark
          @click="onClickUpdateBtn(item.id)"
        >
          <v-icon>mdi-pencil</v-icon>
        </v-btn>

        <!-- Delete Estate -->
        <v-btn color="red" fab x-small dark @click="onClickDeleteBtn(item)">
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </template>
    </v-data-table>
    <v-dialog v-model="deleteDialog" width="500">
      <v-card>
        <v-card-title>Delete Estate</v-card-title>
        <v-card-text> Are you sure to delete this? </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="deleteDialog = false">Cancel</v-btn>
          <v-btn color="red" dark @click="deleteEstate(selectItem.id)"
            >Delete</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
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
        { text: "Category", value: "category.name", sortable: true },
        { text: "Monthly Rent Fee (¥)", value: "rentFee", sortable: true },
        { text: "Location", value: "location", sortable: false },
        { text: "RentOut", value: "isRentOut", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
      search: null,
      selectItem: {},
      estatelist: [],

      deleteDialog: false,
    };
  },
  async created() {
    await this.fetchEstates();
  },

  methods: {
    async fetchEstates() {
      const res = await http.get("/api/admin/estates");
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.estatelist = data;
        }
      }
    },

    async deleteEstate(id) {
      const res = await http.del(`/api/admin/estates/delete/${id}`);
      if (res && res.status === 200) {
        await this.fetchEstates();
        this.deleteDialog = false;
      }
    },
    onClickCreateBtn() {
      this.$router.push({ path: "/admin/estates/create" });
    },
    onClickUpdateBtn(id) {
      this.$router.push({ path: `/admin/estates/${id}/update` });
    },
    onClickDeleteBtn(item) {
      this.deleteDialog = true;
      this.selectItem = item;
    },
  },
};
</script>