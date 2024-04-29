<template>
    <div class="scanCall-wrapper">
        <el-card shadow="always">
            <div style="display: flex; align-items: center;">
                <div class="control-btns">
                    <el-tag size="medium" style="display: flex; align-items: center;">
                        Enable
                        <el-switch style="margin-left: 10px;" v-model="startGen" @change="handleSwitchChange" />
                    </el-tag>
                </div>
            </div>
            <canvas ref="qrcodeCanvas" class="QRcode"></canvas>
            <div class="hints">Please scan the QR code to check in.</div>
        </el-card>
    </div>
</template>

<script>
import { codeSync, setRemainToAbsent } from '@/api'
import QRCode from 'qrcode';
export default {
    name: 'ScanCall',
    data() {
        return {
            startGen: false,
            intervalId: null
        }
    },
    created() {
        this.$bus.$on("refresh", this.handleRefresh)
    },
    beforeDestroy() {
        this.$bus.$off('refresh', this.handleRefresh)
    },
    mounted() {
        this.generateQRCode("Not Start Yet!")
    },
    methods: {
        handleRefresh() {
            this.startGen = false
            clearInterval(this.intervalId)
            this.generateQRCode("Not Start Yet!")
        },
        generateQRCode(text) {
            try {
                const canvas = this.$refs.qrcodeCanvas;
                QRCode.toCanvas(canvas, text, { scale: 24 });
            } catch (error) { }
        },
        generateRandomCode() {
            let length = 10
            let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
            let result = ''
            for (let i = 0; i < length; i++) {
                result += characters.charAt(Math.floor(Math.random() * characters.length))
            }
            return result
        },
        handleSwitchChange() {
            if (this.$store.state.sections.length == 0) {
                return
            }
            if (this.startGen == true) {
                this.updateQrCode()
                this.intervalId = setInterval(() => {
                    this.updateQrCode()
                }, 1000)
            } else {
                clearInterval(this.intervalId)
                this.generateQRCode("You Are Too Late!")
                let data = {
                    sectionId: this.$store.state.sections[this.$store.state.curSectionIndex].sectionid,
                    date: new Date().toISOString().substr(0, 10)
                }
                setRemainToAbsent(data).then(res => {
                    // console.log(res)
                }).catch(() => { })
            }
        },
        updateQrCode() {
            let code = this.generateRandomCode()
            let json = {
                sectionId: this.$store.state.sections[this.$store.state.curSectionIndex].sectionid,
                date: new Date().toISOString().substr(0, 10),
                code: code
            }
            this.generateQRCode(JSON.stringify(json))
            let data = {
                sectionId: this.$store.state.sections[this.$store.state.curSectionIndex].sectionid,
                code: code
            }
            codeSync(data).then(res => {
                // console.log(res)
            }).catch(() => { })
        }
    }
}
</script>

<style lang="less">
.scanCall-wrapper {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;

    .QRcode {
        width: 30vw !important;
        height: auto !important;
    }

    .hints {
        font-size: 16px;
        color: #302e2e;
        text-align: center;
    }

}
</style>
