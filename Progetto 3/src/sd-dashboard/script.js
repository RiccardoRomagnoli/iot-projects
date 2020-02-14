$(document).ready(function(){

    const server = "https://194aa522.ngrok.io";
    const dataPickr = flatpickr("#myDataPickr", {
        mode: "range",
        dateFormat: "d-m-Y",
        onChange: [function(selectedDates){
            if(selectedDates.length == 2){
                const dateArr = selectedDates.map(date => this.formatDate(date, "Y-m-d"));
                //DATA INIZIO E FINE DA USARE PER RICAVARE DATI CON UNA POST
                let dataInizio = new Date(dateArr[0]);
                let dataFine = new Date(dateArr[1]);

                $.ajax({
                    type: "GET",
                    url: server + "/api/getdeposit"
                }).done(function (data) {
                    let dateUtili = [];
                    let grammiRifiutiA = 0;
                    let grammiRifiutiB = 0;
                    let grammiRifiutiC = 0;
                    let nDepositiA = 0;
                    let nDepositiB = 0;
                    let nDepositiC = 0;

                    for(let i=data.length - 1; i> -1; i--){
                        let dataValore = new Date(data[i].date);
                        if(dataValore >= dataInizio && dataValore <= dataFine){
                            switch(data[i].type){
                                case "A":
                                    nDepositiA++;
                                    grammiRifiutiA += data[i].weight;
                                    break;
                                case "B":
                                    nDepositiB++;
                                    grammiRifiutiB += data[i].weight;
                                    break;
                                case "C":
                                    nDepositiC++;
                                    grammiRifiutiC += data[i].weight;
                                    break;
                            }
                        }
                    }

                    let chartTypeData = [{ y: grammiRifiutiA, nDepositi: nDepositiA, indexLabel:"Grammi A"},
                                         { y: grammiRifiutiB, nDepositi: nDepositiB, indexLabel:"Grammi B"},
                                         { y: grammiRifiutiC, nDepositi: nDepositiC, indexLabel:"Grammi C"}];
                    chartType.options.data[0].dataPoints = chartTypeData;
                    chartType.render();
                });
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

        setTimeout(checkStats, 2000);
    }
    
    checkStats();

    $("#toggleStatoBidoneAttuale").change(function(){
        if(this.checked){
            $.ajax({
                type: "POST",
                url: server + "/api/setavailability",
                data: JSON.stringify({'value':true})
            });
        } else {
            $.ajax({
                type: "POST",
                url: server + "/api/setavailability",
                data: JSON.stringify({'value':false})
            });
        }
        //eventuale aggiornamento istantaneo dei valori di sopra
    });

    function compareDataPointYDescend(dataPoint1, dataPoint2) {
            return dataPoint2.x - dataPoint1.x;
    }

    var chartDate = new CanvasJS.Chart("chartDate", {
        theme: "light2",
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
    chartDate.options.data[0].dataPoints.sort(compareDataPointYDescend);
    chartDate.render();

    var chartType = new CanvasJS.Chart("chartType",
	{
		title:{
			text: "Totale rifiuti per tipo"
		},
		legend: {
			maxWidth: 350,
			itemWidth: 120
		},
		data: [
		{
			type: "pie",
            showInLegend: true,
            toolTipContent: "{y} - {nDepositi}",
			legendText: "{indexLabel}",
		}
		]
	});
});