<template>
<div class="login">
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <h1><span>Vue-Music Backend Management Sys</span></h1>
        </div>

        <el-form :model="form" ref="form" :rules="rules" label-width="80px">
            <el-form-item label="username" prop="username">
                <el-input v-model="form.username" type="text"></el-input>
            </el-form-item>
            <el-form-item label="password" prop="password">
                <el-input v-model="form.password" type="password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="login">Sign in</el-button>
            </el-form-item>
        </el-form>
    </el-card>

</div>
</template>

<script>
import {
    getLoginStatus
} from '@/api/singer/index'

export default {
    data() {
        return {
            form: {
                username: 'admin',
                password: '123'
            },
            rules: {
                username: [{
                    required: true,
                    message: 'Please input username',
                    trigger: 'blur'
                }, {
                    min: 1,
                    max: 20,
                    message: 'The length must be between 1 and 20',
                    trigger: 'change'
                }],
                password: [{
                    required: true,
                    message: 'Please input password',
                    trigger: 'blur'
                }, {
                    min: 1,
                    max: 20,
                    message: 'The length must be between 1 and 20',
                    trigger: 'change'
                }]
            }
        }
    },
    methods: {
        login() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    let params = new URLSearchParams()
                    params.append('username', this.form.username)
                    params.append('password', this.form.password)
                    getLoginStatus(params).then(jsonData => {
                        if (jsonData.code == 1) {
                            this.$message({
                                message: jsonData.msg,
                                type: 'success',
                                duration: 2000
                            })
                            localStorage.setItem('username', this.form.username)
                            this.$router.push('/home')
                        } else {
                            this.showErrorMsg(jsonData.msg)
                        }
                    })
                } else {
                    this.showErrorMsg()
                }
            })
        },
        showErrorMsg(msg) {
            this.$message({
                message: msg,
                type: 'error',
                duration: 2000
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.login {
    width: 100%;
    height: 100vh;
    position: absolute;
    background-color: rgb(101, 173, 255);

    .box-card {
        width: 450px;
        margin: 200px auto;
        background: #96a0aa21;
        color: white;

        h1 {
            text-align: center;
        }

        .el-card {
            font-size: 34px;
        }

        .el-button {
            width: 100%;
        }
    }
}
</style>
