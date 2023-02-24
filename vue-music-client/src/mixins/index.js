import {
    querySongsByName
} from "@/api"

import {
    Message
} from "element-ui"

export const mixin = {
    methods: {
        getCoverImgUrl(relativeUrl) {
            return `${this.$store.state.config.HOST}${relativeUrl}`
        },
        searchForSongs() {
            if (!this.$route.query.kw) {
                this.$store.commit('SAVE_SONGS_FOR_EVERY_SEARCH', [])
                Message({
                    message: '请输入要搜索的内容',
                    type: 'error',
                    duration: 2000
                })
            } else {
                querySongsByName({
                    kw: this.$route.query.kw
                }).then(jsonData => {
                    if (!jsonData.length) {
                        this.$store.commit('SAVE_SONGS_FOR_EVERY_SEARCH', [])
                        Message({
                            message: '请重新搜索关键词',
                            type: 'info',
                            duration: 2000
                        })
                    } else {
                        this.$store.commit('SAVE_SONGS_FOR_EVERY_SEARCH', jsonData)
                    }
                }).catch(err => console.log(err))
            }
        }
    }
}