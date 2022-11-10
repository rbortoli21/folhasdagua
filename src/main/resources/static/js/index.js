//funções utilizadas em outros arquivos

export function makeCarouselList(json){
    var sensorList = JSON.parse(json.body);
    console.log(sensorList)
    $(".carousel-item.active").attr("id", `sensor-item-${sensorList[0].id}`);
    sensorList.forEach((sensor) => {
        var carouselItem = `<div id="sensor-id-${sensor.id}" class="carousel-item">

        </div>
        `
        $("#sensorList").append(carouselItem);
    }   );
}

export function makeDataTable(json){
    const tableBody = json.map((j) => {
      return `<tr>
        <td>${j.amount}%</td>
        <td>${j.date}</td>
        <td>${j.hour}</td>
        <td>${j.status ? 'Funcionando' : 'Algo não funcionou...'}</td>
       </tr>
      `
    }).join('');
    $("#dataTableTBody").append(tableBody);
    console.log("inserido");
}

export function removeContentDataTable(callback){
    $("#dataTableTBody").empty();
    console.log("limpo")
    callback();
}

export function activateLoader(){
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

export function makeAmountAverage(json){
    var average = JSON.parse(json.body).toFixed(2);
    $("#flowsAverage").text(average);
    return average;
}

export function makeLastFlow(json){
    var lastFlow = JSON.parse(json.body);

    $("#statusLabel").removeClass("text-danger").addClass("text-success");
    $("#amountLabel").removeClass("text-danger").addClass("text-success");

    $("#lastFlowAmount").text(lastFlow.amount + "%");
    $("#lastFlowStatus").text("Funcionando");
//    $("#lastFlowDuration").text(lastFlow.duration);


    if(!lastFlow.status){
        $("#lastFlowStatus").text("Tem algo errado...");
        $("#statusLabel").removeClass("text-success").addClass("text-danger")
        $("#amountLabel").removeClass("text-success").addClass("text-danger");
    }
}

