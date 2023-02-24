<template>
<div id="app">
    <router-view />
</div>
</template>

<script>
export default {
    name: 'app',
    components: {}
}
</script>

<style lang="scss">
* {
    margin: 0;
    padding: 0;
}

#app {
    font-family: 'Flyme', 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    position: relative;
}
</style>
