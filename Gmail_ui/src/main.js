import './assets/main.css'
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faHatWizard } from '@fortawesome/free-solid-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons'
library.add(fas);
import { fab } from '@fortawesome/free-brands-svg-icons';
library.add(fab);
import { far } from '@fortawesome/free-regular-svg-icons';
library.add(far);
import { dom } from "@fortawesome/fontawesome-svg-core";
dom.watch();
import { createRouter, createWebHistory } from 'vue-router';
import { createApp } from 'vue'
import App from './App.vue'
import SignIn from './components/SignIn.vue';
import SignUp from './components/SignUp.vue';
import EmailInbox from './components/EmailInbox.vue';
import EmailView from './components/EmailView.vue';
import EmailCompose from "./components/EmailCompose.vue";
import NotFound from './components/NotFound.vue';
import Main from './components/Main.vue';
import Profile from './components/Profile.vue';
import EmailSent from './components/EmailSent.vue';
import AllEmail from './components/AllEmail.vue';
import ChangePassword from './components/ChangePassword.vue';
import ForgotPassword from './components/ForgotPassword.vue';
import ResetPassword from './components/ResetPassword.vue';
import Important from './components/Important.vue';
import Starred from './components/Starred.vue';
import Trash from './components/Trash.vue';

const routes = [
    {
        path: '/',
        redirect: '/signin'
    },
    {
        path: '/signin',
        name: 'SignIn',
        component: SignIn,
    },
    {
        path: '/signup',
        name: 'SignUp',
        component: SignUp
    },
    {
        path: '/forgotpassword',
        name: 'ForgotPassword',
        component: ForgotPassword 
    },
    {
        path: '/resetpassword',
        name: 'ResetPassword',
        component: ResetPassword 
    },
    {
        path: '/main',
        name: 'Main',
        component: Main,
        children: [
            {
                path: '/emailinbox',
                name: 'EmailInbox',
                component: EmailInbox
            },
            {
                path: '/emailsent',
                name: 'EmailSent',
                component: EmailSent
            },
            {
                path: '/allemail',
                name: 'AllEmail',
                component: AllEmail
            },
            {
                path: '/emailcompose',
                name: 'EmailCompose',
                component: EmailCompose
            },
            {
                path: '/emailview/:id',
                name: 'EmailView',
                component: EmailView
            },
            {
                path: '/profile',
                name: 'Profile',
                component: Profile
            },
            {
                path: '/changepassword',
                name: 'ChangePassword',
                component: ChangePassword
            }, 
            {
                path: '/starred',
                name: 'Starred',
                component: Starred
            },
            {
                path: '/trash',
                name: 'Trash',
                component: Trash
            },
            {
                path: '/important',
                name: 'Important',
                component: Important 
            },
        ]
    }, 
    {
        path: '/:catchAll(.*)',
        name: 'NotFound',
        component: NotFound
    }
];
const router = createRouter({
    history: createWebHistory(),
    routes: routes,
    mode: 'history',
    linkActiveClass: 'active',
});
const app = createApp(App);
app.use(router);
app.component("font-awesome-icon", FontAwesomeIcon);
app.mount('#app');
