<template>
<div class="header">
    <el-header height="60px">
        <div class="title">
            <p>Vue2-Music 后台管理系统</p>
        </div>
        <div class="header-right">
            <div class="user-avatar">
                <img src="@/assets/img/whitefox.jpg" alt="用户头像" />
            </div>
            <el-dropdown @command="handleCommand">
                <span class="el-dropdown-link">
                    {{username}}
                    <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                    <el-dropdown-item>个人设置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </el-header>
</div>
</template>

<script>
export default {
    data() {
        return {

        }
    },
    computed: {
        username() {
            return localStorage.getItem('username') || '_username'
        }
    },
    methods: {
        handleCommand(command) {
            if (command === "logout") {
                this.$message({
                    message: '已退出登录',
                    duration: 2000
                })
                setTimeout(() => {
                    localStorage.removeItem('username')
                    this.$router.push("/")
                }, 2000)
            }
        }
    },
    created() {

    }
}
</script>

<style lang="scss" scoped>
.header {
    .el-header {
        background-color: #5299f7;
        color: rgb(255, 255, 255);
        display: flex;
        justify-content: space-between;

        .title {
            width: 400px;
            text-align: left;
            font-size: 2em;
            display: flex;
            align-items: center;
        }
    }

    .header-right {
        display: flex;
        justify-content: center;
        align-items: space-between;

        .user-avatar {
            margin-right: 10px;
            display: flex;
            align-items: center;

            img {
                max-width: 37px;
                max-height: 37px;
                border-radius: 50%;
            }
        }

        .el-dropdown {
            display: flex;
            align-items: center;

            .el-dropdown-link {
                font-size: 18px;
            }
        }
    }
}

/* element-ui style */
.el-dropdown-link {
    cursor: pointer;
    color: #ffffff;
}

.el-icon-arrow-down {
    font-size: 12px;
}
</style>
