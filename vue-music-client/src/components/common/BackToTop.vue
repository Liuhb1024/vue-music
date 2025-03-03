<template>
    <div class="back-to-top" 
         v-show="show"
         @click="scrollToTop">
        <i class="el-icon-top"></i>
    </div>
</template>

<script>
export default {
    name: 'BackToTop',
    data() {
        return {
            show: false
        }
    },
    mounted() {
        window.addEventListener('scroll', this.handleScroll)
    },
    beforeDestroy() {
        window.removeEventListener('scroll', this.handleScroll)
    },
    methods: {
        handleScroll() {
            this.show = window.pageYOffset > 300
        },
        scrollToTop() {
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.back-to-top {
    position: fixed;
    right: 30px;
    bottom: 100px;
    width: 40px;
    height: 40px;
    background: linear-gradient(135deg, #409EFF, #67c3ff);
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    z-index: 1000;
    opacity: 0;
    transform: translateY(20px);
    animation: fadeIn 0.3s ease forwards;

    i {
        font-size: 20px;
        color: #ffffff;
        transition: transform 0.3s ease;
    }

    &:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(64, 158, 255, 0.4);

        i {
            transform: translateY(-2px);
        }
    }

    &:active {
        transform: translateY(0);
    }
}

@media screen and (max-width: 768px) {
    .back-to-top {
        right: 20px;
        bottom: 90px;
        width: 36px;
        height: 36px;

        i {
            font-size: 18px;
        }
    }
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style> 