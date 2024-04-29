<template>
  <!-- eslint-disable vue/require-component-is -->
  <component v-bind="renderTag(to)">
    <slot />
  </component>
</template>

<script>
export default {
  props: {
    to: {
      type: String,
      required: true
    }
  },
  methods: {
    renderTag(url) {
      return {
        is: 'router-link',
        to: url
      }
    }
  }
}
</script>
