<template>
    <div id="scan">
        <h1 style="width: 100%; text-align: center; margin-top: 50px; margin-bottom: 50px;">Duke Attendance Scan</h1>
        <div class="form">
            <p>{{ "longitude: " + longitude }}</p>
            <p>{{ "latitude: " + latitude }}</p>
            <p>{{ "distance: " + distance + " m" }}</p>
            <p>{{ "tolerance: " + accuracy + " m" }}</p>
            <p v-if="msg" style="color:#A40000;">{{ msg }}</p>
            <div>
                <div class="label">Enter your netID</div>
                <input class="input" type="text" v-model="netId">
            </div>
            <div v-if="showScan">
                <QrcodeStream @decode="handleScan" :delay="300" :facingMode="'environment'">
                    <video id="video" ref="video" width="100%" height="auto" autoplay></video>
                </QrcodeStream>
            </div>
        </div>
    </div>
</template>

<script>
import { QrcodeStream } from 'vue-qrcode-reader'
import { takeAttendance2 } from '@/api'
export default {
    name: 'scan',
    components: {
        QrcodeStream
    },
    data() {
        return {
            netId: "",
            distance: null,
            showScan: false,
            msg: "Scan QR Code",
            accuracy: 0,
            latitude: 0,
            longitude: 0,
            disableLoc: true
        }
    },
    methods: {
        calculateDistance(lat1, lon1, lat2, lon2) {
            const R = 6371;
            const dLat = (lat2 - lat1) * Math.PI / 180;
            const dLon = (lon2 - lon1) * Math.PI / 180;
            const a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
            const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            const distance = R * c * 1000;
            return distance;
        },
        handleScan(result) {
            let json = ""
            try {
                json = JSON.parse(result)
            } catch (error) {
                this.msg = result
                return
            }
            this.showScan = false
            let data = {
                sectionId: json.sectionId,
                netId: this.netId,
                date: json.date,
                code: json.code
            }
            // console.log(data)
            takeAttendance2(data).then(res => {
                // console.log(res)
                if (res.data.code == 200 && res.data.data == true) {
                    this.msg = "Successfully Check In!"
                    this.showScan = false;
                } else {
                    this.msg = "Check In Failed! Try Again!"
                    this.showScan = true;
                }
            }).catch(() => {
                this.msg = "Check In Failed! Try Again!"
                this.showScan = true;
            })
        },
        getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    position => {
                        // console.log(position.coords)
                        let distance = this.calculateDistance(position.coords.latitude, position.coords.longitude,
                            36.003515, -78.938298);
                        this.distance = distance.toFixed(2);
                        this.accuracy = position.coords.accuracy.toFixed(2);
                        this.latitude = position.coords.latitude.toFixed(6);
                        this.longitude = position.coords.longitude.toFixed(6);
                        // console.log(this.distance)
                        if (this.distance < 100 || this.disableLoc) {
                            this.showScan = true;
                        } else {
                            this.msg = "You Should In The Class Room!"
                        }
                    },
                    error => {
                        this.distance = null
                        // console.log(error)
                        this.getLocation()
                    }, {
                    enableHighAccuracy: true,
                    timeout: 5000,
                    maximumAge: 0
                }
                );
            }
        }
    },
    beforeCreate() {
        if (this.$store.state.ISLOGIN == false) {
            this.$router.push({ path: '/login' })
        }
    },
    mounted() {
        this.getLocation()
    }
}
</script>

<style scoped>
#scan {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
}

.form {
    margin-top: 20px;
    width: 50%;
    margin-left: auto;
    margin-right: auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.label {
    display: block;
    margin-bottom: 5px;
}

.input {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
}
</style>
