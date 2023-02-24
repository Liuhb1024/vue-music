<template>
<div class="header-wrapper">
    <div class="logo" @click="goHome">
        <i class="fa fa-headphones"></i>
        <span>Vue2-Music</span>
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
            <el-button type="primary" @click="handleLoginOrRegister">登录/注册</el-button>
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
                    <el-dropdown-item command="user-setting">个人设置</el-dropdown-item>
                    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
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
                this.$store.commit('SET_LOGIN_STATUS', false)
                this.$router.go(0)
            }
        }
    },
    computed: {
        ...mapGetters(['activeNav', 'loginStatus', 'userInfo'])
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.header-wrapper {
    display: flex;
    justify-content: space-between;
    height: 60px;
    background-color: rgb(75, 72, 72);
    box-shadow: 0 2px 0 0 $theme-color;
    color: white;

    .logo {
        margin-right: 50px;
        min-width: 250px;
        display: flex;
        align-items: center;
        font-weight: bold;

        >:nth-child(1) {
            font-size: 50px;
            display: inline-block;
            margin: 10px;
        }

        >:nth-child(2) {
            font-size: 35px;
        }
    }

    .logo:hover {
        cursor: pointer;
    }

    .navbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        min-width: 600px;

        li:not(:last-child) {
            box-sizing: border-box;
            height: 100%;
            text-align: center;

            &:hover {
                cursor: pointer;
                color: $theme-color;
                @include active-border-style(3px, solid, $theme-color);
            }

            span {
                display: inline-block;
                margin-top: 20px;
            }
        }

        .header-search-box {
            width: 300px;
            display: flex;

            input {
                display: inline-block;
                width: 90%;
                height: 30px;
            }

            .search-btn {
                margin-left: 10px;
                align-self: center;
                font-size: 20px;

                &:hover {
                    cursor: pointer;
                }
            }
        }
    }

    @mixin user-box {
        width: 180px;
        align-self: center;
        margin-right: 10px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
    }

    .user-avatar {
        @include user-box;

        >div:nth-of-type(2) {
            margin-left: 10px;
        }

        img {
            display: block;
            width: 30px;
            height: 30px;
            border-radius: 50%;
        }

        .el-dropdown-link {
            color: white;

            &:hover {
                cursor: pointer;
            }
        }
    }

    .user-login {
        @include user-box;
    }

}
</style>
