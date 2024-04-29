import axios from 'axios'

const serverURL = 'http://192.168.1.7:8090'

export function login(data) {
    return axios({
        url: serverURL + '/user/login',
        method: 'post',
        params: data
    })
}

export function getAttendData(data) {
    return axios({
        url: serverURL + '/attendance/getAttendData',
        method: 'post',
        params: data
    })
}

export function getAttendance(data) {
    return axios({
        url: serverURL + '/attendance/getAttendance',
        method: 'post',
        params: data
    })
}

export function changeDisplayName(data) {
    return axios({
        url: serverURL + '/user/changeDisplayName',
        method: 'post',
        params: data
    })
}

export function takeAttendance(data) {
    return axios({
        url: serverURL + '/attendance/takeAttendance',
        method: 'post',
        params: data
    })
}

export function getSectionData(data) {
    return axios({
        url: serverURL + '/section/getSectionData',
        method: 'post',
        params: data
    })
}

export function changeNotifyStatus(data) {
    return axios({
        url: serverURL + '/section/changeNotifyStatus',
        method: 'post',
        params: data
    })
}

export function studentExport(data) {
    return axios({
        url: serverURL + '/attendance/studentExport',
        method: 'post',
        params: data
    })
}

export function facultyExport(data) {
    return axios({
        url: serverURL + '/attendance/facultyExport',
        method: 'post',
        params: data
    })
}

export function codeSync(data) {
    return axios({
        url: serverURL + '/attendance/codeSync',
        method: 'post',
        params: data
    })
}

export function setRemainToAbsent(data) {
    return axios({
        url: serverURL + '/attendance/setRemainToAbsent',
        method: 'post',
        params: data
    })
}

export function takeAttendance2(data) {
    return axios({
        url: serverURL + '/attendance/takeAttendance2',
        method: 'post',
        params: data
    })
}
