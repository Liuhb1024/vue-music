<template>
<div class="sign-in-page">
    <div class="sign-in-container">
        <!-- 左侧内容 -->
        <div class="left-section">
            <div class="icon-container">
                <i class="el-icon-headset"></i>
            </div>
            <div class="text-container">
                <h2>音乐，让生活更美好</h2>
                <p>发现、聆听、分享</p>
            </div>
        </div>
        
        <!-- 中间登录表单 -->
        <div class="center-section">
            <div class="logo">
                <i class="el-icon-headset"></i>
                <h2>Melodia</h2>
            </div>
            
            <el-form ref="registerForm" :model="registerForm" :rules="rules" label-width="0" class="login-form">
                <el-form-item prop="phoneNum">
                    <el-input 
                        v-model="registerForm.phoneNum" 
                        prefix-icon="el-icon-user" 
                        placeholder="请输入手机号"
                        clearable>
                    </el-input>
                </el-form-item>
                
                <el-form-item prop="password">
                    <el-input 
                        v-model="registerForm.password" 
                        prefix-icon="el-icon-lock" 
                        type="password" 
                        placeholder="请输入密码"
                        show-password
                        clearable
                        @keyup.enter.native="submitForm">
                    </el-input>
                </el-form-item>
                
                <div class="form-options">
                    <el-checkbox v-model="rememberMe">记住我</el-checkbox>
                    <router-link to="/register" class="to-register">没有账号？去注册</router-link>
                </div>
                
                <el-button type="primary" @click="submitForm" :loading="loading" class="submit-btn" :disabled="loading">
                    <span v-if="!loading">登录</span>
                    <span v-else class="loading-text">
                        <i class="el-icon-loading"></i> 登录中...
                    </span>
                </el-button>
                
                <!-- 添加成功消息提示 -->
                <div v-if="loginSuccess" class="success-message">
                    <i class="el-icon-success"></i> 
                    <span>登录成功，即将跳转...</span>
                    <div class="progress-bar">
                        <div class="progress" :style="{width: progressWidth + '%'}"></div>
                    </div>
                </div>
            </el-form>
        </div>
        
        <!-- 右侧内容 -->
        <div class="right-section">
            <div class="icon-container">
                <i class="el-icon-video-play"></i>
            </div>
            <div class="text-container">
                <h2>开启音乐之旅</h2>
                <p>千万曲库任你选择</p>
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
            loginSuccess: false,
            progressWidth: 0,
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
                                // 显示成功消息和进度条
                                this.loginSuccess = true;
                                
                                // 保存用户状态
                                this.$store.commit('user/SET_LOGIN_STATUS', true);
                                this.$store.commit('user/SET_USER_INFO', jsonData);
                                
                                // 使用进度条动画
                                const startTime = Date.now();
                                const duration = 1500; // 3秒
                                
                                const updateProgress = () => {
                                    const elapsed = Date.now() - startTime;
                                    this.progressWidth = Math.min(100, (elapsed / duration) * 100);
                                    
                                    if (elapsed < duration) {
                                        requestAnimationFrame(updateProgress);
                                    } else {
                                        this.$router.push('/home');
                                    }
                                };
                                
                                requestAnimationFrame(updateProgress);
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
                            if (responseBody.code === 1) {
                                // 显示成功消息和进度条
                                this.loginSuccess = true;
                                
                                // 保存用户状态
                                this.$store.commit('user/SET_LOGIN_STATUS', true);
                                this.$store.commit('user/SET_USER_INFO', responseBody.data);
                                
                                // 使用进度条动画
                                const startTime = Date.now();
                                const duration = 3000; // 3秒
                                
                                const updateProgress = () => {
                                    const elapsed = Date.now() - startTime;
                                    this.progressWidth = Math.min(100, (elapsed / duration) * 100);
                                    
                                    if (elapsed < duration) {
                                        requestAnimationFrame(updateProgress);
                                    } else {
                                        this.$router.push('/home');
                                    }
                                };
                                
                                requestAnimationFrame(updateProgress);
                            } else {
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
    width: 100vw;
    height: 100vh;
    background-color: #2d3a4b;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 9999;
    overflow: hidden;
    
    // 添加背景动画效果
    &:before {
        content: '';
        position: absolute;
        width: 200%;
        height: 200%;
        top: -50%;
        left: -50%;
        background: radial-gradient(circle at center, rgba(255,255,255,0.03) 0%, rgba(0,0,0,0) 70%);
        animation: rotate 30s infinite linear;
        z-index: 0;
    }
    
    @keyframes rotate {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(360deg);
        }
    }
}

.sign-in-container {
    display: flex;
    width: 100%;
    height: 100%;
    position: relative;
    z-index: 1;
}

