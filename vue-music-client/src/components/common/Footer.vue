<template>
<div class="footer">
    <div class="footer-content">
        <div class="footer-section about">
            <h3>关于我们</h3>
            <p>音乐客户端是一个专业的音乐平台，致力于为用户提供高质量的音乐服务和便捷的聆听体验。</p>
            <div class="contact">
                <span><i class="el-icon-location"></i> 中国，潮州</span>
                <span><i class="el-icon-phone"></i> +86 123 4567 8901</span>
                <span><i class="el-icon-message"></i> vueMusicClient.com</span>
            </div>
            <div class="socials">
                <a href="#"><i class="el-icon-s-custom"></i></a>
                <a href="#"><i class="el-icon-s-promotion"></i></a>
                <a href="#"><i class="el-icon-s-platform"></i></a>
                <a href="#"><i class="el-icon-s-cooperation"></i></a>
            </div>
        </div>
        
        <div class="footer-section links">
            <h3>快速链接</h3>
            <ul>
                <li><router-link to="/home">首页</router-link></li>
                <li><router-link to="/songlist">歌单</router-link></li>
                <li><router-link to="/singer">歌手</router-link></li>
                <li><router-link to="/profile">个人中心</router-link></li>
            </ul>
        </div>
        
        <div class="footer-section contact-form">
            <h3>联系我们</h3>
            <form @submit.prevent="submitContact">
                <input type="email" v-model="contactForm.email" class="contact-input" placeholder="输入您的邮箱...">
                <textarea v-model="contactForm.message" class="contact-input" placeholder="您的留言..."></textarea>
                <button type="submit" class="btn">发送</button>
            </form>
        </div>
    </div>
    
    <div class="footer-bottom">
        <p>关于&nbsp;&nbsp;|&nbsp;&nbsp;帮助&nbsp;&nbsp;|&nbsp;&nbsp;条款&nbsp;&nbsp;|&nbsp;&nbsp;反馈</p>
        <p>&copy; {{ new Date().getFullYear() }} 音乐客户端 | 设计与开发</p>
    </div>
</div>
</template>

    
<script>
export default {
    name: 'Footer',
    data() {
        return {
            contactForm: {
                email: '',
                message: ''
            }
        }
    },
    methods: {
        submitContact() {
            this.$message({
                message: '感谢您的留言，我们会尽快回复！',
                type: 'success'
            });
            this.contactForm.email = '';
            this.contactForm.message = '';
        }
    }
}
</script>

    
<style lang="scss" scoped>
.footer {
    background: #202124;
    color: #eee;
    padding-top: 30px;
    width: 100%;
    box-sizing: border-box;
    
    .footer-content {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 20px;
        
        .footer-section {
            flex: 1;
            padding: 0 20px;
            min-width: 250px;
            margin-bottom: 20px;
            
            h3 {
                color: #fff;
                font-size: 18px;
                margin-bottom: 15px;
                position: relative;
                
                &::before {
                    content: '';
                    position: absolute;
                    bottom: -5px;
                    left: 0;
                    width: 50px;
                    height: 2px;
                    background: #409EFF;
                }
            }
            
            &.about {
                p {
                    line-height: 1.6;
                }
                
                .contact {
                    margin-top: 20px;
                    display: flex;
                    flex-direction: column;
                    
                    span {
                        margin-bottom: 10px;
                        display: flex;
                        align-items: center;
                        
                        i {
                            margin-right: 10px;
                            color: #409EFF;
                        }
                    }
                }
                
                .socials {
                    margin-top: 20px;
                    
                    a {
                        display: inline-block;
                        width: 40px;
                        height: 40px;
                        background: #364146;
                        color: #fff;
                        border-radius: 50%;
                        text-align: center;
                        line-height: 40px;
                        margin-right: 10px;
                        transition: all 0.3s;
                        
                        &:hover {
                            background: #409EFF;
                            transform: translateY(-3px);
                        }
                        
                        i {
                            font-size: 20px;
                        }
                    }
                }
            }
            
            &.links {
                ul {
                    list-style: none;
                    padding: 0;
                    
                    li {
                        margin-bottom: 10px;
                        border-bottom: 1px solid #444;
                        padding-bottom: 5px;
                        
                        a {
                            color: #eee;
                            text-decoration: none;
                            transition: all 0.3s;
                            display: block;
                            
                            &:hover {
                                color: #409EFF;
                                padding-left: 5px;
                            }
                        }
                    }
                }
            }
            
            &.contact-form {
                .contact-input {
                    background: #272727;
                    color: #eee;
                    border: none;
                    border-radius: 4px;
                    padding: 10px;
                    width: 100%;
                    margin-bottom: 10px;
                    outline: none;
                    
                    &:focus {
                        box-shadow: 0 0 5px rgba(64, 158, 255, 0.5);
                    }
                }
                
                textarea.contact-input {
                    height: 80px;
                    resize: none;
                }
                
                .btn {
                    background: #409EFF;
                    color: #fff;
                    border: none;
                    padding: 8px 15px;
                    border-radius: 4px;
                    cursor: pointer;
                    transition: all 0.3s;
                    
                    &:hover {
                        background: #66b1ff;
                    }
                }
            }
        }
    }
    
    .footer-bottom {
        background: #16171a;
        padding: 15px 0;
        text-align: center;
        font-size: 14px;
        
        p {
            margin: 5px 0;
        }
    }
}

// 大屏幕适配
@media screen and (min-width: 1600px) {
    .footer .footer-content {
        padding: 0 5%;
    }
}

// 响应式调整
@media screen and (max-width: 768px) {
    .footer .footer-content {
        flex-direction: column;
        
        .footer-section {
            margin-bottom: 30px;
        }
    }
}
</style>
