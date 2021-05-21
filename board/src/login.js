import axios from "axios";

export const login = {
    modules:{
    menu
    },
  state: {
    accessToken: null,
    userInfo: {
      username: "",
      password: "",
    },
    errShow: false,
    errMsg: "",
  },
  getters: {
    // getUserName(state) {
    //   return state.userInfo.userName;
    // },
    // getPassword(state) {
    //   return state.userInfo.password;
    // },
  },
  mutations: {
    printErrMsg(state, payload) {
      this.errMsg = payload.errMsg;
      this.errShow = payload.errShow;
    },
  },
  actions: {
    printErrMsg({ commit }, payload) {
      commit("printErrMsg", payload);
    },
    handleLogin({ commit}, loginObj){
		console.log("오냐?");
      let promise = axios
        .post("/login", loginObj)
      return promise;
    },
    handleLogout({commit}){
      let promise = axios
        .post("/logOut", {}, {})
        sessionStorage.removeItem("refreshtoken");
        localStorage.removeItem("token");
        return promise;
    },
  },
};
