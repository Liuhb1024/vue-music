<template>
<div class="sign-up">
    <div class="title">
        <span>登录/注册</span>
    </div>
    <div class="registerForm">
        <el-form ref="registerForm" :model="registerForm" :rules="rules">
            <el-form-item prop="phoneNum">
                <el-input placeholder="请输入手机号" v-model="registerForm.phoneNum" clearable>
                    <template v-slot:prepend>用户名/手机号</template>
                </el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input placeholder="请输入密码" v-model="registerForm.password" type="password" show-password clearable>
                    <template v-slot:prepend>密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</template>
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
import {nanoid} from 'nanoid'

import {
    dateToString
} from '@/utils'

import {
    login,
    register,
    checkConsumerIfPresent
} from '@/api/consumer'

export default {
    name: 'SignUp',
    data() {
        return {
            registerForm: {
                id: -1,
                username: '',
                password: '123456',
                sex: 0, //默认为0：女
                phoneNum: '18884369734',
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
                ]
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
                    checkConsumerIfPresent({
                        //用Regex判断是手机号还是用户名
                        phoneNumOrUsername: this.registerForm.phoneNum
                    }).then(jsonData => {
                        if (jsonData.code === 1) {
                            return Promise.resolve()
                        } else {
                            return Promise.reject()
                        }
                    }).then(() => {
                        // 手机号未被占用或用户名未被使用，则进行登录
                        login(this.registerForm).then(jsonData => {
                            // 登录成功
                            if (jsonData !== null) {
                                this.$message({
                                    message: '登录成功,1秒后跳转到首页',
                                    type: 'success',
                                    duration: 1000
                                })
                                setTimeout(() => {
                                    //保存用户状态
                                    this.$store.commit('SET_USER_INFO', jsonData)
                                    this.$store.commit('SET_LOGIN_STATUS', true)
                                    //三秒后跳转到首页
                                    this.$router.push('/home')
                                }, 3000)
                            } else {
                                //登录失败
                                this.$message({
                                    message: '登录失败，请检查输入的信息是否有误！',
                                    type: 'error',
                                    duration: 2000
                                })
                            }
                        }).catch(err => console.log(err))
                    }, () => {
                        // 进行注册
                        //生成uuid作为默认用户名
                        this.registerForm.username = nanoid()
                        register(this.registerForm).then(responseBody => {
                            //注册成功,等待三秒跳转到首页
                            if (responseBody.code === 1) {
                                this.$message({
                                    message: '注册成功,三秒后跳转到首页',
                                    type: 'success',
                                    duration: 3000
                                })
                                setTimeout(() => {
                                    //三秒后跳转到首页
                                    this.$store.commit('SET_LOGIN_STATUS', true)
                                    this.$router.push('/home')
                                }, 3000)
                            } else {
                                //注册失败
                                this.$message({
                                    message: responseBody.msg,
                                    type: 'error',
                                    duration: 2000
                                })
                            }
                        }).catch(err => console.log(err))
                    }).catch(err => console.log(err))

                    //如果手机号已被注册，则登录，否则进行注册
                    /* if (isPresent) {
                        let username = this.registerForm.username
                        let phoneNum = this.registerForm.phoneNum
                        let password = this.registerForm.password
                        login(username, phoneNum, password).then(jsonData => {
                            // 登录成功
                            if (jsonData !== null) {
                                this.$message({
                                    message: '注册成功,三秒后跳转到首页',
                                    type: 'success',
                                    duration: 3000
                                })
                                setTimeout(() => {
                                    //保存用户状态
                                    this.$store.commit('SET_USER_ID', jsonData.id)
                                    this.$store.commit('SET_USER_NAME', jsonData.username)
                                    this.$store.commit('SET_USER_AVATAR', jsonData.avatar)
                                    this.$store.commit('SET_LOGIN_STATUS', true)
                                    //三秒后跳转到首页
                                    this.$router.push('/home')
                                }, 3000)
                            } else {
                                //登录失败
                                this.$message({
                                    message: '登录失败，请检查输入的信息是否有误！',
                                    type: 'error',
                                    duration: 2000
                                })
                            }
                        }).catch(err => console.log(err))
                    } else {
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
                                    //三秒后跳转到首页
                                    this.$store.commit('SET_LOGIN_STATUS', true)
                                    this.$router.push('/home')
                                }, 3000)
                            } else {
                                //注册失败
                                this.$message({
                                    message: responseBody.msg,
                                    type: 'error',
                                    duration: 2000
                                })
                            }
                        }).catch(err => console.log(err))
                    } */
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
    width: 500px;
    margin: 20px auto;

    .title {
        font-size: 30px;
        text-align: center;
    }

    .registerForm {
        .el-button {
            width: 100%;
        }
    }
}
</style>
