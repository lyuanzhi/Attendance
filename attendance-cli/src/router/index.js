import Vue from 'vue'
import Router from 'vue-router'
import Layout from '../layout'

Vue.use(Router)

export const constantRoutes = [
    {
        path: '/login',
        component: () => import('../views/Login'),
        hidden: true,
        meta: { title: 'login' }
    },
    {
        path: '/',
        component: Layout,
        redirect: '/home',
        children: [{
            path: 'home',
            component: () => import('../views/Home'),
            meta: {
                title: 'Dashboard'
            }
        }]
    },
    {
        path: '/roll-call',
        component: Layout,
        children: [{
            path: 'index',
            component: () => import('../views/RollCall'),
            meta: {
                title: 'Roll Call'
            }
        }]
    },
    {
        path: '/scan-call',
        component: Layout,
        children: [{
            path: 'index',
            component: () => import('../views/ScanCall'),
            meta: {
                title: 'Scan Call'
            }
        }]
    },
    {
        path: '/scan',
        component: () => import('../views/Scan'),
        hidden: true,
        meta: { title: 'scan' }
    }
]

const routes = [...constantRoutes]

export default new Router({
    routes
})

