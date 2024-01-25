/**
 * 
 */
 //날짜를 변수화
     const date= new Date();
     console.log(date)
     let year =date.getFullYear();  //2024
     let month = '0' + date.getMonth()+1; //1
     month = month.substring(1)  //substring(인덱스)인덱스부터 끝까지 추출
     let day ='0' +date.getDate(); // 025
     day = day.substring(1);

     let today = year + month + day
    
    
      $.ajax({
        type:"get",
        url:"https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=d8fHYrIFHEOMpYMh0Gv5butlqRpXqQKO3olZsCfxk1WlizFI%2Fndgt%2FAb1nARrYInaCKov50Fx2EjywdA43MPOA%3D%3D&pageNo=1&numOfRows=1000&dataType=json&base_date=" + today + "&base_time=0600&nx=55&ny=127",
    
        success:function(data){
          console.log(data);
          console.log(data.response.body.items.item[3].obsrValue);
          let item = data.response.body.items.item[3];
          let content = "날짜:" + item.baseDate +
          ", 발표시각: " + item.baseTime +
          ", 기온: " + item.obsrValue

          
          $('.result').text(content);
        },
        error:function(error){

        }
        
      });