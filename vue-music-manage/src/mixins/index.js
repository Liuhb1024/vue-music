export const mixin = {
    methods: {
        /**
         * 根据静态资源的相对地址获取绝对地址
         * @param {String} 相对地址 uri 
         * @returns 静态资源绝对地址
         */
        getAbsoluteUrlOfStaticResourcesThroughCurrentUrl(currentUrl) {
            return `${this.$store.state.HOST}${currentUrl}`
        },
        /**
         * 将数据库中性别字段映射为“男/女/组合/保密”
         * @param {Number} genderCode 
         * @returns string
         */
        convertToLocaleSexText(genderCode) {
            if (genderCode == 0) {
                return '女'
            } else if (genderCode == 1) {
                return '男'
            } else if (genderCode == 2) {
                return '组合'
            } else {
                return '保密'
            }
        },
        /**
         * 截取数据库返回的 datetime 格式日期字符串的 date 并返回
         * @param {string} val
         * @returns date
         */
        getFormattedDate(val) {
            return String(val).substring(0, 10)
        },
        /**
         * 验证上传的图片是否合法
         * @param {File} file 
         * @returns 
         */
        beforeAvatarUpload(file) {
            const isJPGorPNG = (file.type === 'image/jpeg') || (file.type === "image/png")
            const isLt10M = file.size / 1024 / 1024 < 10;

            if (!isJPGorPNG) {
                this.$message.error('上传头像图片只能是 JPG 或 PNG格式!')
            }
            if (!isLt10M) {
                this.$message.error('上传头像图片大小不能超过 10MB!')
            }
            return isJPGorPNG && isLt10M;
        },
        /**
         * 处理上传图片成功后的回调
         * @param {Response} res 
         * @param {File} file 
         */
        async handleAvatarSuccess(res, file) {
            let _this = this
            if (res.code == 1) {
                const currentPage = _this.currentPage
                await _this.getTableData()
                // 直接设置currentPage，不依赖于$nextTick
                _this.currentPage = currentPage
                _this.$message({
                    message: res.msg,
                    type: 'success',
                    duration: 2000
                })
            } else {
                this.$message.error({
                    message: res.msg,
                    type: 'error',
                    duration: 2000
                })
            }
            // this.imageUrl = URL.createObjectURL(file.raw)
        }
    }
}
