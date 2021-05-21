<template>
  <div id="header">
    <b-navbar toggleable="lg" type="dark" variant="dark" >
      <b-navbar-brand to="/">Vue.js 게시판</b-navbar-brand>
      //{{$store.state.id}} 님! / 권한은 {{$store.state.role}} //
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav >
        <b-navbar-nav >
          <b-nav-item to="/login">로그인</b-nav-item>
          <b-nav-item to="/signUp">회원가입</b-nav-item>
          <b-nav-item to="/">자유게시판</b-nav-item>
          <b-nav-item to="/">??</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  nama: "Header",
  data() {
    return {};
  }
};
</script>
<style>
#header{
  background-color: #111;
}
</style>