
// new Vue({
//     el:"#main",
//     data:{
        
//     },
//     methods: {
//         login(){
//                 var uname=this.uname;
//                 var reg=/^1[345678]\d{9}$/
//                 if(!reg.test(uname)){
//                     $('.alertMag').html("用户名不正确");
//             }
//             axios.get("http://127.0.0.1:3000/login?uname="+uname).then(res=>{
//                 if(res.data==1){
//                    location.href="index.html";
//                 }
            
//             })
//         }
//     }
// })


// 时间
function on() {
    var date1 =new Date;
    var  year=date1.getFullYear();
    var  month=date1.getMonth();
    var day=date1.getDate()
    var xinqi=date1.getDay();    
    var weekday=["日","一","二","三","四","五","六"];
    var time=year+"-"+(month+1)+"-"+day+"   星期"+weekday[xinqi]+"  ";
    document.getElementById("myTime").innerHTML = time;
}
//导航
function nav(){
    var moved=0;     
    var navLi=$('.nav_list>li');
    $('.nav_left_click').click(function(){
        if(!$(this).is(".disabled")){
            moved--;
            $('.nav_list').css("margin-left",-106*moved);
            $('.nav_right_click').removeClass("disabled");
            if(moved==0){
                $(this).addClass("disabled");
            }
        }
    }); 
    $('.nav_right_click').click(function(){ 
        if(!$(this).is(".disabled")){
            moved++;
            $('.nav_list').css("margin-left",-106*moved);
            $('.nav_left_click').removeClass("disabled");
            if(moved==navLi.length-7){
                    $(this).addClass("disabled");
            }
        }
    });
}
// 我的待办
function myHandle(){
    var tableLi=$(".table_left_list>li");
    tableLi.click(function(){
        //获取点击的元素给其添加样式，讲其兄弟元素的样式移除
    　　　　$(this).addClass("tableactive").siblings().removeClass("tableactive");
    　　　　//获取选中元素的下标
    　　　　var index = $(this).index();
    　　　　$(this).parent().siblings().children().eq(index).css("display",'block')
    　　　　.siblings().css("display","none");
    })
}
// 您的位置
function myLocation(){
        $('.nav_list li').click(function(){
            $('.location').text('');
            $('.location').text(($(this).text())+">");
        })
        $()
}

window.onload=function(){
    on();
    nav();
    myHandle();
    myLocation();
}


