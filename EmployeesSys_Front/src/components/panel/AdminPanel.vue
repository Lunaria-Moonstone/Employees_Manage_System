<template>

  <GuideLineAdmin :activeIdx="activeIdx" @scaling-sider="mainPageScaling" />

  <div class="main-view" :class="{ whenSiderOpen: openSider }">
    <slot></slot>
  </div>

</template>

<script lang="ts">

import GuideLineAdmin from '@/components/baseframe/GuideLine-Admin.vue';

export default {
  name: 'AdminPanel',
  props: {
    activeIdx: {
      type: Number,
      required: false,
      default: 0,
    }
  },
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