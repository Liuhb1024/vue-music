<template>
<div class="scroll-top-wrapper" @click="goTop">
    <span class="go-back-btn">返回顶部</span>
</div>
</template>

<script>
export default {
    name: 'ScrollToTop',
    data() {
        return {}
    },
    methods: {
        goTop() {
            let timer = setInterval(function () {
                document.documentElement.scrollTop = document.documentElement.scrollTop - 20
                if (document.documentElement.scrollTop <= 0) {
                    clearInterval(timer)
                }
            }, 10)
        }
    }
}
</script>

<style lang="scss" scoped>
.scroll-top-wrapper {
    width: 40px;
    height: 40px;
    padding-left: 6px;
    padding-bottom: 5px;
    border: 2px solid gray;
    border-radius: 6px;
    position: fixed;
    right: 5px;
    bottom: 30%;

    &:hover {
        cursor: pointer;
    }

    .go-back-btn {
        font-size: 15px;
        color: gray;
    }
}
</style>
