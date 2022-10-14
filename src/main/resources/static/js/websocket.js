var stompClient = null;
function connect() {
    var socket = new SockJS('/webtest');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {

        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/public', function(retorno) {

            var json = JSON.parse(retorno.body)[0];
            console.log(json);
            removeContentDataTable(function(){
                makeDataTable(json);
            })

        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendSensorId() {
    activateLoader();
    stompClient.send("/app/test", {}, 1);
}

$(document).ready(()=>{
    connect()
})

$("#refreshValues").on("click", ()=>{
    sendSensorId();
})

function makeDataTable(json){
    const tableBody = json.map((j) => {
      return `<tr>
        <td>${j.amount}</td>
        <td>${j.date}</td>
        <td>${j.hour}</td>
        <td>${j.status}</td>
        <td>${j.duration}</td>
       </tr>
      `
    }).join('')
        $("#dataTableTBody").append(tableBody);
        console.log("inserido")
        activateLoader()
    }

function removeContentDataTable(callback){
    $("#dataTableTBody").empty();
    console.log("limpo")
    callback();
}


function activateLoader(){
    console.log("loader")
    if($("#loader").is( ":visible" )){
        $("#loader").hide();
        $("body").css('opacity', '1')
    }
    else{
        $("#loader").show();
        $(".imgLoader").css("position", "absolute")
        $("body").css("opacity", "0.4")
        $("body").css("background", "black")
    }
}

