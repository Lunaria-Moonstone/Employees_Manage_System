<template>  
  <div class="login-container">  
    <h2>注册停车场管理系统</h2>  
    <form @submit.prevent="login">  
      <var-space direction="column" size="middle" class="input-field">
        <var-input placeholder="用户名" id="username" v-model="username" aria-required/>
        <var-input placeholder="真实姓名" id="name" v-model="name" aria-required/>
        <var-input placeholder="密码" id="password" v-model="password" aria-required/>
        <var-input placeholder="确认密码" id="re-password" v-model="re_password" aria-required/>
      </var-space>   
      <var-button :type="<Type>signBtnType" native-type="submit">{{ signBtnText }}</var-button>  
      <div style="margin-block-start: 10px; margin-block-end: 5px;"/>
      <hr style="width: 80%;"/>
      <div style="display: flex; justify-content: center; color: gray">
        已有账户？
        <var-link type="primary" underline="none" to="/login">点此登录</var-link>
      </div>
    </form>  
  </div>  

</template>  
  
<script lang="ts">
import md5 from 'md5';
import { Snackbar, type Type } from '@varlet/ui';

import { signUp } from '@/api/sign/_run'

const ENCRYPT_SALT: string = import.meta.env.VITE_ENCRYPT_SALT;

export default {  
  components: {
  },
  data() {  
    return {  
      // input
      username: '',  
      name: '',
      password: '',
      re_password: '',

      signBtnType: 'primary',
      signBtnText: '注册'
    };  
  },  
  methods: {  
    async login() {  
      const situation_a = this.username.length < 3 || this.username.length > 20;
      const situation_b = this.password.length < 6 || this.password.length > 16;
      const situation_c = this.re_password !== this.password;
      const situation_d = this.name === ''
      if (situation_a) { Snackbar.warning('用户名长度必须在3到20位之间'); return; }
      if (situation_b) { Snackbar.warning('密码长度必须在6到16位之间'); return; } 
      if (situation_c) { Snackbar.warning('两次密码输入不一致'); return; }
      if (situation_d) { Snackbar.warning('真实姓名不能为空'); return; }

      var response = await signUp(this.username, this.password, this.name)
      if (response === null) {
        Snackbar.error('发送登录请求失败');
        return;
      }
      if (response.ok) {
        this.signBtnType = 'success';
        this.signBtnText = '注册成功';
        setTimeout(() => {
          this.$router.push('/');
        }, 1000);
      }
    },

  }  
};
</script>  
  
<style scoped>  
.login-container {  
  display: flex;  
  justify-content: center;  
  align-items: center;  
  height: 100vh;  
  background-color: #f1f1f1;  
  flex-flow: column;
}  
  
.input-field {
  margin-bottom: 10px;
}

h2 {  
  font-size: 24px;
  margin-bottom: 20px;  
}  
  
form {  
  display: flex;  
  flex-direction: column;  
  width: 400px;
  padding: 20px;  
}  
  
label {  
  margin-bottom: 10px;  
}  
  
@media (max-width: 600px) {  
  form {  
    width: 90%;  
    margin: 0 auto;  
  }  
}  
</style>
