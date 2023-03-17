<template>
  <el-container>
    <el-header>

    </el-header>
    <el-main style="width: 30%;margin-left: 35%">
      <el-card v-if="loginOrRegister == 1">
        <el-row>
          <el-col :span="6" :offset="18">
            <el-button style="margin-left: 2%;border-color: transparent" v-on:click="loginOrRegister = 2">>注册</el-button>
          </el-col>
        </el-row>

        <el-input placeholder="请输入用户名" style="margin-top: 3%" v-model="signData.username"></el-input>
        <el-input placeholder="请输入密码" style="margin-top: 3%" v-model="signData.password"></el-input>
        <el-row style="margin-top: 5%">
          <el-col :span="6" :offset="18">
            <el-button style="margin-left: 2%;border-color: transparent" v-on:click="loginOrRegister = 3">忘记密码</el-button>
          </el-col>
        </el-row>
        <el-button v-on:click="login" style="margin-top: 3%">登录</el-button>
      </el-card>
      <el-card v-else-if="loginOrRegister == 2">
        <el-row>
          <el-col :span="6" :offset="18">
            <el-button style="margin-left: 2%;border-color: transparent"  v-on:click="loginOrRegister = 1">>登录</el-button>
          </el-col>
        </el-row>
        <el-input placeholder="请输入用户名" style="margin-top: 3%" v-model="signData.username"></el-input>
        <el-input placeholder="请输入密码" style="margin-top: 3%" v-model="signData.password"></el-input>
        <el-input placeholder="请输入手机号" style="margin-top: 3%" v-model="signData.phoneNumber"></el-input>
        <el-input placeholder="密码重置码" style="margin-top: 3%" v-model="signData.answer"></el-input>
        <el-button v-on:click="sign" style="margin-top: 3%">注册</el-button>
      </el-card>
      <el-card v-else-if="loginOrRegister == 3">
        <el-row>
          <el-col :span="6" :offset="18">
            <el-button style="margin-left: 2%;border-color: transparent"  v-on:click="loginOrRegister = 1">>登录</el-button>
          </el-col>
        </el-row>
        <el-input placeholder="请输入用户名" style="margin-top: 3%" v-model="signData.username"></el-input>
        <el-input placeholder="请输入新密码" style="margin-top: 3%" v-model="signData.password"></el-input>
        <el-input placeholder="密码重置码" style="margin-top: 3%" v-model="signData.answer"></el-input>
        <el-button v-on:click="forget" style="margin-top: 3%">重置密码</el-button>
      </el-card>

    </el-main>
  </el-container>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import {ElNotification} from "element-plus";

export default {
  name: 'HomeView',
  components: {
    HelloWorld
  },
  data(){
    return {
      loginOrRegister: 1,
      signData:{
        username: '',
        password: '',
        phoneNumber: '',
        answer: ''
      }
    }
  },
  methods:{
    sign(){
      let _this = this
      _this.$axios.post("/user/sign",_this.signData).then(res=>{
        _this.loginOrRegister = 1
      })
    },
    login(){
      let _this = this
      console.log(_this.signData)
      _this.$axios.post("/user/login",_this.signData).then(res=>{
        console.log(res.data.data)
        localStorage.setItem("userId", res.data.data.id)
        switch (res.data.data.admin){
          case 1,0:{
            this.$router.push({name: "common"})
            break
          }

          case 2:{
            this.$router.push({name: "admin"})
            break
          }

        }
      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '密码不正确',
          type: 'error',
        })
      })
    },
    forget(){
      let _this = this
      console.log(_this.signData)
      _this.$axios.post("/user/forgetPwd",_this.signData).then(res=>{
        _this.loginOrRegister = 1
        // this.$router.push({name: "user", params: {flag: 4}})
      })
    }
  }
}
</script>
