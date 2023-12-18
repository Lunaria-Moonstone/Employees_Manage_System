<template>
  
    <!-- <var-popup v-model:show="modalShown" @closed="beforeModalClosed">
      <slot></slot>
    </var-popup> -->
    
    <el-dialog v-model="modalShown" :title="title" @close="beforeModalClosed" :draggable="draggable">
      <template #default>
        <slot></slot>
      </template>
      <template #footer>
        <slot name="footer"></slot>
      </template>
    </el-dialog>

</template>

<script lang="ts">
import { ElDialog } from 'element-plus'

export default {
  name: 'Modal',
  components: {
    ElDialog,
  },
  props: {
    shown: {
      type: Boolean,
      required: true,
      default: false,
    },
    title: {
      type: String,
      required: false,
      default: ""
    },
    draggable: {
      type: Boolean,
      required: false,
      default: true,
    }
  },
  data() {
    return {
      modalShown: false
    }
  },
  methods: {
    beforeModalClosed() {
      this.modalShown = false
      this.$emit('modal-closed', false)
    }
  },
  watch: {
    shown(newVal: Boolean) {
      this.modalShown = newVal.valueOf()
    },
  }
}
</script>