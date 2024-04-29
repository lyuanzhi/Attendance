<template>
    <div class="app-wrapper">
        <div class="side-container" :class="collapsed ? 'folded' : 'unfolded'">
            <div class="logo">
                <img :src="imgSrc" alt="logo" class="responsive-image">
            </div>
            <SideMenu />
        </div>
        <div class="main-container" :class="collapsed ? 'wider' : 'normal'">
            <div class="main-header">
                <HeaderBar />
            </div>
            <div class="main-content">
                <el-scrollbar wrap-class="scrollbar">
                    <MainView />
                </el-scrollbar>
            </div>
        </div>
    </div>
</template>

<script>
import HeaderBar from './components/HeaderBar/index'
import SideMenu from './components/SideMenu/index'
import MainView from './components/MainView/index'
import Logo from '@/assets/img/logo.png'
import LogoIcon from '@/assets/img/logo.png'

const RESIZE_WIDTH = 1440

export default {
    name: 'Layout',
    data() {
        return {
            collapsed: 0
        }
    },
    components: { HeaderBar, SideMenu, MainView },
    computed: {
        imgSrc() {
            if (this.collapsed) {
                return LogoIcon
            }
            return Logo
        }
    },
    mounted() {
        if (this.$store.state.ISLOGIN == false) {
            this.$router.push({ path: '/login' })
        }
    }
}
</script>

<style lang="less">
@import "../assets/less/scroll-bar";

.responsive-image {
    margin-top: 30px;
    width: 100%;
    height: auto;
    object-fit: contain;
}

.app-wrapper {
    width: 100%;
    height: 100%;
    overflow: hidden;

    .side-container {
        float: left;
        height: 100vh;
        transition: width 0.5s;
        background-color: #263238;

        &.unfolded {
            width: 260px;

            .logo {
                width: 260px;
                height: 84px;
            }
        }

        &.folded {
            width: 64px;

            .logo {
                width: 64px;
                height: 64px;
                padding: 10px;
                box-sizing: border-box;
            }
        }

        img {
            display: block;
            width: 100%;
            height: 100%;
        }
    }

    .main-container {
        float: left;
        height: 100vh;
        transition: width 0.5s;

        &.normal {
            width: calc(100% - 260px);
        }

        &.wider {
            width: calc(100% - 64px);
        }

        .main-content {
            .el-scrollbar {
                height: calc(100vh - 64px - 40px);
                .scroll-bar;

                .scrollbar {
                    height: 100%;
                    overflow-x: hidden;
                }
            }
        }
    }
}</style>
