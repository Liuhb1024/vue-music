<template>
  <div class="home-container">
    <!-- 侧边栏 -->
    <sidebar class="sidebar"></sidebar>
    
    <!-- 主内容区 -->
    <div class="main-content">
      <router-view></router-view>
    </div>
    
    <!-- 将页脚移到这里，作为容器的直接子元素 -->
    <Footer></Footer>
  </div>
</template>

<script>
import Sidebar from '@/components/common/Sidebar'
import Footer from '@/components/common/Footer'

export default {
  name: 'Home',
  components: {
    Sidebar,
    Footer
  }
}
</script>

<style lang="scss" scoped>
.home-container {
  min-height: 100vh;
  position: relative;
  padding-bottom: 300px; /* 为页脚预留空间，根据页脚高度调整 */
  
  .sidebar {
    position: fixed;
    left: 0;
    top: 0;
    bottom: 0;
    width: 200px;
    background: #545c64;
    z-index: 10;
  }
  
  .main-content {
    margin-left: 200px; /* 侧边栏宽度 */
    padding: 20px;
    
    @media (max-width: 768px) {
      margin-left: 0;
    }
  }
}

/* 添加全局样式 */
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
}
</style> 