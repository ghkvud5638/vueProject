<template>
  <div>
	MAIN
  <div class="board">
    <!-- Parent counter : {{ parentCounter }} <br> -->
    <!-- <button @click="addCounter">+</button> -->
    <!-- <button @click="subCounter">-</button> -->
  </div>
  <!-- <mainChild></mainChild> -->
  </div>
</template>

<script>
// import { mapGetters, mapMutations , mapActions } from 'vuex'
// import MainChild from '@/components/MainChild.vue'

export default {
  methods:{
    // ...mapMutations({
    //   //addCounter:'addCounter' ,
    //   subCounter:'subCounter'
    // }),
    // ...mapActions({
    //   addCounter:'addCounter'
    // })

  },
  computed:{
    // ...mapGetters({
    //   parentCounter : 'getCounter'
    // })
  },
  component:{
    // MainChild 컴포넌트를 하위 컴포넌트로 등록
    // 'mainChild':MainChild
  },
}
</script>

<style>

</style>