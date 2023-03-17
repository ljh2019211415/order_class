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
          <span>考核表制定</span>
        </el-menu-item>
        <el-menu-item index="2" @click="select(2)">
          <el-icon><icon-menu /></el-icon>
          <span>本周评分表</span>
        </el-menu-item>
        <el-menu-item index="3" @click="select(3)">
          <el-icon><icon-menu /></el-icon>
          <span>教师列表</span>
        </el-menu-item>
        <el-menu-item index="4" @click="select(4)">
          <el-icon><icon-menu /></el-icon>
          <span>设置督导员</span>
        </el-menu-item>

      </el-menu>
    </el-aside>
    <el-main style="padding: 0">
      <div v-if="menuIndex == 1">
        <h4>考核表制定</h4>
        <el-card>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input1"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value1" :options="options"/>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input2"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value2" :options="options" />
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input3"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value3" :options="options"/>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input4"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value4" :options="options"/>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input5"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value5" :options="options"/>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input6"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value6" :options="options"/>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input7"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value7" :options="options"/>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input8"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value8" :options="options"/>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input9"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value9" :options="options"/>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="15">
              <el-input placeholder="请输入评分问题" v-model="table.input10"></el-input>
            </el-col>
            <el-col :offset="1" :span="8">
              <el-cascader v-model="selectD.value10" :options="options"/>
            </el-col>
          </el-row>
          <el-row style="margin-top: 6%">
            <el-col :offset="18" :span="6">
              <el-button v-on:click="sendTable">确定</el-button>
            </el-col>
          </el-row>
        </el-card>
      </div>
      <div v-if="menuIndex == 2">
        <h4>本周评分表</h4>
        <el-table :data="scoreList" border style="width: 100%">
          <el-table-column prop="date" label="日期" width="180" />
          <el-table-column prop="tname" label="姓名" width="180" />
          <el-table-column  label="操作" >
            <template #default="scope">

              <el-button link type="primary" size="small" @click="show(scope.row)">查看详情</el-button>

            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="menuIndex == 3">
        <h4>教师列表</h4>
        <el-table :data="teacherList" border style="width: 100%">
          <el-table-column prop="id" label="编号" width="180" />
          <el-table-column prop="name" label="姓名" width="180" />
          <el-table-column  label="Address" >
            <template #default="scope">

              <el-button link type="primary" size="small" @click="func(1,scope.row.id)">本周评分记录</el-button>


              <el-button link type="primary" size="small" @click="func(2,scope.row.id)">能力模型图</el-button>
              <el-button link type="primary" size="small" @click="func(3,scope.row.id)">教师课表</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="menuIndex == 4">
        <h4>督导员管理</h4>
        <el-input
            v-model="addId"
            placeholder="请输入要添加的督导员id"
            class="input-with-select"
            style="margin-top: 3%; width: 50%"
        >

          <template #append>
            <el-button v-on:click="add">
              <el-icon>
                <Finished/>
              </el-icon>
            </el-button>
          </template>
        </el-input>
        <el-table :data="adminList" border style="width: 100%">
          <el-table-column prop="id" label="编号" width="180" />
          <el-table-column prop="username" label="姓名" width="180" />
          <el-table-column prop="phoneNumber" label="手机号" width="180" />
          <el-table-column  label="Address" >
            <template #default="scope">

              <el-button link type="primary" size="small" @click="del(scope.row.id)">取消督导员身份</el-button>

            </template>
          </el-table-column>
        </el-table>
      </div>
      <div ></div>
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
      <h4>本周评分表</h4>
    </template>
    <template #default>
      <el-table :data="scoreList" border style="width: 100%">
        <el-table-column prop="date" label="日期" width="180" />
        <el-table-column prop="tname" label="姓名" width="180" />
        <el-table-column  label="操作" >
          <template #default="scope">

            <el-button link type="primary" size="small" @click="show(scope.row)">查看详情</el-button>

          </template>
        </el-table-column>
      </el-table>
    </template>
    <template #footer>

    </template>
  </el-drawer>
  <el-drawer v-model="drawer4" :direction="'rtl'" size="80%">
    <template #header>
      <h4>评分详情</h4>
    </template>
    <template #default>
      <el-table :data="list" border style="width: 100%">
        <el-table-column prop="content" label="内容" width="180" />
        <el-table-column prop="target" label="对应指标" width="180" />
        <el-table-column  prop="score" label="分数" ></el-table-column>
      </el-table>
    </template>
    <template #footer>

    </template>
  </el-drawer>
