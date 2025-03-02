<template>
<div class="sign-in-page">
    <div class="sign-in">
        <div class="left-content">
            <div class="logo">
                <i class="el-icon-headset"></i>
                <span>音乐，让生活更美好</span>
            </div>
            <div class="description">
                发现、聆听、分享
            </div>
        </div>

        <div class="sign-in-content">
            <div class="sign-in-box">
                <div class="sign-in-header">
                    <i class="el-icon-headset"></i>
                    <h1>Music</h1>
                </div>

                <el-form ref="registerForm" :model="registerForm" :rules="rules" label-width="0" class="sign-in-form">
                    <el-form-item prop="phoneNum">
                        <el-input 
                            v-model="registerForm.phoneNum" 
                            prefix-icon="el-icon-user" 
                            placeholder="用户名/手机号"
                            clearable>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input 
                            v-model="registerForm.password" 
                            prefix-icon="el-icon-lock" 
                            type="password" 
                            placeholder="密码"
                            show-password
                            clearable
                            @keyup.enter.native="submitForm">
                        </el-input>
                    </el-form-item>
                    <div class="form-options">
                        <el-checkbox v-model="rememberMe">记住我</el-checkbox>
                        <router-link to="/register" class="to-register">没有账号？去注册</router-link>
                    </div>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm" :loading="loading">
                            {{ loading ? '处理中...' : '登录/注册' }}
                        </el-button>
                    </el-form-item>
                </el-form>

                <div class="sign-in-footer">
                    <div class="divider">
                        <span>其他登录方式</span>
                    </div>
                    <div class="social-login">
                        <i class="el-icon-s-platform"></i>
                        <i class="el-icon-s-custom"></i>
                        <i class="el-icon-s-promotion"></i>
                    </div>
                </div>
            </div>
        </div>

        <div class="right-content">
            <div class="logo">
                <i class="el-icon-video-play"></i>
                <span>开启音乐之旅</span>
            </div>
            <div class="description">
                千万曲库任你选择
            </div>
        </div>
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
    name: 'SignIn',
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
            rememberMe: false,
            loading: false,
            rules: {
                phoneNum: [{
                        required: true,
                        message: '请输入用户名或手机号',
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
                        message: '长度不得小于6个字符',
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
                    this.loading = true;
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
                            this.loading = false;
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
                        }).catch(err => {
                            this.loading = false;
                            console.log(err);
                        })
                    }, () => {
                        // 进行注册
                        //生成uuid作为默认用户名
                        this.registerForm.username = nanoid()
                        register(this.registerForm).then(responseBody => {
                            this.loading = false;
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
                        }).catch(err => {
                            this.loading = false;
                            console.log(err);
                        })
                    }).catch(err => {
                        this.loading = false;
                        console.log(err);
                    })
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
.sign-in-page {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 1000; // 确保覆盖其他内容
    background: #2d3a4b;

    .sign-in {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        
        .left-content, .right-content {
            width: 25%;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background: rgba(255, 255, 255, 0.02);
            padding: 20px;
            transition: all 0.3s;
            
            .logo {
                text-align: center;
                margin-bottom: 20px;
                
                i {
                    font-size: 48px;
                    color: rgba(255, 255, 255, 0.7);
                    margin-bottom: 15px;
                    display: block;
                }
                
                span {
                    font-size: 24px;
                    color: rgba(255, 255, 255, 0.7);
                    display: block;
                }
            }
            
            .description {
                font-size: 16px;
                color: rgba(255, 255, 255, 0.5);
                text-align: center;
            }
            
            &:hover {
                background: rgba(255, 255, 255, 0.05);
                
                .logo {
                    i, span {
                        color: #fff;
                    }
                }
                
                .description {
                    color: rgba(255, 255, 255, 0.8);
                }
            }
        }
        
        .sign-in-content {
            width: 420px;
            padding: 20px;
            
            .sign-in-box {
                background: transparent;
                
                .sign-in-header {
                    text-align: center;
                    margin-bottom: 40px;
                    
                    i {
                        font-size: 40px;
                        color: #fff;
                        margin-bottom: 10px;
                    }
                    
                    h1 {
                        font-size: 26px;
                        color: #fff;
                        margin: 0;
                        font-weight: normal;
                    }
                }
                
                .sign-in-form {
                    .el-input {
                        margin-bottom: 20px;
                        
                        /deep/ .el-input__inner {
                            height: 47px;
                            background: rgba(255, 255, 255, 0.1);
                            border: 1px solid rgba(255, 255, 255, 0.1);
                            border-radius: 4px;
                            color: #fff;
                            padding-left: 45px;
                            
                            &::placeholder {
                                color: rgba(255, 255, 255, 0.7);
                            }
                            
                            &:focus {
                                border-color: #409EFF;
                            }
                        }
                        
                        /deep/ .el-input__prefix {
                            left: 15px;
                            
                            i {
                                font-size: 18px;
                                color: rgba(255, 255, 255, 0.7);
                            }
                        }
                    }
                    
                    .form-options {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        margin-bottom: 20px;
                        color: #fff;
                        
                        /deep/ .el-checkbox {
                            color: #fff;
                            
                            .el-checkbox__label {
                                color: #fff;
                            }
                            
                            .el-checkbox__inner {
                                background: transparent;
                                border-color: rgba(255, 255, 255, 0.3);
                            }
                            
                            &.is-checked .el-checkbox__inner {
                                background-color: #409EFF;
                                border-color: #409EFF;
                            }
                        }
                        
                        .to-register {
                            color: #fff;
                            text-decoration: none;
                            font-size: 14px;
                            opacity: 0.8;
                            
                            &:hover {
                                opacity: 1;
                                color: #409EFF;
                            }
                        }
                    }
                    
                    .el-button {
                        width: 100%;
                        height: 47px;
                        background: #409EFF;
                        border: none;
                        font-size: 16px;
                        
                        &:hover, &:focus {
                            background: #66b1ff;
                        }
                    }
                }
                
                .sign-in-footer {
                    margin-top: 30px;
                    text-align: center;
                    
                    .divider {
                        position: relative;
                        margin: 20px 0;
                        
                        &:before, &:after {
                            content: '';
                            position: absolute;
                            top: 50%;
                            width: 35%;
                            height: 1px;
                            background: rgba(255, 255, 255, 0.2);
                        }
                        
                        &:before {
                            left: 0;
                        }
                        
                        &:after {
                            right: 0;
                        }
                        
                        span {
                            display: inline-block;
                            padding: 0 10px;
                            color: rgba(255, 255, 255, 0.7);
                            font-size: 14px;
                            background: #2d3a4b;
                        }
                    }
                    
                    .social-login {
                        display: flex;
                        justify-content: center;
                        margin-top: 20px;
                        
                        i {
                            font-size: 24px;
                            color: rgba(255, 255, 255, 0.7);
                            margin: 0 15px;
                            cursor: pointer;
                            transition: all 0.3s;
                            
                            &:hover {
                                color: #fff;
                                transform: translateY(-2px);
                            }
                        }
                    }
                }
            }
        }
    }
}

// 响应式调整
@media screen and (max-width: 1200px) {
    .sign-in-page {
        .sign-in {
            .left-content, .right-content {
                width: 20%;
            }
        }
    }
}

@media screen and (max-width: 992px) {
    .sign-in-page {
        .sign-in {
            .left-content, .right-content {
                display: none;
            }
            
            .sign-in-content {
                width: 90%;
                max-width: 420px;
                margin: 0 auto;
            }
        }
    }
}

// 保持原有的移动端响应式样式
@media screen and (max-width: 480px) {
    .sign-in-page {
        .sign-in {
            .sign-in-content {
                width: 90%;
                
                .sign-in-box {
                    .sign-in-header {
                        h1 {
                            font-size: 24px;
                        }
                    }
                }
            }
        }
    }
}
</style>
