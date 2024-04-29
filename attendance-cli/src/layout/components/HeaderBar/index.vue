<template>
    <div class="header-bar clear-fix">
        <Titles />
        <div style="display: flex; justify-content:flex-end; align-items: center;">
            <el-select v-if="this.$store.state.sections.length != 0" v-model="sectionIndex" style="width: 200px"
                @change="selectSectionChange()">
                <el-option v-for="(item, index) in this.$store.state.sections" :key="index" :value="index"
                    :label="item.sectionname" />
            </el-select>
            <Logout />
        </div>

    </div>
</template>

<script>
import Titles from '@/components/Titles'
import Logout from '@/components/Logout'

export default {
    data() {
        return {
            sectionIndex: 0
        }
    },
    components: {
        Titles,
        Logout
    },
    methods: {
        selectSectionChange() {
            this.$store.state.curSectionIndex = this.sectionIndex
            this.$bus.$emit("refresh")
        }
    }
}
</script>

<style lang="less">
.header-bar {
    height: 32px;
    padding: 16px 20px;
}
</style>
