<template>
<div class="profile-page">
    <div class="profile-header">
        <div class="user-info">
            <div class="avatar">
                <img src="@/assets/img/whitefox.jpg" alt="用户头像">
            </div>
            <div class="info">
                <h2>{{ username }}</h2>
                <p class="role">管理员</p>
            </div>
        </div>
    </div>

    <div class="profile-content">
        <el-card class="info-card">
            <div slot="header">
                <i class="el-icon-user"></i>
                <span>基本信息</span>
            </div>
            <div class="info-list">
                <div class="info-item">
                    <span class="label">用户名</span>
                    <span class="value">{{ username }}</span>
                </div>
                <div class="info-item">
                    <span class="label">角色</span>
                    <span class="value">系统管理员</span>
                </div>
                <div class="info-item">
                    <span class="label">注册时间</span>
                    <span class="value">2024-03-20</span>
                </div>
                <div class="info-item">
                    <span class="label">最后登录</span>
                    <span class="value">{{ lastLoginTime }}</span>
                </div>
            </div>
        </el-card>

        <el-card class="stats-card">
            <div slot="header">
                <i class="el-icon-data-line"></i>
                <span>活动统计</span>
            </div>
            <div class="stats-list">
                <div class="stat-item">
                    <div class="number">56</div>
                    <div class="label">登录次数</div>
                </div>
                <div class="stat-item">
                    <div class="number">128</div>
                    <div class="label">操作次数</div>
                </div>
                <div class="stat-item">
                    <div class="number">24</div>
                    <div class="label">在线时长(h)</div>
                </div>
            </div>
        </el-card>
    </div>
</div>
</template>

<script>
export default {
    name: 'ProfilePage',
    data() {
        return {
            username: localStorage.getItem('username') || '_username',
            lastLoginTime: new Date().toLocaleString()
        }
    }
}
</script>

<style lang="scss" scoped>
.profile-page {
    padding: 20px;

    .profile-header {
        background: linear-gradient(135deg, #1890ff, #36a3ff);
        padding: 40px;
        border-radius: 8px;
        margin-bottom: 20px;
        color: white;

        .user-info {
            display: flex;
            align-items: center;

            .avatar {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                overflow: hidden;
                border: 4px solid rgba(255, 255, 255, 0.3);
                margin-right: 20px;

                img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }
            }

            .info {
                h2 {
                    margin: 0 0 10px 0;
                    font-size: 24px;
                }

                .role {
                    margin: 0;
                    opacity: 0.8;
                }
            }
        }
    }

    .profile-content {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: 20px;

        .el-card {
            margin-bottom: 0;

            ::v-deep .el-card__header {
                padding: 15px 20px;
                border-bottom: 1px solid #ebeef5;

                i {
                    color: #409EFF;
                    margin-right: 10px;
                }
            }
        }

        .info-list {
            .info-item {
                display: flex;
                justify-content: space-between;
                padding: 12px 0;
                border-bottom: 1px solid #ebeef5;

                &:last-child {
                    border-bottom: none;
                }

                .label {
                    color: #909399;
                }

                .value {
                    color: #303133;
                }
            }
        }

        .stats-list {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            padding: 20px 0;

            .stat-item {
                text-align: center;

                .number {
                    font-size: 24px;
                    color: #409EFF;
                    font-weight: bold;
                    margin-bottom: 5px;
                }

                .label {
                    color: #909399;
                    font-size: 14px;
                }
            }
        }
    }
}

@media screen and (max-width: 768px) {
    .profile-page {
        padding: 10px;

        .profile-header {
            padding: 20px;
        }
    }
}
</style> 