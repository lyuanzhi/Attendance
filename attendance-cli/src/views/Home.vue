<template>
    <div v-if="this.$store.state.isFaculty == true" class="home-wrapper">
        <div class="date-row">
            <div v-for="(item, index) in generalInfoData" :key="index" class="data-col">
                <el-card v-loading="listLoading" shadow="always" :body-style="{ padding: '0px' }">
                    <div class="date-block">
                        <i :class="[item.icon]" :style="{ background: item.color }" class="icon-box" />
                        <div class="date-cont">
                            <CountTo class="count" :start-val="0" :end-val="item.count" :duration="1000" />
                            <span class="count" v-if="item.title != 'Student Number'">%</span>
                            <p class="title">{{ item.title }}</p>
                        </div>
                    </div>
                </el-card>
            </div>
        </div>
        <el-row class="date-box" :gutter="20">
            <el-col :span="24">
                <el-card v-loading="listLoading" shadow="always" :body-style="{ padding: '0px' }">
                    <ChartsBar :title="title" :data="overallAttend" :key="chartKey" class="data-chart" />
                </el-card>
            </el-col>
        </el-row>
    </div>
    <div v-else class="table-classic-wrapper2">
        <!-- Operation -->
        <el-card shadow="always" style="margin-bottom: 20px;">
            <div class="data-title">Attendance</div>
            <div style="display: flex; align-items: center; justify-content: space-between;">
                <el-button class="control-btns" type="primary" @click="exportVisible = true">Export</el-button>
            </div>
        </el-card>
        <el-card shadow="always">
            <!-- Table -->
            <div class="data-title">Notification</div>
            <el-table v-loading="listLoading" :data="studentTableData" tooltip-effect="dark" style="width: 100%"
                size="large">
                <el-table-column prop="sectionName" label="Section Name" align="center" min-width="200" />
                <el-table-column label="Notification Status" align="center" min-width="300">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.notifyStatus" style="width: 200px" @change="selectChange(scope.row)">
                            <el-option :value="0" label="Enable" />
                            <el-option :value="1" label="Disable" />
                        </el-select>
                    </template>
                </el-table-column>
            </el-table>
            <!-- export -->
            <el-dialog v-loading="exportLoading" title="Export" :visible.sync="exportVisible" width="20%">
                <div class="export-data">
                    <el-button type="primary" @click="exportFile('JSON')">JSON</el-button>
                    <el-button type="primary" @click="exportFile('XML')">XML</el-button>
                </div>
                <div class="hints">Please select the export format.</div>
            </el-dialog>
        </el-card>
    </div>
</template>

