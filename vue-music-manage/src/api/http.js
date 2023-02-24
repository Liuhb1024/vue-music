import axios from "axios"

axios.defaults.withCredentials = true

const request = axios.create({
    baseURL: 'http://localhost:9000/myvue3project/',
    timeout: 4000,
    // headers: {
    //     'Content-Type': 'application/x-www-urlencoded;charset=UTF-8'
    //     'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    //     'Content-Type': 'multipart/form-data'
    // }
})

export function get(url, params = {}) {
    return new Promise((resolve, reject) => {
        request.get(url, {
            params: params
        }).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(err)
        })
    })
}


export function post(url, data = {}, config = {}) {
    return new Promise((resolve, reject) => {
        request.post(url, data, config).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(err)
        })
    })
}

export function put(url, data = {}, config = {}) {
    return new Promise((resolve, reject) => {
        request.put(url, data, config).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(err)
        })
    })
}

export function del(url, data = {}) {
    return new Promise((resolve, reject) => {
        request.delete(url, {
            data
        }).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(err)
        })
    })
}

//响应拦截器
/* axios.interceptors.response.use(resp => {
        if (resp.status == 200) {
            return Promise.resolve(response)
        } else {
            return Promise.reject(response)
        }
    },
    err => {
        if (err.response.status) {
            switch (err.response.status) {
                case 401:
                    router.replace({
                        path: '/',
                        query: {
                            redirect: router.currentRoute.fullPath
                        }
                    })
                    break;
                case 404:
                    break;
            }
            return Promise.reject(error.response)
        }
    }) */