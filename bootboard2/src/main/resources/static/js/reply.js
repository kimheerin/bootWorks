/**
 * 댓글 구현
 */

let replyObject = {
   
   init: function(){
      
      $("#btn-save-reply").click(() => {
         this.insertReply(); //this : 클릭한 대상
      });
   },
      
   insertReply: function(){
      alert("댓글 등록 요청됨");
      
      let boardId = $("#boardId").val();
      //document.getElementById(replyContent).value와 같음
      let replyContent = $("#replyContent").val();
      let reply = {
         content: replyContent //content : 컨트롤러로 넘겨주는 데이터
      }
      
      var header = $("meta[name='_csrf_header']").attr('content');
      var token = $("meta[name='_csrf']").attr('content');
      
      $.ajax({
         type:"POST",
         url: "/reply/" + boardId,
         beforeSend: function(xhr){
              xhr.setRequestHeader(header, token);
          },
         data:JSON.stringify(reply), //자바 스크립트 객체(reply)를 문자화(json)
         contentType: "application/json; charset=utf-8"
      }).done(function(response){
         console.log(response);
      }).fail(function(error){
         alert("에러 발생: " + error);
      });
   }
}

 replyObject.init(); //init 함수 호출