<template>
<div class="user-settings-page">
    <!-- 左侧装饰面板 -->
    <div class="left-panel">
        <div class="music-decor">
            <span class="note">♪</span>
            <span class="note">♫</span>
            <span class="note">♩</span>
            <div class="cd-effect"></div>
        </div>
    </div>

    <!-- 右侧装饰面板 -->
    <div class="right-panel">
        <div class="wave-bars">
            <div class="bar" v-for="n in 12" :key="n"></div>
        </div>
        <div class="circles">
            <div class="circle" v-for="n in 3" :key="n"></div>
        </div>
    </div>

    <div class="settings-container">
        <div class="settings-sidebar">
            <div class="user-info">
                <div class="avatar-container">
                    <img :src="getCoverImgUrl(userInfo.avatar)" alt="用户头像">
                    <div class="avatar-overlay" @click="triggerFileInput">
                        <i class="el-icon-camera"></i>
                        <span>更换头像</span>
                    </div>
                    <input 
                        type="file" 
                        ref="fileInput" 
                        style="display: none" 
                        accept="image/*"
                        @change="handleFileChange"
                    >
                </div>
                <h3>{{ userInfo.username }}</h3>
                <p class="user-email">{{ userInfo.email || userInfo.phoneNum }}</p>
            </div>
            <ul class="settings-menu">
                <li 
                    v-for="(item, index) in menuItems" 
                    :key="index"
                    :class="{ active: activeTab === item.key }"
                    @click="activeTab = item.key"
                >
                    <i :class="item.icon"></i>
                    <span>{{ item.label }}</span>
                </li>
            </ul>
        </div>
        
        <div class="settings-content">
            <!-- 个人资料 -->
            <div v-if="activeTab === 'profile'" class="settings-section">
                <h2>个人资料</h2>
                <el-form :model="profileForm" label-width="100px" :rules="profileRules" ref="profileForm">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="profileForm.username" placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号码" prop="phoneNum">
                        <el-input v-model="profileForm.phoneNum" placeholder="请输入手机号码"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="profileForm.email" placeholder="请输入邮箱"></el-input>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-radio-group v-model="profileForm.sex">
                            <el-radio :label="1">男</el-radio>
                            <el-radio :label="0">女</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="生日">
                        <el-date-picker 
                            v-model="profileForm.birth" 
                            type="date" 
                            placeholder="选择日期"
                            format="yyyy-MM-dd"
                            value-format="yyyy-MM-dd">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="个人简介">
                        <el-input 
                            type="textarea" 
                            v-model="profileForm.introduction" 
                            :rows="4"
                            placeholder="介绍一下自己吧">
                        </el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="saveProfile" :loading="loading">保存修改</el-button>
                    </el-form-item>
                </el-form>
            </div>
            
            <!-- 账户安全 -->
            <div v-if="activeTab === 'security'" class="settings-section">
                <h2>账户安全</h2>
                <el-form :model="securityForm" label-width="100px" :rules="securityRules" ref="securityForm">
                    <el-form-item label="当前密码" prop="currentPassword">
                        <el-input 
                            v-model="securityForm.currentPassword" 
                            type="password" 
                            placeholder="请输入当前密码"
                            show-password>
                        </el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPassword">
                        <el-input 
                            v-model="securityForm.newPassword" 
                            type="password" 
                            placeholder="请输入新密码"
                            show-password>
                        </el-input>
                    </el-form-item>
                    <el-form-item label="确认新密码" prop="confirmPassword">
                        <el-input 
                            v-model="securityForm.confirmPassword" 
                            type="password" 
                            placeholder="请再次输入新密码"
                            show-password>
                        </el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="changePassword" :loading="loading">修改密码</el-button>
                    </el-form-item>
                </el-form>
            </div>
            
            <!-- 偏好设置 -->
            <div v-if="activeTab === 'preferences'" class="settings-section">
                <h2>偏好设置</h2>
                <el-form :model="preferencesForm" label-width="100px">
                    <el-form-item label="主题">
                        <el-radio-group v-model="preferencesForm.theme">
                            <el-radio label="light">浅色</el-radio>
                            <el-radio label="dark">深色</el-radio>
                            <el-radio label="auto">跟随系统</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="音质选择">
                        <el-select v-model="preferencesForm.audioQuality" placeholder="请选择">
                            <el-option label="标准音质" value="standard"></el-option>
                            <el-option label="高音质" value="high"></el-option>
                            <el-option label="无损音质" value="lossless"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="播放设置">
                        <el-checkbox v-model="preferencesForm.autoPlay">自动播放</el-checkbox>
                        <el-checkbox v-model="preferencesForm.continuousPlay">连续播放</el-checkbox>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="savePreferences">保存设置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mixin } from '@/mixins/index'
