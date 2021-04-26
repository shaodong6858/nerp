layui.define(['layer', 'table'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var table = layui.table;
    
    function tableInit(param,data,doneCallback){
    	var mData = [],tNodes = [];
    	var tNodes = data;
        // 补上id和pid字段
        for (var i = 0; i < tNodes.length; i++) {
            var tt = tNodes[i];
            if (!tt.id) {
                if (!param.treeIdName) {
                    layer.msg('参数treeIdName不能为空', {icon: 5});
                    return;
                }
                tt.id = tt[param.treeIdName];
            }
            if (!tt.pid) {
                if (!param.treePidName) {
                    layer.msg('参数treePidName不能为空', {icon: 5});
                    return;
                }
                tt.pid = tt[param.treePidName];
            }
        }

        // 对数据进行排序
        var sort = function (s_pid, data) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].pid == s_pid) {
                    var len = mData.length;
                    if (len > 0 && mData[len - 1].id == s_pid) {
                        mData[len - 1].isParent = true;
                    }
                    mData.push(data[i]);
                    sort(data[i].id, data);
                }
            }
        };
        sort(param.treeSpid, tNodes);
        //console.log('data',tNodes)

        // 重写参数
        param.url = undefined;
        param.data = mData;
        param.page = {
            count: param.data.length,
            limit: param.data.length
        };
        param.cols[0][param.treeColIndex].templet = function (d) {
            var mId = d.id;
            var mPid = d.pid;
            var isDir = d.isParent;
            var emptyNum = treetable.getEmptyNum(mPid, mData);
            var iconHtml = '';
            for (var i = 0; i < emptyNum; i++) {
                iconHtml += '<span class="treeTable-empty"></span><span class="treeTable-empty"></span>';
            }
            if (isDir) {
                iconHtml += '<i class="layui-icon layui-icon-triangle-d"></i> <i class="layui-icon layui-icon-layer"></i>';
            } else {
                iconHtml += '<i class="layui-icon layui-icon-file"></i>';
            }
            iconHtml += '&nbsp;&nbsp;';
            var ttype = isDir ? 'dir' : 'file';
            var vg = '<span class="treeTable-icon open" lay-tid="' + mId + '" lay-tpid="' + mPid + '" lay-ttype="' + ttype + '">';
            return vg + iconHtml + d[param.cols[0][param.treeColIndex].field] + '</span>'
        };

        param.done = function (res, curr, count) {
            $(param.elem).next().addClass('treeTable');
            $('.treeTable .layui-table-page').css('display', 'none');
            $('.treeTable .treeTable-icon').click(function () {
                treetable.toggleRows($(this), param.treeLinkage);
            });
            if (param.treeDefaultClose) {
                treetable.foldAll(param.elem);
            }
            if (doneCallback) {
                doneCallback(res, curr, count);
            }
        };

        // 渲染表格
        table.render(param);
    }

    var treetable = {
        // 渲染树形表格
        render: function (param) {
            // 检查参数
            if (!treetable.checkParam(param)) {
                return;
            }
            var doneCallback = param.done;
            // 获取数据
//          if(param.url){
//          	$.getJSON(param.url, param.where, function (res) {
//	                tableInit(param,res.data,doneCallback);
//	            });
//          }else if(param.data){
            	tableInit(param,param.data,doneCallback);
//          }
            
        },
        // 计算缩进的数量
        getEmptyNum: function (pid, data) {
            var num = 0;
            if (!pid) {
                return num;
            }
            var tPid;
            for (var i = 0; i < data.length; i++) {
                if (pid == data[i].id) {
                    num += 1;
                    tPid = data[i].pid;
                    break;
                }
            }
            return num + treetable.getEmptyNum(tPid, data);
        },
        // 展开/折叠行
        toggleRows: function ($dom, linkage) {
            var type = $dom.attr('lay-ttype');
            if ('file' == type) {
                return;
            }
            var mId = $dom.attr('lay-tid');
            var isOpen = $dom.hasClass('open');
            if (isOpen) {
                $dom.removeClass('open');
            } else {
                $dom.addClass('open');
            }
            $dom.closest('tbody').find('tr').each(function () {
                var $ti = $(this).find('.treeTable-icon');
                var pid = $ti.attr('lay-tpid');
                var ttype = $ti.attr('lay-ttype');
                var tOpen = $ti.hasClass('open');
                if (mId == pid) {
                    if (isOpen) {
                        $(this).hide();
                        if ('dir' == ttype && tOpen == isOpen) {
                            $ti.trigger('click');
                        }
                    } else {
                        $(this).show();
                        if (linkage && 'dir' == ttype && tOpen == isOpen) {
                            $ti.trigger('click');
                        }
                    }
                }
            });
        },
        // 检查参数
        checkParam: function (param) {
            if (!param.treeSpid) {
                layer.msg('参数treeSpid不能为空', {icon: 5});
                return false;
            }

            if (!param.treeColIndex) {
                layer.msg('参数treeColIndex不能为空', {icon: 5});
                return false;
            }
            return true;
        },
        // 展开所有
        expandAll: function (dom) {
            $(dom).next('.treeTable').find('.layui-table-body tbody tr').each(function () {
                var $ti = $(this).find('.treeTable-icon');
                var ttype = $ti.attr('lay-ttype');
                var tOpen = $ti.hasClass('open');
                if ('dir' == ttype && !tOpen) {
                    $ti.trigger('click');
                }
            });
        },
        // 折叠所有
        foldAll: function (dom) {
            $(dom).next('.treeTable').find('.layui-table-body tbody tr').each(function () {
                var $ti = $(this).find('.treeTable-icon');
                var ttype = $ti.attr('lay-ttype');
                var tOpen = $ti.hasClass('open');
                if ('dir' == ttype && tOpen) {
                    $ti.trigger('click');
                }
            });
        }
    };


    exports('treetable', treetable);
});