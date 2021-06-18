import Vue from "vue";
import VueRouter from "vue-router";
// import BoardList from "@/components/board/BoardList";
import Board from "@/components/board/Board";
import login from "@/components/member/login"; 
import BoardDetail from "@/components/board/BoardDetail"; 
import BoardCreate from "@/components/board/BoardCreate";
import NotFound from "@/components/NotFound";
import Signup from "@/components/member/SignUp";
import userInfo from "@/components/member/userInfo";
import Main from "@/components/Main";
import Auth from "@/components/member/Auth";

Vue.use(VueRouter); // 뷰 어플리케이션에 라우터 플러그인을 추가한다.

// 라우터 객체를 생성
export default new VueRouter({
  // mode: "history",
  routes: [
    // {
    //   path: "/",
    //   name: "Board",
    //   component: Board
    // },
    {
      path:"/",
      name:"Main",
      component:Main
    },
    {
      path:"/board",
      name:"Board",
      component:Board
    },
    {
      path:"/auth",
      name:"Auth",
      component:Auth
    },



    {
      path: "/login",
      name: "login",
      component: login
    },
    {
      path: "/detailVue/:seq",
      name: "BoardDetail",
      component: BoardDetail,
      props:true
    },

    {
      path: "/boardCreateVue",
      name: "boardCreateVue",
      component: BoardCreate
    },
    {
      path:"/signUp",
      name:"SignUn",
      component:Signup
    },
    {
      path: "*",
      name: "NotFound",
      component: NotFound
    },
    {
      path:"/userInfo",
      name:"userInfo",
      component:userInfo
    }
  ]
});
