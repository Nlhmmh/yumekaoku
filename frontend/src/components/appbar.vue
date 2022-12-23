<template>
  <v-app-bar app color="deep-purple accent-4" dense dark>
    <a class="navtitle" href="/">Rental House</a>

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
</template>

<script>
export default {
  name: "appbar",

  data: () => ({ isLogin: false, loginUser: {} }),

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
      this.$store.commit("logout");
      if (this.$route.path != "/") {
        this.$router.push({ path: "/" });
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
