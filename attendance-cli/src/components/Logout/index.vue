<template>
    <el-dropdown class="user-avatar-wrapper" @command="handleCommand">
        <div class="avatar-box">
            <el-avatar size="small" :src="avatarSrc" />
            <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="loginOut">Log Out</el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
</template>
  
<script>
import Avatar from '../../assets/img/avatarDuke.png'

export default {
    data() {
        return {
            avatarSrc: Avatar
        }
    },
    methods: {
        handleCommand(command) {
            if (command === 'loginOut') {
                this.loginOut()
            }
        },
        loginOut() {
            this.$confirm('Log out now?', 'Confirm', {
                confirmButtonText: 'Yes',
                cancelButtonText: 'No',
                type: 'warning'
            }).then(() => {
                this.$store.state.ISLOGIN = false
                this.$store.state.sections = []
                this.$store.state.curSectionIndex = 0
                this.$store.state.curNetID = null
                this.$router.push({ path: '/login' })
            }).catch(() => {

            })
        }
    }
}
</script>
  
<style lang="less">
.user-avatar-wrapper {
    float: left;
    width: 48px;
    padding: 3px 0 3px 20px;
    margin-left: 20px;
    border-left: solid 1px #ddd;
    cursor: pointer;

    .avatar-box {
        outline: none;
    }

    .el-avatar--small {
        display: inline-block;
        vertical-align: middle;
        width: 32px;
        height: 32px;
        line-height: 32px;
    }

    i {
        display: inline-block;
        vertical-align: middle;
        margin-left: 2px;
    }
}
</style>
  