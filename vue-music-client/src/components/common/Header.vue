<template>
<div class="header-wrapper">
    <div class="logo" @click="goHome">
        <i class="fa fa-headphones"></i>
        <span>Melodia</span>
    </div>

    <ul class="navbar">
        <li :class="{'active': item.path === activeNav}" v-for="item in navData" :key="item.title" @click="goPage(item.path, item.title)">
            <span>{{ item.title }}</span>
        </li>
        <li>
            <div class="header-search-box">
                <input type="text" v-model="kw" @keyup.enter="goSearch" autocomplete="true" placeholder="搜索音乐、MV、歌单、用户" />
                <div class="search-btn" @click="goSearch">
                    <i class="fa fa-search"></i>
                </div>
            </div>
        </li>
    </ul>

    <!-- 未登录显示按钮 -->
    <div class="user-login" v-if="!loginStatus">
        <div>
            <el-button type="primary" @click="handleLoginOrRegister">登录 / 注册</el-button>
        </div>
    </div>
    <!-- 登录后显示用户信息 -->
    <div class="user-avatar" v-if="loginStatus">
        <div>
            <img :src="getCoverImgUrl(userInfo.avatar)" />
        </div>
        <div>
            <el-dropdown @command="handleCommand">
                <span class="el-dropdown-link">
                    {{ userInfo.username }}<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="user-setting">
                        <i class="el-icon-setting"></i> 个人设置
                    </el-dropdown-item>
                    <el-dropdown-item command="logout">
                        <i class="el-icon-switch-button"></i> 退出登录
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</div>
</template>

<script>
import {
    mapGetters
} from 'vuex'

import {
    navData
} from '@/api/nav'

import {
    mixin
} from '@/mixins/index'

import {
    clearUserInfo,
    clearLoginStatus
} from '@/utils/storage'

