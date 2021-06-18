<template>
      <div class="contents-wrap">

        <b-modal ref="detailPop" hide-footer>
            <!--<AuthGroupDetailPop ref="detailPop" @closedPop="closedPop"/>-->
            <AuthGroupDetailPop @closedPop="closedPop"/>
        </b-modal>
        <div class="form-table">
            <table>
             <colgroup>
                <col style="width:15%;">
                <col>
                <col style="width:79px;">
                <col style="width:15%;">
                <col>
             </colgroup>
                <tbody>
                    <tr>
                        <th scope="row">
                            <label for="aas">권한 적용 시스템</label>
                        </th>
                        <td>
                            <div class="search-value">
                                <vSelect
                                    id="aas"
                                    v-model="authInfo.authSys"
                                    :options="code.authSyss"
                                    :clearable="false"
                                    :searchable="false"
                                />
                            </div>
                        </td>
                        <td></td>
                        <th >권한그룹명</th>
                        <td>
                            <div class="search-value">
                                <InputBox
                                type="text"
                                :value.sync="authInfo.authGroupName"
                                />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">사용여부</th>
                        <td>
                            <RadioButton
                                :text="'전체'"
                                :value="'A'"
                                name="radioSolarYn"
                                :checked="'Y' == authInfo.isUse"
                                @radioChange="isUseChange"
                            />
                            <RadioButton
                                :text="'사용'"
                                :value="'Y'"
                                name="radioSolarYn"
                                :checked="'Y' == authInfo.isUse"
                                @radioChange="isUseChange"
                            />
                            <RadioButton
                                :text="'사용안함'"
                                :value="'N'"
                                name="radioSolarYn"
                                :checked="'N' == authInfo.isUse"
                                @radioChange="isUseChange"
                            />                        
                        </td>
                    </tr>
                </tbody>
            </table>
            <button type="button" class="btn btn-primary" @click="search()">검색</button>
        </div>
        <div class="grid-box">
            <div class="grid-tool-box">
                <div>
                <button type="button" class="btn btn-primary float-right" @click="openDetailPop">권한그룹 변경이력</button>
                <button type="button" class="btn btn-primary float-right" @click.passive.stop="updateGroup">등록</button>
                </div>
            </div>
            <grid class="tui-grid" ref="groupGrid" :data="groupGrid.data" :columns="groupGrid.columns" :options="groupGrid.options"
             @click ="cellClick" @editingFinish="onGroupGridEditingFinish"/>
        </div>
      </div>
</template>

<script>
import AuthGroupDetailPop from "@/components/member/AuthGroupDetailPop";
import vSelect from "vue-select";
import "vue-select/dist/vue-select.css";
import "tui-grid/dist/tui-grid.css";
import InputBox from "@/components/InputBox";
import RadioButton from "@/components/RadioButton";
import { mapState, mapGetters, mapActions } from "vuex";
import { Grid } from "@toast-ui/vue-grid";
import TuiGrid from 'tui-grid';
TuiGrid.setLanguage('ko');


export default {
    name:"Auth",
    components:{
        grid:Grid,
        vSelect,
        InputBox,
        RadioButton,
        AuthGroupDetailPop
    },
    created(){
        this.groupGrid={
            options: { rowHeight: 48,
                // rowHeaders: [{
                //     type: 'checkbox',
                //     header: `<span class="ui-chk"><input id="groupChkAll" type="checkbox" name="_checked"><label for="groupChkAll"></label></span>`,
                //     renderer: {
                //         // type: CheckboxRenderer,
                //     },
                // }],
                scrollX: false,
                scrollY: true,
                bodyHeight: 240,
                showDummyRows: false,
                selectionUnit: 'row',
                display: { noData: 'No Data' },
                editingEvent: 'click',
                pageOptions: {
                    useClient: true,
                    perPage: 5,
                    visiblePages: 5,
                },
            },
            columns: [
                {header: "그룹ID",  name: "groupauthId", width: 300, minWidth: 150,align: "center", sortable: true, formatter: this.gLink, },
                {header: "그룹명",  name: "groupauthNm", width: 300, minWidth: 150,align: "center", sortable: true, formatter: this.gDetail,},
                {header: "설명",    name: "commentTxt", align: "center", sortable: true, },
                // {header: "사용여부", name: "useYn", align: "center",sortable: true, formatter: 'listItemText', editor: this.gSelect("YN"), },
                // {header: "upgroupauthId",    name: "upgroupauthId", hidden: true, },
                // {header: "upgroupauthNm",    name: "upgroupauthNm", hidden: true, },
            ],
            data: [],
        
        }
    },
    computed:{
         ...mapState({
            authInfo: state => state.auth.authInfo,
        }),
        ...mapGetters({
        })
    },
    data(){
        return{
            groupGrid: {},
            code:{
                authSyss:["BO","PO"]
            },
        }
    },
    methods:{
        ...mapActions({
            authGroupSearch:"authGroupSearch"
        }),

        search(){
            console.log("search button clicked")
            console.log(this.authInfo)

            let result = this.authGroupSearch(this.authInfo);
            result.then((res)=>{
                console.log("===search complete===")
                console.log(res)
                console.log(res.data)
                this.$refs.groupGrid.invoke('resetData', res.data )
            }).finally(()=>{
                console.log("끝")
            })
        },
        isUseChange(e){
            this.authInfo.isUse = e.target.value;
        },
        openDetailPop(){
            console.log("openDetailPop clicked")
        },
        updateGroup(){
            console.log("updateGroup clicked")
        },
        cellClick(e){
            const clickValue = this.$refs.groupGrid.invoke('getValue',e.rowKey, 'groupauthId');
            if(e.columnName == 'groupauthNm'){
                let info = {};
                info.groupauthId = clickValue;
                console.log("===groupauthNm clicked===")
                console.log(clickValue)
                console.log("===권한 정보===")
                console.log(this.authInfo)
                // this.authInfo.groupauthId = clickValue
                // this.$refs.detailPop.open(info);
                this.$refs["detailPop"].show(info);
            }


        },
        onGroupGridEditingFinish(){
            console.log("onGroupGridEditingFinish")
        },
        closedPop(rtnObj){
            console.log("closedPop!")
            console.log(rtnObj)
        }
    }
}
</script>

<style scoped lang="scss">
</style>