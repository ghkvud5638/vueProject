import Vue from "vue";
import VueRouter from "vue-router";
// import BoardList from "@/components/board/BoardList";
import Board from "@/components/board/Board";
import login from "@/components/member/login"; 
import BoardDetail from "@/components/board/BoardDetail"; 
import BoardCreate from "@/components/board/BoardCreate";
import NotFound from "@/components/NotFound";
import Signup from "@/components/member/SignUp";
import userInfo from "@/components/userInfo";

Vue.use(VueRouter); // 뷰 어플리케이션에 라우터 플러그인을 추가한다.

// 라우터 객체를 생성
export default new VueRouter({
  // mode: "history",
  routes: [
    {
      path: "/",
      name: "Board",
      component: Board
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
