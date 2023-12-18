<template>
  <var-sticky :offset-top="8">
    <var-app-bar title="" id="horizontal-bar" :elevation="1" :class="{ scaling: !isClose }" round>
      <template #left>
        <span style="margin-inline: 10px; cursor: pointer; color: white;" @click="scalingSider">
          <svg t="1699413487604" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="4282" width="35" height="35">
            <path
              d="M917.333333 186.666667a5.333333 5.333333 0 0 0-5.333333-5.333334H112a5.333333 5.333333 0 0 0-5.333333 5.333334v64a5.333333 5.333333 0 0 0 5.333333 5.333333h800a5.333333 5.333333 0 0 0 5.333333-5.333333zM106.666667 773.333333v64a5.333333 5.333333 0 0 0 5.333333 5.333334h394.666667a5.333333 5.333333 0 0 0 5.333333-5.333334v-64a5.333333 5.333333 0 0 0-5.333333-5.333333H112a5.333333 5.333333 0 0 0-5.333333 5.333333zM512 474.666667H112a5.333333 5.333333 0 0 0-5.333333 5.333333v64a5.333333 5.333333 0 0 0 5.333333 5.333333h800a5.333333 5.333333 0 0 0 5.333333-5.333333V480a5.333333 5.333333 0 0 0-5.333333-5.333333z"
              p-id="4283" fill="#ffffff"></path>
          </svg>
        </span>
      </template>
      <template #right>
        <div style="margin-right: 15px; cursor: pointer;">
          <el-popover trigger="click" :width="80">
            <template #reference>
              {{ name }}
            </template>
            <template #default>
              <div class="msg-username">
                {{ name }}
              </div>
              <hr />
              <div class="msg-funcbtns">
                <var-button text type="primary" style="width: 100%;" @click="signOut">登出</var-button>
              </div>
            </template>
          </el-popover>
        </div>
      </template>
    </var-app-bar>
  </var-sticky>

  <div class="sidebar" :class="{ sidebarClose: isClose }">
    <ul class="sidebar-menu">
      <li>
        <h3>员工管理系统</h3>
      </li>
      <li v-for="item in menuItem" >
        <a :href="item.href" :class="{ active: item.active }">{{ item.title }}</a>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { ElPopover } from 'element-plus';

export default {
  name: 'GuideLineAdmin',
  components: {
    ElPopover,
  },
  props: {
    activeIdx: {
      type: Number,
      default: 0,
      required: false,
    },
  },
  emits: [
    'scaling-sider',
  ],
  data() {
    return {
      isClose: false,
      name: '',
      menuItem: [
        {
          title: '员工管理',
          href: '/admin/user',
          active: false,
        },
      ],

      msgModalShown: false,
    }
  },
  methods: {
    goTo(tg: string) {
      this.$parent !== null ? this.$parent.$router.push(tg) : null;
    },
    scalingSider() {
      this.isClose = !this.isClose;
      this.$emit('scaling-sider', this.isClose);
    },
    signOut() {
      this.$cookies.remove('user');
      this.$router.push('/login')
    }
  },
  created() {
    this.menuItem[this.activeIdx].active = true;
    this.name = this.$cookies.get('user').name;
  },
}
</script>

<style lang="scss" scoped>
#horizontal-bar {
  width: auto;
  transition: margin-left .4s ease-in-out;
}

.msg-username {
  font-weight: bolder;
  display: flex;
  // justify-content: end;
}
.msg-funcbtns {
  >* {
    margin-block-end: 5px;
  }
}
</style>