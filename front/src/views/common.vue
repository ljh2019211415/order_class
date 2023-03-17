<template>
  <el-container>
    <el-aside width="18%">
      <h5 class="mb-2">功能表单</h5>
      <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
      >
        <el-menu-item index="1" @click="select(1)">
          <el-icon><icon-menu /></el-icon>
          <span>教师名单</span>
        </el-menu-item>
        <el-menu-item index="2" @click="select(2)">
          <el-icon><icon-menu /></el-icon>
          <span>个人资料</span>
        </el-menu-item>


      </el-menu>
    </el-aside>
    <el-main style="padding: 0">
      <div v-if="menuIndex == 1">
        <h4>教师列表</h4>
        <el-table :data="teacherList" border style="width: 100%">
          <el-table-column prop="id" label="编号" width="180" />
          <el-table-column prop="name" label="姓名" width="180" />
          <el-table-column  label="操作" >
            <template #default="scope">

              <el-button link type="primary" size="small" @click="func(1,scope.row.id)">考核表打分</el-button>


              <el-button link type="primary" size="small" @click="func(2,scope.row.id)">能力模型图</el-button>
              <el-button link type="primary" size="small" @click="func(3,scope.row.id)">教师课表</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="menuIndex == 2">
        <h4>个人资料</h4>
        <el-card style="width: 70%;margin-left: 15%">
          <el-row>
            <el-col :span="23" :offset="1">
              用户id：{{userInfo.id}}
            </el-col>
          </el-row>

          <div style="margin-left: 2%">用户名：{{userInfo.username}}</div>
          <div style="margin-left: 2%">联系方式：{{userInfo.phoneNumber}}</div>
        </el-card>
      </div>
    </el-main>
  </el-container>
  <el-drawer v-model="drawer3" :direction="'rtl'" size="80%">
    <template #header>
      <h4>教师课表</h4>
    </template>
    <template #default>
      <el-table :data="courseList" border style="width: 100%">
        <el-table-column prop="teacher" label="教师编号" width="180" />
        <el-table-column prop="date" label="周中日期" width="180" />
        <el-table-column  prop="time" label="时间" ></el-table-column>
      </el-table>
    </template>

  </el-drawer>
  <el-drawer v-model="drawer2" :direction="'rtl'" size="80%">
    <template #header>

    </template>
    <template #default>
      <div id="myChart123" :style="{width: '900px', height: '550px'}"></div>
    </template>
    <template #footer>

    </template>
  </el-drawer>
  <el-drawer v-model="drawer1" :direction="'rtl'" size="80%">
    <template #header>

    </template>
    <template #default>
      <el-table :data="scoreTable" border style="width: 100%">
        <el-table-column prop="content" label="问题" width="180" />
        <el-table-column prop="target" label="对应指标" width="180" />
        <el-table-column label="分数" >
          <template #default="scope">

            <el-input placeholder="请输入分数" v-model="input[scope.$index]"></el-input>
          </template>
        </el-table-column>
      </el-table>
      <el-input placeholder="请输入备注" v-model="comment"></el-input>
    </template>
    <template #footer>
      <el-button v-on:click="submit">提交</el-button>
    </template>
  </el-drawer>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name: "common",
  data(){
    return {
      menuIndex: 1,
      userInfo: {},
      teacherList: [],
      drawer1: false,
      drawer2: false,
      drawer3: false,
      courseList: [],
      options:[{
        value: '0',
        label: '教学态度',
      },{
        value: '1',
        label: '教案评分',
      },{
        value: '2',
        label: '教学设计',
      },{
        value: '3',
        label: '课堂调控',
      },{
        value: '4',
        label: '专业知识',
      }],
      content: [],
      target: [],
      scoreTable: [],
      input: [],
      teacher: 0,
      tableId: 0,
      comment: '',

    }
  },
  created() {
    this.select(1)

    },
  methods:{
    echartsInit(id){
      let _this = this


      let myChart = echarts.init(document.getElementById("myChart123"))
      _this.$axios.post("user/getScore",{id:id}).then(res=>{
        console.log(res.data.data)
        myChart.setOption({
          title: {
            text: '能力模型图'
          },
          legend: {
            data: ['能力值']
          },
          radar: {
            // shape: 'circle',
            indicator: [
              { name: '教学态度', max: 10 },
              { name: '教案评分', max: 10 },
              { name: '教学设计', max: 10 },
              { name: '课堂调控', max: 10 },
              { name: '专业知识', max: 10 },
            ]
          },
          series: [
            {
              name: 'Budget vs spending',
              type: 'radar',
              data: [
                {
                  value: res.data.data,
                  name: '能力值'
                }
              ]
            }
          ]
        })
      })


    },
    submit(){
      let _this = this
      let temp = {
        userId: parseInt(localStorage.getItem("userId")),
        teacherId: _this.teacher,
        tableId: _this.tableId,
        comment: _this.comment,
        score: ''
      }
      let i = 0
      for (i = 0; i < _this.input.length - 1; i++) {
        temp.score += _this.input[i] + ','
      }
      temp.score += _this.input[i]
      console.log(temp)
      _this.$axios.post("/user/addRecord",temp).then(res=>{
        console.log(res)
      })

    },
    handleOpen(key, keyPath) {

    },
    handleClose(key, keyPath) {

    },
    func(index,id){
      let _this = this
      if(index == 1){
        _this.drawer1 = true
        _this.teacher = id
        _this.$axios.post("/user/getTable",{}).then(res=>{
          _this.tableId = res.data.data[0].id
          _this.content = res.data.data[0].content.split("*")
          _this.target = res.data.data[0].target.split(",")

          for(let i = 0;i < 10;i++ ){

            _this.scoreTable[i] = {
              content: _this.content[i],
              target: _this.options[_this.target[i]].label,
              score: 0
            }
          }


        })
      }else if(index == 2){
        setTimeout(()=>{
          this.echartsInit(id)
        },1000)

        _this.drawer2 = true
      }else if(index == 3){
        _this.drawer3 = true
        _this.$axios.post("/user/getCourseInfo",{id: id}).then(res=>{
          console.log(res.data.data)
          _this.courseList = res.data.data
          for (let i = 0; i < _this.courseList.length; i++) {
            switch (_this.courseList[i].time){
              case 1:{
                _this.courseList[i].time = "8:00 - 9:35"
                break
              }
              case 2:{
                _this.courseList[i].time = "9:00 - 11:25"
                break
              }
              case 3:{
                _this.courseList[i].time = "13:00 - 14:35"
                break
              }
              case 4:{
                _this.courseList[i].time = "14:50 - 16:25"
                break
              }
            }
          }
        })
      }
    },
    select(index) {
      let _this = this
      _this.menuIndex = index
      if(index == 1){
        _this.$axios.post("/user/getTeacherList",{}).then(res=>{
          _this.teacherList = res.data.data
          console.log(res.data.data)

        })
      }else if(index == 2){
        _this.$axios.post("user/getInfo",{id: localStorage.getItem("userId")}).then(res=>{
          _this.userInfo = res.data.data
        })
      }
    }
  }
}
</script>

<style scoped>

</style>