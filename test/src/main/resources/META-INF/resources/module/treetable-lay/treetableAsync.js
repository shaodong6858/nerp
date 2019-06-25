layui.define(['index','form','treetable','jquery','treetable'],function(exports){
    var treetable = layui.treetable,
        table = layui.table,
        form = layui.form,
	    $ = layui.jquery,
	    setter = layui.setter,
        view = layui.view,
    	admin = layui.admin;
    	
    var tableData = [];
    
    var treetableAsync = {
    	render : function(param){
    		var provincesUrl = param.url;
	
			function init(id){
				admin.req({
					url: provincesUrl + id,
					type:"get",
					success:function(r){
						param.data = tableData = r.data;
						tableInit();
					}
				});
			}
			init(param.treeSpid);
			
			// 渲染表格
		    function tableInit(){
		    	treetable.render(param);
		    }
			
			$("body").on('dblclick','.layui-table-body .layui-table tr',function(){
				var top = $('.layui-table-body').scrollTop();
				var pid = $(this).find('div[class$="'+param.treeIdName+'"]').text();
				var index = $(this).index();
				admin.req({
					url: provincesUrl + pid,
					type:"get",
					success:function(r){
						var isInit = false;
						for(var i=0; i<r.data.length; i++){
							var isPush = true;
							for(var j=0; j<tableData.length; j++){
								if(r.data[i][param.treeIdName] == tableData[j][param.treeIdName]){
									isPush = false;
								}
							}
							if(isPush){
								tableData.push(r.data[i]);
								isInit = true;
							}
						}
						if(isInit){
							param.data = tableData;
							tableInit();
							expandSelfAndParent(index);
							$('.layui-table-body').scrollTop(top);
						}
					}
				});
			});
			
			
			function expandSelfAndParent(index){
				treetable.toggleRows($('.layui-table-body .layui-table tr').eq(index).find('.treeTable-icon'));
				var tpid = $('.layui-table-body .layui-table tr').eq(index).find('.treeTable-icon').attr('lay-tpid');
				var trs = $('.layui-table-body .layui-table tr');
				var j = -1;
				for(var i=0; i<trs.length; i++){
					if($(trs[i]).find('.treeTable-icon').attr('lay-tid') == tpid){
						treetable.toggleRows($('.layui-table-body .layui-table tr').eq(i).find('.treeTable-icon'));
						if($(trs[i]).find('.treeTable-icon').attr('lay-tpid') != 0){
							j=i
						}
						break;
					}
				}
				if(j != -1){
					init(j)
				}
			}
			
    	},
    	
    	toggleRows:function($dom, linkage){
    		treetable.toggleRows($dom, linkage);
    	},
    	getEmptyNum:function(pid, data){
    		treetable.getEmptyNum(pid, data);
    	},
    	checkParam:function(param){
    		treetable.checkParam(param);
    	},
    	expandAll:function(dom){
    		treetable.expandAll(dom);
    	},
    	foldAll:function(dom){
    		treetable.foldAll(dom);
    	}
    }
    
    
    exports('treetableAsync', treetableAsync);

})