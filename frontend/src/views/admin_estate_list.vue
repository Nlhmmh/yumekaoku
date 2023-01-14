<template>
  <v-card class="ma-10 pa-5">
    <v-card-title>
      Estate List
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search By Location"
        single-line
        hide-details
      ></v-text-field>
      <v-btn color="primary" @click="onClickCreateBtn()" class="ml-2 mt-2">
        <v-icon left> mdi-plus </v-icon>
        Add
      </v-btn>
    </v-card-title>
    <v-data-table :headers="headers" :items="estatelist" :items-per-page="10">
      <template v-slot:item.imagePath="{ item }">
        <v-img
          v-if="item.imagePath"
          :src="apiURL + '/api' + item.imagePath"
          width="100"
          height="100"
          contain
        ></v-img>
        <v-img
          v-else
          :src="require('/src/assets/default-estate.jpg')"
          width="100"
          height="100"
          contain
        ></v-img>
      </template>

      <template v-slot:item.rentOut="{ item }">
        <v-switch v-model="item.rentOut"></v-switch>
      </template>

      <template v-slot:item.actions="{ item }">
        <!-- go to detail -->
        <v-btn
          class="mr-3"
          color="success"
          fab
          x-small
          dark
          @click="onDetailClick(item.id)"
        >
          <v-icon>mdi-dots-horizontal</v-icon>
        </v-btn>
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
import utils from "@/utils/utils";

export default {
  name: "admin_estate_list",

  data: () => {
    return {
      apiURL: utils.constant.LOCAL_API_URL,
      headers: [
        { text: "ID", value: "id", sortable: true },
        { text: "Image", value: "imagePath", sortable: false },
        { text: "Title", value: "title", sortable: true },
        { text: "Category", value: "category.name", sortable: true },
        { text: "Monthly Rent Fee (¥)", value: "rentFee", sortable: true },
        { text: "Location", value: "location", sortable: false },
        { text: "RentOut", value: "rentOut", sortable: false },
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
      const res = await utils.http.get(
        "/api/admin/estates",
        this.search
          ? {
              search: this.search,
            }
          : null
      );
      if (res && res.status === 200) {
        const data = await res.json();
        if (data) {
          this.estatelist = data;
        }
      }
    },

    async deleteEstate(id) {
      const res = await utils.http.del(`/api/admin/estates/delete/${id}`);
      if (res && res.status === 200) {
        await this.fetchEstates();
        this.deleteDialog = false;
      }
    },
    onClickCreateBtn() {
      this.$router.push({ path: "/admin/estate/create" });
    },
    onClickUpdateBtn(id) {
      this.$router.push({ path: `/admin/estate/${id}/update` });
    },
    onClickDeleteBtn(item) {
      this.deleteDialog = true;
      this.selectItem = item;
    },
    onDetailClick(id) {
      this.$router.push({ path: `/estates/${id}` });
    },
  },
};
</script>