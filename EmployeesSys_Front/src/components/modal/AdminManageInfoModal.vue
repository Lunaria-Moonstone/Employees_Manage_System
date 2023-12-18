<template>
  <Modal :shown="modalShown" title="详细信息" @modal-closed="mountModalShown">
    <!-- <div v-for="key in data.keys()">{{ key }}:{{ data.get(key) }}</div> -->
    <div class="form-body">
      <var-row :gutter="[10, 10]">
        <var-col v-for="key in data.keys()" :span="12">
          <var-input :placeholder="key.valueOf()" :model-value="String(data.get(key))" variant="outlined" size="small"
            style="width: 100%;" readonly />
        </var-col>
      </var-row>
    </div>
    <template #footer>
      <var-button @click="closeModal" size="small">关闭</var-button>
    </template>
  </Modal>
</template>

<script lang="ts">
import Modal from '../baseframe/Modal.vue';
export default {
  name: 'AdminManageInfoModal',
  components: { Modal },
  props: {
    data: {
      type: Map<String, String | Number | Array<String | Number>>,
      required: true
    },
    shown: {
      type: Boolean,
      required: true,
    }
  },
  emits: ['modal-closed'],
  data() {
    return {
      modalShown: false,
    };
  },
  methods: {
    closeModal() {
      this.modalShown = false
    },
    mountModalShown(newVal: boolean) {
      this.modalShown = newVal;
      this.$emit('modal-closed', false);
    },
  },
  watch: {
    shown(newVal: Boolean) {
      this.modalShown = newVal.valueOf();
    }
  }
}
</script>

<style scoped>
.form-body {
  margin-block-start: 10px;
  padding-inline-end: 10px;
  width: 100%;
}

@media (max-width: 800px) {
  .form-body {
    width: 600px;
  }
}
</style>