</template>

<script>
import * as echarts from "_echarts@5.4.1@echarts";

export default {
  name: "admin",
  data(){
    return{
      menuIndex: 1,
      teacherList: [],
      drawer1: false,
      drawer2: false,
      drawer3: false,
      drawer4: false,
      teacherId: 0,
      courseList: [],
      table:{
        input1: '',
        input2: '',
        input3: '',
        input4: '',
        input5: '',
        input6: '',
        input7: '',
        input8: '',
        input9: '',
        input10: ''
      },
      selectD:{
        value1: '',
        value2: '',
        value3: '',
        value4: '',
        value5: '',
        value6: '',
        value7: '',
        value8: '',
        value9: '',
        value10: '',
      },
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
      adminList: [],
      addId: null,
      scoreList: [],
      content: [],
      target: [],
      score: [],
      list: []
    }
  },
  methods: {
    show(info){
      console.log(info)
      let _this = this
      _this.drawer4 = true
      _this.content = info.e.content.split("*")
      _this.target = info.e.target.split(",")
      _this.score = info.score.split(",")
      for (let i = 0; i< _this.content.length;i++){
        _this.list[i] = {
          content: _this.content[i],
          target: _this.target[i],
          score: _this.score[i]
        }
      }
      console.log(_this.content)
      console.log(_this.target)
      console.log(_this.score)
    },
    del(id){
      let _this = this
      _this.$axios.post("user/setAdmin",{id:id,admin:0}).then(res=>{
        _this.select(4)
      })
    },
    add(){
      let _this = this
      _this.$axios.post("user/setAdmin",{id:_this.addId,admin:1}).then(res=>{
        _this.select(4)
      })
    },
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
    sendTable(){
      let _this = this
      console.log(_this.selectD)
      console.log(_this.table)
      let temp = {
        content: '',
        target: ''
      }
      temp.content += _this.table.input1 + '*'
      temp.content += _this.table.input2 + '*'
      temp.content += _this.table.input3 + '*'
      temp.content += _this.table.input4 + '*'
      temp.content += _this.table.input5 + '*'
      temp.content += _this.table.input6 + '*'
      temp.content += _this.table.input7 + '*'
      temp.content += _this.table.input8 + '*'
      temp.content += _this.table.input9 + '*'
      temp.content += _this.table.input10
      temp.target += _this.selectD.value1 + ','
      temp.target += _this.selectD.value2 + ','
      temp.target += _this.selectD.value3 + ','
      temp.target += _this.selectD.value4 + ','
      temp.target += _this.selectD.value5 + ','
      temp.target += _this.selectD.value6 + ','
      temp.target += _this.selectD.value7 + ','
      temp.target += _this.selectD.value8 + ','
      temp.target += _this.selectD.value9 + ','
      temp.target += _this.selectD.value10
      _this.$axios.post("user/setTable",temp).then(res=>{

      })

    },
    func(index,id){
      let _this = this
      if(index == 1){
        _this.drawer1 = true
        _this.$axios.post("user/getRecord",{id:id}).then(res=>{
          console.log(res.data.data)
          _this.scoreList = res.data.data
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
    handleOpen(key, keyPath) {

    },
    handleClose(key, keyPath) {

    },
    select(index) {
      let _this = this
      _this.menuIndex = index
      if(index == 1){

      }else if(index == 2){
        _this.$axios.post("user/getRecord",{}).then(res=>{
          console.log(res.data.data)
          _this.scoreList = res.data.data
        })
      }else if(index == 3){
        _this.$axios.post("/user/getTeacherList",{}).then(res=>{
          _this.teacherList = res.data.data
          console.log(res.data.data)

        })
      }else if(index == 4){
        _this.$axios.post("user/getAdmin",{}).then(res=>{
          _this.adminList = res.data.data
        })
      }
    }
  }
}
</script>

<style scoped>

</style>