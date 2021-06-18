<template>
  <div>
      Child counter : {{childCounter}}<br>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
export default {
    // Parent 에서 넘겨준 counter 속성을 passedCounter 로 받음
    //props: ['passedCounter']
    computed:{
        ...mapGetters({
            childCounter : 'getCounter'
        })
    },

}
</script>

<style>

</style>