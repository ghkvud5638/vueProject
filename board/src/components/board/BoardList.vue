<template>
  <div class="grid-box">
    <Grid
      class="tui-grid"
      ref="tuiGrid"
      :options="gridProps.options"
      :data="gridProps.data"
      :columns="gridProps.columns"
      @click="onGridClick"
    />
    <div class="per-page-box">
      <span class="total-count-text">
        총
        <strong><span class="count">{{ gridProps.data.length }}</span></strong>
        개 게시글
      </span>
      <!-- <div class="select-box">
        <vSelect
          ref="perPage"
          :options="pageData.data"
          :clearable="false"
          :searchable="false"
          v-model="perPage"
          :reduce="(label) => label.value"
        ></vSelect>
      </div> -->

    </div>
</div>
</template>

<script>
import "vue-select/dist/vue-select.css";
import 'tui-pagination/dist/tui-pagination.css';
import { Grid } from "@toast-ui/vue-grid";
// import vSelect from "vue-select";
import TuiGrid from 'tui-grid';
TuiGrid.applyTheme('clean');
// import BoardDetail from '@/components/board/BoardDetail.vue'


export default {
  name: "BoardList",
  props: ["gridHeader", "boardList"],
   components: {
    Grid,
    // vSelect,
    // BoardDetail
  },
  data(){
    return {
      perPage: 10,
      pageData: {
        data: [
          { label: "10", value: 10 },
          { label: "20", value: 20 },
          { label: "50", value: 50 },
          { label: "100", value: 100 },
        ],
        // selected: "10",
      },
    };
  },
  created(){
  },
  computed: {
    gridProps() {
         return {
            options: {
               scrollX: false,
               scrollY: false,
               rowHeight: 30,
               bodyHeight: 480,
               showDummyRows: false,
               selectionUnit: "row",
               pageOptions: {
                  useClient: true,
                  perPage: this.perPage,
                  visiblePages: 5,
               }
        },
        columns: this.gridHeader,
        data: this.boardList
        }
      },





  },
  watch: {
    gridHeader(val) {
      this.$refs.tuiGrid.invoke("setColumns", val);
    },
    gridProps(val) {
      this.$refs.tuiGrid.invoke("resetData", val.data);
    },

  },
  methods: {
    // 그리드 클릭
    onGridClick(event) {
      if (event.targetType !== "columnHeader") {
        if (event.columnName === "boardTitle") {          
          console.log("click!!");

          const eventRow = this.$refs.tuiGrid.invoke("getRow",event.rowKey);
          this.$router.push({ name:'BoardDetail', params:{seq:eventRow.seq}});

        }
      }
    },

    // 페이지 갯수보기 변경
    onPerPageInput(data) {
      this.$refs.tuiGrid.invoke("setPerPage", data);
    },






  },
  
}
</script>

<style>
.tui-grid-row-odd{
  background-color:white;
}
</style>