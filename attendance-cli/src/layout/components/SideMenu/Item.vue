<script>
export default {
  name: 'Item',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    }
  },
  render(h, context) {
    const { icon, title } = context.props
    const vnodes = []
    if (icon) {
      const iconName = 'icon ' + icon
      vnodes.push(<i class={iconName}></i>)
    }
    if (title) {
      vnodes.push(<span slot='title' style="font-size:16px;font-weight:bold;">{title}</span>)
    }
    return vnodes
  }
}
</script>
