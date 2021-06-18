<template lang="html">
  <div>
      <!-- <ModalPopup :title="'권한그룹상세'" :visible.sync="modalVisible" width="700px"> -->
        <div class="form-table">
          <table>
            <colgroup>
              <col class="col-form-title">
              <col style="width: 100%;">
              <col class="col-form-title">
              <col style="width: 100%;">
            </colgroup>
          <tbody>
            <tr>
              <th scope="row">그룹명</th>
              <td>
                <b-form-input v-model="authInfo.groupauthId" readonly></b-form-input>
              </td>
            </tr>
          </tbody>
          </table>
        </div>
      <!-- </ModalPopup> -->
  </div>
</template>

<script>
// import ModalPopup from "@/components/ModalPopup";

import 'vue-select/dist/vue-select.css';


export default {
    name:"AuthGruopDetailPop",
    components:{
        // ModalPopup
    },
    data(){
        return {
            modalVisible: false,
            // detail: { groupauthId: '', groupauthNm: '', useYn: '', commentTxt: '' },
        }
    },  
    computed:{
    },
    methods:{
        open(info){
            console.log("open methods")
            console.log(info)
            this.modalVisible = true
        }
    }
}
</script>


<style lang="scss" scoped>

</style>