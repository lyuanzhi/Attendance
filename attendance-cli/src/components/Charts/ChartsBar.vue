<template>
  <div ref="dom" class="charts-bar" />
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'ChartsBar',
  props: {
    title: {
      type: String,
      default: ''
    },
    data: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      dom: null
    }
  },
  mounted() {
    this.drawing()
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resize)
  },
  methods: {
    resize() {
      this.dom.resize()
    },
    drawing() {
      const xAxisData = Object.keys(this.data)
      const seriesData = Object.values(this.data)
      const options = {
        // 标题
        title: {
          text: this.title,
          left: 'center',
          top: 15,
          textStyle: {
            fontSize: 24,
            fontWeight: 'normal'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: xAxisData,
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            max: 1
          }
        ],
        color: ['#2d8cf0'],
        series: [
          {
            name: '',
            type: 'bar',
            barWidth: '40%',
            data: seriesData
          }
        ]
      }
      this.dom = echarts.init(this.$refs.dom)
      this.dom.setOption(options)
      window.addEventListener('resize', this.resize)
    }
  }
}
</script>
