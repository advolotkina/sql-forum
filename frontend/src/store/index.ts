import Vue from "vue";
import Vuex from "vuex";
import { StoreOptions } from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    role: localStorage.getItem('user-role') || '',
    login: localStorage.getItem('user-name') || '',
    token: ''
  },
  mutations: {
    authLogin: (state, user) => {
      localStorage.setItem('user-name', user.login);
      localStorage.setItem('user-role', user.role);
      state.login = user.login;
      state.role = user.role;
    },
    authLogout: (state) => {
      state.token = '';
      state.role = '';
      state.login = '';
      localStorage.removeItem('user-role');
      localStorage.removeItem('user-name');
    }
  },
  actions : {
    login: (context, user) => {
      context.commit("authLogin", user)
    },
    logout: (context) => {
      context.commit('authLogout');
    }
  },
  getters: {
    isAuthenticated: state => {
      if (state.role != null && state.role != '') {
        return true;
      } else {
        return false;
      }
    },
    isAdmin: state => {
      if (state.role === 'admin') {
        return true;
      } else {
        return false;
      }
    },
    getUsername: state => {
      return state.login;
    }
  }
});
