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
                    //dati line chart
                    let dataCorrente = dataInizio;
                    let pesoCorrente = 0;
                    let nDepositiCorrente = 0;
                    let chartDateData = [];

                    //dati pie chart
                    let grammiRifiutiA = 0;
                    let grammiRifiutiB = 0;
                    let grammiRifiutiC = 0;
                    let nDepositiA = 0;
                    let nDepositiB = 0;
                    let nDepositiC = 0;

                    //ricavazione dati dalla get
                    for(let i=data.length - 1; i> -1; i--){
                        let dataValore = new Date(data[i].date);
                        let pesoValore = data[i].weight;
                        if(dataValore >= dataInizio && dataValore <= dataFine){
                            //parte relativa alla line chart
                            if(dataCorrente.getTime() === dataValore.getTime()){
                                pesoCorrente += pesoValore;
                                nDepositiCorrente++;
                            } else {
                                if(pesoCorrente != 0){
                                    chartDateData.push({x: dataCorrente, y: pesoCorrente, nDepositi: nDepositiCorrente});
                                } 
                                dataCorrente = dataValore;
                                pesoCorrente = pesoValore
                                nDepositiCorrente = 1;
                            }
                            //parte relativa alla pie chart
                            switch(data[i].type){
                                case "A":
                                    nDepositiA++;
                                    grammiRifiutiA += pesoValore;
                                    break;
                                case "B":
                                    nDepositiB++;
                                    grammiRifiutiB += pesoValore;
                                    break;
                                case "C":
                                    nDepositiC++;
                                    grammiRifiutiC += pesoValore;
                                    break;
                            }
                        }
                    }
                    if(pesoCorrente != 0){
                        chartDateData.push({x: dataCorrente, y: pesoCorrente,  nDepositi: nDepositiCorrente});
                    }
                    chartDate.options.data[0].dataPoints = chartDateData;
                    chartDate.render();

                    //riempimento pie chart
                    let chartTypeData = [];
                    if(nDepositiA > 0){
                        chartTypeData.push({ y: grammiRifiutiA, nDepositi: nDepositiA, indexLabel:"Grammi A"});
                    }
                    if(nDepositiB > 0){
                        chartTypeData.push({ y: grammiRifiutiB, nDepositi: nDepositiB, indexLabel:"Grammi B"});
                    }
                    if(nDepositiC > 0){
                        chartTypeData.push({ y: grammiRifiutiC, nDepositi: nDepositiC, indexLabel:"Grammi C"});
                    }
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
            $("#statoBidoneAttuale").val(data[0].value == "true" ? "Disponibile" : "Non disponibile");
            $("#toggleStatoBidoneAttuale").prop("checked", data[0].value == "true" ? true : false);
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

    var chartDate = new CanvasJS.Chart("chartDate", {
        theme: "light2",
        animationEnabled: true,
        title:{
            text: "Deposito totale giornalieri"   
        },
        axisX: {
            title: "Date dei depositi",
            valueFormatString: "DD MMM"
        },
        axisY:{
            title: "Grammi giornalieri",
            suffix: "g",
        },
        legend:{
            cursor:"pointer",
            verticalAlign: "bottom",
            horizontalAlign: "left",
            dockInsidePlotArea: true,
        },
        data: [{        
            type: "line",
            showInLegend: true,
            name: "Quantità giornaliere di rifiuti",
            markerType: "square",
            xValueFormatString: "DD MMM, YYYY",
            color: "#F08080",
            yValueFormatString: "####g",
            toolTipContent: "{x} - {y} - {nDepositi}"
        }]
    });

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