<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Chat</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile">{{ profile.username }}&nbsp;</span>
            <v-btn depressed color="primary" href="/logout">
                Log out
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-main>
            <v-container>
                <message-form :messages="messages" />
            </v-container>
        </v-main>
    </v-app>
</template>

<script>
    import MessageForm from 'components/messages/MessageForm.vue'
    import { addHandler } from "../../util/ws";

    function getIndex(list, id) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].id === id) {
                return i
            }
        }
        return -1
    }

    export default {
        components: {
            MessageForm
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        },
        created() {
            addHandler(data => {
                let index = getIndex(this.messages, data.id)
                if (index > -1) {
                    this.messages.splice(index, 1, data)
                } else {
                    this.messages.push(data)
                }
            })
        }
    }
</script>

<style>

</style>