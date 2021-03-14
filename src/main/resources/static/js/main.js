    var messageApi = Vue.resource('/message{/id}')
var stompClient = null
const handlers = []

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/activity', message => {
            handlers.forEach(handler => handler(JSON.parse(message.body)));
        });
    });
}

function addHandler(handler) {
    handlers.push(handler)
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendMessage(message) {
    stompClient.send("/app/changeMessage", {}, JSON.stringify(message));
    if (message.text.substr(0,4).includes("Олег", 0)) {
        stompClient.send("/app/botMessage", {}, JSON.stringify(message));
    }
}

function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('message-form', {
    props: ['messages'],
    data: function () {
        return {
            text: ''
        }
    },
    template:
    '<div>' +
        '<messages-list :messages="messages" />' +
        '<input type="text" placeholder="Write something" v-model="text" @keyup.enter="send" />' +
        '<input type="button" value="Send" @click="send" />' +
    '</div>',
    methods: {
        send: function () {
            sendMessage({ text: this.text, autor: frontendData.profile})
            this.text = ''
        }
    }
});

Vue.component('message-row', {
    props: ['message'],
    template: '<div><i>({{ message.autor.username }})</i> {{ message.text }}</div>'
});

Vue.component('messages-list', {
    props: ['messages'],
    template:
        '<div id="messages-scroll">' +
            '<message-row v-for="message in messages" :key="message.id" :message="message" />' +
        '</div>'
});

disconnect()
connect()

var app = new Vue({
    el: '#app',
    template:
        '<div>' +
        '<div>' +
            '<div>{{ profile.username }}&nbsp;<a href="/logout">Log out</a></div>' +
            '<message-form :messages="messages" />' +
        '</div>' +
        '</div>',
    data: {
        messages: frontendData.messages,
        profile: frontendData.profile
    },
    created: function () {
        addHandler(data => {
            let index = getIndex(this.messages, data.id)
            if (index > -1) {
                this.messages.splice(index, 1, data)
            } else {
                this.messages.push(data)
            }
        })
    }
});