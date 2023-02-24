<template>
<div class="sign-up">
    <div class="title">
        <span>用户注册</span>
    </div>
    <div class="registerForm">
        <el-form ref="registerForm" :model="registerForm" :rules="rules">
            <el-form-item prop="phoneNum">
                <el-input placeholder="请输入手机号" v-model="registerForm.phoneNum" clearable>
                    <template v-slot:prepend>+86</template>
                </el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input placeholder="请输入密码" v-model="registerForm.password" type="password" show-password clearable>
                    <template v-slot:prepend>密码</template>
                </el-input>
            </el-form-item>
            <el-form-item prop="reconfirmedPassword">
                <el-input placeholder="请重复密码" v-model="registerForm.reconfirmedPassword" type="password" show-password clearable>
                    <template v-slot:prepend>确认密码</template>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="submitForm" type="primary" plain>登录/注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</div>
</template>

<script>
import {
    dateToString
} from '@/utils'

import {
    register,
} from '@/api/consumer'

export default {
    name: 'SignUp',
    data() {
        return {
            registerForm: {
                id: -1,
                username: '',
                password: '1234567',
                reconfirmedPassword: '1234567',
                sex: 0, //默认为0：女
                phoneNum: '123456',
                email: '',
                birth: '',
                introduction: '这里空空的，什么也没有~',
                avatar: '/img/consumer-img/default.jpg', //设置默认头像
                createTime: '',
                updateTime: ''
            },
            rules: {
                phoneNum: [{
                        required: true,
                        message: '请输入手机号',
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        max: 11,
                        message: '长度在 3 到 11 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                password: [{
                        required: true,
                        message: '请输入密码',
                        trigger: 'blur'
                    },
                    {
                        min: 6,
                        message: '长度不得小于6个字符，且包含大小写字符各一个',
                        trigger: ['blur', 'change']
                    }
                ],
                reconfirmedPassword: [{
                        required: true,
                        message: '请重复密码',
                        trigger: 'blur'
                    },
                    {
                        min: 6,
                        message: '长度不得小于6个字符，且包含大小写字符各一个',
                        trigger: ['blur', 'change']
                    }
                ],
            }
        }
    },
    methods: {
        submitForm() {
            this.$refs.registerForm.validate((valid) => {
                //验证通过，提交用户数据
                if (valid) {
                    this.registerForm.createTime = dateToString(new Date())
                    this.registerForm.updateTime = dateToString(new Date())
                    this.registerForm.birth = dateToString(new Date())
                    //拆解registerForm对象，因为不需要reconfirmedPassword属性
                    const {
                        reconfirmedPassword,
                        ...userData
                    } = this.registerForm
                    //console.log(userData)

                    // 尝试提交
                    register(userData).then(responseBody => {
                        //注册成功,等待三秒跳转到首页
                        if (responseBody.code === 1) {
                            // this.$router.push('/home')
                            this.$message({
                                message: '注册成功,三秒后跳转到首页',
                                type: 'success',
                                duration: 3000
                            })
                            setTimeout(() => {
                                //三秒跳转到首页
                                this.$store.commit('SET_LOGIN_STATUS', true)
                                this.$router.push('/home')
                            }, 3000)
                        } else {
                            this.$message({
                                message: responseBody.msg,
                                type: 'error',
                                duration: 2000
                            })
                        }
                    }).catch(err => console.log(err))
                } else {
                    this.$message({
                        message: '输入的信息有误，请重试',
                        type: 'warning',
                        duration: 2000
                    })
                    return false;
                }
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.sign-up {
    width: 400px;
    margin: 20px auto;

    .title {
        font-size: 32px;
        text-align: center;
    }

    .registerForm {
        .el-button {
            width: 100%;
        }
    }
}
</style>
