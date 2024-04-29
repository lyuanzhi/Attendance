import { Notification } from 'element-ui';

export function notify(msg) {
    Notification({
        title: 'Warning',
        message: msg,
        type: 'warning',
        duration: 1200,
        position: 'top-left'
    });
}