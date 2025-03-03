<template>
<div>

</div>
</template>

<script>
export default {
    name: '',
    data() {
        return {}
    },
    methods: {
        handleLogin() {
            // 登录逻辑
            this.$store.commit('user/SET_LOGIN_STATUS', true);
            this.$store.commit('user/SET_USER_INFO', userData);
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
