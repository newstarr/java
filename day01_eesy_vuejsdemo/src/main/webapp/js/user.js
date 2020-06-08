new Vue({
    el:"#app",
    data:{
        user:{
            id:"",
            username:"",
            password:"",
            email:"",
            age:"",
            sex:""
        },
        userList:[]
    },
    methods:{
      findAll:function(){
          //把vue的对象赋值给变量，vue的对象才有userList，axios没有userList，所以赋值不了
          var _this = this;
          axios.get('/day01_eesy_vuejsdemo/user/findAll.do')
              .then(function (response) {
                  //响应的数据赋值给userList
                  _this.userList = response.data;
                  console.log(response);
              })
              .catch(function (error) {
                    console.log(error);
              })
      },
        findById:function(userId){
            var _this = this;
            axios.get('/day01_eesy_vuejsdemo/user/findById.do', {params:{id:userId}})
                .then(function (response) {
                    _this.user = response.data;
                    $("#myModal").show();
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        update:function (user) {
            var _this = this;
            axios.post('/day01_eesy_vuejsdemo/user/updateUser.do',  _this.user)
                .then(function (response) {
                    _this.findAll();
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
    },
    created:function(){
        this.findAll();
    }
});