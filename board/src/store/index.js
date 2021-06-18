
// import axios from "axios";
import Vue from "vue";
import Vuex from "vuex";
import {login} from "@/store/login"
import {auth,board} from "@/store/system"

Vue.use(Vuex);

// export const store = new Vuex.Store({
export default new Vuex.Store({
    modules:{
        login,
        auth,
        board
    },
    state:{
        userInfo:{},
        authInfo:{}
    },
    // mutations:{

    // },
    // getters:{

    // },
    // actions:{

    // }


})