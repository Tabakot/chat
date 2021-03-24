<template>
    <div id="messages-scroll">
        <message-row v-for="message in messages" :key="message.id" :message="message"/>
    </div>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'

    export default {
        props: ['messages'],
        components: {
            MessageRow
        }
    }
</script>

<style>

</style>