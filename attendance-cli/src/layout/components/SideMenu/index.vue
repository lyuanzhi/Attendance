<template>
  <div class="side-menu-wrapper">
    <el-scrollbar wrap-class="scrollbar">
      <el-menu
        :default-active="activeMenu"
        :collapse="false"
        :unique-opened="true"
        background-color="#263238"
        text-color="#fff"
        active-text-color="#409eff"
      >
        <SideMenuItem
          v-for="route in routes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import SideMenuItem from './SideMenuItem'

export default {
  name: 'SideMenu',
  components: { SideMenuItem },
  computed: {
    routes() {
      return this.$router.options.routes
    },
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    }
  }
}
</script>

<style lang="less">
@import "../../../assets/less/side-menu";
</style>
