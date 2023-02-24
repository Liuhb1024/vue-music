<template>
<div class="footer">
    <el-footer>
        <el-card>
            Frontend 2022
        </el-card>
    </el-footer>
</div>
</template>

<script>
export default {
    data() {
        return {}
    }
}
</script>

<style lang="scss" scoped>
.footer {
    padding: 0;

    .el-footer {
        padding: 0;
        background-color: rgb(186, 245, 184);
        color: #333;
        text-align: center;

        .el-card {
            background-color: #cca379;
            height: 100%;
            padding: 0;
        }
    }
}
</style>
