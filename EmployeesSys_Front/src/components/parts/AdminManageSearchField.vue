<template>
    <var-row class="search-field" :gutter="[10, 10]">
      <var-col v-for="item, idx in items" :span="6">
        <!-- <var-input class="search-input" v-if="item.type === 'input'" variant="outlined" :placeholder="item.label"
        v-model="itemModel[idx]" size="small"/> -->
        <!-- <var-select v-else></var-select> -->
        <el-row style="width: 100%;">
          <el-col :span="24">
            <label style="padding-inline-start: 10px; font-weight: bold; font-size: 14px;">{{ item.label }}</label>
          </el-col>
          <el-col :span="24" style="margin-block-start: 3px;">
            <el-input class="search-input" v-if="item.type === 'input'" :placeholder="item.label"
              v-model="itemModel[idx]" />
            <el-select class="search-input" v-else-if="item.type === 'select'" :placeholder="item.label"
              v-model="itemModel[idx]" filterable clearable>
              <el-option v-for="opt in item.options" :value="opt.value" :label="opt.label" />
            </el-select>
            <el-date-picker class="search-input" v-else :placeholder="item.label" v-model="itemModel[idx]"
              style="width: 100%; padding-inline: 10px;" />
          </el-col>
        </el-row>
      </var-col>
    </var-row>
    <div class="search-buttons">
      <var-button type="primary" text outline @click="collectData">查找</var-button>
      <var-button type="default" text outline @click="cleanForm">重置</var-button>
    </div>

</template>

<script lang="ts">
import type { Ref } from 'vue';
import { ref } from 'vue';
import { ElInput, ElSelect, ElOption, ElRow, ElCol, ElDatePicker } from 'element-plus';

import type { AdminManageSearchFieldItem } from '@/type-regulator/ComponentsType';

export default {
  name: 'AdminManageSearchField',
  components: {
    ElInput, ElSelect, ElOption, ElCol, ElRow, ElDatePicker
  },
  props: {
    items: {
      type: Array<AdminManageSearchFieldItem>,
      required: true,
    }
  },
  emits: [
    'post-data'
  ],
  data() {
    const itemModel: Ref<Array<string>> = ref([]);

    return {
      itemModel,
    };
  },
  created() {
    for (var _ of this.items) this.itemModel.push('');
  },
  methods: {
    /**
     * clear search form
     */
    cleanForm() {
      for (var idx of [...Array(this.itemModel.length).keys()])
        this.itemModel[idx] = '';
    },
    /**
     * collect and post data to parent 
     */
    collectData() {
      var data: { [key: string]: string | number } = {};
      this.itemModel.forEach((value, idx, _) => {
        value !== '' ? data[this.items[idx].key] = value : null;
      });
      this.$emit('post-data', data);
    }
  }
}
</script>

<style lang="scss" scoped>

.search-field {
  padding-block: 15px;
  max-height: 30vh;
  overflow-y: scroll;
  overflow-x: hidden;
}

.search-field::-webkit-scrollbar {
  display: none;
}

.search-input {
  width: 100%;
  padding-inline: 10px;
}

.search-buttons {
  padding-block: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 36px;

  >* {
    margin-inline: 5px;
  }
}
</style>