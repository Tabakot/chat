<template>
    <v-card>
        <v-row
                align="center"
                class="spacer"
                no-gutters
        >
            <v-col
                    cols="2"
                    sm="2"
                    md="1"
            >
                <v-list-item-avatar>
                    <v-avatar
                            color="primary"
                            size="56px"
                    >
                        <span class="white--text headline">{{ message.autor.username.substr(0,1) }}</span>
                    </v-avatar>
                </v-list-item-avatar>
            </v-col>
            <v-col class="hidden-xs-only">
                <v-card-text primary-title>

                    <v-list-item-title>
                        {{ message.autor.username }}
                    </v-list-item-title>
                    <v-list-item-subtitle>
                        {{ message.text }}
                    </v-list-item-subtitle>
                </v-card-text>
            </v-col>
        </v-row>
    </v-card>
</template>

<script>
    export default {
        props: ['message']
    }
</script>

<style>

</style>