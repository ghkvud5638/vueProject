import axios from "axios";

export const auth = {
    state:{
        authInfo:{
            isUse:"",
            authSys:"전체",
            authGroupName:"",
            groupauthId:""
        },
        code:{
            authSyss:["BO","PO"]
        },

    },
    getters:{

    },
    mutations:{

    },
    actions:{
        authGroupSearch({commit}, loginObj){
            console.log("hi! authGroupSearch")
            console.log(commit)
            console.log(loginObj)
            let promise = axios
                .post("/api/auth/search",loginObj)
            return promise;
        },
    }

}

export const board={

}
