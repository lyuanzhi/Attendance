<template>
    <div class="table-classic-wrapper">
        <el-card shadow="always">
            <!-- Operation -->
            <div style="display: flex; align-items: center; justify-content: space-between;">
                <el-button class="control-btns" type="primary" @click="exportVisible = true">Export</el-button>
                <div class="control-btns">
                    <el-tag size="medium" style="display: flex; align-items: center;">
                        Disable Changing Display Name
                        <el-switch style="margin-left: 10px;" v-model="editForbid" />
                    </el-tag>
                </div>
            </div>
            <!-- Search -->
            <el-form ref="searchForm" :inline="true" class="search-form">
                <el-form-item label="NetID" label-width="90px">
                    <el-input v-model="netId" placeholder="NetID" />
                </el-form-item>
                <el-form-item label="Date" label-width="70px">
                    <el-date-picker v-model="dateVal" type="date" placeholder="Select Date" format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button style="margin-left: 30px;" type="primary" @click="onSubmit">Search</el-button>
                </el-form-item>
            </el-form>
            <!-- Table -->
            <el-table v-loading="listLoading" :data="tableData" tooltip-effect="dark" style="width: 100%" size="medium">
                <el-table-column prop="netId" label="NetID" align="center" width="300" sortable>
                    <template slot-scope="scope">
                        <div slot="reference">
                            <el-tag size="medium">{{ scope.row.netId }}</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="displayName" label="Display Name" align="center" min-width="200">
                    <template slot-scope="scope">
                        <template v-if="scope.row.editable">
                            <el-input v-model="scope.row.displayName" style="width: 100px;" />
                            <el-button style="margin-left: 5px;" class="cancel-btn" type="warning"
                                @click="cancelEdit(scope.row)">Cancel</el-button>
                        </template>
                        <span v-else>{{ scope.row.displayName }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="Attendance Status" align="center" width="300">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.attendStatus" style="width: 200px" @change="selectChange(scope.row)">
                            <el-option :value="0" label="Unselected" disabled />
                            <el-option :value="1" label="Present" />
                            <el-option :value="2" label="Absent" />
                            <el-option :value="3" label="Tardy" />
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="Operation" width="300">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.editable" type="success" size="small"
                            @click="confirmEdit(scope.row)">Save</el-button>
                        <el-button v-else type="primary" size="small" :disabled="editForbid"
                            @click="scope.row.editable = !scope.row.editable; scope.row.originDisplayName = scope.row.displayName;">Change
                            Display Name</el-button>
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
import { getAttendance, changeDisplayName, takeAttendance, facultyExport } from '@/api'
import { notify } from '@/notify'
import {saveAs} from 'file-saver';
export default {
    name: 'RollCall',
    data() {
        return {
            dateVal: new Date().toISOString().substr(0, 10),
            netId: "",
            editForbid: false,
            listLoading: false,
            tableData: [],
            exportVisible: false,
            exportLoading: false
        }
    },
    created() {
        this.fetchData()
        this.$bus.$on("refresh", this.handleRefresh)
    },
    beforeDestroy() {
        this.$bus.$off('refresh', this.handleRefresh)
    },
    methods: {
        exportFile(type) {
            this.exportLoading = true
            let data = {
                sectionId: this.$store.state.sections[this.$store.state.curSectionIndex].sectionid,
                netId: this.$store.state.curNetID,
                type: type
            }
            // console.log(data)
            facultyExport(data).then(res => {
                // console.log(res)
                if (res.data.code == 200 && res.data.data != null) {
                    this.exportVisible = false
                    this.exportLoading = false
                    let str = new Blob([res.data.data], { type: 'text/plain;charset=utf-8' });
                    saveAs(str, 'attendance_report_for_faculty.' + type.toLowerCase());
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
            this.dateVal = new Date().toISOString().substr(0, 10)
            this.netId = ""
            this.fetchData()
        },
        fetchData() {
            if (this.$store.state.sections.length == 0) {
                return
            }
            this.listLoading = true
            let data = {
                sectionId: this.$store.state.sections[this.$store.state.curSectionIndex].sectionid,
                netId: this.netId,
                date: this.dateVal
            }
            // console.log(data)
            getAttendance(data).then(res => {
                // console.log(res)
                if (res.data.code == 200 && res.data.data != null) {
                    this.tableData = res.data.data.map(item => {
                        this.$set(item, 'editable', false)
                        this.$set(item, 'originDisplayName', "")
                        this.$set(item, 'originAttendStatus', item.attendStatus)
                        return item
                    })
                    this.listLoading = false
                } else {
                    notify(res.data.message)
                    this.listLoading = false
                }
            }).catch(() => {
                notify('Failed to Fetch Data!')
                this.listLoading = false
            })
        },
        onSubmit() {
            this.fetchData()
        },
        selectChange(row) {
            let data = {
                sectionId: this.$store.state.sections[this.$store.state.curSectionIndex].sectionid,
                netId: row.netId,
                date: this.dateVal,
                attendStatus: row.attendStatus
            }
            // console.log(data)
            takeAttendance(data).then(res => {
                // console.log(res)
                if (res.data.code == 200 && res.data.data == true) {
                    row.originAttendStatus = row.attendStatus
                } else {
                    row.attendStatus = row.originAttendStatus
                    notify('Failed to Change Attendance Status!')
                }
            }).catch(() => {
                row.attendStatus = row.originAttendStatus
                notify('Failed to Change Attendance Status!')
            })
        },
        cancelEdit(row) {
            row.editable = false
            row.displayName = row.originDisplayName
        },
        confirmEdit(row) {
            row.editable = false
            let data = {
                netId: row.netId,
                displayName: row.displayName
            }
            // console.log(data)
            changeDisplayName(data).then(res => {
                // console.log(res)
                if (res.data.code == 200 && res.data.data == true) {

                } else {
                    row.displayName = row.originDisplayName
                    notify('Failed to Change Display Name!')
                }
            }).catch(() => {
                row.displayName = row.originDisplayName
                notify('Failed to Change Display Name!')
            })
        }
    }
}
</script>

<style lang="less">
.table-classic-wrapper {
    .el-card {
        min-height: 656px;
    }

    .control-btns {
        margin-bottom: 20px;
    }

    .search-form {
        padding-top: 18px;
        margin-bottom: 15px;
        background-color: #f7f8fb;
    }

    .el-table thead {
        font-weight: 600;

        th {
            background-color: #f2f3f7;
        }
    }

    .export-data {
        display: flex;
        justify-content: space-around;
        width: 300px;
        margin: 0 auto 30px;
    }

    .hints {
        font-size: 12px;
        color: #aaa;
        text-align: center;
    }
}
</style>
