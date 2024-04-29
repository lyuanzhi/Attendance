import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		ISLOGIN: false,
        sections: [],
        curSectionIndex: 0,
        isFaculty: true,
        curNetID: null
	}
})

export default store
