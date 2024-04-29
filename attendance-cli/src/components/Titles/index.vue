<template>
    <div class="bread-crumbs-wrapper">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>
                <el-button type="text" class="home-link" :disabled="!hasClick" @click="goHome">
                    <i class="icon vue-dsn-icon-index" />
                    Dashboard
                </el-button>
            </el-breadcrumb-item>
            <el-breadcrumb-item v-if="this.$store.state.sections.length != 0">{{
                this.$store.state.sections[this.$store.state.curSectionIndex].sectionname }}</el-breadcrumb-item>
            <el-breadcrumb-item v-else-if="this.$store.state.isFaculty == true">Hogwarts</el-breadcrumb-item>
            <el-breadcrumb-item v-for="item in levelList" :key="item.path">{{ item.meta.title }}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</template>

<script>
export default {
    name: 'Titles',
    data() {
        return {
            levelList: [],
            hasClick: false
        }
    },
    watch: {
        $route(route) {
            this.getBreadCrumb()
            this.isHome(route.path)
        }
    },
    created() {
        this.getBreadCrumb()
        this.isHome(this.$route.path)
    },
    methods: {
        isHome(path) {
            if (path === '/home') {
                this.hasClick = false
            } else {
                this.hasClick = true
            }
        },
        goHome() {
            this.$router.push('/home')
        },
        getBreadCrumb() {
            const matched = this.$route.matched.filter(item => item.meta && item.meta.title)
            this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.title !== 'Dashboard')
        }
    }
}
</script>

<style lang="less">
.bread-crumbs-wrapper {
    float: left;
    width: 35%;
    height: 32px;
    padding: 0 30px;

    .el-breadcrumb {
        line-height: 32px;
    }

    .home-link {

        &,
        .icon {
            font-weight: bold;
            font-size: 14px;
            color: #333 !important;
        }

        &:hover {
            color: #409eff;

            .icon {
                color: #409eff;
            }
        }

        &.is-disabled:hover {
            color: #333;

            .icon {
                color: #333;
            }
        }
    }
}
</style>
