<template>
  <div class="main-view">
    <transition name="fade" mode="out-in">
      <keep-alive>
        <router-view :key="key" />
      </keep-alive>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'MainView',
  computed: {
    key() {
      return this.$route.path
    }
  }
}
</script>

<style lang="less">
.main-view {
  padding: 15px 25px;
  box-sizing: border-box;
}
</style>
