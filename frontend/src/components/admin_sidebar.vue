<template>
  <v-navigation-drawer v-model="openDrawer" absolute temporary>
    <v-list nav dense>
      <!-- <v-list-item-group
        v-model="group"
        active-class="deep-purple--text text--accent-4"
      > -->
      <v-list-item
        v-for="item in itemList"
        :key="item.title"
        @click="goToRoute(item.path)"
      >
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-title>{{ item.title }}</v-list-item-title>
      </v-list-item>
      <template v-slot:append>
        <div class="pa-2">
          <v-btn block> Logout </v-btn>
        </div>
      </template>
      <!-- </v-list-item-group> -->
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: "admin_sidebar",
  // props: ["openDrawer"],

  data: () => {
    return {
      itemList: [
        {
          title: "Estates",
          icon: "mdi-home",
          path: "/admin/estates",
        },
        {
          title: "Categories",
          icon: "mdi-home",
          path: "/admin/categories",
        },
      ],
    };
  },

  methods: {
    goToRoute(path) {
      if (this.$route.path != path) {
        this.$router.push({ path });
      }
    },
  },
};
</script>