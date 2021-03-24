<template>
    <div>
        <messages-list :messages="messages"/>

        <v-layout row>
            <v-container fluid>
                <v-row>
                    <v-col
                            sm="6"
                    >
                        <v-text-field
                                label="New message"
                                placeholder="Write something"
                                persistent-hint
                                outlined
                                v-model="text"
                                @keyup.enter="send"
                        ></v-text-field>
                    </v-col>
                    <v-col
                            sm="6"
                    >
                        <v-btn depressed color="primary" @click="send">
                            Send
                        </v-btn>
                    </v-col>
                </v-row>
            </v-container>

        </v-layout>
    </div>
</template>

<script>
    import MessagesList from 'components/messages/MessageList.vue'
    import { sendMessage } from "../../../util/ws";

    export default {
        props: ['messages'],
        components: {
            MessagesList
        },
        data() {
            return {
                text: ''
            }
        },
        methods: {
            send() {
                sendMessage({ text: this.text, autor: frontendData.profile})
                this.text = ''
            }
        }
    }
</script>

<style>

</style>