/**
 * 2022.09.26 댓글모음
 */

console.log("reply module start");
var replyService = (function(){
    function replyinsert(reply, callback){
        console.log("reply insert...");

        $.ajax({
            type : 'POST',
            url : '/reply/insert',
            data : JSON.stringify(reply),
            contentType : "application/json; charset=UTF-8;",
            success : function(result,status,xhr){
                if(callback){
                    callback(result);
                }
            },
            erorr : function(status,xhr,er){
                if(er){
                    error(er);
                }
            }
        });
    }

    function list(param, callback ,error){
        var bno = param.bno;
        var cpage = param.cpage || 1; 
        $.getJSON("/reply/list/" + bno +"/"+cpage+".json", function(data){
            if(callback){
                callback(data.total,data.rdb);
            }
        }).fail(function(xhr,status,err){
            if(err){
                error();
            }
        });
    };

    function replydetail(rno,callback,error){
        
        $.get("/reply/"+rno+".json",function(result){
            if(callback){
                callback(result);
            }
        }).fail(function(xhr,status,err){
            if(error){
                error;
            }
        });
    };

    function replyupdate(reply,callback,error){
        console.log("댓글 번호: "+reply.seqno);
        $.ajax({
            type :  'put',
            url : "/reply/update",
            data : JSON.stringify(reply),
            contentType : "application/json; charset=UTF-8",
            success : function(result,status,xhr){
                if(callback){
                    callback(result);
                }
            },
            error : function(xhr,status,er){
                if(error){
                    error(er);
                }
            }
        });
    };

    function replydelete(no,callback,error){
        $.ajax({
            type : 'delete',
            url : "/reply/delete",
            data : JSON.stringify(no),
            contentType : "application/json; charset=utf-8",
            success : function(result,status,xhr){
                if(callback){
                    callback(result);
                }
            },
            error : function(xhr,status,er){
                if(error){
                    error(er);
                }
            }
        });
    };
    return {add:replyinsert,list:list,replydetail:replydetail,replyupdate:replyupdate,replydelete:replydelete};
})();



