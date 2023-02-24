<template>
<div class="menu">
    <el-menu default-active="info" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" :collapse=collapse router>
        <div v-for="(menu,index) in menus">
            <el-menu-item :index="menu.index" :key="index">
                <template>
                    <i :class="menu.icon"></i>
                    <span>{{menu.title}}</span>
                </template>
            </el-menu-item>
        </div>
    </el-menu>
</div>
</template>

<script>
export default {
    name: 'Menu',
    data() {
        return {
            collapse: false,
            menus: [{
                    icon: 'el-icon-location',
                    index: 'info',
                    title: '系统首页'
                },
                {
                    icon: 'el-icon-setting',
                    index: 'consumer',
                    title: '用户管理'
                }, {
                    icon: 'el-icon-setting',
                    index: 'singer',
                    title: '歌手管理'
                }, {
                    icon: 'el-icon-setting',
                    index: 'songlist',
                    title: '歌单管理'
                }
            ]
        }
    },
    created() {
        //this.menus = [...this.$router.options.routes]
    },
    mounted() {

    },
    methods: {
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        }
    }
}
</script>

<style lang="scss">
.menu {
    height: 100%;

    .el-menu {
        height: 100%;

    }
}
</style>