<script>
import { getSectionData, getAttendData, changeNotifyStatus, studentExport } from '@/api'
import CountTo from 'vue-count-to'
import ChartsBar from '@/components/Charts/ChartsBar'
import { notify } from '@/notify'
import { saveAs } from 'file-saver';
export default {
    name: 'Home',
    components: { CountTo, ChartsBar },
    data() {
        return {
            title: 'Overall Attendance Distribution',
            generalInfoData: [
                { title: 'Student Number', icon: 'vue-dsn-icon-dianji', count: 0, color: '#2d8cf0' },
                { title: 'Attendance Rate', icon: 'vue-dsn-icon-xinzeng', count: 0, color: '#19be6b' },
                { title: 'Tardiness Rate', icon: 'vue-dsn-icon-xinfeng', count: 0, color: '#ff9900' }
            ],
            overallAttend: {
                "PRESENT": 0,
                "TARDY": 0,
                "ABSENT": 0
            },
            chartKey: 0,
            studentTableData: [],
            listLoading: false,
            exportVisible: false,
            exportLoading: false
        }
    },
    created() {
        if (this.$store.state.isFaculty) {
            // this.fetchFacultyData()
        } else {
            this.fetchStudentData()
        }
        this.$bus.$on("refresh", this.handleRefresh)
    },
    activated() {
        if (this.$store.state.isFaculty) {
            this.fetchFacultyData()
        }
    },
    beforeDestroy() {
        this.$bus.$off('refresh', this.handleRefresh)
    },
    methods: {
        exportFile(type) {
            this.exportLoading = true
            let data = {
                netId: this.$store.state.curNetID,
                type: type
            }
            // console.log(data)
            studentExport(data).then(res => {
                // console.log(res)
                if (res.data.code == 200 && res.data.data != null) {
                    this.exportVisible = false
                    this.exportLoading = false
                    let str = new Blob([res.data.data], { type: 'text/plain;charset=utf-8' });
                    saveAs(str, 'attendance_report_for_student.' + type.toLowerCase());
                } else {
                    notify('Failed to Export!')
                    this.exportLoading = false
                }
            }).catch(() => {
                notify('Failed to Export!')
                this.exportLoading = false
            })
        },
        handleRefresh() {
            this.fetchFacultyData();
        },
        fetchFacultyData() {
            if (this.$store.state.sections.length == 0) {
                return
            }
            this.listLoading = true
            let data = {
                sectionId: this.$store.state.sections[this.$store.state.curSectionIndex].sectionid,
                netId: this.$store.state.curNetID
            }
            getAttendData(data).then(res => {
                // console.log(res)
                if (res.data.code == 200 && res.data.data != null) {
                    let totalNum = res.data.data.absentNum + res.data.data.presentNum + res.data.data.tardyNum
                    let tardyRate = 0
                    let absentRate = 0
                    let presentRate = 0
                    if (totalNum != 0) {
                        tardyRate = res.data.data.tardyNum / totalNum
                        absentRate = res.data.data.absentNum / totalNum
                        presentRate = res.data.data.presentNum / totalNum
                    }
                    this.generalInfoData = [
                        { title: 'Student Number', icon: 'vue-dsn-icon-dianji', count: res.data.data.studentNum, color: '#2d8cf0' },
                        { title: 'Attendance Rate', icon: 'vue-dsn-icon-xinzeng', count: (presentRate + tardyRate) * 100, color: '#19be6b' },
                        { title: 'Tardiness Rate', icon: 'vue-dsn-icon-xinfeng', count: tardyRate * 100, color: '#ff9900' }
                    ]
                    this.overallAttend = {
                        "PRESENT": presentRate,
                        "TARDY": tardyRate,
                        "ABSENT": absentRate
                    }
                    this.chartKey += 1
                    this.listLoading = false
                } else {
                    notify('Failed to Fetch Data!')
                    this.listLoading = false
                }
            }).catch(() => {
                notify('Failed to Fetch Data!')
                this.listLoading = false
            })
        },
        fetchStudentData() {
            this.listLoading = true
            let data = {
                netId: this.$store.state.curNetID
            }
            // console.log(data)
            getSectionData(data).then(res => {
                // console.log(res)
                if (res.data.code == 200 && res.data.data != null) {
                    this.studentTableData = res.data.data.map(item => {
                        this.$set(item, 'originNotifyStatus', item.notifyStatus)
                        return item
                    })
                    this.listLoading = false
                } else {
                    notify('Failed to Fetch Data!')
                    this.listLoading = false
                }
            }).catch(() => {
                notify('Failed to Fetch Data!')
                this.listLoading = false
            })
        },
        selectChange(row) {
            let data = {
                sectionId: row.sectionId,
                netId: this.$store.state.curNetID,
                notifyStatus: row.notifyStatus
            }
            // console.log(data)
            changeNotifyStatus(data).then(res => {
                // console.log(res)
                if (res.data.code == 200 && res.data.data == true) {
                    row.originNotifyStatus = row.notifyStatus
                } else {
                    row.notifyStatus = row.originNotifyStatus
                    notify('Failed to Change Notification Status!')
                }
            }).catch(() => {
                row.notifyStatus = row.originNotifyStatus
                notify('Failed to Change Notification Status!')
            })
        }
    }
}
</script>

<style lang="less">
@import "../assets/less/home";

.table-classic-wrapper2 {

    .el-table thead {
        font-weight: 600;

        th {
            background-color: #f2f3f7;
        }
    }

    .export-data {
        display: flex;
        justify-content: space-around;
        margin: 0 auto 30px;
    }

    .hints {
        font-size: 12px;
        color: #aaa;
        text-align: center;
    }

    .data-title {
        font-size: 24px;
        margin-bottom: 20px;
    }
}
</style>
