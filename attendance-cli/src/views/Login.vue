<template>
    <div class="login-wrapper">
        <div class="form-box">
            <div class="form-title">
                <p>Log in with NetID</p>
            </div>
            <el-form ref="loginForm" :model="loginForm" :rules="loginRules" label-width="0px" class="login-form">
                <el-form-item prop="netID">
                    <el-input v-model="loginForm.netID" type="text" auto-complete="off" placeholder="netID"
                        prefix-icon="el-icon-user" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="password"
                        prefix-icon="el-icon-lock" @keyup.enter.native="handleLogin" />
                </el-form-item>
                <el-form-item v-if="showCheckbox">
                    <el-checkbox v-model="loginForm.isFaculty" :key="checkboxKey">Faculty</el-checkbox>
                </el-form-item>
                <el-form-item>
                    <el-button :loading="loading" size="small" type="primary" style="width:100%; font-size: 20px;"
                        @click.native.prevent="handleLogin">
                        <span v-if="!loading">Log In</span>
                        <span v-else>Loading...</span>
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { login } from '@/api'
import { notify } from '@/notify'
export default {
    name: 'Login',
    data() {
        return {
            loginForm: {
                netID: '',
                password: '',
                isFaculty: true
            },
            loginRules: {
                netID: [{ required: true, trigger: 'blur', message: 'NetID cannot be empty!' }],
                password: [{ required: true, trigger: 'blur', message: 'Password cannot be empty!' }]
            },
            loading: false,
            redirect: undefined,
            showCheckbox: true,
            checkboxKey: 0
        }
    },
    watch: {
        $route: {
            handler: function (route) {
                this.redirect = route.query && route.query.redirect
            },
            immediate: true
        }
    },
    created() {
        this.$store.state.ISLOGIN = false
        this.$store.state.sections = []
        this.$store.state.curSectionIndex = 0
        this.$store.state.isFaculty = true
        this.$store.state.curNetID = null
    },
    mounted() {
        let isMobile = /iPhone|iPad|iPod|Android/i.test(navigator.userAgent)
        if (isMobile) {
            this.loginForm.isFaculty = false
            this.showCheckbox = false
        }
        if (localStorage.getItem('netID') && localStorage.getItem('password') && localStorage.getItem('isFaculty')) {
            this.loginForm.netID = localStorage.getItem('netID')
            this.loginForm.password = localStorage.getItem('password')
            this.loginForm.isFaculty = localStorage.getItem('isFaculty')
            this.checkboxKey += 1
        }
    },
    methods: {
        handleLogin() {
            this.$refs.loginForm.validate(valid => {
                const data = {
                    netID: this.loginForm.netID,
                    password: this.loginForm.password,
                    isFaculty: this.loginForm.isFaculty
                }
                if (valid) {
                    this.loading = true
                    login(data).then(res => {
                        // console.log(res.data.data)
                        if (res.data.code == 200 && res.data.data != null) {
                            this.$store.state.ISLOGIN = true
                            this.$store.state.isFaculty = data.isFaculty
                            this.$store.state.curNetID = res.data.data.netid
                            this.$store.state.sections = res.data.data.sections
                            localStorage.setItem('netID', data.netID)
                            localStorage.setItem('password', data.password)
                            localStorage.setItem('isFaculty', data.isFaculty)
                            this.loading = false
                            let isMobile = /iPhone|iPad|iPod|Android/i.test(navigator.userAgent)
                            if (isMobile) {
                                this.$router.push({ path: this.redirect || '/scan' })
                            } else {
                                this.$router.push({ path: this.redirect || '/home' })
                            }
                        } else {
                            this.loading = false
                            notify('Wrong Password!')
                        }
                    }).catch(() => {
                        this.loading = false
                        notify('Wrong Password!')
                    })
                }
            })
        }
    }
}
</script>

<style lang="less">
.login-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100vh;
    background-size: cover;
    background-position: center;
    background-size: 100%;
    background-image: url(../assets/img/loginBack.jpg);

    .form-box {
        width: 280px;
        padding: 16px 30px;
        background: #fff;
        border-radius: 4px;
        box-shadow: 0 15px 30px 0 rgba(0, 0, 1, .1);

        .form-title {
            margin: 10px auto 35px;
            text-align: center;
            color: #707070;
            font-size: 24px;
        }
    }
}
</style>
