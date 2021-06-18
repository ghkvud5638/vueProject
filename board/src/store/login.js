import axios from "axios";

export const login = {
    state:{
        userInfo:{
            email:"",
            password:""
        }
    },
    getters:{

    },
    mutations:{

    },
    actions:{
        handleLogin({commit}, loginObj){
            console.log("hi!")
            console.log(commit)
            console.log(loginObj)
            let promise = axios
                .post("/loginVue",loginObj)
            return promise;
        },
    }

}
