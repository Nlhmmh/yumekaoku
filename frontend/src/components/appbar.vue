<template>
  <div>
    <v-app-bar app color="success" dense dark>
      <v-app-bar-nav-icon
        v-if="isLogin && loginUser.role === 'admin'"
        @click="openDrawer = true"
      ></v-app-bar-nav-icon>
      <a class="navtitle" href="/">
        <v-icon>mdi-home-city </v-icon> Yumekaoku Rental House</a
      >

      <v-spacer></v-spacer>

      <!-- Home -->
      <router-link class="mx-2 navlink" to="/">ホーム</router-link>

      <!-- Register -->
      <span v-if="!isLogin">|</span>
      <router-link v-if="!isLogin" class="mx-2 navlink" to="/register"
        >アカウント登録</router-link
      >

      <!-- Login -->
      <span v-if="!isLogin">|</span>
      <router-link v-if="!isLogin" class="mx-2 navlink" to="/login"
        >ログイン</router-link
      >

      <!-- Profile -->
      <span v-if="isLogin">|</span>
      <router-link v-if="isLogin" class="mx-2 navlink" to="/profile"
        >プロフィール</router-link
      >

      <!-- Login out -->
      <span v-if="isLogin">|</span>
      <a v-if="isLogin" class="mx-2 navlink" @click="logout()">ログアウト</a>
    </v-app-bar>

    <!-- <admin-sidebar></admin-sidebar> -->
    <v-navigation-drawer v-model="openDrawer" fixed temporary>
      <v-list>
        <v-list-item>
          <v-list-item-avatar>
            <v-icon class="grey lighten-1" dark> mdi-account-circle </v-icon>
          </v-list-item-avatar>
          <span>
            {{ loginUser.name }}
            <br />
            {{ loginUser.email }}
          </span>
        </v-list-item>
      </v-list>
      <v-divider></v-divider>
      <v-list nav dense>
        <v-list-item-group v-model="selectedItem" color="primary">
          <v-list-item
            v-for="(item, i) in itemList"
            :key="i"
            @click="goToRoute(item.path)"
          >
            <v-list-item-icon>
              <v-icon v-text="item.icon"></v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title v-text="item.title"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
      <template v-slot:append>
        <div class="pa-2">
          <v-btn block @click="logout()"> Logout </v-btn>
        </div>
      </template>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: "appbar",

  data: () => ({
    isLogin: false,
    loginUser: {},
    openDrawer: false,
    group: null,
    selectedItem: 0,
    //Sidebar Menu Items List
    itemList: [
      // {
      //   title: "Dashboard",
      //   icon: "mdi-view-dashboard",
      //   path: "/admin/dashboard",
      // },

      { title: "My Profile", icon: "mdi-account-box", path: "/profile" },

      {
        title: "Estates",
        icon: "mdi-home-city",
        path: "/admin/estates",
      },
      {
        title: "Appointments",
        icon: "mdi-calendar",
        path: "/admin/appointments",
      },
      {
        title: "Users",
        icon: "mdi-account-group-outline",
        path: "/admin/users",
      },
      {
        title: "Categories",
        icon: "mdi-domain",
        path: "/admin/categories",
      },
    ],
  }),

  created() {
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal, oldVal) => {
        this.loginUser = newVal;
      },
      { deep: true }
    );

    this.isLogin = this.$store.state.isLogin;
    this.$store.watch(
      () => {
        return this.$store.state.isLogin;
      },
      (newVal, oldVal) => {
        this.isLogin = newVal;
      },
      { deep: true }
    );
  },

  methods: {
    logout() {
      this.openDrawer = false;
      this.$store.commit("logout");

      if (this.$route.path != "/") {
        this.$router.push({ path: "/" });
      }
    },
    goToRoute(path) {
      if (this.$route.path != path) {
        this.$router.push({ path });
      }
    },
  },
};
</script>
<style scoped>
.navlink {
  color: white !important;
  text-decoration: none;
}

.navlink:hover {
  cursor: pointer;
  text-decoration: underline;
}

.navtitle {
  color: white !important;
  text-decoration: none;
  cursor: pointer !important;
}
</style>
