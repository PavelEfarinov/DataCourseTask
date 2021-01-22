<template>
    <div>
            User info
        <div class="user-info">
            <template v-if="user">
                <ul>
                    <li>My login: {{user.login}}</li>
                    <li>My email: {{user.email}}</li>
                </ul>
            </template>
            <template v-else>
                <div>No user found!</div>
            </template>
        </div>
    </div>
</template>


<script>
    export default {
        props: ['user'],
        name: "MyProfile"
    }
</script>

<style scoped>

</style>
