<template>
<div class="home">
    <div class="audio-player-box">
        <AudioPlay></AudioPlay>
    </div>
    <Header />
    <el-container class="content">
        <el-aside width="190px">
            <Menu />
        </el-aside>
        <el-container>
            <el-main>
                <router-view />
            </el-main>
            <el-footer>
                <Footer />
            </el-footer>
        </el-container>
    </el-container>
</div>
</template>

<script>
import Header from './common/Header.vue'
import Menu from './common/Menu.vue'
import Footer from './common/Footer.vue'
import AudioPlay from '@/components/AudioPlayer.vue'

export default {
    name: 'Home',
    components: {
        Header,
        Menu,
        Footer,
        AudioPlay
    },
    data() {
        return {}
    }
}
</script>

<style lang="scss" scoped>
ul {
    list-style-type: none;
    padding: 0;
}

li {
    display: inline-block;
    margin: 0 10px;
}

a {
    color: #42b983;
}

.home {
    width: 100%;
    height: 100vh;
    padding: 0;

    .content {
        width: 100%;
        height: 100%;
    }
}

/*elment-ui*/
.el-main {
    // background-color: #feffb8;
    color: #333;
    text-align: center;
}

.el-aside {
    height: 100%;
    // background-color: blue;
}

.audio-player-box {
    position: fixed;
    top: 4px;
    left: 45%;
}
</style>
