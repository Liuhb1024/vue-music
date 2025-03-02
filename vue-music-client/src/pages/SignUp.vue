<template>
<div class="sign-up-page">
    <div class="sign-up">
        <div class="left-content">
            <div class="logo">
                <i class="el-icon-headset"></i>
                <span>音乐，让生活更美好</span>
            </div>
            <div class="description">
                发现、聆听、分享
            </div>
        </div>

        <div class="sign-up-content">
            <div class="sign-up-box">
                <div class="sign-up-header">
                    <i class="el-icon-headset"></i>
                    <h1>Music</h1>
                </div>

                <el-form ref="registerForm" :model="registerForm" :rules="rules" label-width="0" class="sign-up-form">
                    <el-form-item prop="phoneNum">
                        <el-input 
                            v-model="registerForm.phoneNum" 
                            prefix-icon="el-icon-mobile-phone"
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
                            clearable>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="reconfirmedPassword">
                        <el-input 
                            v-model="registerForm.reconfirmedPassword" 
                            prefix-icon="el-icon-key"
                            type="password" 
                            placeholder="请确认密码"
                            show-password
                            clearable
                            @keyup.enter.native="submitForm">
                        </el-input>
                    </el-form-item>
                    <div class="form-options">
                        <router-link to="/login" class="to-login">已有账号？去登录</router-link>
                    </div>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm" :loading="loading">
                            {{ loading ? '注册中...' : '注册' }}
                        </el-button>
                    </el-form-item>
                </el-form>
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
import { dateToString } from '@/utils'
import { register } from '@/api/consumer'
import { nanoid } from 'nanoid'

export default {
    name: 'SignUp',
    data() {
        return {
            loading: false,
            registerForm: {
                id: -1,
                username: '',
                password: '',
                reconfirmedPassword: '',
                sex: 0,
                phoneNum: '',
                email: '',
                birth: '',
                introduction: '这里空空的，什么也没有~',
                avatar: '/img/consumer-img/default.jpg',
                createTime: '',
                updateTime: ''
            },
            rules: {
                phoneNum: [{
                    required: true,
                    message: '请输入手机号',
                    trigger: 'blur'
                }, {
                    min: 3,
                    max: 11,
                    message: '长度在 3 到 11 个字符',
                    trigger: ['blur', 'change']
                }],
                password: [{
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                }, {
                    min: 6,
                    message: '长度不得小于6个字符',
                    trigger: ['blur', 'change']
                }],
                reconfirmedPassword: [{
                    required: true,
                    message: '请确认密码',
                    trigger: 'blur'
                }, {
                    validator: (rule, value, callback) => {
                        if (value !== this.registerForm.password) {
                            callback(new Error('两次输入的密码不一致'));
                        } else {
                            callback();
                        }
                    },
                    trigger: ['blur', 'change']
                }]
            }
        }
    },
    methods: {
        submitForm() {
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    this.loading = true;
                    
                    this.registerForm.username = nanoid()
                    this.registerForm.createTime = dateToString(new Date())
                    this.registerForm.updateTime = dateToString(new Date())
                    this.registerForm.birth = dateToString(new Date())
                    
                    const { reconfirmedPassword, ...userData } = this.registerForm

                    register(userData).then(responseBody => {
                        this.loading = false;
                        if (responseBody.code === 1) {
                            this.$message({
                                message: '注册成功,三秒后跳转到首页',
                                type: 'success',
                                duration: 3000
                            })
                            setTimeout(() => {
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
                    }).catch(err => {
                        this.loading = false;
                        console.log(err)
                        this.$message({
                            message: '注册失败，请稍后重试',
                            type: 'error',
                            duration: 2000
                        })
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
.sign-up-page {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 1000;
    background: #2d3a4b;

    .sign-up {
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
        
        .sign-up-content {
            width: 420px;
            padding: 20px;
            
            .sign-up-box {
                background: transparent;
                
                .sign-up-header {
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
                
                .sign-up-form {
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
                        justify-content: flex-end;
                        margin-bottom: 20px;
                        
                        .to-login {
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
            }
        }
    }
}

// 响应式调整
@media screen and (max-width: 1200px) {
    .sign-up-page {
        .sign-up {
            .left-content, .right-content {
                width: 20%;
            }
        }
    }
}

@media screen and (max-width: 992px) {
    .sign-up-page {
        .sign-up {
            .left-content, .right-content {
                display: none;
            }
            
            .sign-up-content {
                width: 90%;
                max-width: 420px;
                margin: 0 auto;
            }
        }
    }
}

@media screen and (max-width: 480px) {
    .sign-up-page {
        .sign-up {
            .sign-up-content {
                width: 90%;
                
                .sign-up-box {
                    .sign-up-header {
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
