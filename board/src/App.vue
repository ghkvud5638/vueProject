<template>
 <div id="app" class="app-wrap">
    <Header/>
    <div class="main-wrap">
			<router-view/>
		</div>
  </div>
</template>

<script>
// import request from "request";
import Header from "@/components/Header";


export default {
  name: "App",
  components: {
    Header
  },
  mounted() {
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #f1f1f1;
  /* margin: 3% 30% */
}
.main-wrap{
  margin: 0px 10% 10px 10%;
  background-color: white;
}

</style>
