(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2bb03960"],{3711:function(t,e,o){"use strict";o("eeac")},a55b:function(t,e,o){"use strict";o.r(e);var s=function(){var t=this,e=t._self._c;return e("div",{staticClass:"login-wrapper"},[e("div",{staticClass:"form-box"},[t._m(0),e("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{model:t.loginForm,rules:t.loginRules,"label-width":"0px"}},[e("el-form-item",{attrs:{prop:"netID"}},[e("el-input",{attrs:{type:"text","auto-complete":"off",placeholder:"netID","prefix-icon":"el-icon-user"},model:{value:t.loginForm.netID,callback:function(e){t.$set(t.loginForm,"netID",e)},expression:"loginForm.netID"}})],1),e("el-form-item",{attrs:{prop:"password"}},[e("el-input",{attrs:{type:"password","auto-complete":"off",placeholder:"password","prefix-icon":"el-icon-lock"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleLogin.apply(null,arguments)}},model:{value:t.loginForm.password,callback:function(e){t.$set(t.loginForm,"password",e)},expression:"loginForm.password"}})],1),t.showCheckbox?e("el-form-item",[e("el-checkbox",{model:{value:t.loginForm.isFaculty,callback:function(e){t.$set(t.loginForm,"isFaculty",e)},expression:"loginForm.isFaculty"}},[t._v("Faculty")])],1):t._e(),e("el-form-item",[e("el-button",{staticStyle:{width:"100%","font-size":"20px"},attrs:{loading:t.loading,size:"small",type:"primary"},nativeOn:{click:function(e){return e.preventDefault(),t.handleLogin.apply(null,arguments)}}},[t.loading?e("span",[t._v("Loading...")]):e("span",[t._v("Log In")])])],1)],1)],1)])},i=[function(){var t=this,e=t._self._c;return e("div",{staticClass:"form-title"},[e("p",[t._v("Log in with NetID")])])}],a=(o("14d9"),o("d722")),n=o("c7b3"),r={name:"Login",data(){return{loginForm:{netID:"",password:"",isFaculty:!0},loginRules:{netID:[{required:!0,trigger:"blur",message:"NetID cannot be empty!"}],password:[{required:!0,trigger:"blur",message:"Password cannot be empty!"}]},loading:!1,redirect:void 0,showCheckbox:!0}},watch:{$route:{handler:function(t){this.redirect=t.query&&t.query.redirect},immediate:!0}},created(){this.$store.state.ISLOGIN=!1,this.$store.state.sections=[],this.$store.state.curSectionIndex=0,this.$store.state.isFaculty=!0,this.$store.state.curNetID=null},mounted(){let t=/iPhone|iPad|iPod|Android/i.test(navigator.userAgent);t&&(this.loginForm.isFaculty=!1,this.showCheckbox=!1),localStorage.getItem("netID")&&localStorage.getItem("password")&&localStorage.getItem("isFaculty")&&(this.loginForm.netID=localStorage.getItem("netID"),this.loginForm.password=localStorage.getItem("password"),this.loginForm.isFaculty="true"==localStorage.getItem("isFaculty"))},methods:{handleLogin(){this.$refs.loginForm.validate(t=>{const e={netID:this.loginForm.netID,password:this.loginForm.password,isFaculty:this.loginForm.isFaculty};t&&(this.loading=!0,Object(a["h"])(e).then(t=>{if(200==t.data.code&&null!=t.data.data){this.$store.state.ISLOGIN=!0,this.$store.state.isFaculty=e.isFaculty,this.$store.state.curNetID=t.data.data.netid,this.$store.state.sections=t.data.data.sections,localStorage.setItem("netID",e.netID),localStorage.setItem("password",e.password),localStorage.setItem("isFaculty",e.isFaculty),this.loading=!1;let o=/iPhone|iPad|iPod|Android/i.test(navigator.userAgent);o?this.$router.push({path:this.redirect||"/scan"}):this.$router.push({path:this.redirect||"/home"})}else this.loading=!1,Object(n["a"])("Wrong Password!")}).catch(()=>{this.loading=!1,Object(n["a"])("Wrong Password!")}))})}}},l=r,c=(o("3711"),o("2877")),d=Object(c["a"])(l,s,i,!1,null,null,null);e["default"]=d.exports},c7b3:function(t,e,o){"use strict";o.d(e,"a",(function(){return i}));var s=o("5c96");function i(t){Object(s["Notification"])({title:"Warning",message:t,type:"warning",duration:1200,position:"top-left"})}},eeac:function(t,e,o){}}]);
//# sourceMappingURL=chunk-2bb03960.3ee1974c.js.map