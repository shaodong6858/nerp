 new Vue({
     el:"#main",
     data:{
    	 userInfo: null,
    	 currentNavList: null
     },
     created () {
        this.getUserInfo()
       },
     methods: {
    	getUserInfo (){
    			axios.get('system/user/info?empnumber=fwadmin')
    			  .then((response) => {
    				  var systemInfo = response.data.data;
    				  this.userInfo = systemInfo;
    				  for (var key in this.userInfo.navList){
    					  this.currentNavList = this.userInfo.navList[key];
    					  break;
    				  }
    			      console.log(systemInfo);
    			  })
    			  .catch((error) => {
    			      console.log(error);
    			  });
    		}
     }
 })


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
// 导航右侧选项卡
function navRight(){
    $(".right_tab_con:not(:first)").hide();
    $('.nav_list li').click(function(){
　　　　 var index = $(this).index();
        $(".right_tab_con").hide().eq(index).show();
    })
}

$(".right_tab_con:not(:first)").hide();
    var index = $(this).index();
    $('.nav_list li').click(function(){
        $(".right_tab_con").hide().eq(index).show();
    })
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
}
//左侧导航选项卡
function leftNavtab(){
    $(".con_right_tab:not(:first)").hide();
    $(".system_con:not(:first)").hide();
    $('.panel-body ul li').click(function(){
        $(this).addClass('leftActive').siblings().removeClass('leftActive');
　　　　 var index = $(this).index();
        $(".con_right_tab").hide().eq(index).show();
        $(".system_con").hide().eq(index).show();
    })
}
// 选择人员
function selectPer(){
    $('.select_person').click(function(){
         $('.model_bg').show();
        //  $(".model_bg").fadeIn(3000);
    })
    $('.close_icon').click(function(){
        $('.model_bg').hide();
    })
    // 选择人员折叠
    $('.arrow').click(function(){
        $(this).find('img').toggleClass('icon_transform')
        $(this).next().slideToggle();
        $('.nav_con').not($(this).next()).hide();
    })
    // 部门管理折叠
    $('.department_list_title').click(function(){
        $(this).parent().siblings().slideToggle();
        // $('.nav_con').not($(this).next()).hide();
    })
}

// $(document).click(function(){
//     $('.model_bg').css('display','none');
// })
window.onload=function(){
    on();
    nav();
    myHandle();
    myLocation();
    leftNavtab();
    navRight();
    selectPer();
}
// sTop = $('.con').offset().top;//169
// aTop=$(document).height;
// console.log(aTop);
// $('.con_leftnav').height(aTop-sTop);


