<template>
  <var-overlay v-model:show="overlayShown">
    <var-loading description="LOADING" :loading="true"></var-loading>
  </var-overlay>
</template>

<script lang="ts">
export default {
  name: 'Loading',
  props: {
    shown: {
      type: Boolean,
      required: true,
    }
  },
  data() {
    return {
      overlayShown: false,
    }
  },
  watch: {
    shown(newVal: Boolean) {
      this.overlayShown = newVal.valueOf();
    }
  }
}
</script>