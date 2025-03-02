<template>
<div class="login">
    <div class="login-container">
        <div class="login-box">
            <div class="login-header">
                <i class="el-icon-headset logo-icon"></i>
                <h1>音乐后台管理系统</h1>
                <p class="welcome-text">欢迎回来，请登录您的账号</p>
            </div>

            <el-form :model="form" ref="form" :rules="rules" label-width="0" class="login-form">
                <el-form-item prop="username">
                    <el-input 
                        v-model="form.username" 
                        prefix-icon="el-icon-user" 
                        placeholder="用户名">
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input 
                        v-model="form.password" 
                        prefix-icon="el-icon-lock" 
                        type="password" 
                        placeholder="密码"
                        @keyup.enter.native="login">
                    </el-input>
                </el-form-item>
                <div class="form-options">
                    <el-checkbox v-model="rememberMe">记住我</el-checkbox>
                    <a href="javascript:void(0)" class="forgot-password">忘记密码?</a>
                </div>
                <el-form-item>
                    <el-button type="primary" @click="login" :loading="loading" class="login-button">
                        {{ loading ? '登录中...' : '登录' }}
                    </el-button>
                </el-form-item>
            </el-form>

            <div class="login-footer">
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
    
    <div class="login-decoration">
        <div class="music-notes">
            <i class="el-icon-headset note-icon"></i>
            <i class="el-icon-video-play note-icon"></i>
            <i class="el-icon-microphone note-icon"></i>
        </div>
        <div class="decoration-text">
            <h2>探索音乐的世界</h2>
            <p>管理您的音乐资源，创建精彩的音乐体验</p>
        </div>
    </div>
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
            rememberMe: false,
            loading: false,
            rules: {
                username: [{
                    required: true,
                    message: '请输入用户名',
                    trigger: 'blur'
                }, {
                    min: 1,
                    max: 20,
                    message: '长度在1到20个字符之间',
                    trigger: 'change'
                }],
                password: [{
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                }, {
                    min: 1,
                    max: 20,
                    message: '长度在1到20个字符之间',
                    trigger: 'change'
                }]
            }
        }
    },
    methods: {
        login() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    this.loading = true;
                    let params = new URLSearchParams()
                    params.append('username', this.form.username)
                    params.append('password', this.form.password)
                    getLoginStatus(params).then(jsonData => {
                        this.loading = false;
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
                    }).catch(() => {
                        this.loading = false;
                        this.showErrorMsg('登录失败，请稍后再试')
                    })
                } else {
                    this.showErrorMsg('请正确填写登录信息')
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
    display: flex;
    position: relative;
    overflow: hidden;
    
    .login-container {
        width: 55%;
        display: flex;
        justify-content: center;
        align-items: center;
        background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
        
        .login-box {
            width: 400px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
            padding: 40px;
            
            .login-header {
                text-align: center;
                margin-bottom: 30px;
                
                .logo-icon {
                    font-size: 48px;
                    color: #409EFF;
                    margin-bottom: 15px;
                    display: inline-block;
                }
                
                h1 {
                    font-size: 24px;
                    color: #303133;
                    margin: 0 0 10px;
                }
                
                .welcome-text {
                    color: #606266;
                    font-size: 16px;
                    margin: 0;
                }
            }
            
            .login-form {
                .el-input {
                    margin-bottom: 15px;
                    
                    /deep/ .el-input__inner {
                        height: 50px;
                        border-radius: 25px;
                        padding-left: 45px;
                        border: 1px solid #dcdfe6;
                        transition: all 0.3s;
                        
                        &:focus {
                            border-color: #409EFF;
                            box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
                        }
                    }
                    
                    /deep/ .el-input__prefix {
                        left: 15px;
                        
                        i {
                            font-size: 18px;
                            color: #909399;
                        }
                    }
                }
                
                .form-options {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    margin-bottom: 20px;
                    
                    .forgot-password {
                        color: #409EFF;
                        text-decoration: none;
                        font-size: 14px;
                        
                        &:hover {
                            text-decoration: underline;
                        }
                    }
                }
                
                .login-button {
                    width: 100%;
                    height: 50px;
                    border-radius: 25px;
                    font-size: 16px;
                    font-weight: bold;
                    background: linear-gradient(to right, #409EFF, #64b5f6);
                    border: none;
                    transition: all 0.3s;
                    
                    &:hover, &:focus {
                        background: linear-gradient(to right, #66b1ff, #90caf9);
                        box-shadow: 0 5px 15px rgba(64, 158, 255, 0.3);
                    }
                }
            }
            
            .login-footer {
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
                        background: #e4e7ed;
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
                        background: #fff;
                        position: relative;
                        z-index: 1;
                        color: #909399;
                        font-size: 14px;
                    }
                }
                
                .social-login {
                    display: flex;
                    justify-content: center;
                    margin-top: 20px;
                    
                    i {
                        font-size: 24px;
                        color: #909399;
                        margin: 0 15px;
                        cursor: pointer;
                        transition: all 0.3s;
                        
                        &:hover {
                            color: #409EFF;
                            transform: translateY(-3px);
                        }
                    }
                }
            }
        }
    }
    
    .login-decoration {
        width: 45%;
        background: linear-gradient(135deg, #303133 0%, #545c64 100%);
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        color: #fff;
        
        .music-notes {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-bottom: 40px;
            
            .note-icon {
                font-size: 60px;
                margin: 0 20px;
                opacity: 0.8;
                animation: float 3s infinite ease-in-out;
                
                &:nth-child(1) {
                    animation-delay: 0s;
                }
                
                &:nth-child(2) {
                    animation-delay: 0.5s;
                    font-size: 80px;
                }
                
                &:nth-child(3) {
                    animation-delay: 1s;
                }
            }
        }
        
        .decoration-text {
            text-align: center;
            padding: 0 40px;
            
            h2 {
                font-size: 32px;
                margin-bottom: 15px;
            }
            
            p {
                font-size: 16px;
                opacity: 0.8;
                line-height: 1.6;
                max-width: 400px;
            }
        }
    }
}

@keyframes float {
    0%, 100% {
        transform: translateY(0);
    }
    50% {
        transform: translateY(-15px);
    }
}

@media screen and (max-width: 1200px) {
    .login {
        .login-container {
            width: 60%;
        }
        
        .login-decoration {
            width: 40%;
        }
    }
}

@media screen and (max-width: 992px) {
    .login {
        .login-container {
            width: 100%;
            
            .login-box {
                width: 90%;
                max-width: 400px;
            }
        }
        
        .login-decoration {
            display: none;
        }
    }
}
</style>
