$(document).ready(function(){

    const server = "https://194aa522.ngrok.io";
    const dataPickr = flatpickr("#myDataPickr", {
        mode: "range",
        dateFormat: "d-m-Y",
        onChange: [function(selectedDates){
            if(selectedDates.length == 2){
                const dateArr = selectedDates.map(date => this.formatDate(date, "d-m-Y"));
                //DATA INIZIO E FINE DA USARE PER RICAVARE DATI CON UNA POST
            }
        }]
    });

    function checkStats(){
        $.ajax({
            type: "GET",
            url: server + "/api/ndeposit"
        }).done(function (data) {
            $("#numeroDepositiAttuale").val(data[0].nDeposit);
            $("#quantitaAttuale").val(data[0].weight);
        });

        $.ajax({
            type: "GET",
            url: server + "/api/isAvailable"
        }).done(function (data) {
            $("#statoBidoneAttuale").val(data[0].value ? "Disponibile" : "Non disponibile");
            $("#toggleStatoBidoneAttuale").prop("checked", data[0].value ? true : false);
        });

        setTimeout(checkStats, 5000);
    }
    
    checkStats();

    $("#toggleStatoBidoneAttuale").change(function(){
        if(this.checked){
            $.ajax({
                type: "POST",
                url: server + "/api/setavailability",
                data: {"value" : true}
            });
        } else {
            $.ajax({
                type: "POST",
                url: server + "/api/setavailability",
                data: {"value" : true}
            });
        }
        //eventuale aggiornamento istantaneo dei valori di sopra
    });

    function compareDataPointYAscend(dataPoint1, dataPoint2) {
		return dataPoint1.x - dataPoint2.x;
    }

    function compareDataPointYDescend(dataPoint1, dataPoint2) {
            return dataPoint2.x - dataPoint1.x;
    }

    var chart = new CanvasJS.Chart("chartContainer", {
        theme: "light2", // "light1", "light2", "dark1", "dark2"
        animationEnabled: true,
        title:{
            text: "Deposito totale giornalieri"   
        },
        axisX: {

        },
        axisY:{
            title: "Depositi giornalieri",
            valueFormatString: "$#0"
        },
        data: [{        
            type: "line",
            markerSize: 12,
            xValueFormatString: "MMM, YYYY",
            yValueFormatString: "##.## Kg",
            dataPoints: [        
                { x: new Date(2016, 00, 1), y: 61, indexLabel: "gain", markerType: "triangle",  markerColor: "#6B8E23" },
                { x: new Date(2016, 01, 10), y: 71, indexLabel: "gain", markerType: "triangle",  markerColor: "#6B8E23" },
                { x: new Date(2016, 05, 31) , y: 55, indexLabel: "loss", markerType: "cross", markerColor: "tomato" },
                { x: new Date(2016, 03, 21) , y: 50, indexLabel: "loss", markerType: "cross", markerColor: "tomato" },
                { x: new Date(2016, 07, 11) , y: 65, indexLabel: "gain", markerType: "triangle", markerColor: "#6B8E23" },
                { x: new Date(2016, 05, 12) , y: 85, indexLabel: "gain", markerType: "triangle", markerColor: "#6B8E23" },
                { x: new Date(2016, 04, 31) , y: 68, indexLabel: "loss", markerType: "cross", markerColor: "tomato" },
                { x: new Date(2016, 03, 11) , y: 28, indexLabel: "loss", markerType: "cross", markerColor: "tomato" },
                { x: new Date(2016, 06, 19) , y: 34, indexLabel: "gain", markerType: "triangle", markerColor: "#6B8E23" },
                { x: new Date(2016, 08, 16) , y: 24, indexLabel: "loss", markerType: "cross", markerColor: "tomato" },
                { x: new Date(2016, 10, 14) , y: 44, indexLabel: "gain", markerType: "triangle", markerColor: "#6B8E23" },
                { x: new Date(2016, 11, 21) , y: 34, indexLabel: "loss", markerType: "cross", markerColor: "tomato" }
            ]
        }]
    });
    chart.options.data[0].dataPoints.sort(compareDataPointYDescend);
    chart.render();

});