export default {
    name: 'Header',
    mixins: [mixin],
    data() {
        return {
            navData,
            kw: ''
        }
    },
    created() {
        this.navData = navData
    },
    methods: {
        goHome() {
            this.$router.push('/')
        },
        goPage(path, title) {
            this.$store.commit('SET_ACTIVE_NAV', title)
            this.$router.push({
                path
            })
        },
        goSearch() {
            let kw = this.kw.trim()
            if (kw === '') {
                this.$message({
                    message: '请输入要搜索的内容',
                    type: 'error',
                    duration: 1000
                })
            } else {
                this.$router.push({
                    path: '/search',
                    query: {
                        kw
                    }
                })
            }
        },
        handleLoginOrRegister() {
            this.$router.push('/login')
        },
        handleCommand(command) {
            if (command === 'user-setting') {
                this.$router.push('/setting')
            } else if (command === 'logout') {
                this.$store.commit('user/SET_LOGIN_STATUS', false)
                this.$store.commit('user/SET_USER_INFO', {})
                clearUserInfo()
                clearLoginStatus()
                this.$router.go(0)
            }
        }
    },
    computed: {
        ...mapGetters({
            activeNav: 'activeNav',
            loginStatus: 'loginStatus',
            userInfo: 'userInfo'
        })
    },
    mounted() {
        console.log('Header mounted');
        console.log('loginStatus:', this.loginStatus);
        console.log('userInfo:', this.userInfo);
        console.log('localStorage loginStatus:', localStorage.getItem('loginStatus'));
        console.log('localStorage userInfo:', localStorage.getItem('userInfo'));
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.header-wrapper {
    display: flex;
    justify-content: space-between;
    height: 60px;
    background-color: #2d3a4b;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    color: white;
    position: sticky;
    top: 0;
    z-index: 999;
    backdrop-filter: blur(10px);
    padding: 0 20px;
    width: 100%;
    box-sizing: border-box;

    .logo {
        margin-right: 20px;
        min-width: 200px;
        display: flex;
        align-items: center;
        font-weight: bold;
        transition: all 0.3s ease;
        
        &:hover {
            transform: scale(1.05);
            cursor: pointer;
        }

        >:nth-child(1) {
            font-size: 32px;
            display: inline-block;
            margin: 0 10px;
            color: $theme-color;
        }

        >:nth-child(2) {
            font-size: 24px;
            letter-spacing: 1.5px;
            font-family: 'Montserrat', 'Noto Sans SC', '思源黑体 CN', sans-serif;
            font-weight: 600;
        }
    }

    .navbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex: 1;
        
        li:not(:last-child) {
            box-sizing: border-box;
            height: 100%;
            text-align: center;
            position: relative;
            transition: all 0.3s ease;
            margin: 0 15px;
            padding: 0 5px;

            &:hover {
                cursor: pointer;
                color: $theme-color;
                
                &:after {
                    content: '';
                    position: absolute;
                    bottom: 0;
                    left: 0;
                    width: 100%;
                    height: 3px;
                    background-color: $theme-color;
                    transform: scaleX(1);
                    transition: transform 0.3s ease;
                }
            }

            span {
                display: inline-block;
                margin-top: 20px;
                font-weight: 500;
                font-family: 'Open Sans', 'Noto Sans SC', '思源黑体 CN', sans-serif;
            }
        }

        .active {
            color: $theme-color;
            position: relative;
            
            &:after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                height: 3px;
                background-color: $theme-color;
            }
        }

        .header-search-box {
            display: flex;
            align-items: center;
            position: relative;
            flex: 0 0 300px;
            margin-left: auto;
            margin-right: 20px;

            input {
                width: 100%;
                height: 36px;
                border-radius: 18px;
                border: none;
                padding: 0 40px 0 15px;
                background: rgba(255, 255, 255, 0.15);
                color: white;
                backdrop-filter: blur(5px);
                transition: all 0.3s ease;
                
                &:focus {
                    background: rgba(255, 255, 255, 0.25);
                    outline: none;
                    box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.5);
                }
                
                &::placeholder {
                    color: rgba(255, 255, 255, 0.6);
                }
            }

            .search-btn {
                position: absolute;
                right: 15px;
                top: 50%;
                transform: translateY(-50%);
                font-size: 16px;
                color: rgba(255, 255, 255, 0.8);
                transition: all 0.2s ease;

                &:hover {
                    cursor: pointer;
                    color: $theme-color;
                }
            }
        }
    }

    .user-avatar {
        display: flex;
        align-items: center;
        margin-left: 20px;
        
        >div:first-child {
            margin-right: 10px;
        }
        
        img {
            display: block;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            border: 2px solid rgba(255, 255, 255, 0.2);
            transition: all 0.3s ease;
            object-fit: cover;
            
            &:hover {
                border-color: $theme-color;
                transform: scale(1.05);
            }
        }

        .el-dropdown-link {
            color: white;
            transition: all 0.2s ease;
            cursor: pointer;
            padding: 5px 10px;
            border-radius: 4px;
            
            &:hover {
                background: rgba(255, 255, 255, 0.1);
                color: $theme-color;
            }
        }
    }

    .user-login {
        display: flex;
        align-items: center;
        margin-left: 20px;
        
        .el-button {
            padding: 10px 20px;
            font-weight: 500;
            transition: all 0.3s ease;
            
            &:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(64, 158, 255, 0.4);
            }
        }
    }
}

// 响应式设计
@media screen and (max-width: 992px) {
    .header-wrapper {
        padding: 0 10px;
        
        .logo {
            min-width: 150px;
            
            >:nth-child(1) {
                font-size: 28px;
            }
            
            >:nth-child(2) {
                font-size: 18px;
            }
        }
        
        .navbar {
            li:not(:last-child) {
                margin: 0 8px;
            }
            
            .header-search-box {
                flex: 0 0 200px;
            }
        }
    }
}

@media screen and (max-width: 768px) {
    .header-wrapper {
        .navbar {
            li:not(:last-child) span {
                font-size: 14px;
            }
        }
    }
}
</style>
