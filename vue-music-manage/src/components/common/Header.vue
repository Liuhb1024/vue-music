<template>
<div class="header">
    <el-header height="60px">
        <div class="title" @click="goToHome" style="cursor: pointer;">
            <i class="el-icon-headset logo-icon"></i>
            <p>音乐后台管理系统</p>
        </div>
        <div class="header-right">
            <div class="search-box">
                <el-input 
                    placeholder="搜索..." 
                    prefix-icon="el-icon-search" 
                    size="small">
                </el-input>
            </div>
            <div class="user-avatar">
                <img src="@/assets/img/whitefox.jpg" alt="用户头像" />
            </div>
            <el-dropdown @command="handleCommand">
                <span class="el-dropdown-link">
                    {{username}}
                    <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="profile">
                        <i class="el-icon-user"></i> 个人中心
                    </el-dropdown-item>
                    <el-dropdown-item divided command="logout" class="logout-item">
                        <i class="el-icon-switch-button"></i> 退出登录
                    </el-dropdown-item>
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
                this.$confirm('确认退出登录吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$message({
                        message: '已退出登录',
                        type: 'success',
                        duration: 2000
                    });
                    setTimeout(() => {
                        localStorage.removeItem('username');
                        this.$router.push("/");
                    }, 1000);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消退出'
                    });          
                });
            } else if (command === "profile") {
                this.$router.push("/home/profile");
            }
        },
        goToHome() {
            // 先跳转到首页
            this.$router.push('/home/info');
            
            // 手动触发侧边栏菜单的更新
            // 获取所有菜单项，找到并激活"首页"菜单
            const sidebarMenu = document.querySelector('.el-menu');
            if (sidebarMenu) {
                // 找到所有菜单项
                const menuItems = sidebarMenu.querySelectorAll('.el-menu-item');
                // 移除所有激活状态
                menuItems.forEach(item => {
                    item.classList.remove('is-active');
                });
                // 找到首页菜单项并激活
                const homeMenuItem = Array.from(menuItems).find(item => item.textContent.includes('首页'));
                if (homeMenuItem) {
                    homeMenuItem.classList.add('is-active');
                }
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
        background: linear-gradient(to right, #303133, #545c64);
        color: rgb(255, 255, 255);
        display: flex;
        justify-content: space-between;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        padding: 0 20px;

        .title {
            width: 400px;
            text-align: left;
            font-size: 1.8em;
            display: flex;
            align-items: center;
            font-weight: 500;
            transition: all 0.3s;
            
            .logo-icon {
                font-size: 28px;
                margin-right: 10px;
                color: #fff;
                background: rgba(255, 255, 255, 0.2);
                padding: 8px;
                border-radius: 50%;
            }
            
            p {
                margin: 0;
                letter-spacing: 1px;
            }
            
            &:hover {
                opacity: 0.8;
            }
        }
    }

    .header-right {
        display: flex;
        justify-content: center;
        align-items: center;
        
        .search-box {
            margin-right: 20px;
            width: 200px;
            
            /deep/ .el-input__inner {
                background: rgba(255, 255, 255, 0.15);
                border: none;
                color: #fff;
                border-radius: 20px;
                
                &::placeholder {
                    color: rgba(255, 255, 255, 0.7);
                }
            }
            
            /deep/ .el-input__icon {
                color: rgba(255, 255, 255, 0.7);
            }
        }

        .user-avatar {
            margin-right: 10px;
            display: flex;
            align-items: center;

            img {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                border: 2px solid rgba(255, 255, 255, 0.6);
                transition: all 0.3s;
                
                &:hover {
                    transform: scale(1.1);
                    border-color: #409EFF;
                }
            }
        }

        .el-dropdown {
            display: flex;
            align-items: center;
            margin-left: 5px;

            .el-dropdown-link {
                font-size: 16px;
                padding: 5px 10px;
                border-radius: 4px;
                transition: all 0.3s;
                
                &:hover {
                    background: rgba(255, 255, 255, 0.1);
                }
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

/deep/ .el-dropdown-menu__item {
    display: flex;
    align-items: center;
    
    i {
        margin-right: 5px;
        color: #409EFF;
    }
    
    &.logout-item {
        color: #f56c6c;
        
        i {
            color: #f56c6c;
        }
        
        &:hover, &:focus {
            background-color: #fef0f0;
            color: #f56c6c;
        }
    }
}

/deep/ .el-dropdown-menu__item--divided:before {
    margin: 0;
}

@media screen and (max-width: 768px) {
    .header {
        .el-header {
            .title {
                width: auto;
                font-size: 1.5em;
            }
        }
        
        .header-right {
            .search-box {
                display: none;
            }
        }
    }
}
</style>
