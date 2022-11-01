//funções utilizadas em outros arquivos

export function makeDataTable(json){
    const tableBody = json.map((j) => {
      return `<tr>
        <td>${j.amount}</td>
        <td>${j.date}</td>
        <td>${j.hour}</td>
        <td>${j.status}</td>
        <td>${j.duration}</td>
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
    console.log("LastFlow: ", lastFlow)

    $("#lastFlowAmount").text(lastFlow.amount + "%");
    $("#lastFlowDuration").text(lastFlow.duration);

    $("#lastFlowStatus").text("Funcionando");
    if(lastFlow.status != true) $("#lastFlowStatus").text("Tem algo errado...");
}