.left-section, .right-section {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: white;
    transition: all 0.5s ease;
    
    &:hover {
        background: rgba(255, 255, 255, 0.03);
        
        .icon-container i {
            transform: scale(1.1);
            color: #409EFF;
        }
    }
    
    .icon-container {
        margin-bottom: 20px;
        
        i {
            font-size: 60px;
            color: white;
            transition: all 0.3s ease;
        }
    }
    
    .text-container {
        text-align: center;
        
        h2 {
            font-size: 24px;
            font-weight: normal;
            margin-bottom: 10px;
            opacity: 0.9;
        }
        
        p {
            font-size: 16px;
            opacity: 0.7;
            transition: all 0.3s ease;
        }
    }
}

.center-section {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 0 20px;
    position: relative;
    
    // 添加微妙的光效
    &:before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: radial-gradient(ellipse at center, rgba(255,255,255,0.05) 0%, rgba(0,0,0,0) 70%);
        pointer-events: none;
    }
    
    .logo {
        text-align: center;
        margin-bottom: 30px;
        animation: fadeInDown 0.8s;
        
        i {
            font-size: 40px;
            color: white;
            margin-bottom: 10px;
            display: block;
            transition: all 0.3s ease;
            
            &:hover {
                color: #409EFF;
                transform: rotate(10deg);
            }
        }
        
        h2 {
            font-size: 24px;
            color: white;
            font-weight: normal;
            margin: 0;
            letter-spacing: 1px;
        }
    }
    
    .login-form {
        width: 100%;
        max-width: 350px;
        animation: fadeIn 1s;
        
        .el-input {
            margin-bottom: 20px;
            transition: all 0.3s ease;
            
            &:hover {
                transform: translateY(-2px);
            }
            
            /deep/ .el-input__inner {
                height: 50px;
                background: rgba(255, 255, 255, 0.1);
                border: none;
                border-radius: 4px;
                color: white;
                padding-left: 45px;
                transition: all 0.3s ease;
                
                &::placeholder {
                    color: rgba(255, 255, 255, 0.7);
                }
                
                &:focus {
                    background: rgba(255, 255, 255, 0.15);
                    box-shadow: 0 0 10px rgba(64, 158, 255, 0.3);
                }
            }
            
            /deep/ .el-input__prefix {
                left: 15px;
                
                i {
                    color: rgba(255, 255, 255, 0.7);
                    font-size: 18px;
                    transition: all 0.3s ease;
                }
            }
            
            &:focus-within {
                /deep/ .el-input__prefix i {
                    color: #409EFF;
                }
            }
        }
        
        .form-options {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            
            /deep/ .el-checkbox__label {
                color: rgba(255, 255, 255, 0.8);
            }
            
            /deep/ .el-checkbox__input.is-checked .el-checkbox__inner {
                background-color: #409EFF;
                border-color: #409EFF;
            }
            
            .to-register {
                color: #fff;
                text-decoration: none;
                font-size: 14px;
                opacity: 0.8;
                transition: all 0.3s ease;
                
                &:hover {
                    opacity: 1;
                    color: #409EFF;
                    text-shadow: 0 0 8px rgba(64, 158, 255, 0.5);
                }
            }
        }
        
        .submit-btn {
            width: 100%;
            height: 50px;
            background: #409EFF;
            border: none;
            font-size: 16px;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
            
            &:hover {
                background: #66b1ff;
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            }
            
            &:active {
                transform: translateY(0);
            }
            
            &:after {
                content: '';
                position: absolute;
                top: 50%;
                left: 50%;
                width: 5px;
                height: 5px;
                background: rgba(255, 255, 255, 0.5);
                opacity: 0;
                border-radius: 100%;
                transform: scale(1, 1) translate(-50%);
                transform-origin: 50% 50%;
            }
            
            &:focus:not(:active)::after {
                animation: ripple 1s ease-out;
            }
        }
    }
}

@keyframes ripple {
    0% {
        transform: scale(0, 0);
        opacity: 0.5;
    }
    20% {
        transform: scale(25, 25);
        opacity: 0.3;
    }
    100% {
        opacity: 0;
        transform: scale(40, 40);
    }
}

@keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
}

@keyframes fadeInDown {
    from {
        opacity: 0;
        transform: translateY(-20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@media screen and (max-width: 992px) {
    .sign-in-container {
        .left-section, .right-section {
            display: none;
        }
        
        .center-section {
            flex: 1;
        }
    }
}

// 添加成功消息和进度条样式
.success-message {
    margin-top: 20px;
    padding: 15px;
    background: rgba(103, 194, 58, 0.1);
    border-radius: 4px;
    color: #67c23a;
    display: flex;
    flex-direction: column;
    align-items: center;
    animation: fadeIn 0.5s;
    
    i {
        font-size: 24px;
        margin-bottom: 10px;
    }
    
    span {
        margin-bottom: 15px;
    }
    
    .progress-bar {
        width: 100%;
        height: 4px;
        background: rgba(103, 194, 58, 0.2);
        border-radius: 2px;
        overflow: hidden;
        
        .progress {
            height: 100%;
            background: #67c23a;
            border-radius: 2px;
            transition: width 0.1s linear;
        }
    }
}
</style>
