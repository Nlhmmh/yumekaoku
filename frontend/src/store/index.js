import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLogin: false,
    loginUser: {}
  },
  getters: {
    loginUser: (state) => {
      let loginUser = sessionStorage.getItem("loginUser");
      if (loginUser != null) {
        state.loginUser = JSON.parse(loginUser);
        return JSON.parse(loginUser);
      }
      return state.loginUser;

    },

    isLogin: (state) => {
      let isLogin = sessionStorage.getItem("isLogin");
      if (isLogin != null) {
        state.isLogin = isLogin;
        return isLogin;
      }
      return state.isLogin;
    }
  },
  mutations: {
    setLoginUser(state, user) {
      sessionStorage.setItem("loginUser", JSON.stringify(user));
      sessionStorage.setItem("isLogin", true);
      state.loginUser = user;
      state.isLogin = true;
    },
    logout(state) {
      sessionStorage.removeItem("loginUser");
      sessionStorage.removeItem("isLogin");
      state.loginUser = {};
      state.isLogin = false;
    }
  },
  actions: {
  },
  modules: {
  }
})