import { updateUserInfo, updatePassword } from '@/api/consumer'

export default {
    name: 'UserSettings',
    mixins: [mixin],
    data() {
        // 验证确认密码
        const validateConfirmPassword = (rule, value, callback) => {
            if (value !== this.securityForm.newPassword) {
                callback(new Error('两次输入的密码不一致'));
            } else {
                callback();
            }
        };
        
        return {
            activeTab: 'profile',
            loading: false,
            menuItems: [
                { key: 'profile', label: '个人资料', icon: 'el-icon-user' },
                { key: 'security', label: '账户安全', icon: 'el-icon-lock' },
                { key: 'preferences', label: '偏好设置', icon: 'el-icon-setting' }
            ],
            profileForm: {
                id: '',
                username: '',
                phoneNum: '',
                email: '',
                sex: 1,
                birth: '',
                introduction: '',
                avatar: ''
            },
            profileRules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
                ],
                phoneNum: [
                    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
                ],
                email: [
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
                ]
            },
            securityForm: {
                currentPassword: '',
                newPassword: '',
                confirmPassword: ''
            },
            securityRules: {
                currentPassword: [
                    { required: true, message: '请输入当前密码', trigger: 'blur' }
                ],
                newPassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                    { min: 6, message: '密码长度不能小于6个字符', trigger: 'blur' }
                ],
                confirmPassword: [
                    { required: true, message: '请再次输入新密码', trigger: 'blur' },
                    { validator: validateConfirmPassword, trigger: 'blur' }
                ]
            },
            preferencesForm: {
                theme: 'light',
                audioQuality: 'high',
                autoPlay: true,
                continuousPlay: true
            }
        }
    },
    computed: {
        ...mapGetters(['userInfo'])
    },
    created() {
        // 初始化表单数据
        this.initFormData();
    },
    methods: {
        initFormData() {
            // 复制用户信息到表单
            if (this.userInfo) {
                this.profileForm = {
                    id: this.userInfo.id,
                    username: this.userInfo.username,
                    phoneNum: this.userInfo.phoneNum,
                    email: this.userInfo.email || '',
                    sex: this.userInfo.sex,
                    birth: this.userInfo.birth || '',
                    introduction: this.userInfo.introduction || '',
                    avatar: this.userInfo.avatar
                };
            }
        },
        triggerFileInput() {
            this.$refs.fileInput.click();
        },
        handleFileChange(e) {
            const file = e.target.files[0];
            if (!file) return;
            
            // 检查文件类型
            if (!file.type.match('image.*')) {
                this.$message.error('请选择图片文件');
                return;
            }
            
            // 检查文件大小 (限制为2MB)
            if (file.size > 2 * 1024 * 1024) {
                this.$message.error('图片大小不能超过2MB');
                return;
            }
            
            // 创建预览
            const reader = new FileReader();
            reader.onload = (e) => {
                // 这里可以实现头像预览
                // 实际项目中应该上传到服务器
                this.profileForm.avatar = e.target.result;
            };
            reader.readAsDataURL(file);
        },
        saveProfile() {
            this.$refs.profileForm.validate(valid => {
                if (valid) {
                    this.loading = true;
                    
                    // 调用API更新用户信息
                    updateUserInfo(this.profileForm).then(res => {
                        if (res.code === 200) {
                            this.$message.success('个人资料更新成功');
                            // 更新Vuex中的用户信息
                            this.$store.commit('user/SET_USER_INFO', res.data);
                        } else {
                            this.$message.error(res.message || '更新失败');
                        }
                        this.loading = false;
                    }).catch(err => {
                        console.error(err);
                        this.$message.error('网络错误，请稍后重试');
                        this.loading = false;
                    });
                }
            });
        },
        changePassword() {
            this.$refs.securityForm.validate(valid => {
                if (valid) {
                    this.loading = true;
                    
                    // 调用API更新密码
                    updatePassword({
                        id: this.userInfo.id,
                        oldPassword: this.securityForm.currentPassword,
                        newPassword: this.securityForm.newPassword
                    }).then(res => {
                        if (res.code === 200) {
                            this.$message.success('密码修改成功，请重新登录');
                            // 清空表单
                            this.securityForm = {
                                currentPassword: '',
                                newPassword: '',
                                confirmPassword: ''
                            };
                            // 退出登录
                            setTimeout(() => {
                                this.$store.commit('user/SET_LOGIN_STATUS', false);
                                this.$router.push('/login');
                            }, 1500);
                        } else {
                            this.$message.error(res.message || '密码修改失败');
                        }
                        this.loading = false;
                    }).catch(err => {
                        console.error(err);
                        this.$message.error('网络错误，请稍后重试');
                        this.loading = false;
                    });
                }
            });
        },
        savePreferences() {
            // 保存偏好设置
            this.$message.success('偏好设置已保存');
            // 实际项目中应该调用API保存到服务器
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.user-settings-page {
    min-height: calc(100vh - 60px);
    background-color: #2d3a4b;
    padding: 30px 0;
    position: relative;
    overflow-x: hidden;

    // 左侧装饰面板
    .left-panel {
        position: fixed;
        left: 0;
        top: 0;
        width: 450px;
        height: 100%;
        background: linear-gradient(to right,
            rgba(32, 40, 51, 1) 0%,
            rgba(32, 40, 51, 1) 15%,
            rgba(45, 58, 75, 0.95) 30%,
            rgba(45, 58, 75, 0.6) 60%,
            rgba(45, 58, 75, 0) 100%
        );
        z-index: 0;

        .music-decor {
            width: 100%;
            height: 100%;
            position: relative;

            .note {
                position: absolute;
                color: rgba(103, 195, 255, 0.6);
                font-size: 2em;
                animation: float 3s ease-in-out infinite;
                text-shadow: 0 0 20px rgba(103, 195, 255, 0.4);

                &:nth-child(1) {
                    left: 25%;
                    top: 15%;
                }

                &:nth-child(2) {
                    left: 35%;
                    top: 45%;
                    animation-delay: 0.5s;
                }

                &:nth-child(3) {
                    left: 20%;
                    top: 75%;
                    animation-delay: 1s;
                }
            }

            .cd-effect {
                position: absolute;
                left: 40%;
                top: 25%;
                width: 160px;
                height: 160px;
                border-radius: 50%;
                background: radial-gradient(circle at center, 
                    rgba(255, 255, 255, 0.3) 0%,
                    rgba(255, 255, 255, 0.2) 30%,
                    rgba(255, 255, 255, 0.15) 60%,
                    transparent 80%
                );
                border: 4px solid rgba(103, 195, 255, 0.5);
                box-shadow: 
                    0 0 40px rgba(103, 195, 255, 0.4),
                    inset 0 0 40px rgba(103, 195, 255, 0.4);
                transform: translateX(-50%);
                animation: rotate 20s linear infinite;
            }
        }
    }

    // 右侧装饰面板
    .right-panel {
        position: fixed;
        right: 0;
        top: 0;
        width: 450px;
        height: 100%;
        background: linear-gradient(to left,
            rgba(32, 40, 51, 1) 0%,
            rgba(32, 40, 51, 1) 15%,
            rgba(45, 58, 75, 0.95) 30%,
            rgba(45, 58, 75, 0.6) 60%,
            rgba(45, 58, 75, 0) 100%
        );
        z-index: 0;

        .wave-bars {
            position: absolute;
            right: 25%;
            top: 50%;
            transform: translateY(-50%);
            display: flex;
            gap: 6px;

            .bar {
                width: 3px;
                height: 40px;
                background: rgba(103, 195, 255, 0.4);
                border-radius: 2px;
                animation: waveBar 1.2s ease-in-out infinite;

                @for $i from 1 through 12 {
                    &:nth-child(#{$i}) {
                        animation-delay: $i * 0.1s;
                    }
                }
            }
        }

        .circles {
            position: absolute;
            right: 20%;
            top: 30%;
            display: flex;
            flex-direction: column;
            gap: 25px;

            .circle {
                width: 16px;
                height: 16px;
                border-radius: 50%;
                background: rgba(103, 195, 255, 0.3);
                animation: pulse 2s ease-in-out infinite;

                &:nth-child(1) { animation-delay: 0s; }
                &:nth-child(2) { animation-delay: 0.5s; }
                &:nth-child(3) { animation-delay: 1s; }
            }
        }
    }

    .settings-container {
        position: relative;
        z-index: 1;
        width: 90%;
        max-width: 1000px;
        margin: 40px auto;
        padding: 0;
        display: flex;
        background: rgba(255, 255, 255, 0.95);
        border-radius: 15px;
        box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
        backdrop-filter: blur(10px);
        overflow: hidden;
        
        .settings-sidebar {
            width: 280px;
            background-color: rgba(45, 58, 75, 0.95);
            color: white;
            padding: 30px 0;
            
            .user-info {
                text-align: center;
                padding: 0 20px 30px;
                border-bottom: 1px solid rgba(255, 255, 255, 0.1);
                
                .avatar-container {
                    position: relative;
                    width: 100px;
                    height: 100px;
                    margin: 0 auto 15px;
                    border-radius: 50%;
                    overflow: hidden;
                    
                    img {
                        width: 100%;
                        height: 100%;
                        object-fit: cover;
                    }
                    
                    .avatar-overlay {
                        position: absolute;
                        top: 0;
                        left: 0;
                        width: 100%;
                        height: 100%;
                        background-color: rgba(0, 0, 0, 0.5);
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        align-items: center;
                        opacity: 0;
                        transition: opacity 0.3s ease;
                        cursor: pointer;
                        
                        i {
                            font-size: 24px;
                            margin-bottom: 5px;
                        }
                        
                        span {
                            font-size: 12px;
                        }
                        
                        &:hover {
                            opacity: 1;
                        }
                    }
                }
                
                h3 {
                    margin: 0 0 5px;
                    font-size: 18px;
                    font-weight: 600;
                }
                
                .user-email {
                    margin: 0;
                    font-size: 14px;
                    color: rgba(255, 255, 255, 0.7);
                }
            }
            
            .settings-menu {
                padding: 20px 0;
                
                li {
                    padding: 15px 25px;
                    display: flex;
                    align-items: center;
                    cursor: pointer;
                    transition: all 0.3s ease;
                    
                    i {
                        font-size: 18px;
                        margin-right: 10px;
                    }
                    
                    &:hover {
                        background-color: rgba(255, 255, 255, 0.1);
                    }
                    
                    &.active {
                        background-color: $theme-color;
                        color: white;
                    }
                }
            }
        }
        
        .settings-content {
            flex: 1;
            padding: 40px;
            background: rgba(255, 255, 255, 0.98);
            
            .settings-section {
                h2 {
                    margin-top: 0;
                    margin-bottom: 30px;
                    font-size: 24px;
                    font-weight: 600;
                    color: #2d3a4b;
                    padding-bottom: 15px;
                    border-bottom: 1px solid #eee;
                }
                
                .el-form {
                    max-width: 600px;
                }
            }
        }
    }
}

// 动画效果
@keyframes rotate {
    from { transform: translateX(-50%) rotate(0deg); }
    to { transform: translateX(-50%) rotate(360deg); }
}

@keyframes float {
    0%, 100% {
        transform: translateY(0) rotate(-5deg);
        opacity: 0.8;
    }
    50% {
        transform: translateY(-20px) rotate(5deg);
        opacity: 1;
    }
}

@keyframes waveBar {
    0%, 100% {
        height: 40px;
        opacity: 0.8;
    }
    50% {
        height: 60px;
        opacity: 1;
    }
}

@keyframes pulse {
    0%, 100% {
        transform: scale(1);
        opacity: 0.3;
    }
    50% {
        transform: scale(1.1);
        opacity: 0.6;
    }
}

// 响应式布局调整
@media screen and (max-width: 1800px) {
    .user-settings-page {
        .settings-container {
            margin: 30px auto;
            max-width: 900px;
        }
    }
}

@media screen and (max-width: 1400px) {
    .user-settings-page {
        .settings-container {
            margin: 25px auto;
            max-width: 800px;
        }
    }
}

@media screen and (max-width: 1200px) {
    .user-settings-page {
        .settings-container {
            margin: 20px auto;
            width: 95%;
        }
    }
}

// 保留原有的响应式设计
@media screen and (max-width: 992px) {
    .user-settings-page {
        .settings-container {
            flex-direction: column;
            
            .settings-sidebar {
                width: 100%;
                padding: 20px 0;
                
                .user-info {
                    padding-bottom: 20px;
                }
                
                .settings-menu {
                    display: flex;
                    padding: 10px 0;
                    overflow-x: auto;
                    
                    li {
                        padding: 10px 15px;
                        white-space: nowrap;
                    }
                }
            }
        }
    }
}

@media screen and (max-width: 768px) {
    .user-settings-page {
        padding: 15px 0;
        
        .settings-container {
            width: 95%;
            
            .settings-content {
                padding: 20px 15px;
            }
        }
    }
}
</style> 