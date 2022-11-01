import {makeDataTable} from "/js/index.js"
import {removeContentDataTable} from "/js/index.js"
import {activateLoader} from "/js/index.js"
import {makeLastFlow} from "/js/index.js"
import {makeAmountAverage} from "/js/index.js"


var stompClient = null;
function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/flowList', function(_json) {
            var json = JSON.parse(_json.body)[0];
            removeContentDataTable(function(){
                makeDataTable(json);
            })
        });
        stompClient.subscribe('/topic/realTimeFlow', function(json){
            makeLastFlow(json)
        });
        stompClient.subscribe('/topic/amountAverage', function(json){
            makeAmountAverage(json);
        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
async function saveRealTimeFlow(){
    await stompClient.send("/app/saveRealTimeFlow", {}, getIdSensorActive());
}

async function getRealTimeFlowBySensor(){
    await stompClient.send("/app/getRealTimeFlow", {}, getIdSensorActive());
    getAmountAverageBySensor();
}

async function getFlowListBySensor(callback) {
    await stompClient.send("/app/getFlowListBySensor", {}, getIdSensorActive());
}

function getAmountAverageBySensor(){
    stompClient.send("/app/getAmountAverage", {}, getIdSensorActive());
};

function getIdSensorActive(){
   return $(".carousel-item.active").attr("id").split("-")[2];
}

$(document).ready(()=>{
    connect();
    setTimeout(function(){
        refreshPage();
    }, 2000);
})

function refreshPage(){
    setInterval(() => {
        getFlowListBySensor();
        getRealTimeFlowBySensor();
    }, 3000);
    setInterval(() => {
        saveRealTimeFlow();
    }, 10000);
}



