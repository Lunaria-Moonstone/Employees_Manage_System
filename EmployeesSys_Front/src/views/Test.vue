<template>

  <GuideLineAdmin @scaling-sider="mainPageScaling" />

  <div class="main-view" :class="{ whenSiderOpen: openSider }">
    <slot></slot>
  </div>

</template>

<script lang="ts">

import GuideLineAdmin from '@/components/baseframe/GuideLine-Admin.vue';

export default {
  components: {
    GuideLineAdmin
  },
  data() {
    return {
      openSider: true,
    }
  },
  methods: {
    mainPageScaling(isClose: boolean) {
      this.openSider = !isClose
    },
  }
}
</script>